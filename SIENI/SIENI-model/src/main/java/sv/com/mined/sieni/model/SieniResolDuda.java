/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Table(name = "sieni_resol_duda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniResolDuda.findAll", query = "SELECT s FROM SieniResolDuda s"),
    @NamedQuery(name = "SieniResolDuda.findByIdResolDuda", query = "SELECT s FROM SieniResolDuda s WHERE s.idResolDuda = :idResolDuda"),
    @NamedQuery(name = "SieniResolDuda.findByRdMensaje", query = "SELECT s FROM SieniResolDuda s WHERE s.rdMensaje = :rdMensaje"),
    @NamedQuery(name = "SieniResolDuda.findByConsulta", query = "SELECT s FROM SieniResolDuda s WHERE s.idTemaDuda.idTemaDuda = :idConsulta")
})
public class SieniResolDuda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_resol_duda")
    @SequenceGenerator(name = "sec_sieni_resol_duda", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_resol_duda")
    @Basic(optional = false)
    @Column(name = "id_resol_duda")
    private Long idResolDuda;
    @Column(name = "rd_fecha")
    @Temporal(TemporalType.DATE)
    private Date rdFecha;
    @Column(name = "rd_mensaje")
    private String rdMensaje;
    
    @Column(name = "id_docente")
    private Long idDocente;
    @Transient
    private SieniDocente docente;
//    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
//    @ManyToOne
    @Column(name = "id_alumno")
    private Long idAlumno;
    @Transient
    private SieniAlumno alumno;
    
    @JoinColumn(name = "id_tema_duda", referencedColumnName = "id_tema_duda")
    @ManyToOne
    private SieniTemaDuda idTemaDuda;

    public SieniResolDuda() {
    }

    public SieniResolDuda(Long idResolDuda) {
        this.idResolDuda = idResolDuda;
    }

    public Long getIdResolDuda() {
        return idResolDuda;
    }

    public void setIdResolDuda(Long idResolDuda) {
        this.idResolDuda = idResolDuda;
    }

    public Date getRdFecha() {
        return rdFecha;
    }

    public void setRdFecha(Date rdFecha) {
        this.rdFecha = rdFecha;
    }


    public String getRdMensaje() {
        return rdMensaje;
    }

    public void setRdMensaje(String rdMensaje) {
        this.rdMensaje = rdMensaje;
    }

    public SieniTemaDuda getIdTemaDuda() {
        return idTemaDuda;
    }

    public void setIdTemaDuda(SieniTemaDuda idTemaDuda) {
        this.idTemaDuda = idTemaDuda;
    }
    
    
    public String getFechaFiltrable() {
        String fechaF = null;
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        if (this.rdFecha != null) {
            fechaF = dt1.format(this.rdFecha);
        }
        return fechaF;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idResolDuda != null ? idResolDuda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniResolDuda)) {
            return false;
        }
        SieniResolDuda other = (SieniResolDuda) object;
        if ((this.idResolDuda == null && other.idResolDuda != null) || (this.idResolDuda != null && !this.idResolDuda.equals(other.idResolDuda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniResolDuda[ idResolDuda=" + idResolDuda + " ]";
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

    public SieniDocente getDocente() {
        return docente;
    }

    public void setDocente(SieniDocente docente) {
        this.docente = docente;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }
    
}
