package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.log4j.Logger;

import com.unicomer.inhouse.jndi.JNDIUnicomerSecurity;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.opos.inhouse.gface.ejb.GfaceGuatefacturasControlFtpEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceOthersMigrationEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceSendDataToGuatefacturasEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStatusControlEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.util.validations.GfaceInternalValidationsEjbLocal;
import com.unicomer.opos.inhouse.services.ejbs.SystemParametersEjbLocal;

@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceOthersMigrationEjbLocalImpl", mappedName = "ejb/GfaceOthersMigrationEjbLocalImpl")
@Remote(GfaceOthersMigrationEjbLocal.class)
public class GfaceOthersMigrationEjbLocalImpl implements GfaceOthersMigrationEjbLocal {

    private static final Logger logger = Logger.getLogger(GfaceOthersMigrationEjbLocalImpl.class);

    @Resource(lookup = JNDIUnicomerSecurity.SystemParametersEjbLocal) 
    private SystemParametersEjbLocal systemParamsFacade;
    
    @Resource(lookup = JNDIUnicomerGface.GfaceInternalValidationsEjbLocal)
    private GfaceInternalValidationsEjbLocal iv;
    @Resource(lookup = JNDIUnicomerGface.GfaceStatusControlEjbLocal)
    private GfaceStatusControlEjbLocal sc;
    @Resource(lookup = JNDIUnicomerGface.GfaceSendDataToGuatefacturasEjbLocal)
    GfaceSendDataToGuatefacturasEjbLocal gfaceSendDataToGuatefacturasEjbLocal;

    @PersistenceContext(unitName = "EAgfacePersistence")
    private EntityManager em;

    @Override
    public boolean migrationToGface() {
        boolean ban = true;
        Context ctx = null;
        DataSource ds = null;
        Connection cn = null;
        CallableStatement cs = null;
        logger.info("Executing migration process to gface");
        String ambientCode = "";
        try {
        	ambientCode = ((CallableStatement) systemParamsFacade.getSystemParametersConfig()).getString("un_actual_ambient_code").trim();
            
            logger.info("-" + ambientCode + "-");
            ctx = new InitialContext();
            String strDSName = "jdbc/IHGface";
            ds = (DataSource) ctx.lookup(strDSName);
            java.sql.Date fechaActual = new Date(new java.util.Date().getTime());
            cn = ds.getConnection();
            cs = cn.prepareCall("{call ADMIHGFACE.GFACE_CONTROL.MIGRAR_DATOS_GFACE( ?, ?, ? ,?)}");
            cs.setString("P_AMBIENTE", ambientCode);
            cs.setDate("P_FECHA_TRANSAC", fechaActual);
            cs.setString("P_COUNTRY_CODE", "GT");// ejecuta para guatemala
            cs.registerOutParameter("P_STATUS", java.sql.Types.VARCHAR);
            cs.execute();
            logger.info("The process ended with status: " + cs.getString("P_STATUS"));
            cn.close();
            
          //aplicar validaciones de gface a archivos migrados
            List<GfaceHdrDoc> encabezados=sc.getPendientesValidar();
            if(encabezados!=null&&!encabezados.isEmpty()){
            	//aplica las validaciones
            	iv.validacionInterna(encabezados,null);
            	
            	//establece los archivos que ya estan listos para ser enviados (crea el documento en fileToSend)
            	List<GfaceHdrDoc> lpe = sc.getListasParaEnviar();
            	gfaceSendDataToGuatefacturasEjbLocal.procesarArchivosValidados(lpe, null);
            	
            }else{
            	logger.info("No Transactions found");
            }
            
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
}
