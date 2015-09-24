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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ever
 */
@Entity
@Table(name = "sieni_curso_alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniCursoAlumno.findAll", query = "SELECT s FROM SieniCursoAlumno s"),
    @NamedQuery(name = "SieniCursoAlumno.findByIdCursoAlumno", query = "SELECT s FROM SieniCursoAlumno s WHERE s.idCursoAlumno = :idCursoAlumno")})
public class SieniCursoAlumno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_curso_alumno")
    private Integer idCursoAlumno;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne
    private SieniCurso idCurso;

    public SieniCursoAlumno() {
    }

    public SieniCursoAlumno(Integer idCursoAlumno) {
        this.idCursoAlumno = idCursoAlumno;
    }

    public Integer getIdCursoAlumno() {
        return idCursoAlumno;
    }

    public void setIdCursoAlumno(Integer idCursoAlumno) {
        this.idCursoAlumno = idCursoAlumno;
    }

    public SieniAlumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(SieniAlumno idAlumno) {
        this.idAlumno = idAlumno;
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
        hash += (idCursoAlumno != null ? idCursoAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniCursoAlumno)) {
            return false;
        }
        SieniCursoAlumno other = (SieniCursoAlumno) object;
        if ((this.idCursoAlumno == null && other.idCursoAlumno != null) || (this.idCursoAlumno != null && !this.idCursoAlumno.equals(other.idCursoAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniCursoAlumno[ idCursoAlumno=" + idCursoAlumno + " ]";
    }
    
}
