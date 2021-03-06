/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_tema_duda")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniTemaDuda.findAll", query = "SELECT s FROM SieniTemaDuda s"),
    @NamedQuery(name = "SieniTemaDuda.findByIdTemaDuda", query = "SELECT s FROM SieniTemaDuda s WHERE s.idTemaDuda = :idTemaDuda"),
    @NamedQuery(name = "SieniTemaDuda.findByTdTipo", query = "SELECT s FROM SieniTemaDuda s WHERE s.tdTipo = :tdTipo"),
    @NamedQuery(name = "SieniTemaDuda.findConsultasActivas", query = "SELECT s FROM SieniTemaDuda s WHERE s.tdEstado = 'A'"),
    @NamedQuery(name = "SieniTemaDuda.findConsultasActivasByDocente", query = "SELECT s FROM SieniTemaDuda s WHERE s.tdEstado = 'A' AND ((s.idDocente = :idDocente OR s.idAlumno=:idDocente) and s.tdTipoUsr='D') or (s.idDocente = :idDocente and s.tdTipoUsr='A') order by s.idTemaDuda"),
    @NamedQuery(name = "SieniTemaDuda.findConsultasActivasByAlumno", query = "SELECT s FROM SieniTemaDuda s WHERE s.tdEstado = 'A' AND ((s.idAlumno = :idAlumno OR S.idDocente=:idAlumno) and s.tdTipoUsr='A') or (s.idAlumno = :idAlumno and s.tdTipoUsr='D') order by s.idTemaDuda")
})
public class SieniTemaDuda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_tema_duda")
    @SequenceGenerator(name = "sec_sieni_tema_duda", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_tema_duda")
    @Basic(optional = false)
    @Column(name = "id_tema_duda")
    private Long idTemaDuda;
    @Column(name = "td_fecha")
    @Temporal(TemporalType.DATE)
    private Date tdFecha;
    @Column(name = "td_tema")
    private String tdTema;
    @Column(name = "td_consulta")
    private String tdConsulta;
    @Column(name = "td_tipo_usr")
    private String tdTipoUsr;
    @Column(name = "td_tipo")
    private Character tdTipo;
    @Column(name = "td_estado")
    private Character tdEstado;

    @OneToMany(mappedBy = "idTemaDuda")
    private List<SieniResolDuda> sieniResolDudaList;
//    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
//    @ManyToOne
//    private SieniDocente idDocente;
    @Transient
    private SieniDocente docente;
    @Column(name = "id_docente")
    private Long idDocente;
//    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
//    @ManyToOne
    @Column(name = "id_alumno")
    private Long idAlumno;
    @Transient
    private SieniAlumno alumno;

    @Transient
    private String nombreAl;

    @Transient
    private String nombreDc;

    public SieniTemaDuda() {
    }

    public SieniTemaDuda(Long idTemaDuda) {
        this.idTemaDuda = idTemaDuda;
    }

    public Long getIdTemaDuda() {
        return idTemaDuda;
    }

    public void setIdTemaDuda(Long idTemaDuda) {
        this.idTemaDuda = idTemaDuda;
    }

    public Date getTdFecha() {
        return tdFecha;
    }

    public void setTdFecha(Date tdFecha) {
        this.tdFecha = tdFecha;
    }

    public String getTdTema() {
        return tdTema;
    }

    public void setTdTema(String tdTema) {
        this.tdTema = tdTema;
    }

    public String getTdConsulta() {
        return tdConsulta;
    }

    public void setTdConsulta(String tdConsulta) {
        this.tdConsulta = tdConsulta;
    }

    public Character getTdTipo() {
        return tdTipo;
    }

    public void setTdTipo(Character tdTipo) {
        this.tdTipo = tdTipo;
    }

    public Character getTdEstado() {
        return tdEstado;
    }

    public void setTdEstado(Character tdEstado) {
        this.tdEstado = tdEstado;
    }

    @XmlTransient
    public List<SieniResolDuda> getSieniResolDudaList() {
        return sieniResolDudaList;
    }

    public void setSieniResolDudaList(List<SieniResolDuda> sieniResolDudaList) {
        this.sieniResolDudaList = sieniResolDudaList;
    }

//    public SieniDocente getIdDocente() {
//        return idDocente;
//    }
//
//    public void setIdDocente(SieniDocente idDocente) {
//        this.idDocente = idDocente;
//    }
//    public SieniAlumno getIdAlumno() {
//        return idAlumno;
//    }
    public String getFechaFiltrable() {
        String fechaF = null;
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        if (this.tdFecha != null) {
            fechaF = dt1.format(tdFecha);
        }
        return fechaF;
    }

    public String getUserConsulta() {
        String userConsulta = "";
        if (this.idAlumno != null && this.alumno != null) {
            userConsulta = this.alumno.getAlUsuario();
        }
        if (this.idDocente != null && this.docente != null) {
            userConsulta = this.docente.getDcUsuario();
        }
        return userConsulta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTemaDuda != null ? idTemaDuda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniTemaDuda)) {
            return false;
        }
        SieniTemaDuda other = (SieniTemaDuda) object;
        if ((this.idTemaDuda == null && other.idTemaDuda != null) || (this.idTemaDuda != null && !this.idTemaDuda.equals(other.idTemaDuda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniTemaDuda[ idTemaDuda=" + idTemaDuda + " ]";
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

    public String getTdTipoUsr() {
        return tdTipoUsr;
    }

    public void setTdTipoUsr(String tdTipoUsr) {
        this.tdTipoUsr = tdTipoUsr;
    }

    public String getNombreAl() {
        return nombreAl;
    }

    public void setNombreAl(String nombreAl) {
        this.nombreAl = nombreAl;
    }

    public String getNombreDc() {
        return nombreDc;
    }

    public void setNombreDc(String nombreDc) {
        this.nombreDc = nombreDc;
    }

}
