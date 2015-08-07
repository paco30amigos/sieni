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
public class SieniDocentRolPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_docent_r_dud")
    private long idDocentRDud;
    @Basic(optional = false)
    @Column(name = "f_rol_doc")
    private long fRolDoc;

    public SieniDocentRolPK() {
    }

    public SieniDocentRolPK(long idDocentRDud, long fRolDoc) {
        this.idDocentRDud = idDocentRDud;
        this.fRolDoc = fRolDoc;
    }

    public long getIdDocentRDud() {
        return idDocentRDud;
    }

    public void setIdDocentRDud(long idDocentRDud) {
        this.idDocentRDud = idDocentRDud;
    }

    public long getFRolDoc() {
        return fRolDoc;
    }

    public void setFRolDoc(long fRolDoc) {
        this.fRolDoc = fRolDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idDocentRDud;
        hash += (int) fRolDoc;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniDocentRolPK)) {
            return false;
        }
        SieniDocentRolPK other = (SieniDocentRolPK) object;
        if (this.idDocentRDud != other.idDocentRDud) {
            return false;
        }
        if (this.fRolDoc != other.fRolDoc) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniDocentRolPK[ idDocentRDud=" + idDocentRDud + ", fRolDoc=" + fRolDoc + " ]";
    }
    
}
