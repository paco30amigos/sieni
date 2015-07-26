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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_nota", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_nota"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniNota.findAll", query = "SELECT s FROM SieniNota s"),
    @NamedQuery(name = "SieniNota.findByIdNota", query = "SELECT s FROM SieniNota s WHERE s.idNota = :idNota"),
    @NamedQuery(name = "SieniNota.findByNtCalificacion", query = "SELECT s FROM SieniNota s WHERE s.ntCalificacion = :ntCalificacion")})
public class SieniNota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_nota", nullable = false)
    private Long idNota;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "nt_calificacion", precision = 17, scale = 17)
    private Double ntCalificacion;
    @JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion")
    @ManyToOne
    private SieniEvaluacion idEvaluacion;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;

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

    public SieniEvaluacion getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(SieniEvaluacion idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
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
    
}
