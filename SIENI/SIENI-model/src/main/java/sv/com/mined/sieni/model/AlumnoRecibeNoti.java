/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author INFORMATICA
 */
@Entity
@Table(name = "alumno_recibe_noti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AlumnoRecibeNoti.findAll", query = "SELECT a FROM AlumnoRecibeNoti a"),
    @NamedQuery(name = "AlumnoRecibeNoti.findByIdAlumno", query = "SELECT a FROM AlumnoRecibeNoti a WHERE a.alumnoRecibeNotiPK.idAlumno = :idAlumno and a.idNotificacion.nfEstado='A'"),
    @NamedQuery(name = "AlumnoRecibeNoti.findByIdNotificacion", query = "SELECT a FROM AlumnoRecibeNoti a WHERE a.alumnoRecibeNotiPK.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "AlumnoRecibeNoti.findByNotiVisto", query = "SELECT a FROM AlumnoRecibeNoti a WHERE a.notiVisto = :notiVisto")
})

public class AlumnoRecibeNoti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AlumnoRecibeNotiPK alumnoRecibeNotiPK;
    @Basic(optional = false)
    @Column(name = "noti_visto")
    private boolean notiVisto;
    
//    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno", insertable = false, updatable = false)
//    @ManyToOne(optional = false)
//    private SieniAlumno idAlumno;
    @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SieniNotificacion idNotificacion;

    public AlumnoRecibeNoti() {
    }

    public AlumnoRecibeNoti(AlumnoRecibeNotiPK alumnoRecibeNotiPK) {
        this.alumnoRecibeNotiPK = alumnoRecibeNotiPK;
    }

    public AlumnoRecibeNoti(AlumnoRecibeNotiPK alumnoRecibeNotiPK, boolean notiVisto) {
        this.alumnoRecibeNotiPK = alumnoRecibeNotiPK;
        this.notiVisto = notiVisto;
    }

    public AlumnoRecibeNoti(long idAlumno, long idNotificacion) {
        this.alumnoRecibeNotiPK = new AlumnoRecibeNotiPK(idAlumno, idNotificacion);
    }

    public AlumnoRecibeNotiPK getAlumnoRecibeNotiPK() {
        return alumnoRecibeNotiPK;
    }

    public void setAlumnoRecibeNotiPK(AlumnoRecibeNotiPK alumnoRecibeNotiPK) {
        this.alumnoRecibeNotiPK = alumnoRecibeNotiPK;
    }

    public boolean getNotiVisto() {
        return notiVisto;
    }

    public void setNotiVisto(boolean notiVisto) {
        this.notiVisto = notiVisto;
    }

//    public SieniAlumno getIdAlumno() {
//        return idAlumno;
//    }

    public SieniNotificacion getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(SieniNotificacion idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alumnoRecibeNotiPK != null ? alumnoRecibeNotiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlumnoRecibeNoti)) {
            return false;
        }
        AlumnoRecibeNoti other = (AlumnoRecibeNoti) object;
        if ((this.alumnoRecibeNotiPK == null && other.alumnoRecibeNotiPK != null) || (this.alumnoRecibeNotiPK != null && !this.alumnoRecibeNotiPK.equals(other.alumnoRecibeNotiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.AlumnoRecibeNoti[ alumnoRecibeNotiPK=" + alumnoRecibeNotiPK + " ]";
    }
    
}
