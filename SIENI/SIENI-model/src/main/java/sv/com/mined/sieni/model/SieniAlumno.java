/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAlumno.findAlumnosActivos", query = "SELECT s FROM SieniAlumno s  WHERE s.alEstado='A'"),
    @NamedQuery(name = "SieniAlumno.findAlumnoUsuario", query = "SELECT s FROM SieniAlumno s  WHERE s.alUsuario=:usuario AND s.alContrasenia=:pass"),
    @NamedQuery(name = "SieniAlumno.findAlumnosNoMatriculados", query = "SELECT s FROM SieniAlumno s LEFT JOIN s.sieniMatriculaList sr where sr.idMatricula IS NULL  "),
    @NamedQuery(name = "SieniAlumno.findAlumnosSinUsuario", query = "SELECT s FROM SieniAlumno s LEFT JOIN s.sieniAlumnRolList sr where sr.idAlumnRol IS NULL"),// or s.alEstado=3 eliminado
    @NamedQuery(name = "SieniAlumno.findAll", query = "SELECT s FROM SieniAlumno s"),
    @NamedQuery(name = "SieniAlumno.findByIdAlumno", query = "SELECT s FROM SieniAlumno s WHERE s.idAlumno = :idAlumno"),
    @NamedQuery(name = "SieniAlumno.findByAlPrimNombre", query = "SELECT s FROM SieniAlumno s WHERE s.alPrimNombre = :alPrimNombre"),
    @NamedQuery(name = "SieniAlumno.findByAlSeguNombre", query = "SELECT s FROM SieniAlumno s WHERE s.alSeguNombre = :alSeguNombre"),
    @NamedQuery(name = "SieniAlumno.findByAlTercNombre", query = "SELECT s FROM SieniAlumno s WHERE s.alTercNombre = :alTercNombre"),
    @NamedQuery(name = "SieniAlumno.findByAlPrimApe", query = "SELECT s FROM SieniAlumno s WHERE s.alPrimApe = :alPrimApe"),
    @NamedQuery(name = "SieniAlumno.findByAlSeguApe", query = "SELECT s FROM SieniAlumno s WHERE s.alSeguApe = :alSeguApe"),
    @NamedQuery(name = "SieniAlumno.findByAlTercApe", query = "SELECT s FROM SieniAlumno s WHERE s.alTercApe = :alTercApe"),
    @NamedQuery(name = "SieniAlumno.findByAlDireccion", query = "SELECT s FROM SieniAlumno s WHERE s.alDireccion = :alDireccion"),
    @NamedQuery(name = "SieniAlumno.findByAlTelefonoEm1", query = "SELECT s FROM SieniAlumno s WHERE s.alTelefonoEm1 = :alTelefonoEm1"),
    @NamedQuery(name = "SieniAlumno.findByAlTelefonoEm2", query = "SELECT s FROM SieniAlumno s WHERE s.alTelefonoEm2 = :alTelefonoEm2"),
    @NamedQuery(name = "SieniAlumno.findByAlTelefonoEm3", query = "SELECT s FROM SieniAlumno s WHERE s.alTelefonoEm3 = :alTelefonoEm3"),
    @NamedQuery(name = "SieniAlumno.findByAlUsuario", query = "SELECT s FROM SieniAlumno s WHERE s.alUsuario = :alUsuario"),
    @NamedQuery(name = "SieniAlumno.findByAlContrasenia", query = "SELECT s FROM SieniAlumno s WHERE s.alContrasenia = :alContrasenia"),
    @NamedQuery(name = "SieniAlumno.findByAlCorreo", query = "SELECT s FROM SieniAlumno s WHERE s.alCorreo = :alCorreo"),
    @NamedQuery(name = "SieniAlumno.findByAlFechaNacimiento", query = "SELECT s FROM SieniAlumno s WHERE s.alFechaNacimiento = :alFechaNacimiento"),
    @NamedQuery(name = "SieniAlumno.findByAlEstado", query = "SELECT s FROM SieniAlumno s WHERE s.alEstado = :alEstado")})
public class SieniAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_alumno")
    @SequenceGenerator(name = "sec_sieni_alumno", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_alumno")
    @Basic(optional = false)
    @Column(name = "id_alumno")
    private Long idAlumno;
    @Column(name = "al_prim_nombre")
    private String alPrimNombre;
    @Column(name = "al_segu_nombre")
    private String alSeguNombre;
    @Column(name = "al_terc_nombre")
    private String alTercNombre;
    @Column(name = "al_prim_ape")
    private String alPrimApe;
    @Column(name = "al_segu_ape")
    private String alSeguApe;
    @Column(name = "al_terc_ape")
    private String alTercApe;
    @Column(name = "al_direccion")
    private String alDireccion;
    @Column(name = "al_telefono_em_1")
    private String alTelefonoEm1;
    @Column(name = "al_telefono_em_2")
    private String alTelefonoEm2;
    @Column(name = "al_telefono_em_3")
    private String alTelefonoEm3;
    @Column(name = "al_usuario")
    private String alUsuario;
    @Column(name = "al_contrasenia")
    private String alContrasenia;
    @Column(name = "al_correo")
    private String alCorreo;
    @Lob
    @Column(name = "al_foto")
    private byte[] alFoto;
    @Column(name = "al_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date alFechaNacimiento;
    @Column(name = "al_estado")
    private Character alEstado;
    @JoinTable(name = "tema_duda", joinColumns = {
        @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")}, inverseJoinColumns = {
        @JoinColumn(name = "id_tema_duda", referencedColumnName = "id_tema_duda")})
    @ManyToMany
    private List<SieniTemaDuda> sieniTemaDudaList;
    @JoinTable(name = "alumno_recibe_noti", joinColumns = {
        @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")}, inverseJoinColumns = {
        @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion")})
    @ManyToMany
    private List<SieniNotificacion> sieniNotificacionList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniAlumnRDud> sieniAlumnRDudList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniAlumnRol> sieniAlumnRolList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniPntosContrl> sieniPntosContrlList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniMatricula> sieniMatriculaList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniNota> sieniNotaList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniCurso> sieniCursoList;
    @Transient
    private String nombreCompleto;
    @Transient
    private String fechaNacimientoFiltrable;
    @Transient
    private StreamedContent fotoContenido;

    public SieniAlumno() {
    }

    public SieniAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Long getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(Long idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getAlPrimNombre() {
        return alPrimNombre;
    }

    public void setAlPrimNombre(String alPrimNombre) {
        this.alPrimNombre = alPrimNombre;
    }

    public String getAlSeguNombre() {
        return alSeguNombre;
    }

    public void setAlSeguNombre(String alSeguNombre) {
        this.alSeguNombre = alSeguNombre;
    }

    public String getAlTercNombre() {
        return alTercNombre;
    }

    public void setAlTercNombre(String alTercNombre) {
        this.alTercNombre = alTercNombre;
    }

    public String getAlPrimApe() {
        return alPrimApe;
    }

    public void setAlPrimApe(String alPrimApe) {
        this.alPrimApe = alPrimApe;
    }

    public String getAlSeguApe() {
        return alSeguApe;
    }

    public void setAlSeguApe(String alSeguApe) {
        this.alSeguApe = alSeguApe;
    }

    public String getAlTercApe() {
        return alTercApe;
    }

    public void setAlTercApe(String alTercApe) {
        this.alTercApe = alTercApe;
    }

    public String getAlDireccion() {
        return alDireccion;
    }

    public void setAlDireccion(String alDireccion) {
        this.alDireccion = alDireccion;
    }

    public String getAlTelefonoEm1() {
        return alTelefonoEm1;
    }

    public void setAlTelefonoEm1(String alTelefonoEm1) {
        this.alTelefonoEm1 = alTelefonoEm1;
    }

    public String getAlTelefonoEm2() {
        return alTelefonoEm2;
    }

    public void setAlTelefonoEm2(String alTelefonoEm2) {
        this.alTelefonoEm2 = alTelefonoEm2;
    }

    public String getAlTelefonoEm3() {
        return alTelefonoEm3;
    }

    public void setAlTelefonoEm3(String alTelefonoEm3) {
        this.alTelefonoEm3 = alTelefonoEm3;
    }

    public String getAlUsuario() {
        return alUsuario;
    }

    public void setAlUsuario(String alUsuario) {
        this.alUsuario = alUsuario;
    }

    public String getAlContrasenia() {
        return alContrasenia;
    }

    public void setAlContrasenia(String alContrasenia) {
        this.alContrasenia = alContrasenia;
    }

    public String getAlCorreo() {
        return alCorreo;
    }

    public void setAlCorreo(String alCorreo) {
        this.alCorreo = alCorreo;
    }

    public byte[] getAlFoto() {
        return alFoto;
    }

    public void setAlFoto(byte[] alFoto) {
        this.alFoto = alFoto;
    }

    public Date getAlFechaNacimiento() {
        return alFechaNacimiento;
    }

    public void setAlFechaNacimiento(Date alFechaNacimiento) {
        this.alFechaNacimiento = alFechaNacimiento;
    }

    public Character getAlEstado() {
        return alEstado;
    }

    public void setAlEstado(Character alEstado) {
        this.alEstado = alEstado;
    }

    @XmlTransient
    public List<SieniTemaDuda> getSieniTemaDudaList() {
        return sieniTemaDudaList;
    }

    public void setSieniTemaDudaList(List<SieniTemaDuda> sieniTemaDudaList) {
        this.sieniTemaDudaList = sieniTemaDudaList;
    }

    @XmlTransient
    public List<SieniNotificacion> getSieniNotificacionList() {
        return sieniNotificacionList;
    }

    public void setSieniNotificacionList(List<SieniNotificacion> sieniNotificacionList) {
        this.sieniNotificacionList = sieniNotificacionList;
    }

    @XmlTransient
    public List<SieniAlumnRDud> getSieniAlumnRDudList() {
        return sieniAlumnRDudList;
    }

    public void setSieniAlumnRDudList(List<SieniAlumnRDud> sieniAlumnRDudList) {
        this.sieniAlumnRDudList = sieniAlumnRDudList;
    }

    @XmlTransient
    public List<SieniAlumnRol> getSieniAlumnRolList() {
        return sieniAlumnRolList;
    }

    public void setSieniAlumnRolList(List<SieniAlumnRol> sieniAlumnRolList) {
        this.sieniAlumnRolList = sieniAlumnRolList;
    }

    @XmlTransient
    public List<SieniPntosContrl> getSieniPntosContrlList() {
        return sieniPntosContrlList;
    }

    public void setSieniPntosContrlList(List<SieniPntosContrl> sieniPntosContrlList) {
        this.sieniPntosContrlList = sieniPntosContrlList;
    }

    @XmlTransient
    public List<SieniMatricula> getSieniMatriculaList() {
        return sieniMatriculaList;
    }

    public void setSieniMatriculaList(List<SieniMatricula> sieniMatriculaList) {
        this.sieniMatriculaList = sieniMatriculaList;
    }

    @XmlTransient
    public List<SieniNota> getSieniNotaList() {
        return sieniNotaList;
    }

    public void setSieniNotaList(List<SieniNota> sieniNotaList) {
        this.sieniNotaList = sieniNotaList;
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
        hash += (idAlumno != null ? idAlumno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniAlumno)) {
            return false;
        }
        SieniAlumno other = (SieniAlumno) object;
        if ((this.idAlumno == null && other.idAlumno != null) || (this.idAlumno != null && !this.idAlumno.equals(other.idAlumno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniAlumno[ idAlumno=" + idAlumno + " ]";
    }

    public String getNombreCompleto() {
        String nombre = this.alPrimNombre + (this.alSeguNombre != null ? " " + this.alSeguNombre : "") + (this.alTercNombre != null ? " " + this.alTercNombre : "");
        String Apellido = " " + this.alPrimApe + (this.alSeguApe != null ? " " + this.alSeguApe : "") + (this.alTercApe != null ? " " + this.alTercApe : "");
        nombreCompleto = nombre + Apellido;
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getFechaNacimientoFiltrable() {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
        if (alFechaNacimiento != null) {
            fechaNacimientoFiltrable = dt1.format(alFechaNacimiento);
        }
        return fechaNacimientoFiltrable;
    }

    public void setFechaNacimientoFiltrable(String fechaNacimientoFiltrable) {
        this.fechaNacimientoFiltrable = fechaNacimientoFiltrable;
    }

    public StreamedContent getFotoContenido() {
        fotoContenido = null;
        if (alFoto != null) {
            InputStream input = new ByteArrayInputStream(alFoto);
            fotoContenido = new DefaultStreamedContent(input, "image/jpg");
        }
        return fotoContenido;
    }
}
