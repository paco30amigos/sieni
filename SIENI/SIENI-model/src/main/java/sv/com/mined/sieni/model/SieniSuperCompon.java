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
@Table(name = "sieni_super_compon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniSuperCompon.findAll", query = "SELECT s FROM SieniSuperCompon s"),
    @NamedQuery(name = "SieniSuperCompon.findByIdSuperCompon", query = "SELECT s FROM SieniSuperCompon s WHERE s.idSuperCompon = :idSuperCompon"),
    @NamedQuery(name = "SieniSuperCompon.findByScNombre", query = "SELECT s FROM SieniSuperCompon s WHERE s.scNombre = :scNombre"),
    @NamedQuery(name = "SieniSuperCompon.findByScDescripcion", query = "SELECT s FROM SieniSuperCompon s WHERE s.scDescripcion = :scDescripcion"),
    @NamedQuery(name = "SieniSuperCompon.findByScFechaIngreso", query = "SELECT s FROM SieniSuperCompon s WHERE s.scFechaIngreso = :scFechaIngreso")})
public class SieniSuperCompon implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_super_compon")
    private Long idSuperCompon;
    @Column(name = "sc_nombre")
    private String scNombre;
    @Column(name = "sc_descripcion")
    private String scDescripcion;
    @Column(name = "sc_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date scFechaIngreso;
    @OneToMany(mappedBy = "idSuperCompon")
    private List<SieniComponente> sieniComponenteList;

    public SieniSuperCompon() {
    }

    public SieniSuperCompon(Long idSuperCompon) {
        this.idSuperCompon = idSuperCompon;
    }

    public Long getIdSuperCompon() {
        return idSuperCompon;
    }

    public void setIdSuperCompon(Long idSuperCompon) {
        this.idSuperCompon = idSuperCompon;
    }

    public String getScNombre() {
        return scNombre;
    }

    public void setScNombre(String scNombre) {
        this.scNombre = scNombre;
    }

    public String getScDescripcion() {
        return scDescripcion;
    }

    public void setScDescripcion(String scDescripcion) {
        this.scDescripcion = scDescripcion;
    }

    public Date getScFechaIngreso() {
        return scFechaIngreso;
    }

    public void setScFechaIngreso(Date scFechaIngreso) {
        this.scFechaIngreso = scFechaIngreso;
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
        hash += (idSuperCompon != null ? idSuperCompon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniSuperCompon)) {
            return false;
        }
        SieniSuperCompon other = (SieniSuperCompon) object;
        if ((this.idSuperCompon == null && other.idSuperCompon != null) || (this.idSuperCompon != null && !this.idSuperCompon.equals(other.idSuperCompon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniSuperCompon[ idSuperCompon=" + idSuperCompon + " ]";
    }
    
}
