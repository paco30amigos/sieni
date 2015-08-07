/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_resol_duda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniResolDuda.findAll", query = "SELECT s FROM SieniResolDuda s"),
    @NamedQuery(name = "SieniResolDuda.findByIdResolDuda", query = "SELECT s FROM SieniResolDuda s WHERE s.idResolDuda = :idResolDuda"),
    @NamedQuery(name = "SieniResolDuda.findByRdMensaje", query = "SELECT s FROM SieniResolDuda s WHERE s.rdMensaje = :rdMensaje")})
public class SieniResolDuda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_resol_duda")
    private Long idResolDuda;
    @Column(name = "rd_mensaje")
    private String rdMensaje;
    @JoinColumn(name = "id_tema_duda", referencedColumnName = "id_tema_duda")
    @ManyToOne
    private SieniTemaDuda idTemaDuda;

    public SieniResolDuda() {
    }

    public SieniResolDuda(Long idResolDuda) {
        this.idResolDuda = idResolDuda;
    }

    public Long getIdResolDuda() {
        return idResolDuda;
    }

    public void setIdResolDuda(Long idResolDuda) {
        this.idResolDuda = idResolDuda;
    }

    public String getRdMensaje() {
        return rdMensaje;
    }

    public void setRdMensaje(String rdMensaje) {
        this.rdMensaje = rdMensaje;
    }

    public SieniTemaDuda getIdTemaDuda() {
        return idTemaDuda;
    }

    public void setIdTemaDuda(SieniTemaDuda idTemaDuda) {
        this.idTemaDuda = idTemaDuda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResolDuda != null ? idResolDuda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniResolDuda)) {
            return false;
        }
        SieniResolDuda other = (SieniResolDuda) object;
        if ((this.idResolDuda == null && other.idResolDuda != null) || (this.idResolDuda != null && !this.idResolDuda.equals(other.idResolDuda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniResolDuda[ idResolDuda=" + idResolDuda + " ]";
    }
    
}
