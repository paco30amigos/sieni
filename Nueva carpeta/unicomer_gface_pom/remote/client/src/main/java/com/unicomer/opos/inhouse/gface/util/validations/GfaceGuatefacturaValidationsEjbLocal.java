package com.unicomer.opos.inhouse.gface.util.validations;

import java.util.List;

import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ResultadoValidacionPojo;

public interface GfaceGuatefacturaValidationsEjbLocal {
	public ResultadoValidacionPojo guateFacturasValidation(List<GfaceHdrDoc> registros);
}
