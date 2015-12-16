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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author INFORMATICA
 */
@Entity
@Table(name = "doc_recibe_noti")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DocRecibeNoti.findAll", query = "SELECT d FROM DocRecibeNoti d"),
    @NamedQuery(name = "DocRecibeNoti.findByIdDocente", query = "SELECT d FROM DocRecibeNoti d WHERE d.docRecibeNotiPK.idDocente = :idDocente"),
    @NamedQuery(name = "DocRecibeNoti.findByIdNotificacion", query = "SELECT d FROM DocRecibeNoti d WHERE d.docRecibeNotiPK.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "DocRecibeNoti.findByNotiVisto", query = "SELECT d FROM DocRecibeNoti d WHERE d.notiVisto = :notiVisto")})
public class DocRecibeNoti implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DocRecibeNotiPK docRecibeNotiPK;
    @Basic(optional = false)
    @Column(name = "noti_visto")
    private boolean notiVisto;
    
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SieniDocente idDocente;
    @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SieniNotificacion idNotificacion;
    
    public DocRecibeNoti() {
    }

    public DocRecibeNoti(DocRecibeNotiPK docRecibeNotiPK) {
        this.docRecibeNotiPK = docRecibeNotiPK;
    }

    public DocRecibeNoti(DocRecibeNotiPK docRecibeNotiPK, boolean notiVisto) {
        this.docRecibeNotiPK = docRecibeNotiPK;
        this.notiVisto = notiVisto;
    }

    public DocRecibeNoti(long idDocente, long idNotificacion) {
        this.docRecibeNotiPK = new DocRecibeNotiPK(idDocente, idNotificacion);
    }

    public DocRecibeNotiPK getDocRecibeNotiPK() {
        return docRecibeNotiPK;
    }

    public void setDocRecibeNotiPK(DocRecibeNotiPK docRecibeNotiPK) {
        this.docRecibeNotiPK = docRecibeNotiPK;
    }

    public boolean getNotiVisto() {
        return notiVisto;
    }

    public void setNotiVisto(boolean notiVisto) {
        this.notiVisto = notiVisto;
    }

    public SieniDocente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(SieniDocente idDocente) {
        this.idDocente = idDocente;
    }

    public SieniNotificacion getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(SieniNotificacion idNotificacion) {
        this.idNotificacion = idNotificacion;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (docRecibeNotiPK != null ? docRecibeNotiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DocRecibeNoti)) {
            return false;
        }
        DocRecibeNoti other = (DocRecibeNoti) object;
        if ((this.docRecibeNotiPK == null && other.docRecibeNotiPK != null) || (this.docRecibeNotiPK != null && !this.docRecibeNotiPK.equals(other.docRecibeNotiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.DocRecibeNoti[ docRecibeNotiPK=" + docRecibeNotiPK + " ]";
    }
    
}
