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
public class DocRecibeNotiPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_docente")
    private Long idDocente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_notificacion")
    private Long idNotificacion;

    public DocRecibeNotiPK() {
    }

    public DocRecibeNotiPK(long idDocente, long idNotificacion) {
        this.idDocente = idDocente;
        this.idNotificacion = idNotificacion;
    }

    public long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(long idDocente) {
        this.idDocente = idDocente;
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
        hash += (idDocente != null ? idDocente.hashCode() : 0);
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocRecibeNotiPK)) {
            return false;
        }
        DocRecibeNotiPK other = (DocRecibeNotiPK) object;
        if (this.idDocente != other.idDocente) {
            return false;
        }
        if (this.idNotificacion != other.idNotificacion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.DocRecibeNotiPK[ idDocente=" + idDocente + ", idNotificacion=" + idNotificacion + " ]";
    }
    
}
