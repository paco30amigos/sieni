/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniRol.findAll", query = "SELECT s FROM SieniRol s"),
    @NamedQuery(name = "SieniRol.findByIdRol", query = "SELECT s FROM SieniRol s WHERE s.idRol = :idRol"),
    @NamedQuery(name = "SieniRol.findByRlTipo", query = "SELECT s FROM SieniRol s WHERE s.rlTipo = :rlTipo"),
    @NamedQuery(name = "SieniRol.findByRlDescripcion", query = "SELECT s FROM SieniRol s WHERE s.rlDescripcion = :rlDescripcion")})
public class SieniRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_rol")
    private Long idRol;
    @Column(name = "rl_tipo")
    private Character rlTipo;
    @Column(name = "rl_descripcion")
    private String rlDescripcion;

    public SieniRol() {
    }

    public SieniRol(Long idRol) {
        this.idRol = idRol;
    }

    public Long getIdRol() {
        return idRol;
    }

    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    public Character getRlTipo() {
        return rlTipo;
    }

    public void setRlTipo(Character rlTipo) {
        this.rlTipo = rlTipo;
    }

    public String getRlDescripcion() {
        return rlDescripcion;
    }

    public void setRlDescripcion(String rlDescripcion) {
        this.rlDescripcion = rlDescripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniRol)) {
            return false;
        }
        SieniRol other = (SieniRol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniRol[ idRol=" + idRol + " ]";
    }
    
}
