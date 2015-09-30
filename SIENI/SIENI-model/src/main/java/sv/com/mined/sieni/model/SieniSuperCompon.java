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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
    @NamedQuery(name = "SieniSuperCompon.findAllNoInactivos", query = "SELECT s FROM SieniSuperCompon s where s.scEstado not in (:estado) ORDER BY s.idSuperCompon"),
    @NamedQuery(name = "SieniSuperCompon.findAll", query = "SELECT s FROM SieniSuperCompon s"),
    @NamedQuery(name = "SieniSuperCompon.findByClase", query = "SELECT s FROM SieniSuperCompon s JOIN FETCH s.sieniClaseSupCompList c where c.idClase.idClase=:idClase and c.idClase.clEstado not in (:estado) and s.scEstado not in (:estado)"),
    @NamedQuery(name = "SieniSuperCompon.findByIdSuperCompon", query = "SELECT s FROM SieniSuperCompon s WHERE s.idSuperCompon = :idSuperCompon"),
    @NamedQuery(name = "SieniSuperCompon.findByScNombre", query = "SELECT s FROM SieniSuperCompon s WHERE s.scNombre = :scNombre"),
    @NamedQuery(name = "SieniSuperCompon.findByScDescripcion", query = "SELECT s FROM SieniSuperCompon s WHERE s.scDescripcion = :scDescripcion"),
    @NamedQuery(name = "SieniSuperCompon.findByScFechaIngreso", query = "SELECT s FROM SieniSuperCompon s WHERE s.scFechaIngreso = :scFechaIngreso")})
public class SieniSuperCompon implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fCompSuperCompon")
    private List<SieniClaseSupComp> sieniClaseSupCompList;

    @JoinColumn(name = "id_tipo_super_compon", referencedColumnName = "id_tipo_super_compon")
    @ManyToOne
    private SieniTipoSuperCompon idTipoSuperCompon;
    @Column(name = "sc_estado")
    private Character scEstado;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_super_compon")
    @SequenceGenerator(name = "sec_sieni_super_compon", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_super_compon")
    @Basic(optional = false)
    @Column(name = "id_super_compon")
    private Long idSuperCompon;
    @Column(name = "sc_nombre")
    private String scNombre;
    @Column(name = "sc_ancho")
    private Integer scAncho;
    @Column(name = "sc_alto")
    private Integer scAlto;
    @Column(name = "sc_pos_x")
    private Integer scPosX;
    @Column(name = "sc_pos_y")
    private Integer scPosY;
    @Column(name = "sc_descripcion")
    private String scDescripcion;
    @Column(name = "sc_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date scFechaIngreso;
    @OneToMany(mappedBy = "idSuperCompon", fetch = FetchType.EAGER)
    private List<SieniComponente> sieniComponenteList;

    @Transient
    private String estado;

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

    public Character getScEstado() {
        return scEstado;
    }

    public void setScEstado(Character scEstado) {
        this.scEstado = scEstado;
    }

    public SieniTipoSuperCompon getIdTipoSuperCompon() {
        return idTipoSuperCompon;
    }

    public void setIdTipoSuperCompon(SieniTipoSuperCompon idTipoSuperCompon) {
        this.idTipoSuperCompon = idTipoSuperCompon;
    }

    public String getEstado() {
        switch (scEstado) {
            case 'A':
                estado = "Activo";
                break;
            case 'T':
                estado = "Trabajando";
                break;
            case 'I':
                estado = "Eliminado";
                break;
        }
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getScAncho() {
        return scAncho;
    }

    public void setScAncho(Integer scAncho) {
        this.scAncho = scAncho;
    }

    public Integer getScAlto() {
        return scAlto;
    }

    public void setScAlto(Integer scAlto) {
        this.scAlto = scAlto;
    }

    public List<SieniClaseSupComp> getSieniClaseSupCompList() {
        return sieniClaseSupCompList;
    }

    public void setSieniClaseSupCompList(List<SieniClaseSupComp> sieniClaseSupCompList) {
        this.sieniClaseSupCompList = sieniClaseSupCompList;
    }

    public Integer getScPosX() {
        return scPosX;
    }

    public void setScPosX(Integer scPosX) {
        this.scPosX = scPosX;
    }

    public Integer getScPosY() {
        return scPosY;
    }

    public void setScPosY(Integer scPosY) {
        this.scPosY = scPosY;
    }

}
