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
@Table(name = "sieni_docent_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniDocentRol.findAll", query = "SELECT s FROM SieniDocentRol s"),
    @NamedQuery(name = "SieniDocentRol.findByIdDocentRDud", query = "SELECT s FROM SieniDocentRol s WHERE s.sieniDocentRolPK.idDocentRDud = :idDocentRDud"),
    @NamedQuery(name = "SieniDocentRol.findByFRolDoc", query = "SELECT s FROM SieniDocentRol s WHERE s.sieniDocentRolPK.fRolDoc = :fRolDoc")})
public class SieniDocentRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SieniDocentRolPK sieniDocentRolPK;
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
    @ManyToOne
    private SieniDocente idDocente;

    public SieniDocentRol() {
    }

    public SieniDocentRol(SieniDocentRolPK sieniDocentRolPK) {
        this.sieniDocentRolPK = sieniDocentRolPK;
    }

    public SieniDocentRol(long idDocentRDud, long fRolDoc) {
        this.sieniDocentRolPK = new SieniDocentRolPK(idDocentRDud, fRolDoc);
    }

    public SieniDocentRolPK getSieniDocentRolPK() {
        return sieniDocentRolPK;
    }

    public void setSieniDocentRolPK(SieniDocentRolPK sieniDocentRolPK) {
        this.sieniDocentRolPK = sieniDocentRolPK;
    }

    public SieniDocente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(SieniDocente idDocente) {
        this.idDocente = idDocente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sieniDocentRolPK != null ? sieniDocentRolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniDocentRol)) {
            return false;
        }
        SieniDocentRol other = (SieniDocentRol) object;
        if ((this.sieniDocentRolPK == null && other.sieniDocentRolPK != null) || (this.sieniDocentRolPK != null && !this.sieniDocentRolPK.equals(other.sieniDocentRolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniDocentRol[ sieniDocentRolPK=" + sieniDocentRolPK + " ]";
    }
    
}
