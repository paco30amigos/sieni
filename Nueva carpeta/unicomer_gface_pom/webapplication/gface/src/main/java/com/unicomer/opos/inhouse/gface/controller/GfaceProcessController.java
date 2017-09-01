package com.unicomer.opos.inhouse.gface.controller;
//package com.unicomer.opos.inhouse.gface.controller;
//
//import java.io.Serializable;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.ResourceBundle;
//
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ManagedProperty;
//import javax.faces.bean.ViewScoped;
//import javax.faces.context.FacesContext;
//
//import com.unicomer.inhouse.gface.model.util.GfaceCopiaArchivos;
//import com.unicomer.inhouse.gface.model.util.GfaceFileUtils;
//import com.unicomer.inhouse.gface.model.util.GfaceFormatUtilities;
//import com.unicomer.inhouse.gface.model.util.ZipUtilities;
//import com.unicomer.opos.inhouse.gface.controller.process.GfaceReadDataFromOthers;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceAssocDocEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceBtchFilesEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceDocErrDtlEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceDtlDocEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceErrRegCtgEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceHdrDocEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceOthersMigrationEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.GfaceStRegEjbLocal;
//import com.unicomer.opos.inhouse.gface.ejb.impl.ControlErroresEjbLocalImpl;
//import com.unicomer.opos.inhouse.gface.ejb.impl.GfaceBatchControlEjbLocalImpl;
//import com.unicomer.opos.inhouse.gface.ejb.impl.GfaceGuatefacturaFileReaderEjbLocalImpl;
//import com.unicomer.opos.inhouse.gface.ejb.impl.GfaceStatusControlEjbLocalImpl;
//import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;
//import com.unicomer.opos.inhouse.gface.entity.GfaceBtchFiles;
//import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
//import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
//import com.unicomer.opos.inhouse.gface.pojo.GfaceConstants;
//import com.unicomer.opos.inhouse.gface.pojo.GfaceDocPojo;
//import com.unicomer.opos.inhouse.gface.pojo.ResultadoValidacionPojo;
//import com.unicomer.opos.inhouse.gface.util.validations.GfaceGuatefacturaValidationsEjbLocalImpl;
//import com.unicomer.opos.inhouse.gface.util.validations.GfaceInternalValidationsEjbLocalImpl;
//import com.unicomer.opos.inhouse.security.ejbs.UnUserDaoEjbLocal;
//import com.unicomer.opos.inhouse.security.entities.UnUser;
//
///**
// * @author francisco_medina
// *
// */
//@ViewScoped
//@ManagedBean(name = "GfaceProcessController")
//public class GfaceProcessController implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//    //Message Bundle
//    ResourceBundle rbPCAUI;
//
//    @EJB//(lookup = EjbSecurityJNDI.JNDI_BASE + "/UnUserDaoEjb!com.unicomer.opos.inhouse.security.ejbs.UnUserDaoEjbLocal")
//    private UnUserDaoEjbLocal UnUserFacade;
//
//    @EJB
//    private GfaceOthersMigrationEjbLocal gfaceOthersMigrationEjbLocal;
//    @EJB
//    private GfaceBtchFilesEjbLocal gfaceBtchFilesEjbLocal;
//    @EJB
//    private GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal;
//    @EJB
//    private GfaceDtlDocEjbLocal gfaceDtlDocEjbLocal;
//    @EJB
//    private GfaceAssocDocEjbLocal gfaceAssocDocEjbLocal;
//    @EJB
//    private GfaceErrRegCtgEjbLocal gfaceErrRegCtgEjbLocal;
//    @EJB
//    private GfaceDocErrDtlEjbLocal gfaceDocErrDtlEjbLocal;
//    
//    @EJB
//    private GfaceStRegEjbLocal gfaceStRegEjbLocal;
//    
//    private List<GfaceHdrDoc> hdrModificados;
//    private List<GfaceDtlDoc> detModificados;
//    private List<GfaceAssocDoc> docModificados;
//
//    @ManagedProperty(value = "#{UnUserController.uniUser}")
//    private UnUser userInSession;
//
//    //ini objetos principales
//    GfaceReadDataFromOthers erfo = new GfaceReadDataFromOthers();
//    GfaceStatusControlEjbLocalImpl sc = new GfaceStatusControlEjbLocalImpl();
//    GfaceBatchControlEjbLocalImpl bc = new GfaceBatchControlEjbLocalImpl();
//    GfaceInternalValidationsEjbLocalImpl iv = new GfaceInternalValidationsEjbLocalImpl();
//    GfaceGuatefacturaValidationsEjbLocalImpl gv = new GfaceGuatefacturaValidationsEjbLocalImpl();
//    GfaceFormatUtilities fu = new GfaceFormatUtilities();
//    GfaceGuatefacturaFileReaderEjbLocalImpl gfr = new GfaceGuatefacturaFileReaderEjbLocalImpl();
//    GfaceFileUtils fut = new GfaceFileUtils();
//    ZipUtilities zu = new ZipUtilities();
//    ControlErroresEjbLocalImpl ce = new ControlErroresEjbLocalImpl();
//    GfaceCopiaArchivos ca = new GfaceCopiaArchivos();
//    //fin objetos principales
//
//    @PostConstruct
//    public void init() {
//        //Initialize the vars
//        rbPCAUI = ResourceBundle.getBundle("com.unicomer.opos.inhouse.gface.util.i18n.Bundlegface", FacesContext.getCurrentInstance().getViewRoot().getLocale());
//        //inicializa los objetos que utilizan EJB
//        erfo.setGfaceOthersMigrationEjbLocal(gfaceOthersMigrationEjbLocal);
//        sc.setGfaceHdrDocEjbLocal(gfaceHdrDocEjbLocal);
//        sc.setUserInSession(userInSession);
//
//        bc.setGfaceHdrDocEjbLocal(gfaceHdrDocEjbLocal);
//        bc.setUserInSession(userInSession);
//        bc.setGfaceBtchFilesEjbLocal(gfaceBtchFilesEjbLocal);
//
//        ce.setUserInSession(userInSession);
//        ce.setGfaceErrRegCtgEjbLocal(gfaceErrRegCtgEjbLocal);
//        ce.setGfaceDocErrDtlEjbLocal(gfaceDocErrDtlEjbLocal);
//
//        //agrega el elemento inicializado para control de errores
//        gfr.setCe(ce);
//        //agrega objetos para la validacion
////        gv.setGfr(gfr);
//
//        //validaciones internas
//        iv.setGfaceHdrDocEjbLocal(gfaceHdrDocEjbLocal);
//    }
//
//    //flujo manual
//    public void copiarArchivos() {
//        Date fecha = new Date();
//        BigInteger usuario = new BigInteger(userInSession.getUserId().toString());
//        HashMap<String, Object> paramsCopia = new HashMap<String, Object>();
//        paramsCopia.put("fecha", fecha);
//        paramsCopia.put("usuario", usuario);
//
//        //obtiene las copias de los encabezados
//        paramsCopia.put("original", hdrModificados);
//        List<GfaceHdrDoc> hdrParaModificar = ca.copiarLimpiarHeader(paramsCopia);
//        //envia los parametros para hacer la actualizacion
//        hdrParaModificar.addAll(hdrModificados);
//        paramsCopia.put("registros", hdrParaModificar);
//        gfaceHdrDocEjbLocal.merge(paramsCopia,false);
//
//        //establece el valor de los header para utilizarlos de referencia en la copia de detalle y documentos asociados
//        paramsCopia.put("headerModificados", getHashHeader(hdrParaModificar));
//
//        //obtiene las copias de los detalles
//        paramsCopia.put("original", detModificados);
//        List<GfaceDtlDoc> detParaModificar = ca.copiarLimpiarDetail(paramsCopia);
//        //envia los parametros para hacer la actualizacion y creacion de los registros nuevos
//        detModificados.addAll(detParaModificar);
//        paramsCopia.put("registros", detModificados);
//        gfaceDtlDocEjbLocal.merge(paramsCopia);
//
//        //obtiene las copias de los documentos asociados
//        paramsCopia.put("original", docModificados);
//        List<GfaceAssocDoc> docParaModificar = ca.copiarLimpiarDocs(paramsCopia);
//        //envia los parametros para hacer la actualizacion y creacion de los registros nuevos
//        docModificados.addAll(docParaModificar);
//        paramsCopia.put("registros", docModificados);
//        gfaceAssocDocEjbLocal.merge(paramsCopia);
//
//        //cambia el estatus de las facturas copiadas para que sean validadas
//        sc.cambiarValidInterInvalPorPenValid(hdrParaModificar);
//    }
//
//    public HashMap<BigInteger, GfaceHdrDoc> getHashHeader(List<GfaceHdrDoc> headers) {
//        HashMap<BigInteger, GfaceHdrDoc> ret = new HashMap<BigInteger, GfaceHdrDoc>();
//        for (GfaceHdrDoc actual : headers) {
//            ret.put(actual.getCopiado().getHdrId(), actual);
//        }
//        return ret;
//    }
//
//    public void testCopias() {
//        List<GfaceHdrDoc> pv = sc.getEnviadasExitosamente();
//        hdrModificados = pv;
//        detModificados = new ArrayList<GfaceDtlDoc>();
//        docModificados = new ArrayList<GfaceAssocDoc>();
//        for (GfaceHdrDoc actual : hdrModificados) {
//            detModificados.addAll(actual.getGfaceDtlDocList());
//            docModificados.addAll(actual.getGfaceAssocDocList());
//        }
////        copiarArchivos();
//    }
//
//    public synchronized void mainProcess() {
////        testCopias();
//
//        //gface recibe transacciones de others y guarda en tablas de gface
//        erfo.getData();
//
//        List<GfaceHdrDoc> pv = sc.getPendientesValidar();
//        validacionInterna(pv);
//
//        List<GfaceHdrDoc> lpe = sc.getListasParaEnviar();
//        procesarArchivosValidados(lpe);
//
//        List<GfaceHdrDoc> ee = sc.getEnviadasExitosamente();
//        procesarArchivosValidadosPorGuatefactura(ee);
//
//    }
//
//    public void validacionInterna(List<GfaceHdrDoc> pv) {
//        //busca las transacciones de gface pendientes de validar
//        //flujo de validacion de datos        
//        if (pv != null && !pv.isEmpty()) {
//            //actualiza el estado de los registros en la BD
//            sc.cambiarPenValidPorValidEnProc(pv);
//            //retorna el resultado de las validaciones
//            ResultadoValidacionPojo rvi = iv.internalValidation(pv);
//            //tiene errores de validaciones internas?
//            if (rvi.getBuenos() != null && !rvi.getBuenos().isEmpty()) {
//                sc.cambiarValidEnProcPorListaParaEnv(rvi.getBuenos());
//            }
//            if (rvi.getMalos() != null && !rvi.getMalos().tieneArchivosMalos()) {
//                sc.cambiarValidEnProcPorValidInterInval(rvi.getMalos().getHeaderConErrores());
//            }
//        }
//    }
//
//    public void procesarArchivosValidados(List<GfaceHdrDoc> lpe) {
//        GfaceBtchFiles batch = null;
//        //Flujo de envio de informacion a guatefacturas
//
//        if (lpe != null && !lpe.isEmpty()) {
//            //contiene de toda la informacion de la factura
//            List<GfaceDocPojo> files = GfaceDocPojo.getInfo(lpe, false,rbPCAUI);
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
//                    batch = bc.createBatch(docs.get(tipoDocumento), nombre.toString(),info.get(tipoDocumento).toString());
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
//                sc.cambiarListaParaEnvPorEnvExitosamente(lpe);
//                bc.cambiarSubidaEnProgresoPorEsperandoRespuesta(batch);
//            }
//        }
//    }
//
//    public void procesarArchivosValidadosPorGuatefactura(List<GfaceHdrDoc> ee) {
//        //tendria que estar en scheduler para preverificacion periodica
//        //flujo de recoleccion de archivos validados por guatefacturas
//
//        if (ee != null && !ee.isEmpty()) {
//            ResultadoValidacionPojo rve = gv.guateFacturasValidation(ee);
//            if (rve.getBuenos() != null && !rve.getBuenos().isEmpty()) {
//                //guardar cambios de encabezados en la  BD
//                sc.cambiarEnvExitosamentePorAutoriz(rve.getBuenos());
//            }
//            if (rve.getMalos() != null && rve.getMalos().tieneArchivosMalos()) {
//                //guardar errores en transacciones
//                sc.cambiarEnvExitosamentePorValidExternInval(rve.getMalos().getHeaderConErrores());
//            }
//            bc.actualizarBatchProcesadosPorGuatefactura(rve);
//        }
//    }
//
//    public UnUser getUserInSession() {
//        return userInSession;
//    }
//
//    public void setUserInSession(UnUser userInSession) {
//        this.userInSession = userInSession;
//    }
//
//    public List<GfaceHdrDoc> getHdrModificados() {
//        return hdrModificados;
//    }
//
//    public void setHdrModificados(List<GfaceHdrDoc> hdrModificados) {
//        this.hdrModificados = hdrModificados;
//    }
//
//}
