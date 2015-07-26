/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_tema_duda", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_tema_duda"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniTemaDuda.findAll", query = "SELECT s FROM SieniTemaDuda s"),
    @NamedQuery(name = "SieniTemaDuda.findByIdTemaDuda", query = "SELECT s FROM SieniTemaDuda s WHERE s.idTemaDuda = :idTemaDuda"),
    @NamedQuery(name = "SieniTemaDuda.findByTdNombre", query = "SELECT s FROM SieniTemaDuda s WHERE s.tdNombre = :tdNombre"),
    @NamedQuery(name = "SieniTemaDuda.findByTdTipo", query = "SELECT s FROM SieniTemaDuda s WHERE s.tdTipo = :tdTipo"),
    @NamedQuery(name = "SieniTemaDuda.findByTdEstado", query = "SELECT s FROM SieniTemaDuda s WHERE s.tdEstado = :tdEstado")})
public class SieniTemaDuda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_tema_duda", nullable = false)
    private Long idTemaDuda;
    @Column(name = "td_nombre", length = 200)
    private String tdNombre;
    @Column(name = "td_tipo")
    private Character tdTipo;
    @Column(name = "td_estado")
    private Character tdEstado;
    @ManyToMany(mappedBy = "sieniTemaDudaList")
    private List<SieniAlumno> sieniAlumnoList;
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
    @ManyToOne
    private SieniDocente idDocente;
    @OneToMany(mappedBy = "idTemaDuda")
    private List<SieniResolDuda> sieniResolDudaList;

    public SieniTemaDuda() {
    }

    public SieniTemaDuda(Long idTemaDuda) {
        this.idTemaDuda = idTemaDuda;
    }

    public Long getIdTemaDuda() {
        return idTemaDuda;
    }

    public void setIdTemaDuda(Long idTemaDuda) {
        this.idTemaDuda = idTemaDuda;
    }

    public String getTdNombre() {
        return tdNombre;
    }

    public void setTdNombre(String tdNombre) {
        this.tdNombre = tdNombre;
    }

    public Character getTdTipo() {
        return tdTipo;
    }

    public void setTdTipo(Character tdTipo) {
        this.tdTipo = tdTipo;
    }

    public Character getTdEstado() {
        return tdEstado;
    }

    public void setTdEstado(Character tdEstado) {
        this.tdEstado = tdEstado;
    }

    @XmlTransient
    public List<SieniAlumno> getSieniAlumnoList() {
        return sieniAlumnoList;
    }

    public void setSieniAlumnoList(List<SieniAlumno> sieniAlumnoList) {
        this.sieniAlumnoList = sieniAlumnoList;
    }

    public SieniDocente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(SieniDocente idDocente) {
        this.idDocente = idDocente;
    }

    @XmlTransient
    public List<SieniResolDuda> getSieniResolDudaList() {
        return sieniResolDudaList;
    }

    public void setSieniResolDudaList(List<SieniResolDuda> sieniResolDudaList) {
        this.sieniResolDudaList = sieniResolDudaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemaDuda != null ? idTemaDuda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniTemaDuda)) {
            return false;
        }
        SieniTemaDuda other = (SieniTemaDuda) object;
        if ((this.idTemaDuda == null && other.idTemaDuda != null) || (this.idTemaDuda != null && !this.idTemaDuda.equals(other.idTemaDuda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniTemaDuda[ idTemaDuda=" + idTemaDuda + " ]";
    }
    
}
