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
public class SieniEvalSupCompPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_eval_sup_comp")
    private long idEvalSupComp;
    @Basic(optional = false)
    @Column(name = "f_sup_comp")
    private long fSupComp;

    public SieniEvalSupCompPK() {
    }

    public SieniEvalSupCompPK(long idEvalSupComp, long fSupComp) {
        this.idEvalSupComp = idEvalSupComp;
        this.fSupComp = fSupComp;
    }

    public long getIdEvalSupComp() {
        return idEvalSupComp;
    }

    public void setIdEvalSupComp(long idEvalSupComp) {
        this.idEvalSupComp = idEvalSupComp;
    }

    public long getFSupComp() {
        return fSupComp;
    }

    public void setFSupComp(long fSupComp) {
        this.fSupComp = fSupComp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEvalSupComp;
        hash += (int) fSupComp;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvalSupCompPK)) {
            return false;
        }
        SieniEvalSupCompPK other = (SieniEvalSupCompPK) object;
        if (this.idEvalSupComp != other.idEvalSupComp) {
            return false;
        }
        if (this.fSupComp != other.fSupComp) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvalSupCompPK[ idEvalSupComp=" + idEvalSupComp + ", fSupComp=" + fSupComp + " ]";
    }
    
}
