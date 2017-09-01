/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.annotation.Resource;

import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.opos.inhouse.gface.ejb.GfaceHdrDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceSendDataToGuatefacturasEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStatusControlEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.GfaceConstants;
import com.unicomer.opos.inhouse.security.entities.UnUser;

/**
 *
 * @author francisco_medina
 */
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceStatusControlEjbLocalImpl", mappedName = "ejb/GfaceStatusControlEjbLocalImpl")
@Remote(GfaceStatusControlEjbLocal.class)
public class GfaceStatusControlEjbLocalImpl extends GfaceConstants implements GfaceStatusControlEjbLocal {

	@Resource(lookup = JNDIUnicomerGface.GfaceHdrDocEjbLocal)
    private GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal;
	
//    private UnUser userInSession;

    public List<GfaceHdrDoc> getPendientesValidar() {
        List<GfaceHdrDoc> ret = getStatus(ST_PEND_VALID); 
        return ret;
    }

    public List<GfaceHdrDoc> getValidacionesEnProceso() {
        List<GfaceHdrDoc> ret = getStatus(ST_INTER_VALID);
        return ret;
    } 

    public List<GfaceHdrDoc> getInvalidoPorValidacionesInternas() {
        List<GfaceHdrDoc> ret = getStatus(ST_VAL_INTER_INVALID);
        for(GfaceHdrDoc actual:ret){
        	//hace editable el nit y la serie de la factura
        	actual.setBanFsclCtIde(true);
        	actual.setBanSerieNum(true);
        	actual.setBanCorrNum(true);
        }
        return ret;
    }

    public List<GfaceHdrDoc> getListasParaEnviar() {
        List<GfaceHdrDoc> ret = getStatus(ST_LISTO_ENVIAR);
        return ret;
    }

    public List<GfaceHdrDoc> getEnviadasExitosamente() {
        List<GfaceHdrDoc> ret = getStatus(ST_ENV_EXITO);
        return ret;
    }

    public List<GfaceHdrDoc> getAutorizadas() {
        List<GfaceHdrDoc> ret = getStatus(ST_AUTORIZ);
        return ret;
    }

    public List<GfaceHdrDoc> getInvalidasPorValidacionesExternas() {
        List<GfaceHdrDoc> ret = getStatus(ST_VAL_EXTER_INVALID);
        for(GfaceHdrDoc actual:ret){
        	//hace editable el nit y la serie de la factura
        	actual.setBanFsclCtIde(true);
        	actual.setBanSerieNum(true);
        	actual.setBanCorrNum(true);
        }
        
        return ret;
    }

    public List<GfaceHdrDoc> getInvalidas() {
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();
        ret.addAll(getInvalidoPorValidacionesInternas());
        ret.addAll(getInvalidasPorValidacionesExternas());
        return ret;
    }
    
    public List<GfaceHdrDoc> getInvalidas(String documentType,String warning,String error,String tienda) {
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("docType",documentType);
        params.put("errCode",error);
        params.put("warCode",warning);
        params.put("storeCode",tienda);
        params.put("rgsStCode", GfaceConstants.ST_VAL_INTER_INVALID);
        params.put("flgSt", GfaceConstants.REG_ACTIVE);
        //busca y guarda las validaciones internas
        ret = gfaceHdrDocEjbLocal.getHeaderByStatusDocTypeErrWrStr(params);
        //busca y guarda las validaciones externas        
        params.put("rgsStCode", GfaceConstants.ST_VAL_EXTER_INVALID);
        ret.addAll(gfaceHdrDocEjbLocal.getHeaderByStatusDocTypeErrWrStr(params));
        for(GfaceHdrDoc actual:ret){
        	//hace editable el nit y la serie de la factura
        	actual.setBanFsclCtIde(true);
        	actual.setBanSerieNum(true);
        	actual.setBanCorrNum(true);
        }
        return ret;
    }

    public List<GfaceHdrDoc> getAll() {
        //trae todos los registros activos actuales
        List<GfaceHdrDoc> ret = new ArrayList<GfaceHdrDoc>();
        return ret;
    }

    private List<GfaceHdrDoc> getStatus(BigInteger estatus) {
        //donde flgSt=Activo (ultimo registro validado)
        //y rgsStCode de la tabla GfaceStReg es igual a estatus
        List<GfaceHdrDoc> ret;
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("rgsStCode", estatus);
        params.put("flgSt", GfaceConstants.REG_ACTIVE);
        ret = gfaceHdrDocEjbLocal.getHeaderByStatus(params);
        return ret;
    }

    public void cambiarPenValidPorValidEnProc(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        for (GfaceHdrDoc actual : registros) {
            actual.getSttRgsId().setRgsStCode(ST_INTER_VALID);
            actual.getSttRgsId().setAuditDateUpd(fecha);
            actual.getSttRgsId().setAuditUserUpd(usuario);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);
        
        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
        //actualizar registro o revolver la lista
    }

    public void cambiarValidEnProcPorListaParaEnv(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        for (GfaceHdrDoc actual : registros) {
            actual.getSttRgsId().setRgsStCode(ST_LISTO_ENVIAR);
            actual.getSttRgsId().setAuditDateUpd(fecha);
            actual.getSttRgsId().setAuditUserUpd(usuario);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);
        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
    }

    public void cambiarValidEnProcPorValidInterInval(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
//        CopiaPojo cp = new CopiaPojo();
//        List<GfaceHdrDoc> copias;
        try {
            //crea copias para no modificar el registro anterior
//            HashMap<String, Object> paramsCopia = new HashMap<String, Object>();
//            paramsCopia.put("fecha", fecha);
//            paramsCopia.put("usuario", usuario);
//            paramsCopia.put("original", registros);
//            copias = cp.copiarLimpiarHeader(paramsCopia);
//            //agrega las copias de los datos modificados como nuevos registros
//            registros.addAll(copias);

            //actualiza los registros copiados y los pone como desactivados
            for (GfaceHdrDoc actual : registros) {
                actual.getSttRgsId().setRgsStCode(ST_VAL_INTER_INVALID);
                actual.getSttRgsId().setAuditDateUpd(fecha);
                actual.getSttRgsId().setAuditUserUpd(usuario);
            }
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);

        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
    }

    public void cambiarValidInterInvalPorPenValid(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        for (GfaceHdrDoc actual : registros) {
            actual.getSttRgsId().setRgsStCode(ST_PEND_VALID);
            actual.getSttRgsId().setAuditDateUpd(fecha);
            actual.getSttRgsId().setAuditUserUpd(usuario);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);

        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
    }

    public void cambiarListaParaEnvPorEnvExitosamente(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        for (GfaceHdrDoc actual : registros) {
            actual.getSttRgsId().setRgsStCode(ST_ENV_EXITO);
            actual.getSttRgsId().setAuditDateUpd(fecha);
            actual.getSttRgsId().setAuditUserUpd(usuario);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);

        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
    }

    public void cambiarEnvExitosamentePorAutoriz(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        for (GfaceHdrDoc actual : registros) {
            actual.getSttRgsId().setRgsStCode(ST_AUTORIZ);
            actual.getSttRgsId().setAuditDateUpd(fecha);
            actual.getSttRgsId().setAuditUserUpd(usuario);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);

        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
    }

    public void cambiarEnvExitosamentePorValidExternInval(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        for (GfaceHdrDoc actual : registros) {
            actual.getSttRgsId().setRgsStCode(ST_VAL_EXTER_INVALID);
            actual.getSttRgsId().setAuditDateUpd(fecha);
            actual.getSttRgsId().setAuditUserUpd(usuario);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);

        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
    }

    public void cambiarValidExternInvalPorPenValid(List<GfaceHdrDoc> registros,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        for (GfaceHdrDoc actual : registros) {
            actual.getSttRgsId().setRgsStCode(ST_PEND_VALID);
            actual.getSttRgsId().setAuditDateUpd(fecha);
            actual.getSttRgsId().setAuditUserUpd(usuario);
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);

        if (!gfaceHdrDocEjbLocal.merge(params,true)) {
            //no se pudo actualizar
        }
    }

    public GfaceHdrDocEjbLocal getGfaceHdrDocEjbLocal() {
        return gfaceHdrDocEjbLocal;
    }

    public void setGfaceHdrDocEjbLocal(GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal) {
        this.gfaceHdrDocEjbLocal = gfaceHdrDocEjbLocal;
    }

//    public UnUser getUserInSession() {
//        return userInSession;
//    }
//
//    public void setUserInSession(UnUser userInSession) {
//        this.userInSession = userInSession;
//    }

}
