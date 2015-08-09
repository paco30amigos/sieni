/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.pojos;

/**
 *
 * @author Laptop
 */
public class UsuariosPojo {

    private Long idUsuario;
    private String usuario;
    private String tipoUsuario;
    private String tipoPermiso;
    private String nombre;
    private String pass1;
    private String pass2;
    private String codTipoUsuario;
    private long codTipoPermiso;
    private String codNombre;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getTipoPermiso() {
        return tipoPermiso;
    }

    public void setTipoPermiso(String tipoPermiso) {
        this.tipoPermiso = tipoPermiso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getCodTipoUsuario() {
        return codTipoUsuario;
    }

    public void setCodTipoUsuario(String codTipoUsuario) {
        this.codTipoUsuario = codTipoUsuario;
    }

    public long getCodTipoPermiso() {
        return codTipoPermiso;
    }

    public void setCodTipoPermiso(long codTipoPermiso) {
        this.codTipoPermiso = codTipoPermiso;
    }

    public String getCodNombre() {
        return codNombre;
    }

    public void setCodNombre(String codNombre) {
        this.codNombre = codNombre;
    }

}
