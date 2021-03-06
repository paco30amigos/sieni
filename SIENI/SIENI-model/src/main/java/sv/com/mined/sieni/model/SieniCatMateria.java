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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alejandro
 */
@Entity
@Table(name = "sieni_cat_materia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniCatMateria.findAll", query = "SELECT s FROM SieniCatMateria s"),
    @NamedQuery(name = "SieniCatMateria.findAllNoInactivos", query = "SELECT s FROM SieniCatMateria s where s.catEstado not in (:estado)"),
    @NamedQuery(name = "SieniCatMateria.findByEstado", query = "SELECT s FROM SieniCatMateria s where s.catEstado =:estado"),
    @NamedQuery(name = "SieniCatMateria.findByIdCatMateria", query = "SELECT s FROM SieniCatMateria s WHERE s.idCatMateria = :idCatMateria"),
    @NamedQuery(name = "SieniCatMateria.findByCatNombre", query = "SELECT s FROM SieniCatMateria s WHERE s.catNombre = :nombre and s.catEstado not in (:estado)")})
public class SieniCatMateria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_cat_materia")
    @SequenceGenerator(name = "sec_sieni_cat_materia", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_cat_materia")
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cat_materia")
    private Long idCatMateria;
    @Size(max = 100)
    @Column(name = "cat_nombre")
    private String catNombre;
    @Column(name = "cat_estado")
    private Character catEstado;
    @Transient
    String estado;

    public SieniCatMateria() {
    }

    public SieniCatMateria(Long idCatMateria) {
        this.idCatMateria = idCatMateria;
    }

    public Long getIdCatMateria() {
        return idCatMateria;
    }

    public void setIdCatMateria(Long idCatMateria) {
        this.idCatMateria = idCatMateria;
    }

    public String getCatNombre() {
        return catNombre;
    }

    public void setCatNombre(String catNombre) {
        this.catNombre = catNombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCatMateria != null ? idCatMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniCatMateria)) {
            return false;
        }
        SieniCatMateria other = (SieniCatMateria) object;
        if ((this.idCatMateria == null && other.idCatMateria != null) || (this.idCatMateria != null && !this.idCatMateria.equals(other.idCatMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniCatMateria[ idCatMateria=" + idCatMateria + " ]";
    }

    public Character getCatEstado() {
        return catEstado;
    }

    public void setCatEstado(Character catEstado) {
        this.catEstado = catEstado;
    }

    public String getEstado() {
        estado = "";
        if (this.getCatEstado() != null) {
            switch (this.getCatEstado()) {
                case 'A':
                    estado = "Disponible";
                    break;
                case 'T':
                    estado = "No Disponible";
                    break;
                case 'I':
                    estado = "Eliminado";
                    break;
            }
        }
        return estado;
    }

}
