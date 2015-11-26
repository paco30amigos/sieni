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
import javax.validation.constraints.Size;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_elem_plantilla")
@NamedQueries({
    @NamedQuery(name = "SieniElemPlantilla.findAll", query = "SELECT s FROM SieniElemPlantilla s"),
    @NamedQuery(name = "SieniElemPlantilla.findByIdPlantilla", query = "SELECT s FROM SieniElemPlantilla s where s.epEstado not in (:estado) and s.idPlantilla.idPlantilla=:idPlantilla order by s.epOrden")})
public class SieniElemPlantilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_elem_plantilla")
    @SequenceGenerator(name = "sec_sieni_elem_plantilla", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_elem_plantilla")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_elem_plantilla")
    private Long idElemPlantilla;
    @Size(max = 100)
    @Column(name = "ep_descripcion")
    private String epDescripcion;
    @Column(name = "ep_orden")
    private Integer epOrden;
    @Column(name = "ep_estado")
    private Character epEstado;
    @JoinColumn(name = "id_tipo_elem_plantilla", referencedColumnName = "id_tipo_elem_plantilla")
    @ManyToOne
    private SieniTipoElemPlantilla idTipoElemPlantilla;
    @JoinColumn(name = "id_plantilla", referencedColumnName = "id_plantilla")
    @ManyToOne
    private SieniPlantilla idPlantilla;

    public SieniElemPlantilla() {
    }

    public SieniElemPlantilla(Long idElemPlantilla) {
        this.idElemPlantilla = idElemPlantilla;
    }

    public Long getIdElemPlantilla() {
        return idElemPlantilla;
    }

    public void setIdElemPlantilla(Long idElemPlantilla) {
        this.idElemPlantilla = idElemPlantilla;
    }

    public String getEpDescripcion() {
        return epDescripcion;
    }

    public void setEpDescripcion(String epDescripcion) {
        this.epDescripcion = epDescripcion;
    }

    public Integer getEpOrden() {
        return epOrden;
    }

    public void setEpOrden(Integer epOrden) {
        this.epOrden = epOrden;
    }

    public Character getEpEstado() {
        return epEstado;
    }

    public void setEpEstado(Character epEstado) {
        this.epEstado = epEstado;
    }

    public SieniTipoElemPlantilla getIdTipoElemPlantilla() {
        return idTipoElemPlantilla;
    }

    public void setIdTipoElemPlantilla(SieniTipoElemPlantilla idTipoElemPlantilla) {
        this.idTipoElemPlantilla = idTipoElemPlantilla;
    }

    public SieniPlantilla getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(SieniPlantilla idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idElemPlantilla != null ? idElemPlantilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniElemPlantilla)) {
            return false;
        }
        SieniElemPlantilla other = (SieniElemPlantilla) object;
        if ((this.idElemPlantilla == null && other.idElemPlantilla != null) || (this.idElemPlantilla != null && !this.idElemPlantilla.equals(other.idElemPlantilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniElemPlantilla[ idElemPlantilla=" + idElemPlantilla + " ]";
    }

}
