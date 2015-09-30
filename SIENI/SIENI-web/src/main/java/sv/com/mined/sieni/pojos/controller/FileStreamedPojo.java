/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos.controller;

import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniComponente;
import sv.com.mined.sieni.model.SieniTipoComponente;

/**
 *
 * @author francisco_medina
 */
public class FileStreamedPojo {

//    private StreamedContent archivo;
    private Integer index;
    private SieniArchivo archivoBD;
    private SieniTipoComponente tipoComponente;
    private SieniComponente componente;
    private String texto;

    public FileStreamedPojo() {
    }

//    public StreamedContent getArchivo() {
//        return archivo;
//    }
//
//    public void setArchivo(StreamedContent archivo) {
//        this.archivo = archivo;
//    }
    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public SieniArchivo getArchivoBD() {
        return archivoBD;
    }

    public void setArchivoBD(SieniArchivo archivoBD) {
        this.archivoBD = archivoBD;
    }

    public SieniTipoComponente getTipoComponente() {
        return tipoComponente;
    }

    public void setTipoComponente(SieniTipoComponente tipoComponente) {
        this.tipoComponente = tipoComponente;
    }

    public SieniComponente getComponente() {
        return componente;
    }

    public void setComponente(SieniComponente componente) {
        this.componente = componente;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
