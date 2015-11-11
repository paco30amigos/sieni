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
@Table(name = "sieni_nota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniNota.findAllNoEliminadas", query = "SELECT s FROM SieniNota s where s.ntEstado not in (:estado) and s.idAlumno.alEstado not in (:estado)"),
    @NamedQuery(name = "SieniNota.findAll", query = "SELECT s FROM SieniNota s"),
    @NamedQuery(name = "SieniNota.findByAlumno", query = "SELECT s FROM SieniNota s where s.idAlumno.idAlumno=:idAlumno and s.ntEstado not in (:estado)"),
    @NamedQuery(name = "SieniNota.findRango", query = "SELECT s FROM SieniNota s where s.ntFechaIngreso>=:desde and s.ntFechaIngreso<=:hasta"),
    @NamedQuery(name = "SieniNota.findRangoGrado", query = "SELECT s FROM SieniNota s where s.ntFechaIngreso>=:desde and s.ntFechaIngreso<=:hasta and s.idEvaluacion.idCurso.idGrado.idGrado=:grado"),
    @NamedQuery(name = "SieniNota.findRangoGradoSeccion", query = "SELECT s FROM SieniNota s where s.ntFechaIngreso>=:desde and s.ntFechaIngreso<=:hasta and s.idEvaluacion.idCurso.idGrado.idGrado=:grado and s.idEvaluacion.idCurso.idSeccion.idSeccion=:seccion"),
    @NamedQuery(name = "SieniNota.findNotaRegistrada", query = "SELECT s FROM SieniNota s where s.idAlumno.idAlumno=:idAlumno and s.idEvaluacion.idEvaluacion=:idEvaluacion and s.ntEstado not in (:estado) and s.idAlumno.alEstado not in (:estado) and s.idEvaluacion.evEstado not in (:estado)"),
    @NamedQuery(name = "SieniNota.findByIdNota", query = "SELECT s FROM SieniNota s WHERE s.idNota = :idNota"),
    @NamedQuery(name = "SieniNota.findByNtCalificacion", query = "SELECT s FROM SieniNota s WHERE s.ntCalificacion = :ntCalificacion"),
    @NamedQuery(name = "SieniNota.findByNtTipoIngreso", query = "SELECT s FROM SieniNota s WHERE s.ntTipoIngreso = :ntTipoIngreso"),
    @NamedQuery(name = "SieniNota.findByGradoSecMatRpt", query = "SELECT s FROM SieniNota s WHERE s.idEvaluacion.idMateria.idMateria =:materia and s.idEvaluacion.idCurso.idGrado.idGrado =:grado and s.idEvaluacion.idCurso.idSeccion.idSeccion =:seccion and s.idEvaluacion.evTipo =:tipo and s.ntFechaIngreso BETWEEN :desde AND :hasta")})
public class SieniNota implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_sieni_nota")
    @SequenceGenerator(name = "sec_sieni_nota", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_nota")
    @Basic(optional = false)
    @Column(name = "id_nota")
    private Long idNota;
    @Column(name = "nt_anio")
    private Integer ntAnio;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nt_calificacion")
    private Double ntCalificacion;
    @Column(name = "nt_tipo_ingreso")
    private String ntTipoIngreso;
    @Column(name = "nt_estado")
    private Character ntEstado;
    @Column(name = "nt_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date ntFechaIngreso;
    @Column(name = "nt_docente")
    private Long ntDocente;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;
    @JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion")
    @ManyToOne
    private SieniEvaluacion idEvaluacion;
    @Transient
    private String tipoIngreso;
    @Transient
    private String nombreCompleto;
    @Transient
    private List<String> errores;

    public SieniNota() {
    }

    public SieniNota(Long idNota) {
        this.idNota = idNota;
    }

    public Long getIdNota() {
        return idNota;
    }

    public void setIdNota(Long idNota) {
        this.idNota = idNota;
    }

    public Double getNtCalificacion() {
        return ntCalificacion;
    }

    public void setNtCalificacion(Double ntCalificacion) {
        this.ntCalificacion = ntCalificacion;
    }

    public String getNtTipoIngreso() {
        return ntTipoIngreso;
    }

    public void setNtTipoIngreso(String ntTipoIngreso) {
        this.ntTipoIngreso = ntTipoIngreso;
    }

    public SieniAlumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(SieniAlumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    public SieniEvaluacion getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(SieniEvaluacion idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNota != null ? idNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniNota)) {
            return false;
        }
        SieniNota other = (SieniNota) object;
        if ((this.idNota == null && other.idNota != null) || (this.idNota != null && !this.idNota.equals(other.idNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniNota[ idNota=" + idNota + " ]";
    }

    public String getTipoIngreso() {
        if (ntTipoIngreso.equals("A")) {
            tipoIngreso = "Automático";
        } else if (ntTipoIngreso.equals("M")) {
            tipoIngreso = "Manual";
        } else {
            tipoIngreso = "Excel";
        }
        return tipoIngreso;
    }

    public void setTipoIngreso(String tipoIngreso) {
        this.tipoIngreso = tipoIngreso;
    }

    public Character getNtEstado() {
        return ntEstado;
    }

    public void setNtEstado(Character ntEstado) {
        this.ntEstado = ntEstado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public Integer getNtAnio() {
        return ntAnio;
    }

    public void setNtAnio(Integer ntAnio) {
        this.ntAnio = ntAnio;
    }

    public Date getNtFechaIngreso() {
        return ntFechaIngreso;
    }

    public void setNtFechaIngreso(Date ntFechaIngreso) {
        this.ntFechaIngreso = ntFechaIngreso;
    }

    public Long getNtDocente() {
        return ntDocente;
    }

    public void setNtDocente(Long ntDocente) {
        this.ntDocente = ntDocente;
    }

}
