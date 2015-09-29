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
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_inte_entr_comp")
@NamedQueries({
    @NamedQuery(name = "SieniInteEntrComp.findAll", query = "SELECT s FROM SieniInteEntrComp s"),
    @NamedQuery(name = "SieniInteEntrComp.findByClase", query = "SELECT s FROM SieniInteEntrComp s where s.idClase.idClase=:idClase and s.ieEstado not in (:estado) and s.idClase.clEstado not in (:estado)")})
public class SieniInteEntrComp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_inte_entr_comp")
    @SequenceGenerator(name = "sec_sieni_inte_entr_comp", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_inte_entr_comp")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_inte_entre_comp")
    private Long idInteEntreComp;
    @Column(name = "ie_retraso")
    private Integer ieRetraso;
    @Column(name = "ie_estado")
    private Character ieEstado;
    @JoinColumn(name = "id_comp_interact2", referencedColumnName = "id_comp_interaccion")
    @ManyToOne
    private SieniCompInteraccion idCompInteract2;
    @JoinColumn(name = "id_comp_interac1", referencedColumnName = "id_comp_interaccion")
    @ManyToOne
    private SieniCompInteraccion idCompInterac1;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
    @ManyToOne
    private SieniClase idClase;

    public SieniInteEntrComp() {
    }

    public SieniInteEntrComp(Long idInteEntreComp) {
        this.idInteEntreComp = idInteEntreComp;
    }

    public Long getIdInteEntreComp() {
        return idInteEntreComp;
    }

    public void setIdInteEntreComp(Long idInteEntreComp) {
        this.idInteEntreComp = idInteEntreComp;
    }

    public Integer getIeRetraso() {
        return ieRetraso;
    }

    public void setIeRetraso(Integer ieRetraso) {
        this.ieRetraso = ieRetraso;
    }

    public SieniCompInteraccion getIdCompInteract2() {
        return idCompInteract2;
    }

    public void setIdCompInteract2(SieniCompInteraccion idCompInteract2) {
        this.idCompInteract2 = idCompInteract2;
    }

    public SieniCompInteraccion getIdCompInterac1() {
        return idCompInterac1;
    }

    public void setIdCompInterac1(SieniCompInteraccion idCompInterac1) {
        this.idCompInterac1 = idCompInterac1;
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
        hash += (idInteEntreComp != null ? idInteEntreComp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniInteEntrComp)) {
            return false;
        }
        SieniInteEntrComp other = (SieniInteEntrComp) object;
        if ((this.idInteEntreComp == null && other.idInteEntreComp != null) || (this.idInteEntreComp != null && !this.idInteEntreComp.equals(other.idInteEntreComp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniInteEntrComp[ idInteEntreComp=" + idInteEntreComp + " ]";
    }

    public Character getIeEstado() {
        return ieEstado;
    }

    public void setIeEstado(Character ieEstado) {
        this.ieEstado = ieEstado;
    }

}
