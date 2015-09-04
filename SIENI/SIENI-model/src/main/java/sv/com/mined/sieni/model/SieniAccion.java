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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAccion.findAll", query = "SELECT s FROM SieniAccion s"),
    @NamedQuery(name = "SieniAccion.findByIdAccion", query = "SELECT s FROM SieniAccion s WHERE s.idAccion = :idAccion"),
    @NamedQuery(name = "SieniAccion.findByAcDescripcion", query = "SELECT s FROM SieniAccion s WHERE s.acDescripcion = :acDescripcion"),
    @NamedQuery(name = "SieniAccion.findByEvCodigo", query = "SELECT s FROM SieniAccion s WHERE s.evCodigo = :evCodigo")})
public class SieniAccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_accion")
    private Long idAccion;
    @Column(name = "ac_descripcion")
    private String acDescripcion;
    @Column(name = "ev_codigo")
    private String evCodigo;
    @OneToMany(mappedBy = "idAccion")
    private List<SieniCompInteraccion> sieniCompInteraccionList;

    public SieniAccion() {
    }

    public SieniAccion(Long idAccion) {
        this.idAccion = idAccion;
    }

    public Long getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Long idAccion) {
        this.idAccion = idAccion;
    }

    public String getAcDescripcion() {
        return acDescripcion;
    }

    public void setAcDescripcion(String acDescripcion) {
        this.acDescripcion = acDescripcion;
    }

    public String getEvCodigo() {
        return evCodigo;
    }

    public void setEvCodigo(String evCodigo) {
        this.evCodigo = evCodigo;
    }

    @XmlTransient
    public List<SieniCompInteraccion> getSieniCompInteraccionList() {
        return sieniCompInteraccionList;
    }

    public void setSieniCompInteraccionList(List<SieniCompInteraccion> sieniCompInteraccionList) {
        this.sieniCompInteraccionList = sieniCompInteraccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccion != null ? idAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAccion)) {
            return false;
        }
        SieniAccion other = (SieniAccion) object;
        if ((this.idAccion == null && other.idAccion != null) || (this.idAccion != null && !this.idAccion.equals(other.idAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAccion[ idAccion=" + idAccion + " ]";
    }
    
}
