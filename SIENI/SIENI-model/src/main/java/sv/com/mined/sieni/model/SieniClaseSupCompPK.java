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
 * @author bugtraq
 */
@Embeddable
public class SieniClaseSupCompPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_clase_sup_comp", nullable = false)
    private long idClaseSupComp;
    @Basic(optional = false)
    @Column(name = "f_comp_super_compon", nullable = false)
    private long fCompSuperCompon;

    public SieniClaseSupCompPK() {
    }

    public SieniClaseSupCompPK(long idClaseSupComp, long fCompSuperCompon) {
        this.idClaseSupComp = idClaseSupComp;
        this.fCompSuperCompon = fCompSuperCompon;
    }

    public long getIdClaseSupComp() {
        return idClaseSupComp;
    }

    public void setIdClaseSupComp(long idClaseSupComp) {
        this.idClaseSupComp = idClaseSupComp;
    }

    public long getFCompSuperCompon() {
        return fCompSuperCompon;
    }

    public void setFCompSuperCompon(long fCompSuperCompon) {
        this.fCompSuperCompon = fCompSuperCompon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idClaseSupComp;
        hash += (int) fCompSuperCompon;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniClaseSupCompPK)) {
            return false;
        }
        SieniClaseSupCompPK other = (SieniClaseSupCompPK) object;
        if (this.idClaseSupComp != other.idClaseSupComp) {
            return false;
        }
        if (this.fCompSuperCompon != other.fCompSuperCompon) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniClaseSupCompPK[ idClaseSupComp=" + idClaseSupComp + ", fCompSuperCompon=" + fCompSuperCompon + " ]";
    }
    
}
