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
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "sieni_docente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniDocente.findDocenteUsuario", query = "SELECT s FROM SieniDocente s  WHERE s.dcUsuario=:usuario AND s.dcContrasenia=:pass"),
    @NamedQuery(name = "SieniDocente.findDocentesSinUsuario", query = "SELECT s FROM SieniDocente s LEFT JOIN s.sieniDocentRolList sr WHERE sr.idDocenteRol IS NULL"),
    @NamedQuery(name = "SieniDocente.findAll", query = "SELECT s FROM SieniDocente s"),
    @NamedQuery(name = "SieniDocente.findByIdDocente", query = "SELECT s FROM SieniDocente s WHERE s.idDocente = :idDocente"),
    @NamedQuery(name = "SieniDocente.findByDcPrimNombre", query = "SELECT s FROM SieniDocente s WHERE s.dcPrimNombre = :dcPrimNombre"),
    @NamedQuery(name = "SieniDocente.findByDcSeguNombre", query = "SELECT s FROM SieniDocente s WHERE s.dcSeguNombre = :dcSeguNombre"),
    @NamedQuery(name = "SieniDocente.findByDcTercNombre", query = "SELECT s FROM SieniDocente s WHERE s.dcTercNombre = :dcTercNombre"),
    @NamedQuery(name = "SieniDocente.findByDcPrimApe", query = "SELECT s FROM SieniDocente s WHERE s.dcPrimApe = :dcPrimApe"),
    @NamedQuery(name = "SieniDocente.findByDcSeguApe", query = "SELECT s FROM SieniDocente s WHERE s.dcSeguApe = :dcSeguApe"),
    @NamedQuery(name = "SieniDocente.findByDcTercApe", query = "SELECT s FROM SieniDocente s WHERE s.dcTercApe = :dcTercApe"),
    @NamedQuery(name = "SieniDocente.findByDcTipo", query = "SELECT s FROM SieniDocente s WHERE s.dcTipo = :dcTipo"),
    @NamedQuery(name = "SieniDocente.findByDcUsuario", query = "SELECT s FROM SieniDocente s WHERE s.dcUsuario = :dcUsuario"),
    @NamedQuery(name = "SieniDocente.findByDcContrasenia", query = "SELECT s FROM SieniDocente s WHERE s.dcContrasenia = :dcContrasenia"),
    @NamedQuery(name = "SieniDocente.findByDcCorreo", query = "SELECT s FROM SieniDocente s WHERE s.dcCorreo = :dcCorreo"),
    @NamedQuery(name = "SieniDocente.findByDcDireccion", query = "SELECT s FROM SieniDocente s WHERE s.dcDireccion = :dcDireccion"),
    @NamedQuery(name = "SieniDocente.findByDcFechaNacimiento", query = "SELECT s FROM SieniDocente s WHERE s.dcFechaNacimiento = :dcFechaNacimiento"),
    @NamedQuery(name = "SieniDocente.findByDcTelefonoEm1", query = "SELECT s FROM SieniDocente s WHERE s.dcTelefonoEm1 = :dcTelefonoEm1"),
    @NamedQuery(name = "SieniDocente.findByDcTelefonoEm2", query = "SELECT s FROM SieniDocente s WHERE s.dcTelefonoEm2 = :dcTelefonoEm2"),
    @NamedQuery(name = "SieniDocente.findByDcTelefonoEm3", query = "SELECT s FROM SieniDocente s WHERE s.dcTelefonoEm3 = :dcTelefonoEm3"),
    @NamedQuery(name = "SieniDocente.findByDcEstado", query = "SELECT s FROM SieniDocente s WHERE s.dcEstado = :dcEstado")})
public class SieniDocente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_sieni_docente")
    @SequenceGenerator(name = "sec_sieni_docente", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_docente")
    @Basic(optional = false)
    @Column(name = "id_docente")
    private Long idDocente;
    @Column(name = "dc_prim_nombre")
    private String dcPrimNombre;
    @Column(name = "dc_segu_nombre")
    private String dcSeguNombre;
    @Column(name = "dc_terc_nombre")
    private String dcTercNombre;
    @Column(name = "dc_prim_ape")
    private String dcPrimApe;
    @Column(name = "dc_segu_ape")
    private String dcSeguApe;
    @Column(name = "dc_terc_ape")
    private String dcTercApe;
    @Column(name = "dc_tipo")
    private String dcTipo;
    @Column(name = "dc_usuario")
    private String dcUsuario;
    @Column(name = "dc_contrasenia")
    private String dcContrasenia;
    @Column(name = "dc_correo")
    private String dcCorreo;
    @Column(name = "dc_direccion")
    private String dcDireccion;
    @Lob
    @Column(name = "dc_foto")
    private byte[] dcFoto;
    @Column(name = "dc_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date dcFechaNacimiento;
    @Column(name = "dc_telefono_em_1")
    private String dcTelefonoEm1;
    @Column(name = "dc_telefono_em_2")
    private String dcTelefonoEm2;
    @Column(name = "dc_telefono_em_3")
    private String dcTelefonoEm3;
    @Column(name = "dc_fecha_baja")
    @Temporal(TemporalType.DATE)
    private Date dcFechaBaja;
    @Column(name = "dc_estado")
    private Character dcEstado;
    @JoinTable(name = "doc_recibe_noti", joinColumns = {
        @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")}, inverseJoinColumns = {
        @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion")})
    @ManyToMany
    private List<SieniNotificacion> sieniNotificacionList;
    @OneToMany(mappedBy = "idDocente", fetch = FetchType.EAGER)
    private List<SieniDocentRol> sieniDocentRolList;
    @OneToMany(mappedBy = "idDocente")
    private List<SieniDocentRDud> sieniDocentRDudList;
    @OneToMany(mappedBy = "idDocente")
    private List<SieniCurso> sieniCursoList;
    @OneToMany(mappedBy = "idDocente")
    private List<SieniTemaDuda> sieniTemaDudaList;
    @Transient
    private String nombreCompleto;
    @Transient
    private String fechaNacimientoFiltrable;

    public SieniDocente() {
    }

    public SieniDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public String getDcPrimNombre() {
        return dcPrimNombre;
    }

    public void setDcPrimNombre(String dcPrimNombre) {
        this.dcPrimNombre = dcPrimNombre;
    }

    public String getDcSeguNombre() {
        return dcSeguNombre;
    }

    public void setDcSeguNombre(String dcSeguNombre) {
        this.dcSeguNombre = dcSeguNombre;
    }

    public String getDcTercNombre() {
        return dcTercNombre;
    }

    public void setDcTercNombre(String dcTercNombre) {
        this.dcTercNombre = dcTercNombre;
    }

    public String getDcPrimApe() {
        return dcPrimApe;
    }

    public void setDcPrimApe(String dcPrimApe) {
        this.dcPrimApe = dcPrimApe;
    }

    public String getDcSeguApe() {
        return dcSeguApe;
    }

    public void setDcSeguApe(String dcSeguApe) {
        this.dcSeguApe = dcSeguApe;
    }

    public String getDcTercApe() {
        return dcTercApe;
    }

    public void setDcTercApe(String dcTercApe) {
        this.dcTercApe = dcTercApe;
    }

    public String getDcTipo() {
        return dcTipo;
    }

    public void setDcTipo(String dcTipo) {
        this.dcTipo = dcTipo;
    }

    public String getDcUsuario() {
        return dcUsuario;
    }

    public void setDcUsuario(String dcUsuario) {
        this.dcUsuario = dcUsuario;
    }

    public String getDcContrasenia() {
        return dcContrasenia;
    }

    public void setDcContrasenia(String dcContrasenia) {
        this.dcContrasenia = dcContrasenia;
    }

    public String getDcCorreo() {
        return dcCorreo;
    }

    public void setDcCorreo(String dcCorreo) {
        this.dcCorreo = dcCorreo;
    }

    public String getDcDireccion() {
        return dcDireccion;
    }

    public void setDcDireccion(String dcDireccion) {
        this.dcDireccion = dcDireccion;
    }

    public byte[] getDcFoto() {
        return dcFoto;
    }

    public void setDcFoto(byte[] dcFoto) {
        this.dcFoto = dcFoto;
    }

    public Date getDcFechaNacimiento() {
        return dcFechaNacimiento;
    }

    public void setDcFechaNacimiento(Date dcFechaNacimiento) {
        this.dcFechaNacimiento = dcFechaNacimiento;
    }

    public String getDcTelefonoEm1() {
        return dcTelefonoEm1;
    }

    public void setDcTelefonoEm1(String dcTelefonoEm1) {
        this.dcTelefonoEm1 = dcTelefonoEm1;
    }

    public String getDcTelefonoEm2() {
        return dcTelefonoEm2;
    }

    public void setDcTelefonoEm2(String dcTelefonoEm2) {
        this.dcTelefonoEm2 = dcTelefonoEm2;
    }

    public String getDcTelefonoEm3() {
        return dcTelefonoEm3;
    }

    public void setDcTelefonoEm3(String dcTelefonoEm3) {
        this.dcTelefonoEm3 = dcTelefonoEm3;
    }

    public Character getDcEstado() {
        return dcEstado;
    }

    public void setDcEstado(Character dcEstado) {
        this.dcEstado = dcEstado;
    }

    @XmlTransient
    public List<SieniNotificacion> getSieniNotificacionList() {
        return sieniNotificacionList;
    }

    public void setSieniNotificacionList(List<SieniNotificacion> sieniNotificacionList) {
        this.sieniNotificacionList = sieniNotificacionList;
    }

    @XmlTransient
    public List<SieniDocentRol> getSieniDocentRolList() {
        return sieniDocentRolList;
    }

    public void setSieniDocentRolList(List<SieniDocentRol> sieniDocentRolList) {
        this.sieniDocentRolList = sieniDocentRolList;
    }

    @XmlTransient
    public List<SieniDocentRDud> getSieniDocentRDudList() {
        return sieniDocentRDudList;
    }

    public void setSieniDocentRDudList(List<SieniDocentRDud> sieniDocentRDudList) {
        this.sieniDocentRDudList = sieniDocentRDudList;
    }

    @XmlTransient
    public List<SieniCurso> getSieniCursoList() {
        return sieniCursoList;
    }

    public void setSieniCursoList(List<SieniCurso> sieniCursoList) {
        this.sieniCursoList = sieniCursoList;
    }

    @XmlTransient
    public List<SieniTemaDuda> getSieniTemaDudaList() {
        return sieniTemaDudaList;
    }

    public void setSieniTemaDudaList(List<SieniTemaDuda> sieniTemaDudaList) {
        this.sieniTemaDudaList = sieniTemaDudaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocente != null ? idDocente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniDocente)) {
            return false;
        }
        SieniDocente other = (SieniDocente) object;
        if ((this.idDocente == null && other.idDocente != null) || (this.idDocente != null && !this.idDocente.equals(other.idDocente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniDocente[ idDocente=" + idDocente + " ]";
    }

    public String getNombreCompleto() {
        String nombre = this.dcPrimNombre + (this.dcSeguNombre != null ? " " + this.dcSeguNombre : "") + (this.dcTercNombre != null ? " " + this.dcTercNombre : "");
        String Apellido = " " + this.dcPrimApe + (this.dcSeguApe != null ? " " + this.dcSeguApe : "") + (this.dcTercApe != null ? " " + this.dcTercApe : "");
        nombreCompleto = nombre + Apellido;
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getAlFechaNacimiento() {
        return dcFechaNacimiento;
    }

    public void setAlFechaNacimiento(Date alFechaNacimiento) {
        this.dcFechaNacimiento = alFechaNacimiento;
    }

    public String getFechaNacimientoFiltrable() {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/mm/yyyy");
        if (dcFechaNacimiento != null) {
            fechaNacimientoFiltrable = dt1.format(dcFechaNacimiento);
        }
        return fechaNacimientoFiltrable;
    }

    public void setFechaNacimientoFiltrable(String fechaNacimientoFiltrable) {
        this.fechaNacimientoFiltrable = fechaNacimientoFiltrable;
    }

    public Date getDcFechaBaja() {
        return dcFechaBaja;
    }

    public void setDcFechaBaja(Date dcFechaBaja) {
        this.dcFechaBaja = dcFechaBaja;
    }
}
