package com.unicomer.opos.inhouse.gface.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.security.entities.UnUser;

@Remote
public interface GfaceSendDataToGuatefacturasEjbLocal {
	public boolean sendDataToGuatefacturas();
	public void procesarArchivosValidados(List<GfaceHdrDoc> lpe,UnUser userInSession);
}
