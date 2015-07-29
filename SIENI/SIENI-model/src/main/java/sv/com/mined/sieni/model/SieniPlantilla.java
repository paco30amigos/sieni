/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_plantilla", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_plantilla"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniPlantilla.findAll", query = "SELECT s FROM SieniPlantilla s"),
    @NamedQuery(name = "SieniPlantilla.findByIdPlantilla", query = "SELECT s FROM SieniPlantilla s WHERE s.idPlantilla = :idPlantilla"),
    @NamedQuery(name = "SieniPlantilla.findByPlNombre", query = "SELECT s FROM SieniPlantilla s WHERE s.plNombre = :plNombre"),
    @NamedQuery(name = "SieniPlantilla.findByPlFechaIngreso", query = "SELECT s FROM SieniPlantilla s WHERE s.plFechaIngreso = :plFechaIngreso"),
    @NamedQuery(name = "SieniPlantilla.findByPlFechaModificacion", query = "SELECT s FROM SieniPlantilla s WHERE s.plFechaModificacion = :plFechaModificacion")})
public class SieniPlantilla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_plantilla", nullable = false)
    private Long idPlantilla;
    @Column(name = "pl_nombre", length = 100)
    private String plNombre;
    @Column(name = "pl_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date plFechaIngreso;
    @Column(name = "pl_fecha_modificacion")
    @Temporal(TemporalType.DATE)
    private Date plFechaModificacion;
    @JoinColumn(name = "id_materia", referencedColumnName = "id_materia")
    @ManyToOne
    private SieniMateria idMateria;
    @OneToMany(mappedBy = "idPlantilla")
    private List<SieniElemPlantilla> sieniElemPlantillaList;
    @OneToMany(mappedBy = "idPlantilla")
    private List<SieniClase> sieniClaseList;

    public SieniPlantilla() {
    }

    public SieniPlantilla(Long idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public Long getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(Long idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public String getPlNombre() {
        return plNombre;
    }

    public void setPlNombre(String plNombre) {
        this.plNombre = plNombre;
    }

    public Date getPlFechaIngreso() {
        return plFechaIngreso;
    }

    public void setPlFechaIngreso(Date plFechaIngreso) {
        this.plFechaIngreso = plFechaIngreso;
    }

    public Date getPlFechaModificacion() {
        return plFechaModificacion;
    }

    public void setPlFechaModificacion(Date plFechaModificacion) {
        this.plFechaModificacion = plFechaModificacion;
    }

    public SieniMateria getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(SieniMateria idMateria) {
        this.idMateria = idMateria;
    }

    @XmlTransient
    public List<SieniElemPlantilla> getSieniElemPlantillaList() {
        return sieniElemPlantillaList;
    }

    public void setSieniElemPlantillaList(List<SieniElemPlantilla> sieniElemPlantillaList) {
        this.sieniElemPlantillaList = sieniElemPlantillaList;
    }

    @XmlTransient
    public List<SieniClase> getSieniClaseList() {
        return sieniClaseList;
    }

    public void setSieniClaseList(List<SieniClase> sieniClaseList) {
        this.sieniClaseList = sieniClaseList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlantilla != null ? idPlantilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniPlantilla)) {
            return false;
        }
        SieniPlantilla other = (SieniPlantilla) object;
        if ((this.idPlantilla == null && other.idPlantilla != null) || (this.idPlantilla != null && !this.idPlantilla.equals(other.idPlantilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniPlantilla[ idPlantilla=" + idPlantilla + " ]";
    }
    
}