/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Laptop
 */
@Embeddable
public class SieniAlumnRolPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_alumn_rol")
    private long idAlumnRol;
    @Basic(optional = false)
    @Column(name = "f_rol")
    private long fRol;

    public SieniAlumnRolPK() {
    }

    public SieniAlumnRolPK(long idAlumnRol, long fRol) {
        this.idAlumnRol = idAlumnRol;
        this.fRol = fRol;
    }

    public long getIdAlumnRol() {
        return idAlumnRol;
    }

    public void setIdAlumnRol(long idAlumnRol) {
        this.idAlumnRol = idAlumnRol;
    }

    public long getFRol() {
        return fRol;
    }

    public void setFRol(long fRol) {
        this.fRol = fRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAlumnRol;
        hash += (int) fRol;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAlumnRolPK)) {
            return false;
        }
        SieniAlumnRolPK other = (SieniAlumnRolPK) object;
        if (this.idAlumnRol != other.idAlumnRol) {
            return false;
        }
        if (this.fRol != other.fRol) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAlumnRolPK[ idAlumnRol=" + idAlumnRol + ", fRol=" + fRol + " ]";
    }
    
}
