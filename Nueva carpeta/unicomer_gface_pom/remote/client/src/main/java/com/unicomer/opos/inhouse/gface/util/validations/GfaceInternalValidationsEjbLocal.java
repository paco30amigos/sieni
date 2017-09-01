package com.unicomer.opos.inhouse.gface.util.validations;

import java.util.List;
import java.util.Set;

import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ValidationPojo;
import com.unicomer.opos.inhouse.security.entities.UnUser;

public interface GfaceInternalValidationsEjbLocal {
	//public ResultadoValidacionPojo internalValidation(List<GfaceHdrDoc> encabezados);

    public List<ValidationPojo> validarEncabezado(GfaceHdrDoc encabezado);

    public List<ValidationPojo> validarDetalle(Set<GfaceDtlDoc> detalle);

    public List<ValidationPojo> validarDocumentos(Set<GfaceAssocDoc> docs);

    public boolean validarTotales();
    
    public void validacionInterna(List<GfaceHdrDoc> pv,UnUser userInSession);
    
}
