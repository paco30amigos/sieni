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
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "sieni_notificacion", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_notificacion"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniNotificacion.findAll", query = "SELECT s FROM SieniNotificacion s"),
    @NamedQuery(name = "SieniNotificacion.findByIdNotificacion", query = "SELECT s FROM SieniNotificacion s WHERE s.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "SieniNotificacion.findByNfMensaje", query = "SELECT s FROM SieniNotificacion s WHERE s.nfMensaje = :nfMensaje"),
    @NamedQuery(name = "SieniNotificacion.findByNfFechaIngreso", query = "SELECT s FROM SieniNotificacion s WHERE s.nfFechaIngreso = :nfFechaIngreso"),
    @NamedQuery(name = "SieniNotificacion.findByNfFechaFin", query = "SELECT s FROM SieniNotificacion s WHERE s.nfFechaFin = :nfFechaFin"),
    @NamedQuery(name = "SieniNotificacion.findByNfEstado", query = "SELECT s FROM SieniNotificacion s WHERE s.nfEstado = :nfEstado")})
public class SieniNotificacion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_notificacion", nullable = false)
    private Long idNotificacion;
    @Column(name = "nf_mensaje", length = 4000)
    private String nfMensaje;
    @Column(name = "nf_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date nfFechaIngreso;
    @Column(name = "nf_fecha_fin")
    @Temporal(TemporalType.DATE)
    private Date nfFechaFin;
    @Column(name = "nf_estado")
    private Character nfEstado;
    @ManyToMany(mappedBy = "sieniNotificacionList")
    private List<SieniDocente> sieniDocenteList;
    @ManyToMany(mappedBy = "sieniNotificacionList")
    private List<SieniAlumno> sieniAlumnoList;

    public SieniNotificacion() {
    }

    public SieniNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getNfMensaje() {
        return nfMensaje;
    }

    public void setNfMensaje(String nfMensaje) {
        this.nfMensaje = nfMensaje;
    }

    public Date getNfFechaIngreso() {
        return nfFechaIngreso;
    }

    public void setNfFechaIngreso(Date nfFechaIngreso) {
        this.nfFechaIngreso = nfFechaIngreso;
    }

    public Date getNfFechaFin() {
        return nfFechaFin;
    }

    public void setNfFechaFin(Date nfFechaFin) {
        this.nfFechaFin = nfFechaFin;
    }

    public Character getNfEstado() {
        return nfEstado;
    }

    public void setNfEstado(Character nfEstado) {
        this.nfEstado = nfEstado;
    }

    @XmlTransient
    public List<SieniDocente> getSieniDocenteList() {
        return sieniDocenteList;
    }

    public void setSieniDocenteList(List<SieniDocente> sieniDocenteList) {
        this.sieniDocenteList = sieniDocenteList;
    }

    @XmlTransient
    public List<SieniAlumno> getSieniAlumnoList() {
        return sieniAlumnoList;
    }

    public void setSieniAlumnoList(List<SieniAlumno> sieniAlumnoList) {
        this.sieniAlumnoList = sieniAlumnoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniNotificacion)) {
            return false;
        }
        SieniNotificacion other = (SieniNotificacion) object;
        if ((this.idNotificacion == null && other.idNotificacion != null) || (this.idNotificacion != null && !this.idNotificacion.equals(other.idNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniNotificacion[ idNotificacion=" + idNotificacion + " ]";
    }
    
}
