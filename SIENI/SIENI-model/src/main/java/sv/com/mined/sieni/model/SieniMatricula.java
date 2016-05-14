/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_matricula")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniMatricula.findAnio", query = "SELECT s FROM SieniMatricula s,SieniAlumno al where s.idAlumno=al.idAlumno and s.mtFechaIngreso>=:anioDesde and s.mtFechaIngreso<=:anioHasta and al.alEstado not in (:estado) and s.mtEstado not in (:estado)"),
    @NamedQuery(name = "SieniMatricula.findAnioGrado", query = "SELECT s FROM SieniMatricula s,SieniAlumno al where s.idAlumno=al.idAlumno and s.mtFechaIngreso>=:anioDesde and s.mtFechaIngreso<=:anioHasta and s.idGrado.idGrado=:grado and al.alEstado not in (:estado) and s.mtEstado not in (:estado)"),
    @NamedQuery(name = "SieniMatricula.findAnioGradoSeccion", query = "SELECT s FROM SieniMatricula s ,SieniAlumno al where s.idAlumno=al.idAlumno and s.mtFechaIngreso>=:anioDesde and s.mtFechaIngreso<=:anioHasta and s.idGrado.idGrado=:grado and s.idSeccion.idSeccion=:seccion and al.alEstado not in (:estado) and s.mtEstado not in (:estado)"),
    @NamedQuery(name = "SieniMatricula.findAllNoInactivos", query = "SELECT s FROM SieniMatricula s where s.mtEstado not in (:estado)"),
    @NamedQuery(name = "SieniMatricula.findAllNoInactivosRpt", query = "SELECT s FROM SieniMatricula s where s.mtEstado not in (:estado) AND s.mtFechaIngreso BETWEEN :desde AND :hasta"),
    @NamedQuery(name = "SieniMatricula.findAll", query = "SELECT s FROM SieniMatricula s"),
    @NamedQuery(name = "SieniMatricula.findAlumNoInactivos", query = "SELECT al FROM SieniMatricula s,SieniAlumno al where s.idAlumno=al.idAlumno and s.mtEstado not in (:estado) AND s.mtFechaIngreso BETWEEN :desde AND :hasta"),
    @NamedQuery(name = "SieniMatricula.findMatriculasByAnioEstado", query = "SELECT s FROM SieniMatricula s where s.mtAnio=:anio and s.mtEstado=:estado"),
    @NamedQuery(name = "SieniMatricula.findByIdMatricula", query = "SELECT s FROM SieniMatricula s WHERE s.idMatricula = :idMatricula"),
    @NamedQuery(name = "SieniMatricula.findByIdAlumnoAnio", query = "SELECT s FROM SieniMatricula s,SieniAlumno al WHERE s.idAlumno=al.idAlumno and al.idAlumno = :idAlumno AND s.mtAnio=:mtAnio and al.alEstado not in ('I') and s.mtEstado not in ('I')"),
    @NamedQuery(name = "SieniMatricula.findUltimaMatriculaAlumno", query = "SELECT max(s.idMatricula) FROM SieniMatricula s,SieniAlumno al WHERE s.idAlumno=al.idAlumno and al.idAlumno = :idAlumno and al.alEstado='A'"),
    @NamedQuery(name = "SieniMatricula.findByMtFechaIngreso", query = "SELECT s FROM SieniMatricula s WHERE s.mtFechaIngreso = :mtFechaIngreso"),
    @NamedQuery(name = "SieniMatricula.findByMtEstado", query = "SELECT s FROM SieniMatricula s WHERE s.mtEstado = :mtEstado"),
    @NamedQuery(name = "SieniMatricula.findByMtAnio", query = "SELECT s FROM SieniMatricula s WHERE s.mtAnio = :mtAnio")})
public class SieniMatricula implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_sieni_matricula")
    @SequenceGenerator(name = "sec_sieni_matricula", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_matricula")
    @Basic(optional = false)
    @Column(name = "id_matricula")
    private Long idMatricula;
    @Column(name = "mt_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date mtFechaIngreso;
    @Column(name = "mt_estado")
    private Character mtEstado;
    @Column(name = "mt_anio")
    private String mtAnio;
//    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
//    @ManyToOne
//    private SieniAlumno idAlumno;
    @Column(name = "id_alumno")
    private Long idAlumno;
    @JoinColumn(name = "id_grado", referencedColumnName = "id_grado")
    @ManyToOne
    private SieniGrado idGrado;
    @JoinColumn(name = "id_seccion", referencedColumnName = "id_seccion")
    @ManyToOne
    private SieniSeccion idSeccion;
    @Transient
    private SieniAlumno alumno;

    @PrePersist
    protected void onCreate() {
        mtFechaIngreso = new Date();
    }

    public SieniMatricula() {
    }

    public SieniMatricula(Long idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Long getIdMatricula() {
        return idMatricula;
    }

    public void setIdMatricula(Long idMatricula) {
        this.idMatricula = idMatricula;
    }

    public Date getMtFechaIngreso() {
        return mtFechaIngreso;
    }

    public void setMtFechaIngreso(Date mtFechaIngreso) {
        this.mtFechaIngreso = mtFechaIngreso;
    }

    public Character getMtEstado() {
        return mtEstado;
    }

    public void setMtEstado(Character mtEstado) {
        this.mtEstado = mtEstado;
    }

    public String getMtAnio() {
        return mtAnio;
    }

    public void setMtAnio(String mtAnio) {
        this.mtAnio = mtAnio;
    }

//    public SieniAlumno getIdAlumno() {
//        return idAlumno;
//    }
    public SieniGrado getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(SieniGrado idGrado) {
        this.idGrado = idGrado;
    }

    public SieniSeccion getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(SieniSeccion idSeccion) {
        this.idSeccion = idSeccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMatricula != null ? idMatricula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniMatricula)) {
            return false;
        }
        SieniMatricula other = (SieniMatricula) object;
        if ((this.idMatricula == null && other.idMatricula != null) || (this.idMatricula != null && !this.idMatricula.equals(other.idMatricula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniMatricula[ idMatricula=" + idMatricula + " ]";
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
