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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "sieni_materia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniMateria.findAll", query = "SELECT s FROM SieniMateria s"),
    @NamedQuery(name = "SieniMateria.findByIdMateria", query = "SELECT s FROM SieniMateria s WHERE s.idMateria = :idMateria"),
    @NamedQuery(name = "SieniMateria.findByMaNombre", query = "SELECT s FROM SieniMateria s WHERE s.maNombre = :maNombre"),
    @NamedQuery(name = "SieniMateria.findByMaCodigo", query = "SELECT s FROM SieniMateria s WHERE s.maCodigo = :maCodigo"),
    @NamedQuery(name = "SieniMateria.findByMaFechaIngreso", query = "SELECT s FROM SieniMateria s WHERE s.maFechaIngreso = :maFechaIngreso")})
public class SieniMateria implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_materia")
    private Long idMateria;
    @Column(name = "ma_nombre")
    private String maNombre;
    @Column(name = "ma_codigo")
    private String maCodigo;
    @Column(name = "ma_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date maFechaIngreso;
    @OneToMany(mappedBy = "idMateria")
    private List<SieniPlantilla> sieniPlantillaList;
    @OneToMany(mappedBy = "idMateria")
    private List<SieniEvaluacion> sieniEvaluacionList;
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    @ManyToOne
    private SieniGrado idGrado;
    @OneToMany(mappedBy = "idMateria")
    private List<SieniCurso> sieniCursoList;

    public SieniMateria() {
    }

    public SieniMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public Long getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(Long idMateria) {
        this.idMateria = idMateria;
    }

    public String getMaNombre() {
        return maNombre;
    }

    public void setMaNombre(String maNombre) {
        this.maNombre = maNombre;
    }

    public String getMaCodigo() {
        return maCodigo;
    }

    public void setMaCodigo(String maCodigo) {
        this.maCodigo = maCodigo;
    }

    public Date getMaFechaIngreso() {
        return maFechaIngreso;
    }

    public void setMaFechaIngreso(Date maFechaIngreso) {
        this.maFechaIngreso = maFechaIngreso;
    }

    @XmlTransient
    public List<SieniPlantilla> getSieniPlantillaList() {
        return sieniPlantillaList;
    }

    public void setSieniPlantillaList(List<SieniPlantilla> sieniPlantillaList) {
        this.sieniPlantillaList = sieniPlantillaList;
    }

    @XmlTransient
    public List<SieniEvaluacion> getSieniEvaluacionList() {
        return sieniEvaluacionList;
    }

    public void setSieniEvaluacionList(List<SieniEvaluacion> sieniEvaluacionList) {
        this.sieniEvaluacionList = sieniEvaluacionList;
    }

    public SieniGrado getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(SieniGrado idGrado) {
        this.idGrado = idGrado;
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
        hash += (idMateria != null ? idMateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniMateria)) {
            return false;
        }
        SieniMateria other = (SieniMateria) object;
        if ((this.idMateria == null && other.idMateria != null) || (this.idMateria != null && !this.idMateria.equals(other.idMateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniMateria[ idMateria=" + idMateria + " ]";
    }
    
}
