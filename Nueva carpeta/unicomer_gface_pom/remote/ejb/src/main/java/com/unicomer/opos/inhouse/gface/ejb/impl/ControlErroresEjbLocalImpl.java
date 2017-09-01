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

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.opos.inhouse.gface.ejb.ControlErroresEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceAssocDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceDocErrDtlEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceErrRegCtgEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceDocErrDtl;
import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ErroresPojo;
import com.unicomer.opos.inhouse.security.entities.UnUser;

/**
 *
 * @author francisco_medina
 */
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "ControlErroresEjbLocalImpl", mappedName = "ejb/ControlErroresEjbLocalImpl")
@Remote(ControlErroresEjbLocal.class)
public class ControlErroresEjbLocalImpl implements ControlErroresEjbLocal {

	@Resource(lookup = JNDIUnicomerGface.GfaceErrRegCtgEjbLocal)
    private GfaceErrRegCtgEjbLocal gfaceErrRegCtgEjbLocal;
	
	@Resource(lookup = JNDIUnicomerGface.GfaceDocErrDtlEjbLocal)
    private GfaceDocErrDtlEjbLocal gfaceDocErrDtlEjbLocal;
    private UnUser userInSession;
    private List<GfaceErrRegCtg> erroresCatalog;

    public void crearErrorRegistro(List<GfaceHdrDoc> headers) {
    	if(headers!=null){
    		HashMap<String, GfaceErrRegCtg> mapaErroresActualizados = actualizarErrores(headers);
    		actualizarDetalleErrores(mapaErroresActualizados, headers);
    	}
    }

    public void crearErrorCatalogo() {

    }

    private List<GfaceErrRegCtg> getErroresActivos() {
        List<GfaceErrRegCtg> ret = gfaceErrRegCtgEjbLocal.findAll();
        return ret;
    }

    public GfaceHdrDoc setErroresHeader(ErroresPojo pojo, GfaceHdrDoc header) {
        if (header.getObjetos() == null || header.getObjetos().isEmpty()) {
            header.setObjetos(new HashMap<String, Object>());
        }
        header.getObjetos().put(pojo.getErrorId(), pojo);
        return header;
    }

    //crea el error si no está en el catalogo
    private HashMap<String, GfaceErrRegCtg> actualizarErrores(List<GfaceHdrDoc> headers) {
        HashMap<String, GfaceErrRegCtg> diferentes = new HashMap<String, GfaceErrRegCtg>();
        List<GfaceErrRegCtg> data = new ArrayList<GfaceErrRegCtg>();
        ErroresPojo e = new ErroresPojo();
        GfaceErrRegCtg entity;

        erroresCatalog = getErroresActivos();

        //encontrar los errores diferentes a crear en el catalogo, si no existen
        for (GfaceHdrDoc h : headers) {
            for (String o : h.getObjetos().keySet()) {
                if (!diferentes.containsKey(o)) {
                    entity = e.transformToEntity((ErroresPojo) h.getObjetos().get(o));
                    entity.setGfaceDocErrDtlList(null);
                    entity.setCtrIsoCode("GT");//codigo iso de guatemala
                    entity.setErregTy("E");//validaciones: externa 'E' interna 'I'
                    //agrega la entidad error para que despues del merge venga actualizada
                    diferentes.put(o, entity);
                }
            }
        }
        for (GfaceErrRegCtg actual : erroresCatalog) {
            if (diferentes.get(actual.getErregCode()) != null) {
                //actualiza la entidad actual por la existente en la BD
                diferentes.put(actual.getErregCode(), actual);
            }
        }
        //agrega los registros para crearlos en la BD si no estan en el catalogo
        for (GfaceErrRegCtg actual : diferentes.values()) {
            if (actual.getErregId() == null) {
                data.add(actual);
            }
        }
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", data);
        params.put("manualId", Boolean.FALSE);
        //buscar error actual, sino existe lo crea
        gfaceErrRegCtgEjbLocal.merge(params);

        return diferentes;
    }

    //crea la relacion entre encabezado y error
    public void actualizarDetalleErrores(HashMap<String, GfaceErrRegCtg> datos, List<GfaceHdrDoc> headers) {
        List<GfaceDocErrDtl> det = new ArrayList<GfaceDocErrDtl>();
        GfaceDocErrDtl nuevo;
        for (GfaceHdrDoc h : headers) {
            for (String o : h.getObjetos().keySet()) {
                nuevo = new GfaceDocErrDtl();
                nuevo.setErregId(datos.get(o));
                nuevo.setHdrId(h);
                setErrorDetail(nuevo, h.getObjetos().get(o));
                det.add(nuevo);
            }
        }
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", det);
        params.put("manualId", Boolean.TRUE);
        //actualizar anteriores y ponerles estado desactivado
        gfaceDocErrDtlEjbLocal.merge(params);
    }
    
    

    private void setErrorDetail(GfaceDocErrDtl nuevo, Object detError) {
        ErroresPojo err = (ErroresPojo) detError;
        nuevo.setFsclCtIde(err.getFsclCtIde());
        nuevo.setNameCt(err.getNameCt());
        nuevo.setAdrsCt(err.getAdrsCt());
        nuevo.setPhoneCt(err.getPhoneCt());
        nuevo.setGenerate(err.getGenerate());
        nuevo.setSlsTy(err.getSlsTy());
        nuevo.setDocType(err.getDocType());
        nuevo.setResol(err.getResol());
        nuevo.setSerieNum(err.getSerieNum());
        nuevo.setPreprint(err.getPreprint());
        nuevo.setDocDate(err.getDocDate());
        nuevo.setCnclDt(err.getCnclDt());
        nuevo.setExtOrder(err.getExtOrder());
        nuevo.setCrcyCode(err.getCrcyCode());
        nuevo.setCrcExcRt(err.getCrcyExcRt());
        nuevo.setTotMnt(err.getTotMnt());
        nuevo.setVatMnt(err.getVatMnt());
        nuevo.setNetMnt(err.getNetMnt());
        nuevo.setGrsMnt(err.getGrsMnt());
        nuevo.setDscMnt(err.getDscMnt());
        nuevo.setXmpMnt(err.getXmpMnt());
        nuevo.setThrMnt(err.getThrMnt());
    }

    public GfaceErrRegCtgEjbLocal getGfaceErrRegCtgEjbLocal() {
        return gfaceErrRegCtgEjbLocal;
    }

    public void setGfaceErrRegCtgEjbLocal(GfaceErrRegCtgEjbLocal gfaceErrRegCtgEjbLocal) {
        this.gfaceErrRegCtgEjbLocal = gfaceErrRegCtgEjbLocal;
    }

    public UnUser getUserInSession() {
        return userInSession;
    }

    public void setUserInSession(UnUser userInSession) {
        this.userInSession = userInSession;
    }

    public GfaceDocErrDtlEjbLocal getGfaceDocErrDtlEjbLocal() {
        return gfaceDocErrDtlEjbLocal;
    }

    public void setGfaceDocErrDtlEjbLocal(GfaceDocErrDtlEjbLocal gfaceDocErrDtlEjbLocal) {
        this.gfaceDocErrDtlEjbLocal = gfaceDocErrDtlEjbLocal;
    }
}
