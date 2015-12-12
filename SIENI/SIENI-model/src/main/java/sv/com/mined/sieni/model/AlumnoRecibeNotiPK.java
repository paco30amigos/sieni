/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author INFORMATICA
 */
@Embeddable
public class AlumnoRecibeNotiPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_alumno")
    private Long idAlumno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_notificacion")
    private Long idNotificacion;

    public AlumnoRecibeNotiPK() {
    }

    public AlumnoRecibeNotiPK(long idAlumno, long idNotificacion) {
        this.idAlumno = idAlumno;
        this.idNotificacion = idNotificacion;
    }

    public long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlumno != null ? idAlumno.hashCode() : 0);
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AlumnoRecibeNotiPK)) {
            return false;
        }
        AlumnoRecibeNotiPK other = (AlumnoRecibeNotiPK) object;
        if (this.idAlumno != other.idAlumno) {
            return false;
        }
        if (this.idNotificacion != other.idNotificacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.AlumnoRecibeNotiPK[ idAlumno=" + idAlumno + ", idNotificacion=" + idNotificacion + " ]";
    }
    
}
