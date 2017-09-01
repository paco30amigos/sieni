/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.controller.process;

import java.lang.reflect.Method;
import java.util.Hashtable;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import javax.annotation.Resource;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.inhouse.gface.util.EjbPchJNDI;
import com.unicomer.opos.inhouse.gface.ejb.GfaceOthersMigrationEjbLocal;

/**
 *
 * @author francisco_medina
 */
public class GfaceSendDataToGuatefacturas {

    private static final Logger logger = Logger.getLogger(GfaceSendDataToGuatefacturas.class);
    @Resource(lookup = JNDIUnicomerGface.GfaceOthersMigrationEjbLocal)
    private GfaceOthersMigrationEjbLocal gfaceOthersMigrationEjbLocal;

    //obtener los datos de others y meterlos en las tablas de gface utilizando el programador de tareas
    public void getData() {
    	
    	logger.info("--- Llamada de programador de tareas ---");
		try{
			Hashtable env = new Hashtable();
			//env.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			final Context ctx = new InitialContext();			
			Object ejbInterface = ctx.lookup(EjbPchJNDI.EJB_InHouseSchedulerEjbLocal);
			Method method = ejbInterface.getClass().getMethod("triggerJob",String.class,String.class);
			method.invoke(ejbInterface,"Envio GFACE a Guatefacturas","gface");
			logger.info("--- Gface programado exitosamente---"); 
		}catch(Exception e){ 
			logger.info("--- Error al iniciar proceso programado ---"+e.getMessage());
		}
    }

    public GfaceOthersMigrationEjbLocal getGfaceOthersMigrationEjbLocal() {
        return gfaceOthersMigrationEjbLocal;
    }

    public void setGfaceOthersMigrationEjbLocal(GfaceOthersMigrationEjbLocal gfaceOthersMigrationEjbLocal) {
        this.gfaceOthersMigrationEjbLocal = gfaceOthersMigrationEjbLocal;
    }

}
