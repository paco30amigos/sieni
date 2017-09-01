/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.controller.form;

import com.unicomer.opos.inhouse.gface.entity.GfaceAssocDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceDtlDoc;
import com.unicomer.opos.inhouse.gface.entity.GfaceHdrDoc;
import com.unicomer.opos.inhouse.gface.pojo.ComboBoxPojo;
import com.unicomer.opos.inhouse.gface.pojo.DatosPojo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class GfaceDepuracionErroresForm {

    //parametros para filtrar
    private Date docDateIni;
    private Date docDateFin;
    private String documentType;
    private String warning;
    private String error;
    private String tienda;

    //listas
    private List<ComboBoxPojo> docTypeList;
    private List<ComboBoxPojo> warningsList;
    private List<ComboBoxPojo> errorsList;
    private List<ComboBoxPojo> tiendaList;

    //otros datos mostrados en pantalla
    private Long transacErrors;
    private Long transacWarning;
    private Long transacPendingToSend;

    //datos de tabla
    private List<DatosPojo> tabla;
    private List<DatosPojo> seleccionados;
    private DatosPojo datoActual;
    private Integer index;
    
    //campos para procesos
    private List<GfaceHdrDoc> hdrModificados;
    private List<GfaceDtlDoc> detModificados;
    private List<GfaceAssocDoc> docModificados;
    private List<GfaceHdrDoc> hdrOriginales;
    private List<GfaceDtlDoc> detOriginales;
    private List<GfaceAssocDoc> docOriginales;

    public Date getDocDateIni() {
        return docDateIni;
    }

    public void setDocDateIni(Date docDateIni) {
        this.docDateIni = docDateIni;
    }

    public Date getDocDateFin() {
        return docDateFin;
    }

    public void setDocDateFin(Date docDateFin) {
        this.docDateFin = docDateFin;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<ComboBoxPojo> getDocTypeList() {
        return docTypeList;
    }

    public void setDocTypeList(List<ComboBoxPojo> docTypeList) {
        this.docTypeList = docTypeList;
    }

    public Long getTransacErrors() {
        return transacErrors;
    }

    public void setTransacErrors(Long transacErrors) {
        this.transacErrors = transacErrors;
    }

    public Long getTransacWarning() {
        return transacWarning;
    }

    public void setTransacWarning(Long transacWarning) {
        this.transacWarning = transacWarning;
    }

    public Long getTransacPendingToSend() {
        return transacPendingToSend;
    }

    public void setTransacPendingToSend(Long transacPendingToSend) {
        this.transacPendingToSend = transacPendingToSend;
    }

    public List<ComboBoxPojo> getWarningsList() {
        return warningsList;
    }

    public void setWarningsList(List<ComboBoxPojo> warningsList) {
        this.warningsList = warningsList;
    }

    public List<ComboBoxPojo> getErrorsList() {
        return errorsList;
    }

    public void setErrorsList(List<ComboBoxPojo> errorsList) {
        this.errorsList = errorsList;
    }

    public List<DatosPojo> getTabla() {
        return tabla;
    }

    public void setTabla(List<DatosPojo> tabla) {
        this.tabla = tabla;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public List<ComboBoxPojo> getTiendaList() {
        return tiendaList;
    }

    public void setTiendaList(List<ComboBoxPojo> tiendaList) {
        this.tiendaList = tiendaList;
    }

    public List<DatosPojo> getSeleccionados() {
        return seleccionados;
    }

    public void setSeleccionados(List<DatosPojo> seleccionados) {
        this.seleccionados = seleccionados;
    }

	public DatosPojo getDatoActual() {
		return datoActual;
	}

	public void setDatoActual(DatosPojo datoActual) {
		this.datoActual = datoActual;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

    public List<GfaceHdrDoc> getHdrModificados() {
        return hdrModificados;
    }

    public void setHdrModificados(List<GfaceHdrDoc> hdrModificados) {
        this.hdrModificados = hdrModificados;
    }

    public List<GfaceDtlDoc> getDetModificados() {
        return detModificados;
    }

    public void setDetModificados(List<GfaceDtlDoc> detModificados) {
        this.detModificados = detModificados;
    }

    public List<GfaceAssocDoc> getDocModificados() {
        return docModificados;
    }

    public void setDocModificados(List<GfaceAssocDoc> docModificados) {
        this.docModificados = docModificados;
    }

    public List<GfaceHdrDoc> getHdrOriginales() {
        return hdrOriginales;
    }

    public void setHdrOriginales(List<GfaceHdrDoc> hdrOriginales) {
        this.hdrOriginales = hdrOriginales;
    }

    public List<GfaceDtlDoc> getDetOriginales() {
        return detOriginales;
    }

    public void setDetOriginales(List<GfaceDtlDoc> detOriginales) {
        this.detOriginales = detOriginales;
    }

    public List<GfaceAssocDoc> getDocOriginales() {
        return docOriginales;
    }

    public void setDocOriginales(List<GfaceAssocDoc> docOriginales) {
        this.docOriginales = docOriginales;
    }

}
