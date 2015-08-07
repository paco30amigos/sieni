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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_tipo_componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniTipoComponente.findAll", query = "SELECT s FROM SieniTipoComponente s"),
    @NamedQuery(name = "SieniTipoComponente.findByIdTipoComponente", query = "SELECT s FROM SieniTipoComponente s WHERE s.idTipoComponente = :idTipoComponente"),
    @NamedQuery(name = "SieniTipoComponente.findByTcDescripcion", query = "SELECT s FROM SieniTipoComponente s WHERE s.tcDescripcion = :tcDescripcion"),
    @NamedQuery(name = "SieniTipoComponente.findByTcFechaIngreso", query = "SELECT s FROM SieniTipoComponente s WHERE s.tcFechaIngreso = :tcFechaIngreso")})
public class SieniTipoComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_componente")
    private Long idTipoComponente;
    @Column(name = "tc_descripcion")
    private String tcDescripcion;
    @Column(name = "tc_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date tcFechaIngreso;
    @OneToMany(mappedBy = "idTipoComponente")
    private List<SieniComponente> sieniComponenteList;

    public SieniTipoComponente() {
    }

    public SieniTipoComponente(Long idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public Long getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(Long idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    public String getTcDescripcion() {
        return tcDescripcion;
    }

    public void setTcDescripcion(String tcDescripcion) {
        this.tcDescripcion = tcDescripcion;
    }

    public Date getTcFechaIngreso() {
        return tcFechaIngreso;
    }

    public void setTcFechaIngreso(Date tcFechaIngreso) {
        this.tcFechaIngreso = tcFechaIngreso;
    }

    @XmlTransient
    public List<SieniComponente> getSieniComponenteList() {
        return sieniComponenteList;
    }

    public void setSieniComponenteList(List<SieniComponente> sieniComponenteList) {
        this.sieniComponenteList = sieniComponenteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoComponente != null ? idTipoComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniTipoComponente)) {
            return false;
        }
        SieniTipoComponente other = (SieniTipoComponente) object;
        if ((this.idTipoComponente == null && other.idTipoComponente != null) || (this.idTipoComponente != null && !this.idTipoComponente.equals(other.idTipoComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniTipoComponente[ idTipoComponente=" + idTipoComponente + " ]";
    }
    
}
