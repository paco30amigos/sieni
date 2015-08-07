/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_clase_sup_comp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniClaseSupComp.findAll", query = "SELECT s FROM SieniClaseSupComp s"),
    @NamedQuery(name = "SieniClaseSupComp.findByIdClaseSupComp", query = "SELECT s FROM SieniClaseSupComp s WHERE s.sieniClaseSupCompPK.idClaseSupComp = :idClaseSupComp"),
    @NamedQuery(name = "SieniClaseSupComp.findByFCompSuperCompon", query = "SELECT s FROM SieniClaseSupComp s WHERE s.sieniClaseSupCompPK.fCompSuperCompon = :fCompSuperCompon")})
public class SieniClaseSupComp implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SieniClaseSupCompPK sieniClaseSupCompPK;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
    @ManyToOne
    private SieniClase idClase;

    public SieniClaseSupComp() {
    }

    public SieniClaseSupComp(SieniClaseSupCompPK sieniClaseSupCompPK) {
        this.sieniClaseSupCompPK = sieniClaseSupCompPK;
    }

    public SieniClaseSupComp(long idClaseSupComp, long fCompSuperCompon) {
        this.sieniClaseSupCompPK = new SieniClaseSupCompPK(idClaseSupComp, fCompSuperCompon);
    }

    public SieniClaseSupCompPK getSieniClaseSupCompPK() {
        return sieniClaseSupCompPK;
    }

    public void setSieniClaseSupCompPK(SieniClaseSupCompPK sieniClaseSupCompPK) {
        this.sieniClaseSupCompPK = sieniClaseSupCompPK;
    }

    public SieniClase getIdClase() {
        return idClase;
    }

    public void setIdClase(SieniClase idClase) {
        this.idClase = idClase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sieniClaseSupCompPK != null ? sieniClaseSupCompPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniClaseSupComp)) {
            return false;
        }
        SieniClaseSupComp other = (SieniClaseSupComp) object;
        if ((this.sieniClaseSupCompPK == null && other.sieniClaseSupCompPK != null) || (this.sieniClaseSupCompPK != null && !this.sieniClaseSupCompPK.equals(other.sieniClaseSupCompPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniClaseSupComp[ sieniClaseSupCompPK=" + sieniClaseSupCompPK + " ]";
    }
    
}
