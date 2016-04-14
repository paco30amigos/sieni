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
@Table(name = "sieni_docent_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniDocentRol.findAllNoInactivos", query = "SELECT s FROM SieniDocentRol s where s.sdrEstado not in (:estado) order by s.idDocenteRol"),
    @NamedQuery(name = "SieniDocentRol.findAdmins", query = "SELECT s FROM SieniDocentRol s where s.fRolDoc=:rol and s.sdrEstado=:estado"),
    @NamedQuery(name = "SieniDocentRol.findAll", query = "SELECT s FROM SieniDocentRol s"),
    @NamedQuery(name = "SieniDocentRol.findRoles", query = "SELECT s FROM SieniDocentRol s where s.idDocente=:idDocente and s.sdrEstado not in (:estado)"),
    @NamedQuery(name = "SieniDocentRol.findByIdDocenteRol", query = "SELECT s FROM SieniDocentRol s WHERE s.idDocenteRol = :idDocenteRol"),
    @NamedQuery(name = "SieniDocentRol.findByFRolDoc", query = "SELECT s FROM SieniDocentRol s WHERE s.fRolDoc = :fRolDoc"),
    @NamedQuery(name = "SieniDocentRol.findByEstado", query = "SELECT s FROM SieniDocentRol s WHERE s.sdrEstado = :sdrEstado"),
    @NamedQuery(name = "SieniDocentRol.findByFIngreso", query = "SELECT s FROM SieniDocentRol s, SieniDocente d WHERE s.idDocente = :idDocente AND d.idDocente=s.idDocente")})
public class SieniDocentRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_docent_rol")
    @SequenceGenerator(name = "sec_sieni_docent_rol", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_docent_rol")
    @Column(name = "id_docente_rol")
    private Long idDocenteRol;
    @Basic(optional = false)
    @Column(name = "f_rol_doc")
    private long fRolDoc;
    @Column(name = "sdr_estado")
    private Character sdrEstado;
    @Column(name = "sdr_fecha_ingreso", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sdrFechaIngreso;

    @PrePersist
    protected void onCreate() {
        sdrFechaIngreso = new Date();
    }
//    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
//    @ManyToOne
//    private SieniDocente idDocente;
    @Column(name = "id_docente")
    private Long idDocente;
    @Transient
    private SieniDocente docente;

    public SieniDocentRol() {
    }

    public SieniDocentRol(Long idDocenteRol) {
        this.idDocenteRol = idDocenteRol;
    }

    public SieniDocentRol(Long idDocenteRol, long fRolDoc) {
        this.idDocenteRol = idDocenteRol;
        this.fRolDoc = fRolDoc;
    }

    public Long getIdDocenteRol() {
        return idDocenteRol;
    }

    public void setIdDocenteRol(Long idDocenteRol) {
        this.idDocenteRol = idDocenteRol;
    }

    public long getFRolDoc() {
        return fRolDoc;
    }

    public void setFRolDoc(long fRolDoc) {
        this.fRolDoc = fRolDoc;
    }

//    public SieniDocente getIdDocente() {
//        return idDocente;
//    }
//
//    public void setIdDocente(SieniDocente idDocente) {
//        this.idDocente = idDocente;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocenteRol != null ? idDocenteRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniDocentRol)) {
            return false;
        }
        SieniDocentRol other = (SieniDocentRol) object;
        if ((this.idDocenteRol == null && other.idDocenteRol != null) || (this.idDocenteRol != null && !this.idDocenteRol.equals(other.idDocenteRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniDocentRol[ idDocenteRol=" + idDocenteRol + " ]";
    }

    public Character getSdrEstado() {
        return sdrEstado;
    }

    public void setSdrEstado(Character sdrEstado) {
        this.sdrEstado = sdrEstado;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public SieniDocente getDocente() {
        return docente;
    }

    public void setDocente(SieniDocente docente) {
        this.docente = docente;
    }

    public Date getSdrFechaIngreso() {
        return sdrFechaIngreso;
    }

    public void setSdrFechaIngreso(Date sdrFechaIngreso) {
        this.sdrFechaIngreso = sdrFechaIngreso;
    }
    
}
