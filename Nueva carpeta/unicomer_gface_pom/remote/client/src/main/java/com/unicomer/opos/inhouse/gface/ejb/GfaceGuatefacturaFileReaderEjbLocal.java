package com.unicomer.opos.inhouse.gface.ejb;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.security.entities.UnUser;

@Remote
public interface GfaceGuatefacturaFileReaderEjbLocal {
	public HashMap<Boolean, List<GfaceHdrDoc>> leerRegistros2(List<GfaceHdrDoc> registros);
	public void procesarArchivosValidadosGuatefacturas(List<GfaceHdrDoc> ee,UnUser userInSession);
	public void readAllFiles();
}
