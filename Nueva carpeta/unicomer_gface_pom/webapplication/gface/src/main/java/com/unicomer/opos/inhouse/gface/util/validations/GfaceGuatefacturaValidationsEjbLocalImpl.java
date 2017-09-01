/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.util.validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.annotation.Resource;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.opos.inhouse.gface.ejb.GfaceGuatefacturaFileReaderEjbLocal;
import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ArchivosMalosPojo;
import com.unicomer.opos.inhouse.gface.pojo.ResultadoValidacionPojo;
import com.unicomer.opos.inhouse.gface.pojo.ValidationPojo;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class GfaceGuatefacturaValidationsEjbLocalImpl implements GfaceGuatefacturaValidationsEjbLocal {

    //para cuando no se hagan modificaciones sobre los archivos enviados anteriormente
    //por ejemplo se sube un archivo que dio error, pero no se modificó y se intenta
    //volver a subir
	@Resource(lookup = JNDIUnicomerGface.GfaceGuatefacturaFileReaderEjbLocal)
	private GfaceGuatefacturaFileReaderEjbLocal gfr;
    private ResultadoValidacionPojo validPojo;

    public ResultadoValidacionPojo guateFacturasValidation(List<GfaceHdrDoc> registros) {
        validPojo = new ResultadoValidacionPojo();
        List<ValidationPojo> test;
        validPojo.setBuenos(new ArrayList<GfaceHdrDoc>());
        validPojo.setMalos(new ArchivosMalosPojo());
        validPojo.getMalos().setHeaderConErrores(new ArrayList<GfaceHdrDoc>());
        validPojo.getMalos().setHeader(new ArrayList<GfaceHdrDoc>());
        validPojo.getMalos().setDetalle(new ArrayList<GfaceDtlDoc>());
        validPojo.getMalos().setDoc(new ArrayList<GfaceAssocDoc>());

        for (GfaceHdrDoc header : registros) {
            test = new ArrayList<ValidationPojo>();
            test.addAll(validarEncabezado(header));
            test.addAll(validarDetalle(header.getGfaceDtlDocList()));
            test.addAll(validarDocumentos(header.getGfaceAssocDocList()));

            //primeras validaciones, validaciones previo a verificacion de guatefacturas
            if (ValidationPojo.isValid(test)) {
                validPojo.getBuenos().add(header);
            } else {
                validPojo.getMalos().getHeaderConErrores().add(header);
            }

            //segunda validacion, validaciones retornadas por guatefacturas
            if (!validPojo.getBuenos().isEmpty()) {
                HashMap<Boolean, List<GfaceHdrDoc>> resultado = gfr.leerRegistros2(validPojo.getBuenos());
                validPojo.setBuenos(resultado.get(Boolean.TRUE));
                validPojo.getMalos().getHeaderConErrores().addAll(resultado.get(Boolean.FALSE));
                //TODO procesar advertencias (caso en que este en bueno, pero tenga error tambien)
            }
        }
        return validPojo;
    }

    public List<ValidationPojo> validarEncabezado(GfaceHdrDoc encabezado) {
        List<ValidationPojo> ret = new ArrayList<ValidationPojo>();
        //registrar errores en la BD
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

//    public GfaceGuatefacturaFileReaderEjbLocalImpl getGfr() {
//        return gfr;
//    }
//
//    public void setGfr(GfaceGuatefacturaFileReaderEjbLocalImpl gfr) {
//        this.gfr = gfr;
//    }

}
