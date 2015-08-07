/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_anio_escolar")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAnioEscolar.findAll", query = "SELECT s FROM SieniAnioEscolar s"),
    @NamedQuery(name = "SieniAnioEscolar.findByIdAnioEscolar", query = "SELECT s FROM SieniAnioEscolar s WHERE s.idAnioEscolar = :idAnioEscolar"),
    @NamedQuery(name = "SieniAnioEscolar.findByAeAnio", query = "SELECT s FROM SieniAnioEscolar s WHERE s.aeAnio = :aeAnio"),
    @NamedQuery(name = "SieniAnioEscolar.findByAeInicio", query = "SELECT s FROM SieniAnioEscolar s WHERE s.aeInicio = :aeInicio"),
    @NamedQuery(name = "SieniAnioEscolar.findByAeFin", query = "SELECT s FROM SieniAnioEscolar s WHERE s.aeFin = :aeFin")})
public class SieniAnioEscolar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_anio_escolar")
    private Long idAnioEscolar;
    @Column(name = "ae_anio")
    private Integer aeAnio;
    @Column(name = "ae_inicio")
    @Temporal(TemporalType.DATE)
    private Date aeInicio;
    @Column(name = "ae_fin")
    @Temporal(TemporalType.DATE)
    private Date aeFin;
    @OneToMany(mappedBy = "idAnioEscolar")
    private List<SieniSeccion> sieniSeccionList;

    public SieniAnioEscolar() {
    }

    public SieniAnioEscolar(Long idAnioEscolar) {
        this.idAnioEscolar = idAnioEscolar;
    }

    public Long getIdAnioEscolar() {
        return idAnioEscolar;
    }

    public void setIdAnioEscolar(Long idAnioEscolar) {
        this.idAnioEscolar = idAnioEscolar;
    }

    public Integer getAeAnio() {
        return aeAnio;
    }

    public void setAeAnio(Integer aeAnio) {
        this.aeAnio = aeAnio;
    }

    public Date getAeInicio() {
        return aeInicio;
    }

    public void setAeInicio(Date aeInicio) {
        this.aeInicio = aeInicio;
    }

    public Date getAeFin() {
        return aeFin;
    }

    public void setAeFin(Date aeFin) {
        this.aeFin = aeFin;
    }

    @XmlTransient
    public List<SieniSeccion> getSieniSeccionList() {
        return sieniSeccionList;
    }

    public void setSieniSeccionList(List<SieniSeccion> sieniSeccionList) {
        this.sieniSeccionList = sieniSeccionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnioEscolar != null ? idAnioEscolar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAnioEscolar)) {
            return false;
        }
        SieniAnioEscolar other = (SieniAnioEscolar) object;
        if ((this.idAnioEscolar == null && other.idAnioEscolar != null) || (this.idAnioEscolar != null && !this.idAnioEscolar.equals(other.idAnioEscolar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAnioEscolar[ idAnioEscolar=" + idAnioEscolar + " ]";
    }
    
}
