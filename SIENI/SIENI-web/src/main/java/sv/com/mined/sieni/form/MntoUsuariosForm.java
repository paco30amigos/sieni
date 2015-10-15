/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.form;

import java.util.List;
import sv.com.mined.sieni.pojos.UsuariosPojo;

/**
 *
 * @author Laptop
 */
public class MntoUsuariosForm {

    private int indexMenu;
    private UsuariosPojo eliminar;

    // consulta de alumnos
    private List<UsuariosPojo> usuariosList;
    //registro de alumnos
    private UsuariosPojo usuarioNuevo;
    private List<UsuariosPojo> nombresDisponibles;
    //modificacion de alumnos
    private UsuariosPojo usuarioModifica;
    private List<UsuariosPojo> nombresDisponiblesModifica;

    private UsuariosPojo usuarioModPass;
    
    public int getIndexMenu() {
        return indexMenu;
    }

    public void setIndexMenu(int indexMenu) {
        this.indexMenu = indexMenu;
    }

    public UsuariosPojo getEliminar() {
        return eliminar;
    }

    public void setEliminar(UsuariosPojo eliminar) {
        this.eliminar = eliminar;
    }

    public List<UsuariosPojo> getUsuariosList() {
        return usuariosList;
    }

    public void setUsuariosList(List<UsuariosPojo> usuariosList) {
        this.usuariosList = usuariosList;
    }

    public UsuariosPojo getUsuarioNuevo() {
        return usuarioNuevo;
    }

    public void setUsuarioNuevo(UsuariosPojo usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }

    public UsuariosPojo getUsuarioModifica() {
        return usuarioModifica;
    }

    public void setUsuarioModifica(UsuariosPojo usuarioModifica) {
        this.usuarioModifica = usuarioModifica;
    }

    public List<UsuariosPojo> getNombresDisponibles() {
        return nombresDisponibles;
    }

    public void setNombresDisponibles(List<UsuariosPojo> nombresDisponibles) {
        this.nombresDisponibles = nombresDisponibles;
    }

    public List<UsuariosPojo> getNombresDisponiblesModifica() {
        return nombresDisponiblesModifica;
    }

    public void setNombresDisponiblesModifica(List<UsuariosPojo> nombresDisponiblesModifica) {
        this.nombresDisponiblesModifica = nombresDisponiblesModifica;
    }

    public UsuariosPojo getUsuarioModPass() {
        return usuarioModPass;
    }

    public void setUsuarioModPass(UsuariosPojo usuarioModPass) {
        this.usuarioModPass = usuarioModPass;
    }
    
}
