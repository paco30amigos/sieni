package com.unicomer.opos.inhouse.gface.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.security.entities.UnUser;

@Remote
public interface GfaceStatusControlEjbLocal {
	public List<GfaceHdrDoc> getPendientesValidar();
	public List<GfaceHdrDoc> getValidacionesEnProceso();
	public List<GfaceHdrDoc> getInvalidoPorValidacionesInternas();
	public List<GfaceHdrDoc> getListasParaEnviar();
	public List<GfaceHdrDoc> getEnviadasExitosamente();
	public List<GfaceHdrDoc> getInvalidas(String documentType,String warning,String error,String tienda);
	public void cambiarEnvExitosamentePorAutoriz(List<GfaceHdrDoc> registros,UnUser userInSession);
	public void cambiarEnvExitosamentePorValidExternInval(List<GfaceHdrDoc> registros,UnUser userInSession);
	public void cambiarPenValidPorValidEnProc(List<GfaceHdrDoc> registros,UnUser userInSession);
	public void cambiarValidEnProcPorListaParaEnv(List<GfaceHdrDoc> registros,UnUser userInSession) ;
	public void cambiarValidEnProcPorValidInterInval(List<GfaceHdrDoc> registros,UnUser userInSession);
	public void cambiarValidInterInvalPorPenValid(List<GfaceHdrDoc> registros,UnUser userInSession);
	public void cambiarListaParaEnvPorEnvExitosamente(List<GfaceHdrDoc> registros,UnUser userInSession);
}
