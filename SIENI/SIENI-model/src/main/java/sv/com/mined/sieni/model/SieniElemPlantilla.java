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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_elem_plantilla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniElemPlantilla.findAll", query = "SELECT s FROM SieniElemPlantilla s"),
    @NamedQuery(name = "SieniElemPlantilla.findByIdElemPlantilla", query = "SELECT s FROM SieniElemPlantilla s WHERE s.idElemPlantilla = :idElemPlantilla"),
    @NamedQuery(name = "SieniElemPlantilla.findByEpDescripcion", query = "SELECT s FROM SieniElemPlantilla s WHERE s.epDescripcion = :epDescripcion"),
    @NamedQuery(name = "SieniElemPlantilla.findByEpOrden", query = "SELECT s FROM SieniElemPlantilla s WHERE s.epOrden = :epOrden"),
    @NamedQuery(name = "SieniElemPlantilla.findByEpTipo", query = "SELECT s FROM SieniElemPlantilla s WHERE s.epTipo = :epTipo")})
public class SieniElemPlantilla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_elem_plantilla")
    private Long idElemPlantilla;
    @Column(name = "ep_descripcion")
    private String epDescripcion;
    @Column(name = "ep_orden")
    private Integer epOrden;
    @Column(name = "ep_tipo")
    private Character epTipo;
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

    public Character getEpTipo() {
        return epTipo;
    }

    public void setEpTipo(Character epTipo) {
        this.epTipo = epTipo;
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
