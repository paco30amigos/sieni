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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_alumn_rol", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_alumn_rol", "f_rol"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAlumnRol.findAll", query = "SELECT s FROM SieniAlumnRol s"),
    @NamedQuery(name = "SieniAlumnRol.findByIdAlumnRol", query = "SELECT s FROM SieniAlumnRol s WHERE s.sieniAlumnRolPK.idAlumnRol = :idAlumnRol"),
    @NamedQuery(name = "SieniAlumnRol.findByFRol", query = "SELECT s FROM SieniAlumnRol s WHERE s.sieniAlumnRolPK.fRol = :fRol")})
public class SieniAlumnRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SieniAlumnRolPK sieniAlumnRolPK;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;

    public SieniAlumnRol() {
    }

    public SieniAlumnRol(SieniAlumnRolPK sieniAlumnRolPK) {
        this.sieniAlumnRolPK = sieniAlumnRolPK;
    }

    public SieniAlumnRol(long idAlumnRol, long fRol) {
        this.sieniAlumnRolPK = new SieniAlumnRolPK(idAlumnRol, fRol);
    }

    public SieniAlumnRolPK getSieniAlumnRolPK() {
        return sieniAlumnRolPK;
    }

    public void setSieniAlumnRolPK(SieniAlumnRolPK sieniAlumnRolPK) {
        this.sieniAlumnRolPK = sieniAlumnRolPK;
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
        hash += (sieniAlumnRolPK != null ? sieniAlumnRolPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAlumnRol)) {
            return false;
        }
        SieniAlumnRol other = (SieniAlumnRol) object;
        if ((this.sieniAlumnRolPK == null && other.sieniAlumnRolPK != null) || (this.sieniAlumnRolPK != null && !this.sieniAlumnRolPK.equals(other.sieniAlumnRolPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAlumnRol[ sieniAlumnRolPK=" + sieniAlumnRolPK + " ]";
    }
    
}
