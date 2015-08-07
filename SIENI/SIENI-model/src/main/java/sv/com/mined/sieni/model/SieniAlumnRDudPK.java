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
public class SieniAlumnRDudPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_alumn_r_dud")
    private long idAlumnRDud;
    @Basic(optional = false)
    @Column(name = "f_resol_duda")
    private long fResolDuda;

    public SieniAlumnRDudPK() {
    }

    public SieniAlumnRDudPK(long idAlumnRDud, long fResolDuda) {
        this.idAlumnRDud = idAlumnRDud;
        this.fResolDuda = fResolDuda;
    }

    public long getIdAlumnRDud() {
        return idAlumnRDud;
    }

    public void setIdAlumnRDud(long idAlumnRDud) {
        this.idAlumnRDud = idAlumnRDud;
    }

    public long getFResolDuda() {
        return fResolDuda;
    }

    public void setFResolDuda(long fResolDuda) {
        this.fResolDuda = fResolDuda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idAlumnRDud;
        hash += (int) fResolDuda;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAlumnRDudPK)) {
            return false;
        }
        SieniAlumnRDudPK other = (SieniAlumnRDudPK) object;
        if (this.idAlumnRDud != other.idAlumnRDud) {
            return false;
        }
        if (this.fResolDuda != other.fResolDuda) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAlumnRDudPK[ idAlumnRDud=" + idAlumnRDud + ", fResolDuda=" + fResolDuda + " ]";
    }
    
}
