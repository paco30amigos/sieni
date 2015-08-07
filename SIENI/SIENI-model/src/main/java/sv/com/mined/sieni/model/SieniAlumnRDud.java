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
@Table(name = "sieni_alumn_r_dud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAlumnRDud.findAll", query = "SELECT s FROM SieniAlumnRDud s"),
    @NamedQuery(name = "SieniAlumnRDud.findByIdAlumnRDud", query = "SELECT s FROM SieniAlumnRDud s WHERE s.sieniAlumnRDudPK.idAlumnRDud = :idAlumnRDud"),
    @NamedQuery(name = "SieniAlumnRDud.findByFResolDuda", query = "SELECT s FROM SieniAlumnRDud s WHERE s.sieniAlumnRDudPK.fResolDuda = :fResolDuda")})
public class SieniAlumnRDud implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SieniAlumnRDudPK sieniAlumnRDudPK;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;

    public SieniAlumnRDud() {
    }

    public SieniAlumnRDud(SieniAlumnRDudPK sieniAlumnRDudPK) {
        this.sieniAlumnRDudPK = sieniAlumnRDudPK;
    }

    public SieniAlumnRDud(long idAlumnRDud, long fResolDuda) {
        this.sieniAlumnRDudPK = new SieniAlumnRDudPK(idAlumnRDud, fResolDuda);
    }

    public SieniAlumnRDudPK getSieniAlumnRDudPK() {
        return sieniAlumnRDudPK;
    }

    public void setSieniAlumnRDudPK(SieniAlumnRDudPK sieniAlumnRDudPK) {
        this.sieniAlumnRDudPK = sieniAlumnRDudPK;
    }

    public SieniAlumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(SieniAlumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sieniAlumnRDudPK != null ? sieniAlumnRDudPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAlumnRDud)) {
            return false;
        }
        SieniAlumnRDud other = (SieniAlumnRDud) object;
        if ((this.sieniAlumnRDudPK == null && other.sieniAlumnRDudPK != null) || (this.sieniAlumnRDudPK != null && !this.sieniAlumnRDudPK.equals(other.sieniAlumnRDudPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAlumnRDud[ sieniAlumnRDudPK=" + sieniAlumnRDudPK + " ]";
    }
    
}
