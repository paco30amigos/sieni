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
@Table(name = "sieni_clase_vid_ptos")
@NamedQueries({
    @NamedQuery(name = "SieniClaseVidPtos.findAll", query = "SELECT s FROM SieniClaseVidPtos s")})
public class SieniClaseVidPtos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_clase_video_ptos_act")
    private Long idClaseVideoPtosAct;
    @Column(name = "vp_tiempo_activ")
    private Integer vpTiempoActiv;
    @JoinColumn(name = "id_tipo_elem_plantilla", referencedColumnName = "id_tipo_elem_plantilla")
    @ManyToOne
    private SieniTipoElemPlantilla idTipoElemPlantilla;

    public SieniClaseVidPtos() {
    }

    public SieniClaseVidPtos(Long idClaseVideoPtosAct) {
        this.idClaseVideoPtosAct = idClaseVideoPtosAct;
    }

    public Long getIdClaseVideoPtosAct() {
        return idClaseVideoPtosAct;
    }

    public void setIdClaseVideoPtosAct(Long idClaseVideoPtosAct) {
        this.idClaseVideoPtosAct = idClaseVideoPtosAct;
    }

    public Integer getVpTiempoActiv() {
        return vpTiempoActiv;
    }

    public void setVpTiempoActiv(Integer vpTiempoActiv) {
        this.vpTiempoActiv = vpTiempoActiv;
    }

    public SieniTipoElemPlantilla getIdTipoElemPlantilla() {
        return idTipoElemPlantilla;
    }

    public void setIdTipoElemPlantilla(SieniTipoElemPlantilla idTipoElemPlantilla) {
        this.idTipoElemPlantilla = idTipoElemPlantilla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClaseVideoPtosAct != null ? idClaseVideoPtosAct.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniClaseVidPtos)) {
            return false;
        }
        SieniClaseVidPtos other = (SieniClaseVidPtos) object;
        if ((this.idClaseVideoPtosAct == null && other.idClaseVideoPtosAct != null) || (this.idClaseVideoPtosAct != null && !this.idClaseVideoPtosAct.equals(other.idClaseVideoPtosAct))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniClaseVidPtos[ idClaseVideoPtosAct=" + idClaseVideoPtosAct + " ]";
    }
    
}
