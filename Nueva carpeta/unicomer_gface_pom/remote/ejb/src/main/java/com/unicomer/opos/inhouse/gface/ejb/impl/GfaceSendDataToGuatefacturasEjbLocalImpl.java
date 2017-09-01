package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.unicomer.inhouse.jndi.JNDIUnicomerSecurity;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.inhouse.gface.model.util.GfaceFormatUtilities;
import com.unicomer.opos.inhouse.gface.ejb.GfaceBatchControlEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceBtchFilesEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceSendDataToGuatefacturasEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStatusControlEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceBtchFiles;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.GfaceDocPojo;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import com.unicomer.opos.inhouse.services.ejbs.SystemParametersEjbLocal;

@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceSendDataToGuatefacturasEjbLocalImpl", mappedName = "ejb/GfaceSendDataToGuatefacturasEjbLocalImpl")
@Remote(GfaceSendDataToGuatefacturasEjbLocal.class)
public class GfaceSendDataToGuatefacturasEjbLocalImpl implements GfaceSendDataToGuatefacturasEjbLocal {

    private static final Logger logger = Logger.getLogger(GfaceSendDataToGuatefacturasEjbLocalImpl.class);


    @Resource(lookup = JNDIUnicomerSecurity.SystemParametersEjbLocal)
	private SystemParametersEjbLocal systemParamsFacade;
    @Resource(lookup = JNDIUnicomerGface.GfaceBtchFilesEjbLocal)
    private GfaceBtchFilesEjbLocal gfaceBtchFilesEjbLocal;
                                   
    GfaceFormatUtilities fu = new GfaceFormatUtilities(); 
    @Resource(lookup = JNDIUnicomerGface.GfaceBatchControlEjbLocal)
    GfaceBatchControlEjbLocal bc;
    @Resource(lookup = JNDIUnicomerGface.GfaceStatusControlEjbLocal)
    GfaceStatusControlEjbLocal sc;
    
    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    public boolean sendDataToGuatefacturas() {
    	boolean ban = true;
        Context ctx = null;
        DataSource ds = null;
        Connection cn = null;
        CallableStatement cs = null;
        logger.info("Executing sending process to gface");
        String ambientCode = "";
        try{
        	ambientCode = (systemParamsFacade.getSystemParametersConfig()).get("un_actual_ambient_code").toString().trim();
        	logger.info("-" + ambientCode + "-");
            ctx = new InitialContext();
            String strDSName = "jdbc/IHGface";
            ds = (DataSource) ctx.lookup(strDSName);
            cn = ds.getConnection();
            cs = cn.prepareCall("{call ADMIHGFACE.GFACE_CONTROL.ENVIAR_DATOS_GUATEFACTURA(?)}");
            cs.registerOutParameter("P_STATUS", java.sql.Types.VARCHAR);
            cs.execute();
            logger.info("The process ended with status: " + cs.getString("P_STATUS"));
            cn.close();
        } catch (Exception e) {
            logger.error(e, e);
            ban = false;
        } finally {
            if (cs != null) {
                try {
                    cs.close();
                } catch (SQLException e) {
                    ban = false;
                    logger.error(e, e);
                }
            }
            if (cn != null) {
                try {
                    cn.close();
                } catch (SQLException e) {
                    ban = false;
                    logger.error(e, e);
                }
            }
        }
        return ban;
    }
    
    public void procesarArchivosValidados(List<GfaceHdrDoc> lpe,UnUser userInSession) {
        HashMap<String,GfaceBtchFiles> batch = new HashMap<String, GfaceBtchFiles>();
        //Flujo de envio de informacion a guatefacturas

        if (lpe != null && !lpe.isEmpty()) {
            //contiene de toda la informacion de la factura
            List<GfaceDocPojo> files = GfaceDocPojo.getInfo(lpe, false,null);
            //separa la informacion por tipo de documento
            HashMap<String, StringBuffer> info = GfaceDocPojo.getFileInformation(files);
            HashMap<String, List<GfaceHdrDoc>> docs = GfaceDocPojo.getHeaderByDocType(lpe);
            List<String> procesados = new ArrayList<String>();
            BigInteger seqCod = gfaceBtchFilesEjbLocal.getSequenceNext("GFACE_CODE_SEQ");
            BigInteger seqLote = gfaceBtchFilesEjbLocal.getSequenceNext("GFACE_BATCH_SEQ");
            for (String tipoDocumento : info.keySet()) {
                try {
                    String code = fu.completarConCeros(seqCod.toString(), 4);
                    String lote = seqLote.toString();
                    StringBuffer nombre = new StringBuffer(fu.generateFileName(code, lote, tipoDocumento));
                    String informacionDocumento=info.get(tipoDocumento).toString();
                    //crear archivo con informacion de los archivos por tipos de documentos
                    /*fut.writeFile(informacionDocumento, GfaceFileUtils.urlTmpLocalStoreFiles,
                            new StringBuffer()
                            .append(nombre)
                            .append(GfaceFormatUtilities.TXT_EXTENCION).toString()
                    );*/
                    //crea un zip con el archivo a procesar
                    /*zu.comprimir(new StringBuffer()
                            .append(GfaceFileUtils.urlTmpLocalStoreFiles)
                            .append(nombre)
                            .append(GfaceFormatUtilities.TXT_EXTENCION).toString(),
                            GfaceFileUtils.localFilesToSend,
                            new StringBuffer()
                            .append(nombre)
                            .append(GfaceFormatUtilities.ZIP_EXTENCION)
                            .toString(), true);
                    */
                    //agrega los nombres para luego crear los archivos de confirmacion
                    procesados.add(nombre.toString());
                    //el manejo del archivo -OK lo va a hacer integracion

                    //crea el batch para procesar los archivos
                    batch.put(nombre.toString(),bc.createBatch(docs.get(tipoDocumento), nombre.toString(),informacionDocumento,userInSession));
                } catch (Exception e) {
                    //TODO error al procesar archivos de GFACE
                    System.out.println("Error:" + e.getMessage());
                }
            }

            if (batch != null) {
                //crea archivos de confirmacion
                /*for (String archivo : procesados) {
                    fut.writeFile("", GfaceFileUtils.localFilesToSend, new StringBuffer()
                            .append(archivo)
                            .append(GfaceConstants.OK_FILE_END)
                            .append(GfaceFormatUtilities.ZIP_EXTENCION)
                            .toString());
                }*/
//                sc.cambiarListaParaEnvPorEnvExitosamente(lpe,userInSession);
//                //cambia el estado de todos los batch registrados en el paso anterior
//                for(String b:batch.keySet()){
//                	bc.cambiarSubidaEnProgresoPorEsperandoRespuesta(batch.get(b),userInSession);
//                }
            }
        }
    }
}
