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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "sieni_comp_interaccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniCompInteraccion.findByIdSuperComp", query = "SELECT s FROM SieniCompInteraccion s where s.idComponente.idSuperCompon.idSuperCompon=:idSuperCompon and s.inEstado not in (:estado) ORDER BY s.inOrden"),
    @NamedQuery(name = "SieniCompInteraccion.findAll", query = "SELECT s FROM SieniCompInteraccion s"),
    @NamedQuery(name = "SieniCompInteraccion.findByIdCompInteraccion", query = "SELECT s FROM SieniCompInteraccion s WHERE s.idCompInteraccion = :idCompInteraccion"),
    @NamedQuery(name = "SieniCompInteraccion.findByInDuracion", query = "SELECT s FROM SieniCompInteraccion s WHERE s.inDuracion = :inDuracion")})
public class SieniCompInteraccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_comp_interaccion")
    @SequenceGenerator(name = "sec_sieni_comp_interaccion", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_comp_interaccion")
    @Basic(optional = false)
    @Column(name = "id_comp_interaccion")
    private Long idCompInteraccion;
    @Column(name = "in_orden")
    private Integer inOrden;
    @Column(name = "in_estado")
    private Character inEstado;
    @Column(name = "in_duracion")
    private Integer inDuracion;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne
    private SieniEvento idEvento;
    @JoinColumn(name = "id_componente", referencedColumnName = "id_componente")
    @ManyToOne
    private SieniComponente idComponente;
    @JoinColumn(name = "id_accion", referencedColumnName = "id_accion")
    @ManyToOne
    private SieniAccion idAccion;

    public SieniCompInteraccion() {
    }

    public SieniCompInteraccion(Long idCompInteraccion) {
        this.idCompInteraccion = idCompInteraccion;
    }

    public Long getIdCompInteraccion() {
        return idCompInteraccion;
    }

    public void setIdCompInteraccion(Long idCompInteraccion) {
        this.idCompInteraccion = idCompInteraccion;
    }

    public Integer getInDuracion() {
        return inDuracion;
    }

    public void setInDuracion(Integer inDuracion) {
        this.inDuracion = inDuracion;
    }

    public SieniEvento getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(SieniEvento idEvento) {
        this.idEvento = idEvento;
    }

    public SieniComponente getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(SieniComponente idComponente) {
        this.idComponente = idComponente;
    }

    public SieniAccion getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(SieniAccion idAccion) {
        this.idAccion = idAccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCompInteraccion != null ? idCompInteraccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniCompInteraccion)) {
            return false;
        }
        SieniCompInteraccion other = (SieniCompInteraccion) object;
        if ((this.idCompInteraccion == null && other.idCompInteraccion != null) || (this.idCompInteraccion != null && !this.idCompInteraccion.equals(other.idCompInteraccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniCompInteraccion[ idCompInteraccion=" + idCompInteraccion + " ]";
    }

    public Integer getInOrden() {
        return inOrden;
    }

    public void setInOrden(Integer inOrden) {
        this.inOrden = inOrden;
    }

    public Character getInEstado() {
        return inEstado;
    }

    public void setInEstado(Character inEstado) {
        this.inEstado = inEstado;
    }

}
