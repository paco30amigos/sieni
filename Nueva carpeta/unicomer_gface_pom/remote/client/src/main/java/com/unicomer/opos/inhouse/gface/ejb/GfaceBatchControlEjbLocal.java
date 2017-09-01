package com.unicomer.opos.inhouse.gface.ejb;

import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.gface.entity.GfaceBtchFiles;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ResultadoValidacionPojo;
import com.unicomer.opos.inhouse.security.entities.UnUser;

@Remote
public interface GfaceBatchControlEjbLocal {
	public void actualizarBatchProcesadosPorGuatefactura(ResultadoValidacionPojo procesados,UnUser userInSession);
	public GfaceBtchFiles createBatch(List<GfaceHdrDoc> headers, String fileName,String informacionDocumento,UnUser userInSession);
	public void cambiarSubidaEnProgresoPorEsperandoRespuesta(GfaceBtchFiles batch,UnUser userInSession);
	public List<String> getFilesExists(List<String> nombreArchivos,String extension);
}
