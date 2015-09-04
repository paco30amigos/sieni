/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos;

import sv.com.mined.sieni.model.SieniArchivo;
import sv.com.mined.sieni.model.SieniComponente;

/**
 *
 * @author francisco_medina
 */
public class ComponenteArchivoPojo {

    private SieniArchivo archivo;
    private SieniComponente componente;

    public SieniArchivo getArchivo() {
        return archivo;
    }

    public void setArchivo(SieniArchivo archivo) {
        this.archivo = archivo;
    }

    public SieniComponente getComponente() {
        return componente;
    }

    public void setComponente(SieniComponente componente) {
        this.componente = componente;
    }

}
