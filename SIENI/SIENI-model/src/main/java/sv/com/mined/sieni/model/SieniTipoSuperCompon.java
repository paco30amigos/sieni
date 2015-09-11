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
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_tipo_super_compon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniTipoSuperCompon.findAll", query = "SELECT s FROM SieniTipoSuperCompon s"),
    @NamedQuery(name = "SieniTipoSuperCompon.findByIdTipoSuperCompon", query = "SELECT s FROM SieniTipoSuperCompon s WHERE s.idTipoSuperCompon = :idTipoSuperCompon"),
    @NamedQuery(name = "SieniTipoSuperCompon.findByTscDescripcion", query = "SELECT s FROM SieniTipoSuperCompon s WHERE s.tscDescripcion = :tscDescripcion"),
    @NamedQuery(name = "SieniTipoSuperCompon.findByTscFechaIngreso", query = "SELECT s FROM SieniTipoSuperCompon s WHERE s.tscFechaIngreso = :tscFechaIngreso"),
    @NamedQuery(name = "SieniTipoSuperCompon.findByTscEstado", query = "SELECT s FROM SieniTipoSuperCompon s WHERE s.tscEstado = :tscEstado")})
public class SieniTipoSuperCompon implements Serializable {
    @OneToMany(mappedBy = "idTipoSuperCompon")
    private List<SieniAccion> sieniAccionList;
    @OneToMany(mappedBy = "idTipoSuperCompon")
    private List<SieniEvento> sieniEventoList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tipo_super_compon")
    private Long idTipoSuperCompon;
    @Column(name = "tsc_descripcion")
    private String tscDescripcion;
    @Column(name = "tsc_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date tscFechaIngreso;
    @Column(name = "tsc_estado")
    private Character tscEstado;
    @OneToMany(mappedBy = "idTipoSuperCompon")
    private List<SieniSuperCompon> sieniSuperComponList;

    public SieniTipoSuperCompon() {
    }

    public SieniTipoSuperCompon(Long idTipoSuperCompon) {
        this.idTipoSuperCompon = idTipoSuperCompon;
    }

    public Long getIdTipoSuperCompon() {
        return idTipoSuperCompon;
    }

    public void setIdTipoSuperCompon(Long idTipoSuperCompon) {
        this.idTipoSuperCompon = idTipoSuperCompon;
    }

    public String getTscDescripcion() {
        return tscDescripcion;
    }

    public void setTscDescripcion(String tscDescripcion) {
        this.tscDescripcion = tscDescripcion;
    }

    public Date getTscFechaIngreso() {
        return tscFechaIngreso;
    }

    public void setTscFechaIngreso(Date tscFechaIngreso) {
        this.tscFechaIngreso = tscFechaIngreso;
    }

    public Character getTscEstado() {
        return tscEstado;
    }

    public void setTscEstado(Character tscEstado) {
        this.tscEstado = tscEstado;
    }

    @XmlTransient
    public List<SieniSuperCompon> getSieniSuperComponList() {
        return sieniSuperComponList;
    }

    public void setSieniSuperComponList(List<SieniSuperCompon> sieniSuperComponList) {
        this.sieniSuperComponList = sieniSuperComponList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoSuperCompon != null ? idTipoSuperCompon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniTipoSuperCompon)) {
            return false;
        }
        SieniTipoSuperCompon other = (SieniTipoSuperCompon) object;
        if ((this.idTipoSuperCompon == null && other.idTipoSuperCompon != null) || (this.idTipoSuperCompon != null && !this.idTipoSuperCompon.equals(other.idTipoSuperCompon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniTipoSuperCompon[ idTipoSuperCompon=" + idTipoSuperCompon + " ]";
    }

    @XmlTransient
    public List<SieniAccion> getSieniAccionList() {
        return sieniAccionList;
    }

    public void setSieniAccionList(List<SieniAccion> sieniAccionList) {
        this.sieniAccionList = sieniAccionList;
    }

    @XmlTransient
    public List<SieniEvento> getSieniEventoList() {
        return sieniEventoList;
    }

    public void setSieniEventoList(List<SieniEvento> sieniEventoList) {
        this.sieniEventoList = sieniEventoList;
    }
    
}
