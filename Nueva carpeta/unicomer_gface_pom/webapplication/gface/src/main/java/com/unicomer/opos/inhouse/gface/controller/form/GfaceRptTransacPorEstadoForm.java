/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.controller.form;

import java.util.Date;

/**
 *
 * @author francisco_medina
 */
public class GfaceRptTransacPorEstadoForm {

    //parametros para filtrar
    private Date docDateIni;
    private Date docDateFin;
    private String documentType;
    private String serie;
    private String numero;
    private String nitComprador;
    private String tipo;
    private String tienda;
    private String formato;
	
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
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getNitComprador() {
		return nitComprador;
	}
	public void setNitComprador(String nitComprador) {
		this.nitComprador = nitComprador;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTienda() {
		return tienda;
	}
	public void setTienda(String tienda) {
		this.tienda = tienda;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
}
