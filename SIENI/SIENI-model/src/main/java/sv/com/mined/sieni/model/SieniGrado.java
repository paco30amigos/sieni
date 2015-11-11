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
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "sieni_grado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniGrado.findGradoActualAlumno", query = "SELECT s FROM SieniGrado s join fetch s.sieniMatriculaList mt where mt.idAlumno.idAlumno=:idAlumno and mt.mtFechaIngreso =:anioDesde and mt.mtFechaIngreso =:anioHasta and mt.mtEstado not in (:estado) and s.grEstado not in (:estado)"),
    @NamedQuery(name = "SieniGrado.findAll", query = "SELECT s FROM SieniGrado s"),
    @NamedQuery(name = "SieniGrado.findByIdGrado", query = "SELECT s FROM SieniGrado s WHERE s.idGrado = :idGrado"),
    @NamedQuery(name = "SieniGrado.findByGrNombre", query = "SELECT s FROM SieniGrado s WHERE s.grNombre = :grNombre"),
    @NamedQuery(name = "SieniGrado.findByGrNumero", query = "SELECT s FROM SieniGrado s WHERE s.grNumero = :grNumero")})
public class SieniGrado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_grado")
    private Long idGrado;
    @Column(name = "gr_nombre")
    private String grNombre;
    @Column(name = "gr_numero")
    private Integer grNumero;
    @Column(name = "gr_estado")
    private Character grEstado;
    @OneToMany(mappedBy = "idGrado", fetch = FetchType.EAGER)
    private List<SieniSeccion> sieniSeccionList;
    @OneToMany(mappedBy = "idGrado")
    private List<SieniMatricula> sieniMatriculaList;
    @OneToMany(mappedBy = "idGrado", fetch = FetchType.EAGER)
    private List<SieniMateria> sieniMateriaList;
    @OneToMany(mappedBy = "idGrado")
    private List<SieniCurso> sieniCursoList;

    public SieniGrado() {
    }

    public SieniGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public Long getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(Long idGrado) {
        this.idGrado = idGrado;
    }

    public String getGrNombre() {
        return grNombre;
    }

    public void setGrNombre(String grNombre) {
        this.grNombre = grNombre;
    }

    public Integer getGrNumero() {
        return grNumero;
    }

    public void setGrNumero(Integer grNumero) {
        this.grNumero = grNumero;
    }

    @XmlTransient
    public List<SieniSeccion> getSieniSeccionList() {
        return sieniSeccionList;
    }

    public void setSieniSeccionList(List<SieniSeccion> sieniSeccionList) {
        this.sieniSeccionList = sieniSeccionList;
    }

    @XmlTransient
    public List<SieniMatricula> getSieniMatriculaList() {
        return sieniMatriculaList;
    }

    public void setSieniMatriculaList(List<SieniMatricula> sieniMatriculaList) {
        this.sieniMatriculaList = sieniMatriculaList;
    }

    @XmlTransient
    public List<SieniMateria> getSieniMateriaList() {
        return sieniMateriaList;
    }

    public void setSieniMateriaList(List<SieniMateria> sieniMateriaList) {
        this.sieniMateriaList = sieniMateriaList;
    }

    @XmlTransient
    public List<SieniCurso> getSieniCursoList() {
        return sieniCursoList;
    }

    public void setSieniCursoList(List<SieniCurso> sieniCursoList) {
        this.sieniCursoList = sieniCursoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrado != null ? idGrado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniGrado)) {
            return false;
        }
        SieniGrado other = (SieniGrado) object;
        if ((this.idGrado == null && other.idGrado != null) || (this.idGrado != null && !this.idGrado.equals(other.idGrado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniGrado[ idGrado=" + idGrado + " ]";
    }

    public Character getGrEstado() {
        return grEstado;
    }

    public void setGrEstado(Character grEstado) {
        this.grEstado = grEstado;
    }
}
