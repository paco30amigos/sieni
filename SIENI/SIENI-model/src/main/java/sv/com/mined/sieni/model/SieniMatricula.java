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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_matricula", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_matricula"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniMatricula.findAll", query = "SELECT s FROM SieniMatricula s"),
    @NamedQuery(name = "SieniMatricula.findByIdMatricula", query = "SELECT s FROM SieniMatricula s WHERE s.idMatricula = :idMatricula"),
    @NamedQuery(name = "SieniMatricula.findByMtFechaIngreso", query = "SELECT s FROM SieniMatricula s WHERE s.mtFechaIngreso = :mtFechaIngreso"),
    @NamedQuery(name = "SieniMatricula.findByMtEstado", query = "SELECT s FROM SieniMatricula s WHERE s.mtEstado = :mtEstado")})
public class SieniMatricula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_matricula", nullable = false)
    private Long idMatricula;
    @Column(name = "mt_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date mtFechaIngreso;
    @Column(name = "mt_estado")
    private Character mtEstado;
    @JoinColumn(name = "id_seccion", referencedColumnName = "id_seccion")
    @ManyToOne
    private SieniSeccion idSeccion;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;

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

    public SieniSeccion getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(SieniSeccion idSeccion) {
        this.idSeccion = idSeccion;
    }

    public SieniAlumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(SieniAlumno idAlumno) {
        this.idAlumno = idAlumno;
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
    
}