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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniComponente.findAll", query = "SELECT s FROM SieniComponente s"),
    @NamedQuery(name = "SieniComponente.findByIdComponente", query = "SELECT s FROM SieniComponente s WHERE s.idComponente = :idComponente"),
    @NamedQuery(name = "SieniComponente.findByCpDescripcion", query = "SELECT s FROM SieniComponente s WHERE s.cpDescripcion = :cpDescripcion"),
    @NamedQuery(name = "SieniComponente.findByCpEstado", query = "SELECT s FROM SieniComponente s WHERE s.cpEstado = :cpEstado")})
public class SieniComponente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_componente")
    private Long idComponente;
    @Column(name = "cp_descripcion")
    private String cpDescripcion;
    @Column(name = "cp_estado")
    private Character cpEstado;
    @OneToMany(mappedBy = "idComponente")
    private List<SieniArchivo> sieniArchivoList;
    @JoinColumn(name = "id_super_compon", referencedColumnName = "id_super_compon")
    @ManyToOne
    private SieniSuperCompon idSuperCompon;
    @JoinColumn(name = "id_tipo_componente", referencedColumnName = "id_tipo_componente")
    @ManyToOne
    private SieniTipoComponente idTipoComponente;

    public SieniComponente() {
    }

    public SieniComponente(Long idComponente) {
        this.idComponente = idComponente;
    }

    public Long getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Long idComponente) {
        this.idComponente = idComponente;
    }

    public String getCpDescripcion() {
        return cpDescripcion;
    }

    public void setCpDescripcion(String cpDescripcion) {
        this.cpDescripcion = cpDescripcion;
    }

    public Character getCpEstado() {
        return cpEstado;
    }

    public void setCpEstado(Character cpEstado) {
        this.cpEstado = cpEstado;
    }

    @XmlTransient
    public List<SieniArchivo> getSieniArchivoList() {
        return sieniArchivoList;
    }

    public void setSieniArchivoList(List<SieniArchivo> sieniArchivoList) {
        this.sieniArchivoList = sieniArchivoList;
    }

    public SieniSuperCompon getIdSuperCompon() {
        return idSuperCompon;
    }

    public void setIdSuperCompon(SieniSuperCompon idSuperCompon) {
        this.idSuperCompon = idSuperCompon;
    }

    public SieniTipoComponente getIdTipoComponente() {
        return idTipoComponente;
    }

    public void setIdTipoComponente(SieniTipoComponente idTipoComponente) {
        this.idTipoComponente = idTipoComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComponente != null ? idComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniComponente)) {
            return false;
        }
        SieniComponente other = (SieniComponente) object;
        if ((this.idComponente == null && other.idComponente != null) || (this.idComponente != null && !this.idComponente.equals(other.idComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniComponente[ idComponente=" + idComponente + " ]";
    }
    
}
