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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_clase", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_clase"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniClase.findAll", query = "SELECT s FROM SieniClase s"),
    @NamedQuery(name = "SieniClase.findByIdClase", query = "SELECT s FROM SieniClase s WHERE s.idClase = :idClase"),
    @NamedQuery(name = "SieniClase.findByClHorario", query = "SELECT s FROM SieniClase s WHERE s.clHorario = :clHorario"),
    @NamedQuery(name = "SieniClase.findByClEstado", query = "SELECT s FROM SieniClase s WHERE s.clEstado = :clEstado"),
    @NamedQuery(name = "SieniClase.findByClTipo", query = "SELECT s FROM SieniClase s WHERE s.clTipo = :clTipo")})
public class SieniClase implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_clase", nullable = false)
    private Long idClase;
    @Column(name = "cl_horario", length = 50)
    private String clHorario;
    @Column(name = "cl_estado")
    private Character clEstado;
    @Column(name = "cl_tipo")
    private Character clTipo;
    @OneToMany(mappedBy = "idClase")
    private List<SieniPntosContrl> sieniPntosContrlList;
    @OneToMany(mappedBy = "idClase")
    private List<SieniClaseSupComp> sieniClaseSupCompList;
    @JoinColumn(name = "id_plantilla", referencedColumnName = "id_plantilla")
    @ManyToOne
    private SieniPlantilla idPlantilla;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne
    private SieniCurso idCurso;

    public SieniClase() {
    }

    public SieniClase(Long idClase) {
        this.idClase = idClase;
    }

    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }

    public String getClHorario() {
        return clHorario;
    }

    public void setClHorario(String clHorario) {
        this.clHorario = clHorario;
    }

    public Character getClEstado() {
        return clEstado;
    }

    public void setClEstado(Character clEstado) {
        this.clEstado = clEstado;
    }

    public Character getClTipo() {
        return clTipo;
    }

    public void setClTipo(Character clTipo) {
        this.clTipo = clTipo;
    }

    @XmlTransient
    public List<SieniPntosContrl> getSieniPntosContrlList() {
        return sieniPntosContrlList;
    }

    public void setSieniPntosContrlList(List<SieniPntosContrl> sieniPntosContrlList) {
        this.sieniPntosContrlList = sieniPntosContrlList;
    }

    @XmlTransient
    public List<SieniClaseSupComp> getSieniClaseSupCompList() {
        return sieniClaseSupCompList;
    }

    public void setSieniClaseSupCompList(List<SieniClaseSupComp> sieniClaseSupCompList) {
        this.sieniClaseSupCompList = sieniClaseSupCompList;
    }

    public SieniPlantilla getIdPlantilla() {
        return idPlantilla;
    }

    public void setIdPlantilla(SieniPlantilla idPlantilla) {
        this.idPlantilla = idPlantilla;
    }

    public SieniCurso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(SieniCurso idCurso) {
        this.idCurso = idCurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idClase != null ? idClase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniClase)) {
            return false;
        }
        SieniClase other = (SieniClase) object;
        if ((this.idClase == null && other.idClase != null) || (this.idClase != null && !this.idClase.equals(other.idClase))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniClase[ idClase=" + idClase + " ]";
    }
    
}
