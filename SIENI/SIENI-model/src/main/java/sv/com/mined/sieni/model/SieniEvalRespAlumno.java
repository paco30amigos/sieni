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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ever
 */
@Entity
@Table(name = "sieni_eval_resp_alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvalRespAlumno.findAll", query = "SELECT s FROM SieniEvalRespAlumno s"),
    @NamedQuery(name = "SieniEvalRespAlumno.findByAlumnoEv", query = "SELECT s FROM SieniEvalRespAlumno s WHERE s.idAlumno.idAlumno=:idAlumno AND s.idEvaluacionItem.idEvaluacion.idEvaluacion=:idEvaluacion"),
    @NamedQuery(name = "SieniEvalRespAlumno.findByIdEvalRespAlumno", query = "SELECT s FROM SieniEvalRespAlumno s WHERE s.idEvalRespAlumno = :idEvalRespAlumno"),
    @NamedQuery(name = "SieniEvalRespAlumno.findByRaRespuesta", query = "SELECT s FROM SieniEvalRespAlumno s WHERE s.raRespuesta = :raRespuesta"),
    @NamedQuery(name = "SieniEvalRespAlumno.findByRaEstado", query = "SELECT s FROM SieniEvalRespAlumno s WHERE s.raEstado = :raEstado"),
    @NamedQuery(name = "SieniEvalRespAlumno.findByRaFechaIngreso", query = "SELECT s FROM SieniEvalRespAlumno s WHERE s.raFechaIngreso = :raFechaIngreso")})
public class SieniEvalRespAlumno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_eval_resp_alumno")
    @SequenceGenerator(name = "sec_sieni_eval_resp_alumno", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_eval_resp_alumno")
    @Basic(optional = false) 
    @Column(name = "id_eval_resp_alumno")
    private Long idEvalRespAlumno;
    @Size(max = 2147483647)
    @Column(name = "ra_respuesta")
    private String raRespuesta;
    @Column(name = "ra_estado")
    private Character raEstado;
    @Column(name = "ra_fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date raFechaIngreso;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;
    @JoinColumn(name = "id_evaluacion_item", referencedColumnName = "id_evaluacion_item")
    @ManyToOne
    private SieniEvaluacionItem idEvaluacionItem;

    public SieniEvalRespAlumno() {
    }

    public SieniEvalRespAlumno(Long idEvalRespAlumno) {
        this.idEvalRespAlumno = idEvalRespAlumno;
    }

    public Long getIdEvalRespAlumno() {
        return idEvalRespAlumno;
    }

    public void setIdEvalRespAlumno(Long idEvalRespAlumno) {
        this.idEvalRespAlumno = idEvalRespAlumno;
    }

    public String getRaRespuesta() {
        return raRespuesta;
    }

    public void setRaRespuesta(String raRespuesta) {
        this.raRespuesta = raRespuesta;
    }

    public Character getRaEstado() {
        return raEstado;
    }

    public void setRaEstado(Character raEstado) {
        this.raEstado = raEstado;
    }

    public Date getRaFechaIngreso() {
        return raFechaIngreso;
    }

    public void setRaFechaIngreso(Date raFechaIngreso) {
        this.raFechaIngreso = raFechaIngreso;
    }

    public SieniAlumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(SieniAlumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    public SieniEvaluacionItem getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(SieniEvaluacionItem idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvalRespAlumno != null ? idEvalRespAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvalRespAlumno)) {
            return false;
        }
        SieniEvalRespAlumno other = (SieniEvalRespAlumno) object;
        if ((this.idEvalRespAlumno == null && other.idEvalRespAlumno != null) || (this.idEvalRespAlumno != null && !this.idEvalRespAlumno.equals(other.idEvalRespAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvalRespAlumno[ idEvalRespAlumno=" + idEvalRespAlumno + " ]";
    }
    
}
