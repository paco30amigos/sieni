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
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_clase_docente")
@NamedQueries({
    @NamedQuery(name = "SieniClaseDocente.findAll", query = "SELECT s FROM SieniClaseDocente s"),
    @NamedQuery(name = "SieniClaseDocente.findByIdClaseDocente", query = "SELECT s FROM SieniClaseDocente s WHERE s.idClaseDocente = :idClaseDocente")})
public class SieniClaseDocente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_clase_docente")
    private Long idClaseDocente;
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
    @ManyToOne
    private SieniDocente idDocente;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
    @ManyToOne
    private SieniClase idClase;

    public SieniClaseDocente() {
    }

    public SieniClaseDocente(Long idClaseDocente) {
        this.idClaseDocente = idClaseDocente;
    }

    public Long getIdClaseDocente() {
        return idClaseDocente;
    }

    public void setIdClaseDocente(Long idClaseDocente) {
        this.idClaseDocente = idClaseDocente;
    }

    public SieniDocente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(SieniDocente idDocente) {
        this.idDocente = idDocente;
    }

    public SieniClase getIdClase() {
        return idClase;
    }

    public void setIdClase(SieniClase idClase) {
        this.idClase = idClase;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClaseDocente != null ? idClaseDocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniClaseDocente)) {
            return false;
        }
        SieniClaseDocente other = (SieniClaseDocente) object;
        if ((this.idClaseDocente == null && other.idClaseDocente != null) || (this.idClaseDocente != null && !this.idClaseDocente.equals(other.idClaseDocente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniClaseDocente[ idClaseDocente=" + idClaseDocente + " ]";
    }
    
}
