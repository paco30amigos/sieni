/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "sieni_seccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniSeccion.findAll", query = "SELECT s FROM SieniSeccion s"),
    @NamedQuery(name = "SieniSeccion.findByIdSeccion", query = "SELECT s FROM SieniSeccion s WHERE s.idSeccion = :idSeccion"),
    @NamedQuery(name = "SieniSeccion.findByScDescripcion", query = "SELECT s FROM SieniSeccion s WHERE s.scDescripcion = :scDescripcion"),
    @NamedQuery(name = "SieniSeccion.findByScCoordinador", query = "SELECT s FROM SieniSeccion s WHERE s.scCoordinador = :scCoordinador")})
public class SieniSeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_seccion")
    private Long idSeccion;
    @Column(name = "sc_descripcion")
    private String scDescripcion;
    @Column(name = "sc_coordinador")
    private BigInteger scCoordinador;
    @JoinColumn(name = "id_anio_escolar", referencedColumnName = "id_anio_escolar")
    @ManyToOne
    private SieniAnioEscolar idAnioEscolar;
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    @ManyToOne
    private SieniGrado idGrado;
    @OneToMany(mappedBy = "idSeccion")
    private List<SieniMatricula> sieniMatriculaList;
    @OneToMany(mappedBy = "idSeccion")
    private List<SieniCurso> sieniCursoList;

    public SieniSeccion() {
    }

    public SieniSeccion(Long idSeccion) {
        this.idSeccion = idSeccion;
    }

    public Long getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(Long idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getScDescripcion() {
        return scDescripcion;
    }

    public void setScDescripcion(String scDescripcion) {
        this.scDescripcion = scDescripcion;
    }

    public BigInteger getScCoordinador() {
        return scCoordinador;
    }

    public void setScCoordinador(BigInteger scCoordinador) {
        this.scCoordinador = scCoordinador;
    }

    public SieniAnioEscolar getIdAnioEscolar() {
        return idAnioEscolar;
    }

    public void setIdAnioEscolar(SieniAnioEscolar idAnioEscolar) {
        this.idAnioEscolar = idAnioEscolar;
    }

    public SieniGrado getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(SieniGrado idGrado) {
        this.idGrado = idGrado;
    }

    @XmlTransient
    public List<SieniMatricula> getSieniMatriculaList() {
        return sieniMatriculaList;
    }

    public void setSieniMatriculaList(List<SieniMatricula> sieniMatriculaList) {
        this.sieniMatriculaList = sieniMatriculaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSeccion != null ? idSeccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniSeccion)) {
            return false;
        }
        SieniSeccion other = (SieniSeccion) object;
        if ((this.idSeccion == null && other.idSeccion != null) || (this.idSeccion != null && !this.idSeccion.equals(other.idSeccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniSeccion[ idSeccion=" + idSeccion + " ]";
    }

    public List<SieniCurso> getSieniCursoList() {
        return sieniCursoList;
    }

    public void setSieniCursoList(List<SieniCurso> sieniCursoList) {
        this.sieniCursoList = sieniCursoList;
    }

}
