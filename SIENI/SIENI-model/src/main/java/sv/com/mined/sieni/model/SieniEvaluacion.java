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
@Table(name = "sieni_evaluacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvaluacion.findAll", query = "SELECT s FROM SieniEvaluacion s"),
    @NamedQuery(name = "SieniEvaluacion.findByIdEvaluacion", query = "SELECT s FROM SieniEvaluacion s WHERE s.idEvaluacion = :idEvaluacion"),
    @NamedQuery(name = "SieniEvaluacion.findByEvNombre", query = "SELECT s FROM SieniEvaluacion s WHERE s.evNombre = :evNombre"),
    @NamedQuery(name = "SieniEvaluacion.findActivos", query = "SELECT s FROM SieniEvaluacion s  WHERE s.evEstado NOT IN ('I')"),
    @NamedQuery(name = "SieniEvaluacion.findByEvPonderacion", query = "SELECT s FROM SieniEvaluacion s WHERE s.evPonderacion = :evPonderacion"),
    @NamedQuery(name = "SieniEvaluacion.findByEvVersion", query = "SELECT s FROM SieniEvaluacion s WHERE s.evVersion = :evVersion"),
    @NamedQuery(name = "SieniEvaluacion.findByEvEstado", query = "SELECT s FROM SieniEvaluacion s WHERE s.evEstado = :evEstado"),
    @NamedQuery(name = "SieniEvaluacion.findByEvFechaInicio", query = "SELECT s FROM SieniEvaluacion s WHERE s.evFechaInicio = :evFechaInicio"),
    @NamedQuery(name = "SieniEvaluacion.findByEvFechaCierre", query = "SELECT s FROM SieniEvaluacion s WHERE s.evFechaCierre = :evFechaCierre"),
    @NamedQuery(name = "SieniEvaluacion.findByEvTipo", query = "SELECT s FROM SieniEvaluacion s WHERE s.evTipo = :evTipo")})
public class SieniEvaluacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_eva")
    @SequenceGenerator(name = "sec_sieni_eva", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_eva")
    @Basic(optional = false)
    @Column(name = "id_evaluacion")
    private Long idEvaluacion;
    @Column(name = "ev_nombre")
    private String evNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ev_ponderacion")
    private Double evPonderacion;
    @Column(name = "ev_version")
    private String evVersion;
    @Column(name = "ev_estado")
    private Character evEstado;
    @Column(name = "ev_fecha_inicio")
    @Temporal(TemporalType.DATE)
    private Date evFechaInicio;
    @Column(name = "ev_fecha_cierre")
    @Temporal(TemporalType.DATE)
    private Date evFechaCierre;
    @Column(name = "ev_tipo")
    private String evTipo;
    @Column(name = "ev_duracion")
    private Long evDuracion;
    @Column(name = "ev_intentos")
    private Long evIntentos;
    @Column(name = "ev_total_preguntas")
    private Long evTotalPreguntas;
    @Column(name = "ev_preguntas_pagina")
    private Long evPreguntasPagina;
    @Column(name = "ev_nota_fin")
    private String evNotaFin;
    @Column(name = "ev_preguntas_aleatorias")
    private String evPreguntasAleatorias;
    @Column(name = "ev_respuestas_aleatorias")
    private String evRespuestasAleatorias;
    @Column(name = "ev_ver_respuesta")
    private String evVerRespuesta;
    
    @OneToMany(mappedBy = "idEvaluacion")
    private List<SieniEvalSupComp> sieniEvalSupCompList;
    @OneToMany(mappedBy = "idEvaluacion")
    private List<SieniNota> sieniNotaList;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne
    private SieniCurso idCurso;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne
    private SieniMateria idMateria;
    @OneToMany(mappedBy = "idEvaluacion")
    private List<SieniEvalSoluc> sieniEvalSolucList;

    public SieniEvaluacion() {
    }

    public SieniEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Long getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(Long idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getEvNombre() {
        return evNombre;
    }

    public void setEvNombre(String evNombre) {
        this.evNombre = evNombre;
    }

    public Double getEvPonderacion() {
        return evPonderacion;
    }

    public void setEvPonderacion(Double evPonderacion) {
        this.evPonderacion = evPonderacion;
    }

    public String getEvVersion() {
        return evVersion;
    }

    public void setEvVersion(String evVersion) {
        this.evVersion = evVersion;
    }

    public Character getEvEstado() {
        return evEstado;
    }

    public void setEvEstado(Character evEstado) {
        this.evEstado = evEstado;
    }

    public Date getEvFechaInicio() {
        return evFechaInicio;
    }

    public void setEvFechaInicio(Date evFechaInicio) {
        this.evFechaInicio = evFechaInicio;
    }

    public Date getEvFechaCierre() {
        return evFechaCierre;
    }

    public void setEvFechaCierre(Date evFechaCierre) {
        this.evFechaCierre = evFechaCierre;
    }

    public String getEvTipo() {
        return evTipo;
    }

    public void setEvTipo(String evTipo) {
        this.evTipo = evTipo;
    }

    @XmlTransient
    public List<SieniEvalSupComp> getSieniEvalSupCompList() {
        return sieniEvalSupCompList;
    }

    public void setSieniEvalSupCompList(List<SieniEvalSupComp> sieniEvalSupCompList) {
        this.sieniEvalSupCompList = sieniEvalSupCompList;
    }

    @XmlTransient
    public List<SieniNota> getSieniNotaList() {
        return sieniNotaList;
    }

    public void setSieniNotaList(List<SieniNota> sieniNotaList) {
        this.sieniNotaList = sieniNotaList;
    }

    public SieniCurso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(SieniCurso idCurso) {
        this.idCurso = idCurso;
    }

    public SieniMateria getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(SieniMateria idMateria) {
        this.idMateria = idMateria;
    }
    
    public Long getEvDuracion() {
        return evDuracion;
    }

    public void setEvDuracion(Long evDuracion) {
        this.evDuracion = evDuracion;
    }

    public Long getEvIntentos() {
        return evIntentos;
    }

    public void setEvIntentos(Long evIntentos) {
        this.evIntentos = evIntentos;
    }

    public Long getEvTotalPreguntas() {
        return evTotalPreguntas;
    }

    public void setEvTotalPreguntas(Long evTotalPreguntas) {
        this.evTotalPreguntas = evTotalPreguntas;
    }

    public Long getEvPreguntasPagina() {
        return evPreguntasPagina;
    }

    public void setEvPreguntasPagina(Long evPreguntasPagina) {
        this.evPreguntasPagina = evPreguntasPagina;
    }

    public String getEvNotaFin() {
        return evNotaFin;
    }

    public void setEvNotaFin(String evNotaFin) {
        this.evNotaFin = evNotaFin;
    }

    public String getEvPreguntasAleatorias() {
        return evPreguntasAleatorias;
    }

    public void setEvPreguntasAleatorias(String evPreguntasAleatorias) {
        this.evPreguntasAleatorias = evPreguntasAleatorias;
    }

    public String getEvRespuestasAleatorias() {
        return evRespuestasAleatorias;
    }

    public void setEvRespuestasAleatorias(String evRespuestasAleatorias) {
        this.evRespuestasAleatorias = evRespuestasAleatorias;
    }

    public String getEvVerRespuesta() {
        return evVerRespuesta;
    }

    public void setEvVerRespuesta(String evVerRespuesta) {
        this.evVerRespuesta = evVerRespuesta;
    }

    @XmlTransient
    public List<SieniEvalSoluc> getSieniEvalSolucList() {
        return sieniEvalSolucList;
    }

    public void setSieniEvalSolucList(List<SieniEvalSoluc> sieniEvalSolucList) {
        this.sieniEvalSolucList = sieniEvalSolucList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvaluacion != null ? idEvaluacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvaluacion)) {
            return false;
        }
        SieniEvaluacion other = (SieniEvaluacion) object;
        if ((this.idEvaluacion == null && other.idEvaluacion != null) || (this.idEvaluacion != null && !this.idEvaluacion.equals(other.idEvaluacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvaluacion[ idEvaluacion=" + idEvaluacion + " ]";
    }
    
}
