/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_alumn_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAlumnRol.findAll", query = "SELECT s FROM SieniAlumnRol s"),
    @NamedQuery(name = "SieniAlumnRol.findRolesAlumno", query = "SELECT s FROM SieniAlumnRol s where s.idAlumno=:idAlumno and s.sarEstado not in(:estado)"),
    @NamedQuery(name = "SieniAlumnRol.findAllNoInactivos", query = "SELECT s FROM SieniAlumnRol s where s.sarEstado not in (:estado) order by s.idAlumnRol"),
    @NamedQuery(name = "SieniAlumnRol.findByIdAlumnRol", query = "SELECT s FROM SieniAlumnRol s WHERE s.idAlumnRol = :idAlumnRol"),
    @NamedQuery(name = "SieniAlumnRol.findByFRol", query = "SELECT s FROM SieniAlumnRol s WHERE s.fRol = :fRol"),
    @NamedQuery(name = "SieniAlumnRol.findByEstado", query = "SELECT s FROM SieniAlumnRol s WHERE s.sarEstado = :sarEstado"),
    @NamedQuery(name = "SieniAlumnRol.findByFIngreso", query = "SELECT s FROM SieniAlumnRol s, SieniAlumno a WHERE s.idAlumno = :idAlumno AND s.idAlumno=a.idAlumno")})
public class SieniAlumnRol implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_alumn_rol")
    @SequenceGenerator(name = "sec_sieni_alumn_rol", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_alumn_rol")
    @Column(name = "id_alumn_rol")
    private Long idAlumnRol;
    @Basic(optional = false)
    @Column(name = "f_rol")
    private long fRol;
    @Column(name = "sar_estado")
    private Character sarEstado;
    @Column(name = "sar_fecha_ingreso", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sarFechaIngreso;

    @PrePersist
    protected void onCreate() {
        sarFechaIngreso = new Date();
    }
//    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
//    @ManyToOne
    @Column(name = "id_alumno")
    private Long idAlumno;
    @Transient
    private SieniAlumno alumno;

    public SieniAlumnRol() {
    }

    public SieniAlumnRol(Long idAlumnRol) {
        this.idAlumnRol = idAlumnRol;
    }

    public SieniAlumnRol(Long idAlumnRol, long fRol) {
        this.idAlumnRol = idAlumnRol;
        this.fRol = fRol;
    }

    public Long getIdAlumnRol() {
        return idAlumnRol;
    }

    public void setIdAlumnRol(Long idAlumnRol) {
        this.idAlumnRol = idAlumnRol;
    }

    public long getFRol() {
        return fRol;
    }

    public void setFRol(long fRol) {
        this.fRol = fRol;
    }

//    public SieniAlumno getIdAlumno() {
//        return idAlumno;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlumnRol != null ? idAlumnRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAlumnRol)) {
            return false;
        }
        SieniAlumnRol other = (SieniAlumnRol) object;
        if ((this.idAlumnRol == null && other.idAlumnRol != null) || (this.idAlumnRol != null && !this.idAlumnRol.equals(other.idAlumnRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAlumnRol[ idAlumnRol=" + idAlumnRol + " ]";
    }

    public Character getSarEstado() {
        return sarEstado;
    }

    public void setSarEstado(Character sarEstado) {
        this.sarEstado = sarEstado;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public SieniAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(SieniAlumno alumno) {
        this.alumno = alumno;
    }

    public Date getSarFechaIngreso() {
        return sarFechaIngreso;
    }

    public void setSarFechaIngreso(Date sarFechaIngreso) {
        this.sarFechaIngreso = sarFechaIngreso;
    }
    
}
