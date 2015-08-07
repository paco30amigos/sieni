/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_curso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniCurso.findAll", query = "SELECT s FROM SieniCurso s"),
    @NamedQuery(name = "SieniCurso.findByIdCurso", query = "SELECT s FROM SieniCurso s WHERE s.idCurso = :idCurso"),
    @NamedQuery(name = "SieniCurso.findByCrNombre", query = "SELECT s FROM SieniCurso s WHERE s.crNombre = :crNombre"),
    @NamedQuery(name = "SieniCurso.findByCrCapacidad", query = "SELECT s FROM SieniCurso s WHERE s.crCapacidad = :crCapacidad"),
    @NamedQuery(name = "SieniCurso.findByCrFechaIngreso", query = "SELECT s FROM SieniCurso s WHERE s.crFechaIngreso = :crFechaIngreso")})
public class SieniCurso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_curso")
    private Long idCurso;
    @Column(name = "cr_nombre")
    private String crNombre;
    @Column(name = "cr_capacidad")
    private Integer crCapacidad;
    @Column(name = "cr_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date crFechaIngreso;
    @OneToMany(mappedBy = "idCurso")
    private List<SieniNoticia> sieniNoticiaList;
    @OneToMany(mappedBy = "idCurso")
    private List<SieniEvaluacion> sieniEvaluacionList;
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
    @ManyToOne
    private SieniDocente idDocente;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne
    private SieniMateria idMateria;
    @OneToMany(mappedBy = "idCurso")
    private List<SieniClase> sieniClaseList;

    public SieniCurso() {
    }

    public SieniCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public String getCrNombre() {
        return crNombre;
    }

    public void setCrNombre(String crNombre) {
        this.crNombre = crNombre;
    }

    public Integer getCrCapacidad() {
        return crCapacidad;
    }

    public void setCrCapacidad(Integer crCapacidad) {
        this.crCapacidad = crCapacidad;
    }

    public Date getCrFechaIngreso() {
        return crFechaIngreso;
    }

    public void setCrFechaIngreso(Date crFechaIngreso) {
        this.crFechaIngreso = crFechaIngreso;
    }

    @XmlTransient
    public List<SieniNoticia> getSieniNoticiaList() {
        return sieniNoticiaList;
    }

    public void setSieniNoticiaList(List<SieniNoticia> sieniNoticiaList) {
        this.sieniNoticiaList = sieniNoticiaList;
    }

    @XmlTransient
    public List<SieniEvaluacion> getSieniEvaluacionList() {
        return sieniEvaluacionList;
    }

    public void setSieniEvaluacionList(List<SieniEvaluacion> sieniEvaluacionList) {
        this.sieniEvaluacionList = sieniEvaluacionList;
    }

    public SieniDocente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(SieniDocente idDocente) {
        this.idDocente = idDocente;
    }

    public SieniMateria getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(SieniMateria idMateria) {
        this.idMateria = idMateria;
    }

    @XmlTransient
    public List<SieniClase> getSieniClaseList() {
        return sieniClaseList;
    }

    public void setSieniClaseList(List<SieniClase> sieniClaseList) {
        this.sieniClaseList = sieniClaseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCurso != null ? idCurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniCurso)) {
            return false;
        }
        SieniCurso other = (SieniCurso) object;
        if ((this.idCurso == null && other.idCurso != null) || (this.idCurso != null && !this.idCurso.equals(other.idCurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniCurso[ idCurso=" + idCurso + " ]";
    }
    
}
