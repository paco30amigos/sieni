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
@Table(name = "sieni_eval_soluc", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_eval_soluc"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvalSoluc.findAll", query = "SELECT s FROM SieniEvalSoluc s"),
    @NamedQuery(name = "SieniEvalSoluc.findByIdEvalSoluc", query = "SELECT s FROM SieniEvalSoluc s WHERE s.idEvalSoluc = :idEvalSoluc"),
    @NamedQuery(name = "SieniEvalSoluc.findByEsDescripcion", query = "SELECT s FROM SieniEvalSoluc s WHERE s.esDescripcion = :esDescripcion"),
    @NamedQuery(name = "SieniEvalSoluc.findByEsVersion", query = "SELECT s FROM SieniEvalSoluc s WHERE s.esVersion = :esVersion")})
public class SieniEvalSoluc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_eval_soluc", nullable = false)
    private Long idEvalSoluc;
    @Column(name = "es_descripcion", length = 100)
    private String esDescripcion;
    @Column(name = "es_version", length = 20)
    private String esVersion;
    @JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion")
    @ManyToOne
    private SieniEvaluacion idEvaluacion;

    public SieniEvalSoluc() {
    }

    public SieniEvalSoluc(Long idEvalSoluc) {
        this.idEvalSoluc = idEvalSoluc;
    }

    public Long getIdEvalSoluc() {
        return idEvalSoluc;
    }

    public void setIdEvalSoluc(Long idEvalSoluc) {
        this.idEvalSoluc = idEvalSoluc;
    }

    public String getEsDescripcion() {
        return esDescripcion;
    }

    public void setEsDescripcion(String esDescripcion) {
        this.esDescripcion = esDescripcion;
    }

    public String getEsVersion() {
        return esVersion;
    }

    public void setEsVersion(String esVersion) {
        this.esVersion = esVersion;
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
        hash += (idEvalSoluc != null ? idEvalSoluc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvalSoluc)) {
            return false;
        }
        SieniEvalSoluc other = (SieniEvalSoluc) object;
        if ((this.idEvalSoluc == null && other.idEvalSoluc != null) || (this.idEvalSoluc != null && !this.idEvalSoluc.equals(other.idEvalSoluc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvalSoluc[ idEvalSoluc=" + idEvalSoluc + " ]";
    }
    
}
