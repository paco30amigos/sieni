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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_materia_docente")
@NamedQueries({
    @NamedQuery(name = "SieniMateriaDocente.findByDocente", query = "SELECT s FROM SieniMateriaDocente s where s.idDocente=:idDocente and s.mdEstado not in (:estado)"),
    @NamedQuery(name = "SieniMateriaDocente.findByMateria", query = "SELECT d FROM SieniMateriaDocente s,SieniDocente d where s.idMateria.idMateria=:idMateria and s.idDocente=d.idDocente and s.mdEstado not in (:estado)"),
    @NamedQuery(name = "SieniMateriaDocente.findAll", query = "SELECT s FROM SieniMateriaDocente s")})
public class SieniMateriaDocente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_sieni_materia_docente")
    @SequenceGenerator(name = "sec_sieni_materia_docente", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_materia_docente")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_materia_docente")
    private Long idMateriaDocente;
    @Column(name = "md_estado")
    private Character mdEstado;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne
    private SieniMateria idMateria;
//    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
//    @ManyToOne
//    private SieniDocente idDocente;
    @Column(name = "id_docente")
    private Long idDocente;
    @Transient
    private SieniDocente docente;

    public SieniMateriaDocente() {
    }

    public SieniMateriaDocente(Long idMateriaDocente) {
        this.idMateriaDocente = idMateriaDocente;
    }

    public Long getIdMateriaDocente() {
        return idMateriaDocente;
    }

    public void setIdMateriaDocente(Long idMateriaDocente) {
        this.idMateriaDocente = idMateriaDocente;
    }

    public SieniMateria getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(SieniMateria idMateria) {
        this.idMateria = idMateria;
    }

//    public SieniDocente getIdDocente() {
//        return idDocente;
//    }
//
//    public void setIdDocente(SieniDocente idDocente) {
//        this.idDocente = idDocente;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMateriaDocente != null ? idMateriaDocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniMateriaDocente)) {
            return false;
        }
        SieniMateriaDocente other = (SieniMateriaDocente) object;
        if ((this.idMateriaDocente == null && other.idMateriaDocente != null) || (this.idMateriaDocente != null && !this.idMateriaDocente.equals(other.idMateriaDocente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniMateriaDocente[ idMateriaDocente=" + idMateriaDocente + " ]";
    }

    public Character getMdEstado() {
        return mdEstado;
    }

    public void setMdEstado(Character mdEstado) {
        this.mdEstado = mdEstado;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public SieniDocente getDocente() {
        return docente;
    }

    public void setDocente(SieniDocente docente) {
        this.docente = docente;
    }

}
