/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.inhouse.gface.model.util.CsvParser;
import com.unicomer.inhouse.gface.model.util.GfaceFileUtils;
import com.unicomer.inhouse.gface.model.util.GfaceFormatUtilities;
import com.unicomer.inhouse.gface.model.util.ZipUtilities;
import com.unicomer.opos.inhouse.gface.ejb.ControlErroresEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceBatchControlEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceBtchFilesEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceGuatefacturaFileReaderEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStatusControlEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceBtchFiles;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.CaePojo;
import com.unicomer.opos.inhouse.gface.pojo.ErroresPojo;
import com.unicomer.opos.inhouse.gface.pojo.GfaceConstants;
import com.unicomer.opos.inhouse.gface.pojo.ResultadoValidacionPojo;
import com.unicomer.opos.inhouse.gface.util.validations.GfaceGuatefacturaValidationsEjbLocal;
import com.unicomer.opos.inhouse.gface.util.validations.GfaceInternalValidationsEjbLocal;
import com.unicomer.opos.inhouse.security.entities.UnUser;

/**
 *
 * @author francisco_medina
 */
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceGuatefacturaFileReaderEjbLocalImpl", mappedName = "ejb/GfaceGuatefacturaFileReaderEjbLocalImpl")
@Remote(GfaceGuatefacturaFileReaderEjbLocal.class)
public class GfaceGuatefacturaFileReaderEjbLocalImpl implements GfaceGuatefacturaFileReaderEjbLocal {

	@Resource(lookup = JNDIUnicomerGface.ControlErroresEjbLocal) 
    private ControlErroresEjbLocal ce;
	@Resource(lookup = JNDIUnicomerGface.GfaceStatusControlEjbLocal)
	private GfaceStatusControlEjbLocal sc;
	@Resource(lookup = JNDIUnicomerGface.GfaceBatchControlEjbLocal)
	private GfaceBatchControlEjbLocal bc;
	@Resource(lookup = JNDIUnicomerGface.GfaceGuatefacturaValidationsEjbLocal)
	private GfaceGuatefacturaValidationsEjbLocal gv; 
	@Resource(lookup = JNDIUnicomerGface.GfaceInternalValidationsEjbLocal)
	private GfaceInternalValidationsEjbLocal iv;
	@Resource(lookup = JNDIUnicomerGface.GfaceBtchFilesEjbLocal)
	private GfaceBtchFilesEjbLocal gfaceBtchFilesEjbLocal;
	

    public void readAllFiles() {
    	//lo pone en blanco cuando es ejecutado por scheduler
    	/*UnUser userInSession=null;  	
    	List<GfaceHdrDoc> ee = sc.getEnviadasExitosamente();
    	if(ee!=null&&!ee.isEmpty()){
    		procesarArchivosValidadosGuatefacturas(ee,userInSession);
    	}
    	*/
    }

    public void readFilesOK() {
    }

    public void readFilesErr() {
    }

    //lee los registros, obtenidos desde la BD
    public HashMap<Boolean, List<GfaceHdrDoc>> leerRegistros2(List<GfaceHdrDoc> registros){
    	HashMap<Boolean, List<GfaceHdrDoc>> ret = new HashMap<Boolean, List<GfaceHdrDoc>>();
    	ZipUtilities zu = new ZipUtilities();
    	ret.put(Boolean.TRUE, new ArrayList<GfaceHdrDoc>());//registros buenos
        ret.put(Boolean.FALSE, new ArrayList<GfaceHdrDoc>());//registros malos
        HashMap<String, List<GfaceHdrDoc>> hpb = getHeaderPorBatch(registros);
        HashMap<String, String> archivos = new HashMap<String, String>();
        HashMap<String, String> batch = new HashMap<String, String>();
        List<String> archivosListos = new ArrayList<String>();
        List<String> archivosOk = new ArrayList<String>();
        List<String> batchListos = new ArrayList<String>();
        StringBuffer zipFile, zipFileOk;
        StringBuffer zipUncompressFile = new StringBuffer();
        
        for (String actual : hpb.keySet()) {
            zipFile = new StringBuffer();
            zipFileOk = new StringBuffer();
            //crea el nombre del archivo que contiene la informacion
            zipFile.append(actual);
            zipFile.append(GfaceFormatUtilities.ZIP_EXTENCION);

            //crea el nombre del archivo de confirmacion
            zipFileOk.append(actual);
            zipFileOk.append(GfaceConstants.OK_FILE_END);
            zipFileOk.append(GfaceFormatUtilities.ZIP_EXTENCION);
            archivosOk.add(zipFileOk.toString());

            //los agrega para busqueda eficiente
            archivos.put(zipFileOk.toString(), zipFile.toString());
            batch.put(zipFileOk.toString(), actual);
        }
        
        List<String> archivosProcesados = bc.getFilesExists(archivosOk,GfaceConstants.OK_FILE_END.concat(GfaceFormatUtilities.ZIP_EXTENCION));//busca los archivos en la ruta del servidor externo y las pone en el servidor local
        
        for (String actual : archivosProcesados) {
            //a partir del archivo de confirmacion obtiene el archivo con la informacion
            archivosListos.add(archivos.get(actual));
            batchListos.add(batch.get(actual));
        }
        
        HashMap<Boolean, List<String>> archSeparados = buscarBatchErroresYBuenos2(hpb.keySet());
        List<String> buenos = archSeparados.get(Boolean.TRUE);
        List<String> malos = archSeparados.get(Boolean.FALSE);
        if (!buenos.isEmpty()) {
            //procesar archivos correctos
            List<GfaceHdrDoc> archOK = getConfirmadasParaActualizar2(buenos, hpb);
            ret.get(Boolean.TRUE).addAll(archOK);
        }
        if (!malos.isEmpty()) {
            //procesar archivos con errores
            List<GfaceHdrDoc> archERR = getErroresParaActualizar2(malos, hpb);
            ret.get(Boolean.FALSE).addAll(archERR);
        }
    	return ret;
    }
    public HashMap<Boolean, List<GfaceHdrDoc>> leerRegistros(List<GfaceHdrDoc> registros) {
        GfaceGuatefacturasControlFtpEjbLocalImpl cftp = new GfaceGuatefacturasControlFtpEjbLocalImpl();
        ZipUtilities zu = new ZipUtilities();
        HashMap<Boolean, List<GfaceHdrDoc>> ret = new HashMap<Boolean, List<GfaceHdrDoc>>();
        ret.put(Boolean.TRUE, new ArrayList<GfaceHdrDoc>());//registros buenos
        ret.put(Boolean.FALSE, new ArrayList<GfaceHdrDoc>());//registros malos
        HashMap<String, List<GfaceHdrDoc>> hpb = getHeaderPorBatch(registros);
        HashMap<String, String> archivos = new HashMap<String, String>();
        HashMap<String, String> batch = new HashMap<String, String>();
        List<String> archivosListos = new ArrayList<String>();
        List<String> archivosOk = new ArrayList<String>();
        List<String> batchListos = new ArrayList<String>();
        StringBuffer zipFile, zipFileOk;
        StringBuffer zipUncompressFile = new StringBuffer();
        //establece las variables por defecto para FTP
        cftp.setFtpParams(cftp.getFtpParamsDefault());
        String directorioBase = cftp.getFtpParams().get("localStoreFiles");
        for (String actual : hpb.keySet()) {
            zipFile = new StringBuffer();
            zipFileOk = new StringBuffer();
            //crea el nombre del archivo que contiene la informacion
            zipFile.append(actual);
            zipFile.append(GfaceFormatUtilities.ZIP_EXTENCION);

            //crea el nombre del archivo de confirmacion
            zipFileOk.append(actual);
            zipFileOk.append(GfaceConstants.OK_FILE_END);
            zipFileOk.append(GfaceFormatUtilities.ZIP_EXTENCION);
            archivosOk.add(zipFileOk.toString());

            //los agrega para busqueda eficiente
            archivos.put(zipFileOk.toString(), zipFile.toString());
            batch.put(zipFileOk.toString(), actual);
        }
        List<String> archivosProcesados = cftp.getFilesExists(archivosOk);//busca los archivos en la ruta del servidor externo y las pone en el servidor local
        for (String actual : archivosProcesados) {
            //a partir del archivo de confirmacion obtiene el archivo con la informacion
            archivosListos.add(archivos.get(actual));
            batchListos.add(batch.get(actual));
        }
        cftp.receive(archivosListos);//busca los archivos en la ruta del servidor externo y las pone en el servidor local

        for (String actual : hpb.keySet()) {
            if (batchListos.contains(actual)) {
                zipFile = new StringBuffer();
                zipFile.append(actual);
                zipFile.append(GfaceFormatUtilities.ZIP_EXTENCION);
                zipUncompressFile.append(directorioBase);
                zipUncompressFile.append(File.separator);
                zipUncompressFile.append(actual);
                //descomprime el archivo en una carpeta con el nombre del archivo zip
                zu.descomprimir(directorioBase, zipUncompressFile.toString(), zipFile.toString());
            }
        }
        HashMap<Boolean, List<String>> archSeparados = buscarBatchErroresYBuenos(hpb.keySet(), directorioBase);
        List<String> buenos = archSeparados.get(Boolean.TRUE);
        List<String> malos = archSeparados.get(Boolean.FALSE);
        if (!buenos.isEmpty()) {
            //procesar archivos correctos
            List<GfaceHdrDoc> archOK = getConfirmadasParaActualizar(buenos, hpb, directorioBase);
            ret.get(Boolean.TRUE).addAll(archOK);
        }
        if (!malos.isEmpty()) {
            //procesar archivos con errores
            List<GfaceHdrDoc> archERR = getErroresParaActualizar(malos, hpb, directorioBase);
            ret.get(Boolean.FALSE).addAll(archERR);
        }
        return ret;
    }

    //buscar errores y buenos
    private HashMap<Boolean, List<String>> buscarBatchErroresYBuenos(Set<String> batchId, String directorio) {
        StringBuffer nombreArchivo, nombreArchivoOK;
        HashMap<Boolean, List<String>> ret = new HashMap<Boolean, List<String>>();
        ret.put(Boolean.TRUE, new ArrayList<String>());//listado de datos buenos
        ret.put(Boolean.FALSE, new ArrayList<String>());//listado de datos malos

        for (String actual : batchId) {
            nombreArchivo = new StringBuffer();
            nombreArchivo.append(directorio);
            nombreArchivo.append(File.separator);
            nombreArchivo.append(actual);//agrega el nombre de la carpeta del archivo para buscar
            nombreArchivo.append(File.separator);
            nombreArchivo.append(actual);
            nombreArchivo.append(GfaceConstants.ERROR_FILE_END);
            nombreArchivo.append(GfaceFormatUtilities.TXT_EXTENCION);

            nombreArchivoOK = new StringBuffer();
            nombreArchivoOK.append(directorio);
            nombreArchivoOK.append(File.separator);
            nombreArchivoOK.append(actual);//agrega el nombre de la carpeta del archivo para buscar
            nombreArchivoOK.append(File.separator);
            nombreArchivoOK.append(actual);
            nombreArchivoOK.append(GfaceFormatUtilities.TXT_EXTENCION);

            if (new File(nombreArchivo.toString()).exists()) {
                ret.get(Boolean.FALSE).add(actual);
            }

            if (new File(nombreArchivoOK.toString()).exists()) {
                ret.get(Boolean.TRUE).add(actual);
            }
        }
        return ret;
    }
    
  //buscar errores y buenos, obtenidos desde la BD
    private HashMap<Boolean, List<String>> buscarBatchErroresYBuenos2(Set<String> batchId) {
		ZipUtilities zu = new ZipUtilities();
		StringBuffer nombreArchivo, nombreArchivoOK;
		HashMap<Boolean, List<String>> ret = new HashMap<Boolean, List<String>>();
		ret.put(Boolean.TRUE, new ArrayList<String>());// listado de datos
														// buenos
		ret.put(Boolean.FALSE, new ArrayList<String>());// listado de datos
														// malos

		for (String actual : batchId) {
			nombreArchivo = new StringBuffer();
			// nombreArchivo.append(directorio);
			// nombreArchivo.append(File.separator);
			// nombreArchivo.append(actual);//agrega el nombre de la carpeta del
			// archivo para buscar
			// nombreArchivo.append(File.separator);
			nombreArchivo.append(actual);
			nombreArchivo.append(GfaceConstants.ERROR_FILE_END);
			nombreArchivo.append(GfaceFormatUtilities.TXT_EXTENCION);

			nombreArchivoOK = new StringBuffer();
			// nombreArchivoOK.append(directorio);
			// nombreArchivoOK.append(File.separator);
			// nombreArchivoOK.append(actual);//agrega el nombre de la carpeta
			// del archivo para buscar
			// nombreArchivoOK.append(File.separator);
			nombreArchivoOK.append(actual);
			nombreArchivoOK.append(GfaceFormatUtilities.TXT_EXTENCION);

			// obtener el inputstream desde la BD
			GfaceBtchFiles batch = gfaceBtchFilesEjbLocal.find(actual);

			List<String> nombresZip=zu.getNombresArchivo(batch.getFileReceived());
			if (nombresZip.contains(nombreArchivo.toString())) {
				ret.get(Boolean.FALSE).add(actual);
			}
			if (nombresZip.contains(nombreArchivoOK.toString())) {
				ret.get(Boolean.TRUE).add(actual);
			}
		}
        return ret;
    }

    //buscar y establecer errores
    //buscar confirmadas
    private List<GfaceHdrDoc> getConfirmadasParaActualizar(List<String> buenas, HashMap<String, List<GfaceHdrDoc>> registros, String directorio) {
        CsvParser cp = new CsvParser();
        StringBuffer nombreArchivo;
        StringBuffer rootFile;
        File archivo;
        CaePojo pojo;
        HashMap<String, GfaceHdrDoc> docs;
        StringBuffer llave;
        StringBuffer llave2;
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();
        List<File> filesToDelete = new ArrayList<File>();
        GfaceFileUtils fu = new GfaceFileUtils();
//        Set<String> buenas = registros.keySet();

        for (String actual : buenas) {
            nombreArchivo = new StringBuffer();
            rootFile = new StringBuffer();
            rootFile.append(directorio);
            rootFile.append(File.separator);
            rootFile.append(actual);//agrega el nombre de la carpeta del archivo para buscar
            nombreArchivo.append(rootFile);
            nombreArchivo.append(File.separator);
            nombreArchivo.append(actual);
            nombreArchivo.append(GfaceFormatUtilities.TXT_EXTENCION);

//            filesToDelete.add(new File(rootFile.toString()));
            docs = new HashMap<String, GfaceHdrDoc>();
            archivo = new File(nombreArchivo.toString());
            filesToDelete.add(new File(nombreArchivo.toString()));
            try {
                FileReader fr = new FileReader(archivo);
                BufferedReader br = new BufferedReader(fr);
                String linea;
                //crea un mapa para busquedas directas por llave unica
                for (GfaceHdrDoc factura : registros.get(actual)) {
                    llave = new StringBuffer();
                    llave.append(factura.getDocType());
                    llave.append("-");
                    llave.append(factura.getSerieNum());
                    llave.append("-");
                    llave.append(factura.getCorrNum());
                    docs.put(llave.toString(), factura);
                }
                while ((linea = br.readLine()) != null) {
                    pojo = (CaePojo) cp.parseCsvLineToObject(linea, CaePojo.class, GfaceConstants.FIELD_SEPARATOR);
                    llave2 = new StringBuffer();
                    llave2.append(pojo.getDocType());
                    llave2.append("-");
                    llave2.append(pojo.getSerieNum());
                    llave2.append("-");
                    llave2.append(pojo.getPreprint());
                    GfaceHdrDoc docActualizar = docs.get(llave2.toString());
                    if (docActualizar != null) {
                        //actualiza la firma electronica
                        docActualizar.getSttRgsId().setAuthCode(pojo.getSign());
                        //actualiza 
                        ret.add(docActualizar);
                    } else {
                        System.err.println("Error inesperado" + llave2 + "," + actual);
                    }
                }
                br.close();
                fr.close();
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
            //eliminar archivos procesados
            fu.deleteFiles(filesToDelete);

        }
        return ret;
    }
    
    //buscar confirmadas, obtenidos desde la BD
    private List<GfaceHdrDoc> getConfirmadasParaActualizar2(List<String> buenas, HashMap<String, List<GfaceHdrDoc>> registros) {
    	ZipUtilities zu = new ZipUtilities();
    	CsvParser cp = new CsvParser();
        StringBuffer nombreArchivo;
        CaePojo pojo;
        HashMap<String, GfaceHdrDoc> docs;
        StringBuffer llave;
        StringBuffer llave2;
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();

        for (String actual : buenas) {
            nombreArchivo = new StringBuffer();
            nombreArchivo.append(actual);
            nombreArchivo.append(GfaceFormatUtilities.TXT_EXTENCION);

            //busca el valor byte64 almacenado en la base de datos
            GfaceBtchFiles batch = gfaceBtchFilesEjbLocal.find(actual);
            //Obtener el la informacion del archivo ya descomprimido
            String data=zu.getDataArchivo(batch.getFileReceived(), nombreArchivo.toString());
            
            docs = new HashMap<String, GfaceHdrDoc>();
            try {
                String[] lineas=data.split("\n");
                //crea un mapa para busquedas directas por llave unica
                for (GfaceHdrDoc factura : registros.get(actual)) {
                    llave = new StringBuffer();
                    llave.append(factura.getDocType());
                    llave.append("-");
                    llave.append(factura.getSerieNum());
                    llave.append("-");
                    llave.append(factura.getCorrNum());
                    docs.put(llave.toString(), factura);
                }
                for(String linea:lineas){
                	pojo = (CaePojo) cp.parseCsvLineToObject(linea, CaePojo.class, GfaceConstants.FIELD_SEPARATOR);
                    llave2 = new StringBuffer();
                    llave2.append(pojo.getDocType());
                    llave2.append("-");
                    llave2.append(pojo.getSerieNum());
                    llave2.append("-");
                    llave2.append(pojo.getPreprint()); 
                    GfaceHdrDoc docActualizar = docs.get(llave2.toString());
                    if (docActualizar != null) {
                        //actualiza la firma electronica
                        docActualizar.getSttRgsId().setAuthCode(pojo.getSign());
                        //actualiza 
                        ret.add(docActualizar);
                    } else {
                        System.err.println("Error inesperado" + llave2 + "," + actual);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
            //eliminar archivos procesados y validados
            //no lo puede eliminar xq no se si tiene errores
//            batch.setFileReceived(null);
//            gfaceBtchFilesEjbLocal.edit(batch);

        }
        return ret;
    }

    //buscar y actualizar errores
    private List<GfaceHdrDoc> getErroresParaActualizar(List<String> errores, HashMap<String, List<GfaceHdrDoc>> registros, String directorio) {
        CsvParser cp = new CsvParser();
        StringBuffer nombreArchivo;
        StringBuffer rootFile;
        File archivo;
        ErroresPojo pojo;
        HashMap<String, GfaceHdrDoc> docs;
        StringBuffer llave;
        StringBuffer llave2;
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();
        List<File> filesToDelete = new ArrayList<File>();
        GfaceFileUtils fu = new GfaceFileUtils();

        for (String actual : errores) {
            nombreArchivo = new StringBuffer();
            rootFile = new StringBuffer();
            rootFile.append(directorio);
            rootFile.append(File.separator);
            rootFile.append(actual);//agrega el nombre de la carpeta del archivo para buscar
            nombreArchivo.append(rootFile);
            nombreArchivo.append(File.separator);
            nombreArchivo.append(actual);
            nombreArchivo.append(GfaceConstants.ERROR_FILE_END);
            nombreArchivo.append(GfaceFormatUtilities.TXT_EXTENCION);

//            filesToDelete.add(new File(rootFile.toString()));
            docs = new HashMap<String, GfaceHdrDoc>();
            archivo = new File(nombreArchivo.toString());
            filesToDelete.add(new File(nombreArchivo.toString()));
            try {
                FileReader fr = new FileReader(archivo);
                BufferedReader br = new BufferedReader(fr);
                String linea;
                //crea un mapa para busquedas directas por llave unica
                for (GfaceHdrDoc factura : registros.get(actual)) {
                    llave = new StringBuffer();
                    llave.append(factura.getSerieNum());
                    llave.append("-");
                    llave.append(factura.getCorrNum());
                    llave.append("-");
                    llave.append(factura.getDocType());
                    docs.put(llave.toString(), factura);
                }
                while ((linea = br.readLine()) != null) {
                    pojo = (ErroresPojo) cp.parseCsvLineToObject(linea, ErroresPojo.class, GfaceConstants.FIELD_SEPARATOR);
                    llave2 = new StringBuffer();
                    llave2.append(pojo.getSerieNum());
                    llave2.append("-");
                    llave2.append(pojo.getPreprint());
                    llave2.append("-");
                    llave2.append(pojo.getDocType());
                    GfaceHdrDoc docActualizar = docs.get(llave2.toString());
                    if (docActualizar != null) {
                        //TODO Creacion de objetos de errores
                        docActualizar = ce.setErroresHeader(pojo, docActualizar);
                        ret.add(docActualizar);
                    } else {
                        System.err.println("Error inesperado" + llave2 + "," + actual);
                    }
                }
                br.close();
                fr.close();
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
            //eliminar archivos procesados
            fu.deleteFiles(filesToDelete);
        }
        if (!ret.isEmpty()) {
            ce.crearErrorRegistro(ret);
        }
        return ret;
    }
    
    //busca los errores de los datos, obtenidos desde la BD
    private List<GfaceHdrDoc> getErroresParaActualizar2(List<String> errores, HashMap<String, List<GfaceHdrDoc>> registros) {
    	ZipUtilities zu = new ZipUtilities();
    	CsvParser cp = new CsvParser();
        StringBuffer nombreArchivo;
//        StringBuffer rootFile;
//        File archivo;
        ErroresPojo pojo;
        HashMap<String, GfaceHdrDoc> docs;
        StringBuffer llave;
        StringBuffer llave2;
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();
        GfaceFileUtils fu = new GfaceFileUtils();

        for (String actual : errores) {
            nombreArchivo = new StringBuffer();
            nombreArchivo.append(actual);
            nombreArchivo.append(GfaceConstants.ERROR_FILE_END);
            nombreArchivo.append(GfaceFormatUtilities.TXT_EXTENCION);

            docs = new HashMap<String, GfaceHdrDoc>();
          //busca el valor byte64 almacenado en la base de datos
            GfaceBtchFiles batch = gfaceBtchFilesEjbLocal.find(actual);
            //Obtener el la informacion del archivo ya descomprimido
            String data=zu.getDataArchivo(batch.getFileReceived(), nombreArchivo.toString());
            try {
            	String[] lineas=data.split("\n");
                //crea un mapa para busquedas directas por llave unica
                for (GfaceHdrDoc factura : registros.get(actual)) {
                    llave = new StringBuffer();
                    llave.append(factura.getSerieNum());
                    llave.append("-");
                    llave.append(factura.getCorrNum());
                    llave.append("-");
                    llave.append(factura.getDocType());
                    docs.put(llave.toString(), factura);
                }
                for(String linea:lineas){
                	pojo = (ErroresPojo) cp.parseCsvLineToObject(linea, ErroresPojo.class, GfaceConstants.FIELD_SEPARATOR);
                    llave2 = new StringBuffer();
                    llave2.append(pojo.getSerieNum());
                    llave2.append("-");
                    llave2.append(pojo.getPreprint());
                    llave2.append("-");
                    llave2.append(pojo.getDocType());
                    GfaceHdrDoc docActualizar = docs.get(llave2.toString());
                    if (docActualizar != null) {
                        //TODO Creacion de objetos de errores
                        docActualizar = ce.setErroresHeader(pojo, docActualizar);
                        ret.add(docActualizar);
                    } else {
                        System.err.println("Error inesperado" + llave2 + "," + actual);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
            //eliminar archivos procesados
//            fu.deleteFiles(filesToDelete);
        }
        if (!ret.isEmpty()) {
            ce.crearErrorRegistro(ret);
        }
        return ret;
    }

    private HashMap<String, List<GfaceHdrDoc>> getHeaderPorBatch(List<GfaceHdrDoc> registros) {
        HashMap<String, List<GfaceHdrDoc>> ret = new HashMap<String, List<GfaceHdrDoc>>();
        for (GfaceHdrDoc actual : registros) {
            if (!ret.containsKey(actual.getBtchId().getBtchId())) {
                ret.put(actual.getBtchId().getBtchId(), new ArrayList<GfaceHdrDoc>());
            }
            ret.get(actual.getBtchId().getBtchId()).add(actual);
        }
        return ret;
    }

    public ControlErroresEjbLocal getCe() {
        return ce;
    }

    public void setCe(ControlErroresEjbLocal ce) {
        this.ce = ce;
    }

    public void procesarArchivosValidadosGuatefacturas(List<GfaceHdrDoc> ee,UnUser userInSession) {
        //tendria que estar en scheduler para preverificacion periodica
        //flujo de recoleccion de archivos validados por guatefacturas

        if (ee != null && !ee.isEmpty()) {
            ResultadoValidacionPojo rve = gv.guateFacturasValidation(ee);
            if (rve.getBuenos() != null && !rve.getBuenos().isEmpty()) {
                //guardar cambios de encabezados en la  BD
                sc.cambiarEnvExitosamentePorAutoriz(rve.getBuenos(),userInSession);
            }
            if (rve.getMalos() != null && rve.getMalos().tieneArchivosMalos()) {
                //guardar errores en transacciones
                sc.cambiarEnvExitosamentePorValidExternInval(rve.getMalos().getHeaderConErrores(),userInSession);
            }
            bc.actualizarBatchProcesadosPorGuatefactura(rve,userInSession);
        }
    }
    
//    public void procesarArchivosValidados(List<GfaceHdrDoc> lpe,UnUser userInSession) {
//        GfaceBtchFiles batch = null;
//        //Flujo de envio de informacion a guatefacturas
//
//        if (lpe != null && !lpe.isEmpty()) {
//            //contiene de toda la informacion de la factura
//            List<GfaceDocPojo> files = GfaceDocPojo.getInfo(lpe, false,rbGface);
//            //separa la informacion por tipo de documento
//            HashMap<String, StringBuffer> info = GfaceDocPojo.getFileInformation(files);
//            HashMap<String, List<GfaceHdrDoc>> docs = GfaceDocPojo.getHeaderByDocType(lpe);
//            List<String> procesados = new ArrayList<String>();
//            BigInteger seqCod = gfaceBtchFilesEjbLocal.getSequenceNext("GFACE_CODE_SEQ");
//            BigInteger seqLote = gfaceBtchFilesEjbLocal.getSequenceNext("GFACE_BATCH_SEQ");
//            for (String tipoDocumento : info.keySet()) {
//                try {
//                    String code = fu.completarConCeros(seqCod.toString(), 4);
//                    String lote = seqLote.toString();
//                    StringBuffer nombre = new StringBuffer(fu.generateFileName(code, lote, tipoDocumento));
//                    //crear archivo con informacion de los archivos por tipos de documentos
//                    fut.writeFile(info.get(tipoDocumento).toString(), GfaceFileUtils.urlTmpLocalStoreFiles,
//                            new StringBuffer()
//                            .append(nombre)
//                            .append(GfaceFormatUtilities.TXT_EXTENCION).toString()
//                    );
//                    //crea un zip con el archivo a procesar
//                    zu.comprimir(new StringBuffer()
//                            .append(GfaceFileUtils.urlTmpLocalStoreFiles)
//                            .append(nombre)
//                            .append(GfaceFormatUtilities.TXT_EXTENCION).toString(),
//                            GfaceFileUtils.localFilesToSend,
//                            new StringBuffer()
//                            .append(nombre)
//                            .append(GfaceFormatUtilities.ZIP_EXTENCION)
//                            .toString(), true);
//                    //agrega los nombres para luego crear los archivos de confirmacion
//                    procesados.add(nombre.toString());
//                    //el manejo del archivo -OK lo va a hacer integracion
//
//                    //crea el batch para procesar los archivos
//                    batch = bc.createBatch(docs.get(tipoDocumento), nombre.toString(),info.get(tipoDocumento).toString(),userInSession);
//                } catch (Exception e) {
//                    //TODO error al procesar archivos de GFACE
//                    System.out.println("Error:" + e.getMessage());
//                }
//            }
//
//            if (batch != null) {
//                //crea archivos de confirmacion
//                for (String archivo : procesados) {
//                    fut.writeFile("", GfaceFileUtils.localFilesToSend, new StringBuffer()
//                            .append(archivo)
//                            .append(GfaceConstants.OK_FILE_END)
//                            .append(GfaceFormatUtilities.ZIP_EXTENCION)
//                            .toString());
//                }
//                sc.cambiarListaParaEnvPorEnvExitosamente(lpe,userInSession);
//                bc.cambiarSubidaEnProgresoPorEsperandoRespuesta(batch,userInSession);
//            }
//        }
//    }
}
