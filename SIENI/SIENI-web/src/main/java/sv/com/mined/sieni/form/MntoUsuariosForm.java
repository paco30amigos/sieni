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
    //modificacion de alumnos
    private UsuariosPojo usuarioModifica;

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
}
