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
    @NamedQuery(name = "SieniAlumnRDud.findByIdAlumnRDud", query = "SELECT s FROM SieniAlumnRDud s WHERE s.idAlumnRDud = :idAlumnRDud"),
    @NamedQuery(name = "SieniAlumnRDud.findByFResolDuda", query = "SELECT s FROM SieniAlumnRDud s WHERE s.fResolDuda = :fResolDuda")})
public class SieniAlumnRDud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_alumn_r_dud")
    private Long idAlumnRDud;
    @Basic(optional = false)
    @Column(name = "f_resol_duda")
    private long fResolDuda;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;

    public SieniAlumnRDud() {
    }

    public SieniAlumnRDud(Long idAlumnRDud) {
        this.idAlumnRDud = idAlumnRDud;
    }

    public SieniAlumnRDud(Long idAlumnRDud, long fResolDuda) {
        this.idAlumnRDud = idAlumnRDud;
        this.fResolDuda = fResolDuda;
    }

    public Long getIdAlumnRDud() {
        return idAlumnRDud;
    }

    public void setIdAlumnRDud(Long idAlumnRDud) {
        this.idAlumnRDud = idAlumnRDud;
    }

    public long getFResolDuda() {
        return fResolDuda;
    }

    public void setFResolDuda(long fResolDuda) {
        this.fResolDuda = fResolDuda;
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
        hash += (idAlumnRDud != null ? idAlumnRDud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAlumnRDud)) {
            return false;
        }
        SieniAlumnRDud other = (SieniAlumnRDud) object;
        if ((this.idAlumnRDud == null && other.idAlumnRDud != null) || (this.idAlumnRDud != null && !this.idAlumnRDud.equals(other.idAlumnRDud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAlumnRDud[ idAlumnRDud=" + idAlumnRDud + " ]";
    }
    
}
