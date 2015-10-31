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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
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
    @NamedQuery(name = "SieniCurso.findAllByMateria", query = "SELECT s FROM SieniCurso s where s.idMateria.idMateria=:idMateria and s.crEstado!='I'"),
    @NamedQuery(name = "SieniCurso.finByDocGrSecMat", query = "SELECT s FROM SieniCurso s WHERE s.idDocente.idDocente=:idDocente AND s.idGrado.idGrado=:idGrado AND s.idSeccion.idSeccion=:idSeccion AND s.idMateria.idMateria=:idMateria AND s.crNombre=:nombre"),
    @NamedQuery(name = "SieniCurso.findAByEstado", query = "SELECT s FROM SieniCurso s WHERE s.crEstado=:estado"),
    @NamedQuery(name = "SieniCurso.findByIdCurso", query = "SELECT s FROM SieniCurso s WHERE s.idCurso = :idCurso"),
    @NamedQuery(name = "SieniCurso.findByCrNombre", query = "SELECT s FROM SieniCurso s WHERE s.crNombre = :crNombre"),
    @NamedQuery(name = "SieniCurso.findByCrCapacidad", query = "SELECT s FROM SieniCurso s WHERE s.crCapacidad = :crCapacidad"),
    @NamedQuery(name = "SieniCurso.findByCrFechaIngreso", query = "SELECT s FROM SieniCurso s WHERE s.crFechaIngreso = :crFechaIngreso"),
    @NamedQuery(name = "SieniCurso.findActivos", query = "SELECT s FROM SieniCurso s WHERE s.crEstado != 'I' ") })
public class SieniCurso implements Serializable {
    @OneToMany(mappedBy = "idCurso")
    private List<SieniCursoAlumno> sieniCursoAlumnoList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_curso")
    @SequenceGenerator(name = "sec_sieni_curso", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_curso")
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
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    @ManyToOne
    private SieniGrado idGrado;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne
    private SieniMateria idMateria;
    @JoinColumn(name = "id_seccion", referencedColumnName = "id_seccion")
    @ManyToOne
    private SieniSeccion idSeccion;
    @OneToMany(mappedBy = "idCurso")
    private List<SieniClase> sieniClaseList;
    @Column(name = "cr_estado")
    private Character crEstado;
    
    @PrePersist
protected void onCreate() {
    crFechaIngreso = new Date();
}

    public Character getCrEstado() {
        return crEstado;
    }

    public void setCrEstado(Character crEstado) {
        this.crEstado = crEstado;
    }

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

    public SieniGrado getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(SieniGrado idGrado) {
        this.idGrado = idGrado;
    }

    public SieniMateria getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(SieniMateria idMateria) {
        this.idMateria = idMateria;
    }

    public SieniSeccion getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(SieniSeccion idSeccion) {
        this.idSeccion = idSeccion;
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

    @XmlTransient
    public List<SieniCursoAlumno> getSieniCursoAlumnoList() {
        return sieniCursoAlumnoList;
    }

    public void setSieniCursoAlumnoList(List<SieniCursoAlumno> sieniCursoAlumnoList) {
        this.sieniCursoAlumnoList = sieniCursoAlumnoList;
    }
    
}
