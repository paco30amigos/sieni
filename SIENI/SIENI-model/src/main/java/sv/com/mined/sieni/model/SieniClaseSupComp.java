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
@Table(name = "sieni_clase_sup_comp")
@NamedQueries({
    @NamedQuery(name = "SieniClaseSupComp.findAll", query = "SELECT s FROM SieniClaseSupComp s")})
public class SieniClaseSupComp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_clase_sup_comp")
    private Long idClaseSupComp;
    @Column(name = "sc_estado")
    private Character scEstado;
    @JoinColumn(name = "f_comp_super_compon", referencedColumnName = "id_super_compon")
    @ManyToOne(optional = false)
    private SieniSuperCompon fCompSuperCompon;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
    @ManyToOne
    private SieniClase idClase;

    public SieniClaseSupComp() {
    }

    public SieniClaseSupComp(Long idClaseSupComp) {
        this.idClaseSupComp = idClaseSupComp;
    }

    public Long getIdClaseSupComp() {
        return idClaseSupComp;
    }

    public void setIdClaseSupComp(Long idClaseSupComp) {
        this.idClaseSupComp = idClaseSupComp;
    }

    public Character getScEstado() {
        return scEstado;
    }

    public void setScEstado(Character scEstado) {
        this.scEstado = scEstado;
    }

    public SieniSuperCompon getFCompSuperCompon() {
        return fCompSuperCompon;
    }

    public void setFCompSuperCompon(SieniSuperCompon fCompSuperCompon) {
        this.fCompSuperCompon = fCompSuperCompon;
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
        hash += (idClaseSupComp != null ? idClaseSupComp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniClaseSupComp)) {
            return false;
        }
        SieniClaseSupComp other = (SieniClaseSupComp) object;
        if ((this.idClaseSupComp == null && other.idClaseSupComp != null) || (this.idClaseSupComp != null && !this.idClaseSupComp.equals(other.idClaseSupComp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniClaseSupComp[ idClaseSupComp=" + idClaseSupComp + " ]";
    }
    
}
