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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
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
    @NamedQuery(name = "SieniCursoAlumno.findByIdAlumno", query = "SELECT s FROM SieniCursoAlumno s WHERE s.idAlumno=:idAlumno and s.craEstado='A'"),
    @NamedQuery(name = "SieniCursoAlumno.findByIdCursoIdAlumno", query = "SELECT s FROM SieniCursoAlumno s WHERE s.idCurso.idCurso=:idCurso AND s.idAlumno=:idAlumno and s.craEstado='A'"),
    @NamedQuery(name = "SieniCursoAlumno.findByIdCursoAlumno", query = "SELECT s FROM SieniCursoAlumno s WHERE s.idCursoAlumno = :idCursoAlumno and s.craEstado='A'")})
public class SieniCursoAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_curso_alumno")
    @SequenceGenerator(name = "sec_sieni_curso_alumno", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_curso_alumno")
    @Basic(optional = false)
    @Column(name = "id_curso_alumno")
    private Integer idCursoAlumno;
//    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private SieniAlumno idAlumno;
    @Column(name = "id_alumno")
    private Long idAlumno;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne(fetch = FetchType.LAZY)
    private SieniCurso idCurso;
    @Transient
    private SieniAlumno alumno;
    @Column(name = "cra_estado")
    private Character craEstado;

    public Character getCraEstado() {
        return craEstado;
    }

    public void setCraEstado(Character craEstado) {
        this.craEstado = craEstado;
    }

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

//    public SieniAlumno getIdAlumno() {
//        return idAlumno;
//    }
//
//    public void setIdAlumno(SieniAlumno idAlumno) {
//        this.idAlumno = idAlumno;
//    }
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

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public SieniAlumno getAlumno() {
        return alumno;
    }

    public void setAlumno(SieniAlumno alumno) {
        this.alumno = alumno;
    }

}
