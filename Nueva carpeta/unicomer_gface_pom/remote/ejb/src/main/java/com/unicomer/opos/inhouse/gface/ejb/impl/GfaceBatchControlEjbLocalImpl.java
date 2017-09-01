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
import com.unicomer.opos.inhouse.gface.ejb.GfaceBatchControlEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceBtchFilesEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceHdrDocEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceBtchFiles;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.GfaceConstants;
import com.unicomer.opos.inhouse.gface.pojo.ResultadoValidacionPojo;
import com.unicomer.opos.inhouse.security.entities.UnUser;

/**
 *
 * @author francisco_medina
 */
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceBatchControlEjbLocalImpl", mappedName = "ejb/GfaceBatchControlEjbLocalImpl")
@Remote(GfaceBatchControlEjbLocal.class)
public class GfaceBatchControlEjbLocalImpl extends GfaceConstants implements GfaceBatchControlEjbLocal  {


	@Resource(lookup = JNDIUnicomerGface.GfaceHdrDocEjbLocal)
    private GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal;

	@Resource(lookup = JNDIUnicomerGface.GfaceBtchFilesEjbLocal)
    private GfaceBtchFilesEjbLocal gfaceBtchFilesEjbLocal;
    private UnUser userInSession;

    public GfaceBtchFiles createBatch(List<GfaceHdrDoc> headers, String fileName,String informacionDocumento,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        List<GfaceBtchFiles> registros = new ArrayList<GfaceBtchFiles>();
        GfaceBtchFiles batch = new GfaceBtchFiles();

        batch.setBtchId(fileName);
        batch.setSntDate(fecha);
        batch.setFileSend(informacionDocumento);
        batch.setStCode(UPLOAD_IN_PROGRESS);//Upload in progress
        batch.setTotPro(new BigInteger(Integer.toString(headers.size())));
        batch.setGfaceHdrDocList(null);

//        batch.setGfaceHdrDocList(new HashSet<GfaceHdrDoc>(headers));
//        for(GfaceHdrDoc actual:headers){
//        	actual.setBtchId(batch);
//        }
        registros.add(batch);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);
        params.put("batchId", fileName);

        gfaceBtchFilesEjbLocal.merge(params);
        //actualizacion de hijos
        for (GfaceHdrDoc actual : headers) {
            actual.setBtchId(batch);
        }
        params.put("registros", headers);
        gfaceHdrDocEjbLocal.merge(params,false);
        return batch;
    }

    public void cambiarSubidaEnProgresoPorEsperandoRespuesta(GfaceBtchFiles batch,UnUser userInSession) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        List<GfaceBtchFiles> registros = new ArrayList<GfaceBtchFiles>();
        batch.setAuditUserUpd(usuario);
        batch.setAuditDateUpd(fecha);
        batch.setStCode(WATING_FOR_RESPONSE);

        registros.add(batch);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);
        gfaceBtchFilesEjbLocal.merge(params);
    }

    public void cambiarEsperandoRespuestaPorProcesandoRepuesta(GfaceBtchFiles batch) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        List<GfaceBtchFiles> registros = new ArrayList<GfaceBtchFiles>();
        batch.setAuditUserUpd(usuario);
        batch.setAuditDateUpd(fecha);
        batch.setStCode(PROCESSING_RESPONSE);

        registros.add(batch);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);
        gfaceBtchFilesEjbLocal.merge(params);
    }

    public void cambiarProcesandoRepuestaPorCompletado(GfaceBtchFiles batch) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        List<GfaceBtchFiles> registros = new ArrayList<GfaceBtchFiles>();
        batch.setAuditUserUpd(usuario);
        batch.setAuditDateUpd(fecha);
        batch.setStCode(COMPLETED);

        registros.add(batch);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);
        gfaceBtchFilesEjbLocal.merge(params);
    }

    public void cambiarProcesandoRepuestaPorErrorInterno(GfaceBtchFiles batch) {
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }
        List<GfaceBtchFiles> registros = new ArrayList<GfaceBtchFiles>();
        batch.setAuditUserUpd(usuario);
        batch.setAuditDateUpd(fecha);
        batch.setStCode(INTERNAL_ERROR);

        registros.add(batch);
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("fecha", fecha);
        params.put("usuario", usuario);
        params.put("registros", registros);
        gfaceBtchFilesEjbLocal.merge(params);
    }

    public HashMap<String, GfaceBtchFiles> batchDiferentes(ResultadoValidacionPojo procesados) {
        List<GfaceHdrDoc> buenos = procesados.getBuenos();
        List<GfaceHdrDoc> malos = procesados.getMalos().getHeaderConErrores();
        HashMap<String, GfaceBtchFiles> ret = new HashMap<String, GfaceBtchFiles>();
        for (GfaceHdrDoc actual : buenos) {
            if (!ret.containsKey(actual.getBtchId().getBtchId())) {
                ret.put(actual.getBtchId().getBtchId(), actual.getBtchId());
            }
        }
        for (GfaceHdrDoc actual : malos) {
            if (!ret.containsKey(actual.getBtchId().getBtchId())) {
                ret.put(actual.getBtchId().getBtchId(), actual.getBtchId());
            }
        }

        return ret;
    }

    public void actualizarBatchProcesadosPorGuatefactura(ResultadoValidacionPojo procesados,UnUser userInSession) {
        List<GfaceHdrDoc> buenos = procesados.getBuenos();
        List<GfaceHdrDoc> malos = procesados.getMalos().getHeaderConErrores();
        HashMap<String, BigInteger> totBuenosPorBatch = new HashMap<String, BigInteger>();
        HashMap<String, BigInteger> totMalosPorBatch = new HashMap<String, BigInteger>();
        Date fecha = new Date();
        BigInteger usuario;
        if(userInSession!=null){
        	usuario= new BigInteger(userInSession.getUserId().toString());
        }else{
        	//si es un proceso de scheduler, no tiene un usuario asignado
        	usuario= new BigInteger("1");
        }

        BigInteger totBuenos = BigInteger.ZERO;
        BigInteger totMalos = BigInteger.ZERO;
        List<GfaceBtchFiles> registros = new ArrayList<GfaceBtchFiles>();
        //acumula la cantidad de facturas por numero de batch para contar cuantos de esos son correctos o tienen errores
        for (GfaceHdrDoc actual : buenos) {
            if (!totBuenosPorBatch.containsKey(actual.getBtchId().getBtchId())) {
                totBuenosPorBatch.put(actual.getBtchId().getBtchId(), BigInteger.ZERO);
            }
            totBuenos = totBuenosPorBatch.get(actual.getBtchId().getBtchId()).add(BigInteger.ONE);
            totBuenosPorBatch.put(actual.getBtchId().getBtchId(), totBuenos);
        }
        for (GfaceHdrDoc actual : malos) {
            if (!totMalosPorBatch.containsKey(actual.getBtchId().getBtchId())) {
                totMalosPorBatch.put(actual.getBtchId().getBtchId(), BigInteger.ZERO);
            }
            totMalos = totBuenosPorBatch.get(actual.getBtchId().getBtchId()).add(BigInteger.ONE);
            totMalosPorBatch.put(actual.getBtchId().getBtchId(), totMalos);
        }
        HashMap<String, GfaceBtchFiles> diferentes = batchDiferentes(procesados);
        GfaceBtchFiles bchActual = new GfaceBtchFiles();
        for (String actual : diferentes.keySet()) {
            bchActual = diferentes.get(actual);

            BigInteger totalMalosActual = totMalosPorBatch.get(actual);
            BigInteger totalBuenosActual = totBuenosPorBatch.get(actual);
            //suma los registros buenos anteriores a los actuales
            if (totalBuenosActual != null) {
            	if(bchActual.getTotOk()==null){
            		bchActual.setTotOk(totalBuenosActual);
            	}else{
            		bchActual.setTotOk(totalBuenosActual.add(bchActual.getTotOk()));
            	}
            }
            //suma los errores anteriores a los actuales
            if (totalMalosActual != null) {
            	if(bchActual.getTotErr()==null){
            		bchActual.setTotErr(totalMalosActual);
            	}else{
            		bchActual.setTotErr(totalMalosActual.add(bchActual.getTotErr()));
            	}
            }
            //actualiza el estado del batch
            cambiarProcesandoRepuestaPorCompletado(bchActual);
        }
    }
    
    public List<String> getFilesExists(List<String> nombreArchivos,String extension){
    	List<String> ret=new ArrayList<String>();
    	List<String> procList=new ArrayList<String>();
    	List<String> archivosProcesados=gfaceBtchFilesEjbLocal.getFilesProcessed();
    	for(String actual:archivosProcesados){
    		actual=actual.concat(extension);
    		procList.add(actual);
    	}
    	for(String actual:nombreArchivos){
    		//lo agrega solo si ya fue procesado en la BD
    		if(procList.contains(actual)){
    			ret.add(actual);
    		}
    	}
    	return ret;
    }

    public GfaceBtchFilesEjbLocal getGfaceBtchFilesEjbLocal() {
        return gfaceBtchFilesEjbLocal;
    }

    public void setGfaceBtchFilesEjbLocal(GfaceBtchFilesEjbLocal gfaceBtchFilesEjbLocal) {
        this.gfaceBtchFilesEjbLocal = gfaceBtchFilesEjbLocal;
    }

    public UnUser getUserInSession() {
        return userInSession;
    }

    public void setUserInSession(UnUser userInSession) {
        this.userInSession = userInSession;
    }

    public GfaceHdrDocEjbLocal getGfaceHdrDocEjbLocal() {
        return gfaceHdrDocEjbLocal;
    }

    public void setGfaceHdrDocEjbLocal(GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal) {
        this.gfaceHdrDocEjbLocal = gfaceHdrDocEjbLocal;
    }
}
