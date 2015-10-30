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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "sieni_cat_puntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniCatPuntos.findAll", query = "SELECT s FROM SieniCatPuntos s"),
    @NamedQuery(name = "SieniCatPuntos.findByIdCatPuntos", query = "SELECT s FROM SieniCatPuntos s WHERE s.idCatPuntos = :idCatPuntos"),
    @NamedQuery(name = "SieniCatPuntos.findByIdClase", query = "SELECT s FROM SieniCatPuntos s WHERE s.idClase = :idClase"),
    @NamedQuery(name = "SieniCatPuntos.findByIdClaseSupComp", query = "SELECT s FROM SieniCatPuntos s WHERE s.idClaseSupComp = :idClaseSupComp"),
    @NamedQuery(name = "SieniCatPuntos.findByEstadoPuntos", query = "SELECT s FROM SieniCatPuntos s WHERE s.estadoPuntos = :estadoPuntos")})
public class SieniCatPuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cat_puntos")
    private Long idCatPuntos;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
    @ManyToOne
    private SieniClase idClase;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_clase_sup_comp")
    private long idClaseSupComp;
    @Column(name = "cp_estado_puntos")
    private Character cpEstado;
    @Column(name = "cp_num_puntos")
    private Character cpNumPuntos;

    @OneToMany(mappedBy = "idClase")
    private List<SieniClase> sieniClaseList;
    @OneToMany(mappedBy = "idClaseSupComp")
    private List<SieniClaseSupComp> claseSupCompList;

    public SieniCatPuntos() {
    }

    public SieniCatPuntos(Long idCatPuntos) {
        this.idCatPuntos = idCatPuntos;
    }

    public Long getIdCatPuntos() {
        return idCatPuntos;
    }

    public void setIdCatPuntos(Long idCatPuntos) {
        this.idCatPuntos = idCatPuntos;
    }

    public long getIdClaseSupComp() {
        return idClaseSupComp;
    }

    public void setIdClaseSupComp(long idClaseSupComp) {
        this.idClaseSupComp = idClaseSupComp;
    }


    @XmlTransient
    public List<SieniClase> getSieniClaseList() {
        return sieniClaseList;
    }

    public void setSieniClaseList(List<SieniClase> sieniClaseList) {
        this.sieniClaseList = sieniClaseList;
    }

    @XmlTransient
    public List<SieniClaseSupComp> getClaseSupCompList() {
        return claseSupCompList;
    }

    public void setClaseSupCompList(List<SieniClaseSupComp> claseSupCompList) {
        this.claseSupCompList = claseSupCompList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatPuntos != null ? idCatPuntos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniCatPuntos)) {
            return false;
        }
        SieniCatPuntos other = (SieniCatPuntos) object;
        if ((this.idCatPuntos == null && other.idCatPuntos != null) || (this.idCatPuntos != null && !this.idCatPuntos.equals(other.idCatPuntos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniCatPuntos[ idCatPuntos=" + idCatPuntos + " ]";
    }

    public SieniClase getIdClase() {
        return idClase;
    }

    public void setIdClase(SieniClase idClase) {
        this.idClase = idClase;
    }

    public Character getCpEstado() {
        return cpEstado;
    }

    public void setCpEstado(Character cpEstado) {
        this.cpEstado = cpEstado;
    }

    public Character getCpNumPuntos() {
        return cpNumPuntos;
    }

    public void setCpNumPuntos(Character cpNumPuntos) {
        this.cpNumPuntos = cpNumPuntos;
    }

}
