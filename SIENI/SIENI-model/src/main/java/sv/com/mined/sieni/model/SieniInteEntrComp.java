/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_inte_entr_comp")
@NamedQueries({
    @NamedQuery(name = "SieniInteEntrComp.findAll", query = "SELECT s FROM SieniInteEntrComp s"),
    @NamedQuery(name = "SieniInteEntrComp.findByClase", query = "SELECT s FROM SieniInteEntrComp s where s.idClase=:idClase and s.ieEstado not in (:estado)")})
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
    @JoinColumn(name = "ie_sup_c2", referencedColumnName = "id_super_compon")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private SieniSuperCompon ieSupC2;
    @JoinColumn(name = "ie_sup_c1", referencedColumnName = "id_super_compon")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private SieniSuperCompon ieSupC1;
    @JoinColumn(name = "ie_evento_c2", referencedColumnName = "id_evento")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private SieniEvento ieEventoC2;
    @JoinColumn(name = "ie_evento_c1", referencedColumnName = "id_evento")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private SieniEvento ieEventoC1;
    @Column(name = "id_clase")
    private Long idClase;
    @JoinColumn(name = "id_tipo_elem_plantilla", referencedColumnName = "id_tipo_elem_plantilla")
    @ManyToOne(cascade = {CascadeType.REFRESH})
    private SieniTipoElemPlantilla idTipoElemPlantilla;
    @Column(name = "ie_n_pantalla")
    private Integer ieNPantalla;

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

    public Character getIeEstado() {
        return ieEstado;
    }

    public void setIeEstado(Character ieEstado) {
        this.ieEstado = ieEstado;
    }

    public SieniSuperCompon getIeSupC2() {
        return ieSupC2;
    }

    public void setIeSupC2(SieniSuperCompon ieSupC2) {
        this.ieSupC2 = ieSupC2;
    }

    public SieniSuperCompon getIeSupC1() {
        return ieSupC1;
    }

    public void setIeSupC1(SieniSuperCompon ieSupC1) {
        this.ieSupC1 = ieSupC1;
    }

    public SieniEvento getIeEventoC2() {
        return ieEventoC2;
    }

    public void setIeEventoC2(SieniEvento ieEventoC2) {
        this.ieEventoC2 = ieEventoC2;
    }

    public SieniEvento getIeEventoC1() {
        return ieEventoC1;
    }

    public void setIeEventoC1(SieniEvento ieEventoC1) {
        this.ieEventoC1 = ieEventoC1;
    }

    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
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

    public SieniTipoElemPlantilla getIdTipoElemPlantilla() {
        return idTipoElemPlantilla;
    }

    public void setIdTipoElemPlantilla(SieniTipoElemPlantilla idTipoElemPlantilla) {
        this.idTipoElemPlantilla = idTipoElemPlantilla;
    }

    public Integer getIeNPantalla() {
        return ieNPantalla;
    }

    public void setIeNPantalla(Integer ieNPantalla) {
        this.ieNPantalla = ieNPantalla;
    }

}
