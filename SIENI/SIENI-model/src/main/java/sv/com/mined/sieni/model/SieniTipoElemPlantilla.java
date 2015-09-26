/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_tipo_elem_plantilla")
@NamedQueries({
    @NamedQuery(name = "SieniTipoElemPlantilla.findAll", query = "SELECT s FROM SieniTipoElemPlantilla s")})
public class SieniTipoElemPlantilla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_tipo_elem_plantilla")
    private Long idTipoElemPlantilla;
    @Column(name = "te_descripcion")
    private String teDescripcion;
    @Column(name = "te_estado")
    private Character teEstado;
    @OneToMany(mappedBy = "idTipoElemPlantilla")
    private List<SieniElemPlantilla> sieniElemPlantillaList;

    public SieniTipoElemPlantilla() {
    }

    public SieniTipoElemPlantilla(Long idTipoElemPlantilla) {
        this.idTipoElemPlantilla = idTipoElemPlantilla;
    }

    public Long getIdTipoElemPlantilla() {
        return idTipoElemPlantilla;
    }

    public void setIdTipoElemPlantilla(Long idTipoElemPlantilla) {
        this.idTipoElemPlantilla = idTipoElemPlantilla;
    }

    public String getTeDescripcion() {
        return teDescripcion;
    }

    public void setTeDescripcion(String teDescripcion) {
        this.teDescripcion = teDescripcion;
    }

    public Character getTeEstado() {
        return teEstado;
    }

    public void setTeEstado(Character teEstado) {
        this.teEstado = teEstado;
    }

    public List<SieniElemPlantilla> getSieniElemPlantillaList() {
        return sieniElemPlantillaList;
    }

    public void setSieniElemPlantillaList(List<SieniElemPlantilla> sieniElemPlantillaList) {
        this.sieniElemPlantillaList = sieniElemPlantillaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoElemPlantilla != null ? idTipoElemPlantilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniTipoElemPlantilla)) {
            return false;
        }
        SieniTipoElemPlantilla other = (SieniTipoElemPlantilla) object;
        if ((this.idTipoElemPlantilla == null && other.idTipoElemPlantilla != null) || (this.idTipoElemPlantilla != null && !this.idTipoElemPlantilla.equals(other.idTipoElemPlantilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniTipoElemPlantilla[ idTipoElemPlantilla=" + idTipoElemPlantilla + " ]";
    }
    
}
