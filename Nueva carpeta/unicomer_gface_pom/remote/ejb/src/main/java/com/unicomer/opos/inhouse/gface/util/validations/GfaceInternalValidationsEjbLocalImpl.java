/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.util.validations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.annotation.Resource;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.opos.inhouse.gface.ejb.ControlErroresEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceHdrDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceStatusControlEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ArchivosMalosPojo;
import com.unicomer.opos.inhouse.gface.pojo.ErroresPojo;
import com.unicomer.opos.inhouse.gface.pojo.ResultadoValidacionPojo;
import com.unicomer.opos.inhouse.gface.pojo.ValidationPojo;
import com.unicomer.opos.inhouse.security.entities.UnUser;

/**
 *
 * @author francisco_medina
 */
@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceInternalValidationsEjbLocalImpl", mappedName = "ejb/GfaceInternalValidationsEjbLocalImpl")
@Remote(GfaceInternalValidationsEjbLocal.class)
public class GfaceInternalValidationsEjbLocalImpl implements GfaceInternalValidationsEjbLocal {

	@Resource(lookup = JNDIUnicomerGface.GfaceHdrDocEjbLocal)
    private GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal;
    ResultadoValidacionPojo validPojo;
    @Resource(lookup = JNDIUnicomerGface.ControlErroresEjbLocal)
    ControlErroresEjbLocal ce;
    @Resource(lookup = JNDIUnicomerGface.GfaceStatusControlEjbLocal)
    private GfaceStatusControlEjbLocal sc;

	private ResultadoValidacionPojo internalValidation(List<GfaceHdrDoc> encabezados) {
		HashMap<String, GfaceErrRegCtg> o=new HashMap<String, GfaceErrRegCtg>();
		validPojo = new ResultadoValidacionPojo();
        List<ValidationPojo> test;
        validPojo.setBuenos(new ArrayList<GfaceHdrDoc>());
        validPojo.setMalos(new ArchivosMalosPojo());
        validPojo.getMalos().setHeaderConErrores(new ArrayList<GfaceHdrDoc>());
        validPojo.getMalos().setHeader(new ArrayList<GfaceHdrDoc>());
        validPojo.getMalos().setDetalle(new ArrayList<GfaceDtlDoc>());
        validPojo.getMalos().setDoc(new ArrayList<GfaceAssocDoc>());

        boolean error=false;
        for (GfaceHdrDoc header : encabezados) {
            test = new ArrayList<ValidationPojo>();
            test.addAll(validarEncabezado(header));
            test.addAll(validarDetalle(header.getGfaceDtlDocList()));
            test.addAll(validarDocumentos(header.getGfaceAssocDocList()));

            if (ValidationPojo.isValid(test)) {
                validPojo.getBuenos().add(header);
            } else {        
            	error=true;
                validPojo.getMalos().getHeaderConErrores().add(header);
                validPojo.getMalos().getHeader().add(header);
            }
        }
        //si hay errores los actualiza en el detalle de errores
        if(error){
        	ce.crearErrorRegistro(validPojo.getMalos().getHeader());
        }

        return validPojo;
    }

    public List<ValidationPojo> validarEncabezado(GfaceHdrDoc encabezado) {
        List<ValidationPojo> ret = new ArrayList<ValidationPojo>();
        //registrar errores en la BD
        ret.add(new ValidationPojo("NIT No valido", !validarNit(encabezado,new BigInteger("1"))));
        ret.add(new ValidationPojo("Debe ingresar serie", !validarSerieFactura(encabezado, new BigInteger("2"))));
        ret.add(new ValidationPojo("Debe ingresar Numero de factura", !validarNumFactura(encabezado, new BigInteger("3"))));
        return ret;
    }

    public List<ValidationPojo> validarDetalle(Set<GfaceDtlDoc> detalle) {
        List<ValidationPojo> ret = new ArrayList<ValidationPojo>();
        //registrar errores en la BD
        return ret;
    }

    public List<ValidationPojo> validarDocumentos(Set<GfaceAssocDoc> docs) {
        List<ValidationPojo> ret = new ArrayList<ValidationPojo>();
        //registrar errores en la BD
        return ret;
    }

    public boolean validarTotales() {
        boolean ban = true;
        return ban;
    }

    private boolean validarNit(GfaceHdrDoc encabezado,BigInteger errorCode) {
        boolean ban = false;
        String numeroDocumento=encabezado.getFsclCtIde();
            try{
    	    	// en el caso de los NIT que terminan en K, se convierte a mayúsculas
    	        // también el NIT CF o C/F es válido (CF = consumidor final)
    	        numeroDocumento = numeroDocumento.toUpperCase();
    	        if (numeroDocumento.equals("CF")) {
    	            return true;
    	        }
    	        String nit = numeroDocumento;
    	        int pos = nit.indexOf("-");
    	
    	        if (pos < 0) {
    	            String correlativo = numeroDocumento.substring(0,
    	                    numeroDocumento.length() - 1);
    	            correlativo = correlativo + "-";
    	
    	            int pos2 = correlativo.length() - 2;
    	            String digito = numeroDocumento.substring(pos2 + 1);
    	            nit = correlativo + digito;
    	            pos = nit.indexOf("-");
    	            numeroDocumento = nit;
    	        }
    	
    	        String Correlativo = nit.substring(0, pos);
    	        String DigitoVerificador = nit.substring(pos + 1);
    	        DigitoVerificador = DigitoVerificador.toUpperCase();
    	        int Factor = Correlativo.length() + 1;
    	        int Suma = 0;
    	        int Valor = 0;
    	        for (int x = 0; x <= (pos - 1); x++) {
    	            Valor = Integer.parseInt(nit.substring(x, x + 1));
    	            int Multiplicacion = Valor * Factor;
    	            Suma = Suma + Multiplicacion;
    	            Factor = Factor - 1;
    	        }
    	        int xMOd11 = 0;
    	        xMOd11 = (11 - (Suma % 11)) % 11;
    	        int s = xMOd11;
    	        if ((xMOd11 == 10 && DigitoVerificador.equals("K"))
    	                || (s == Integer.parseInt(DigitoVerificador))) {
    	        	ban= true;
    	        } else {
    	        	ban= false;
    	        }
            }catch(Exception e){
            	ban= false;
            }  
            
        if(!ban){
        	//crea un objeto de error para poder registrarlo 
	    	ErroresPojo err= new ErroresPojo();
	    	StringBuilder s=new StringBuilder(errorCode.toString());
	    	err.setErrorId(s.toString());
	    	if(encabezado.getObjetos()!=null){
	    		encabezado.getObjetos().put(s.toString(), err);
	    	}else{
	    		encabezado.setObjetos(new HashMap<String, Object>());
	    		encabezado.getObjetos().put(s.toString(), err);
	    	}
	    	ban=false;
        }
        return ban;
    }
    private boolean validarSerieFactura(GfaceHdrDoc encabezado,BigInteger errorCode) {
        boolean ban = true;
        if(encabezado.getSerieNum()==null||encabezado.getSerieNum().isEmpty()){
        	//crea un objeto de error para poder registrarlo 
	    	ErroresPojo err= new ErroresPojo();
	    	StringBuilder s=new StringBuilder(errorCode.toString());
	    	err.setErrorId(s.toString());
	    	if(encabezado.getObjetos()!=null){
	    		encabezado.getObjetos().put(s.toString(), err);
	    	}else{
	    		encabezado.setObjetos(new HashMap<String, Object>());
	    		encabezado.getObjetos().put(s.toString(), err);
	    	}
	    	ban=false;
        }
        return ban;
    }
    private boolean validarNumFactura(GfaceHdrDoc encabezado,BigInteger errorCode) {
        boolean ban = true;
        if(encabezado.getCorrNum()==null){
        	//crea un objeto de error para poder registrarlo 
	    	ErroresPojo err= new ErroresPojo();
	    	StringBuilder s=new StringBuilder(errorCode.toString());
	    	err.setErrorId(s.toString());
	    	if(encabezado.getObjetos()!=null){
	    		encabezado.getObjetos().put(s.toString(), err);
	    	}else{
	    		encabezado.setObjetos(new HashMap<String, Object>());
	    		encabezado.getObjetos().put(s.toString(), err);
	    	}
	    	ban=false;
        }
        return ban;
    }
    
    public void validacionInterna(List<GfaceHdrDoc> pv,UnUser userInSession) {
        //busca las transacciones de gface pendientes de validar
        //flujo de validacion de datos        
        if (pv != null && !pv.isEmpty()) {
            //actualiza el estado de los registros en la BD
            sc.cambiarPenValidPorValidEnProc(pv,userInSession);
            //retorna el resultado de las validaciones
            ResultadoValidacionPojo rvi = internalValidation(pv);
            //tiene errores de validaciones internas?
            if (rvi.getBuenos() != null && !rvi.getBuenos().isEmpty()) {
                sc.cambiarValidEnProcPorListaParaEnv(rvi.getBuenos(),userInSession);
            }
            if (rvi.getMalos() != null && rvi.getMalos().tieneArchivosMalos()) {
                sc.cambiarValidEnProcPorValidInterInval(rvi.getMalos().getHeaderConErrores(),userInSession);
            }
        }
    }

    public GfaceHdrDocEjbLocal getGfaceHdrDocEjbLocal() {
        return gfaceHdrDocEjbLocal;
    }

    public void setGfaceHdrDocEjbLocal(GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal) {
        this.gfaceHdrDocEjbLocal = gfaceHdrDocEjbLocal;
    }
    
    public void registrarError(GfaceHdrDoc encabezado){
    	
    }
    public ControlErroresEjbLocal getCe() {
		return ce;
	}

	public void setCe(ControlErroresEjbLocal ce) {
		this.ce = ce;
	}
}
