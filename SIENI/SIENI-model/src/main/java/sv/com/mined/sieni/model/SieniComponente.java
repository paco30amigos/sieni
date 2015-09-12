/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_componente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniComponente.findByIdSuperComp", query = "SELECT s FROM SieniComponente s where s.idSuperCompon.idSuperCompon=:idSuperCompon and s.cpEstado not in (:estado) ORDER BY s.cpOrden"),
    @NamedQuery(name = "SieniComponente.findAll", query = "SELECT s FROM SieniComponente s"),
    @NamedQuery(name = "SieniComponente.findByIdComponente", query = "SELECT s FROM SieniComponente s WHERE s.idComponente = :idComponente"),
    @NamedQuery(name = "SieniComponente.findByCpDescripcion", query = "SELECT s FROM SieniComponente s WHERE s.cpDescripcion = :cpDescripcion"),
    @NamedQuery(name = "SieniComponente.findByCpEstado", query = "SELECT s FROM SieniComponente s WHERE s.cpEstado = :cpEstado")})
public class SieniComponente implements Serializable {

    @Column(name = "id_archivo")
    private Long idArchivo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cp_ancho")
    private BigDecimal cpAncho;
    @Column(name = "cp_alto")
    private BigDecimal cpAlto;
    @Column(name = "cp_visible")
    private Character cpVisible;
    @Column(name = "cp_orden")
    private Integer cpOrden;
    @OneToMany(mappedBy = "idComponente")
    private List<SieniCompInteraccion> sieniCompInteraccionList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_componente")
    @SequenceGenerator(name = "sec_sieni_componente", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_componente")
    @Basic(optional = false)
    @Column(name = "id_componente")
    private Long idComponente;
    @Column(name = "cp_descripcion")
    private String cpDescripcion;
    @Column(name = "cp_estado")
    private Character cpEstado;
    @OneToMany(mappedBy = "idComponente", fetch = FetchType.EAGER)
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

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public BigDecimal getCpAncho() {
        return cpAncho;
    }

    public void setCpAncho(BigDecimal cpAncho) {
        this.cpAncho = cpAncho;
    }

    public BigDecimal getCpAlto() {
        return cpAlto;
    }

    public void setCpAlto(BigDecimal cpAlto) {
        this.cpAlto = cpAlto;
    }

    public Character getCpVisible() {
        return cpVisible;
    }

    public void setCpVisible(Character cpVisible) {
        this.cpVisible = cpVisible;
    }

    @XmlTransient
    public List<SieniCompInteraccion> getSieniCompInteraccionList() {
        return sieniCompInteraccionList;
    }

    public void setSieniCompInteraccionList(List<SieniCompInteraccion> sieniCompInteraccionList) {
        this.sieniCompInteraccionList = sieniCompInteraccionList;
    }

    public Integer getCpOrden() {
        return cpOrden;
    }

    public void setCpOrden(Integer cpOrden) {
        this.cpOrden = cpOrden;
    }

}
