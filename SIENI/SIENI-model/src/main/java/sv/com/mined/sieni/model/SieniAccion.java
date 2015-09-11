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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_accion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAccion.findByTipoSuperComponente", query = "SELECT s FROM SieniAccion s where s.idTipoSuperCompon.idTipoSuperCompon=:idTipoSuperComponente"),
    @NamedQuery(name = "SieniAccion.findAll", query = "SELECT s FROM SieniAccion s"),
    @NamedQuery(name = "SieniAccion.findByIdAccion", query = "SELECT s FROM SieniAccion s WHERE s.idAccion = :idAccion"),
    @NamedQuery(name = "SieniAccion.findByAcDescripcion", query = "SELECT s FROM SieniAccion s WHERE s.acDescripcion = :acDescripcion"),
    @NamedQuery(name = "SieniAccion.findByEvCodigo", query = "SELECT s FROM SieniAccion s WHERE s.evCodigo = :evCodigo")})
public class SieniAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_accion")
    private Long idAccion;
    @Size(max = 100)
    @Column(name = "ac_descripcion")
    private String acDescripcion;
    @Size(max = 4000)
    @Column(name = "ev_codigo")
    private String evCodigo;
    @JoinColumn(name = "id_tipo_super_compon", referencedColumnName = "id_tipo_super_compon")
    @ManyToOne
    private SieniTipoSuperCompon idTipoSuperCompon;

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

    public SieniTipoSuperCompon getIdTipoSuperCompon() {
        return idTipoSuperCompon;
    }

    public void setIdTipoSuperCompon(SieniTipoSuperCompon idTipoSuperCompon) {
        this.idTipoSuperCompon = idTipoSuperCompon;
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
