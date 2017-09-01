package com.unicomer.opos.inhouse.gface.ejb;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceErrRegCtg;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ErroresPojo;

@Remote
public interface ControlErroresEjbLocal {
	public GfaceHdrDoc setErroresHeader(ErroresPojo pojo, GfaceHdrDoc header);
	public void crearErrorRegistro(List<GfaceHdrDoc> headers);
	public void actualizarDetalleErrores(HashMap<String, GfaceErrRegCtg> datos, List<GfaceHdrDoc> headers);
}
