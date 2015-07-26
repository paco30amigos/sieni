/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_evaluacion", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_evaluacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvaluacion.findAll", query = "SELECT s FROM SieniEvaluacion s"),
    @NamedQuery(name = "SieniEvaluacion.findByIdEvaluacion", query = "SELECT s FROM SieniEvaluacion s WHERE s.idEvaluacion = :idEvaluacion"),
    @NamedQuery(name = "SieniEvaluacion.findByEvNombre", query = "SELECT s FROM SieniEvaluacion s WHERE s.evNombre = :evNombre"),
    @NamedQuery(name = "SieniEvaluacion.findByEvPonderacion", query = "SELECT s FROM SieniEvaluacion s WHERE s.evPonderacion = :evPonderacion"),
    @NamedQuery(name = "SieniEvaluacion.findByEvVersion", query = "SELECT s FROM SieniEvaluacion s WHERE s.evVersion = :evVersion"),
    @NamedQuery(name = "SieniEvaluacion.findByEvEstado", query = "SELECT s FROM SieniEvaluacion s WHERE s.evEstado = :evEstado")})
public class SieniEvaluacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_evaluacion", nullable = false)
    private Long idEvaluacion;
    @Column(name = "ev_nombre", length = 100)
    private String evNombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ev_ponderacion", precision = 17, scale = 17)
    private Double evPonderacion;
    @Column(name = "ev_version", length = 20)
    private String evVersion;
    @Column(name = "ev_estado")
    private Character evEstado;
    @OneToMany(mappedBy = "idEvaluacion")
    private List<SieniNota> sieniNotaList;
    @OneToMany(mappedBy = "idEvaluacion")
    private List<SieniEvalSupComp> sieniEvalSupCompList;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne
    private SieniCurso idCurso;
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

    @XmlTransient
    public List<SieniNota> getSieniNotaList() {
        return sieniNotaList;
    }

    public void setSieniNotaList(List<SieniNota> sieniNotaList) {
        this.sieniNotaList = sieniNotaList;
    }

    @XmlTransient
    public List<SieniEvalSupComp> getSieniEvalSupCompList() {
        return sieniEvalSupCompList;
    }

    public void setSieniEvalSupCompList(List<SieniEvalSupComp> sieniEvalSupCompList) {
        this.sieniEvalSupCompList = sieniEvalSupCompList;
    }

    public SieniCurso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(SieniCurso idCurso) {
        this.idCurso = idCurso;
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
