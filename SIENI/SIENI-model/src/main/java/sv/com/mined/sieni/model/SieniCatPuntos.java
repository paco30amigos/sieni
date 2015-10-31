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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "sieni_cat_puntos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniCatPuntos.findAll", query = "SELECT s FROM SieniCatPuntos s"),
    @NamedQuery(name = "SieniCatPuntos.findByClase", query = "SELECT s FROM SieniCatPuntos s where s.idClase.idClase=:idClase"),
    @NamedQuery(name = "SieniCatPuntos.findByIdCatPuntos", query = "SELECT s FROM SieniCatPuntos s WHERE s.idCatPuntos = :idCatPuntos"),
    @NamedQuery(name = "SieniCatPuntos.rptAvanceClases", query = "SELECT s FROM SieniCatPuntos s WHERE s.cpEstado = 'A'") })
public class SieniCatPuntos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_cat_puntos")
    @SequenceGenerator(name = "sec_sieni_cat_puntos", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_cat_puntos")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cat_puntos")
    private Long idCatPuntos;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
    @ManyToOne
    private SieniClase idClase;
    @Column(name = "cp_estado_puntos")
    private Character cpEstado;
    @Column(name = "cp_num_puntos")
    private Integer cpNumPuntos;

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

    public Integer getCpNumPuntos() {
        return cpNumPuntos;
    }

    public void setCpNumPuntos(Integer cpNumPuntos) {
        this.cpNumPuntos = cpNumPuntos;
    }

}
