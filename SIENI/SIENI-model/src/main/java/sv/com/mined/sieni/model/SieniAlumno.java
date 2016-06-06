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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_alumno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniAlumno.findAnio", query = "SELECT s FROM SieniAlumno s where s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and s.alEstado not in (:estado)"),
    @NamedQuery(name = "SieniAlumno.findByCarnet", query = "SELECT s FROM SieniAlumno s where s.alEstado='A' and s.alCarnet=:carnet"),
    @NamedQuery(name = "SieniAlumno.findAnioGrado", query = "SELECT s FROM SieniAlumno s  ,SieniMatricula mat join fetch mat.idGrado gr where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and gr.idGrado=:grado and s.alEstado not in (:estado) and mat.mtEstado not in (:estado) and gr.grEstado not in (:estado)"),
    @NamedQuery(name = "SieniAlumno.findAnioGradoSeccion", query = "SELECT s FROM SieniAlumno s  ,SieniMatricula mat join fetch mat.idGrado gr join fetch mat.idSeccion sec where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and gr.idGrado=:grado and sec.idSeccion=:seccion and s.alEstado not in (:estado) and mat.mtEstado not in (:estado) and gr.grEstado not in (:estado)"),
    @NamedQuery(name = "SieniAlumno.findAnioMatriculadoActual", query = "SELECT distinct s FROM SieniAlumno s ,SieniMatricula mat where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and s.alEstado not in (:estado) and mat.mtAnio=:anio and mat.mtEstado not in (:estado)"),
    @NamedQuery(name = "SieniAlumno.findAnioGradoMatriculadoActual", query = "SELECT s FROM SieniAlumno s  ,SieniMatricula mat join fetch mat.idGrado gr where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and gr.idGrado=:grado and s.alEstado not in (:estado) and mat.mtEstado not in (:estado) and gr.grEstado not in (:estado) and mat.mtAnio=:anio "),
    @NamedQuery(name = "SieniAlumno.findAnioGradoSeccionMatriculadoActual", query = "SELECT s FROM SieniAlumno s  ,SieniMatricula mat join fetch mat.idGrado gr join fetch mat.idSeccion sec where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and gr.idGrado=:grado and sec.idSeccion=:seccion and s.alEstado not in (:estado) and mat.mtEstado not in (:estado) and gr.grEstado not in (:estado) and mat.mtAnio=:anio"),
//    @NamedQuery(name = "SieniAlumno.findAnioNoMatriculadoActual", query = "SELECT s FROM SieniAlumno s ,SieniMatricula mat where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and s.alEstado not in (:estado) and (mat.mtAnio not in(:anio) or mat.idMatricula IS NULL)"),
    @NamedQuery(name = "SieniAlumno.findAnioNoMatriculadoActual", query = "SELECT s FROM SieniAlumno s where s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and s.alEstado not in (:estado) and s.idAlumno not in (select mat.idAlumno from SieniMatricula mat where mat.mtAnio in (:anio) and mat.mtEstado='A')"),
//    @NamedQuery(name = "SieniAlumno.findAnioGradoNoMatriculadoActual", query = "SELECT s FROM SieniAlumno s ,SieniMatricula mat join fetch mat.idGrado gr where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and s.alEstado not in (:estado) and mat.mtEstado not in (:estado) and gr.grEstado not in (:estado) and (mat.mtAnio not in(:anio) or mat.idMatricula IS NULL)"),
    @NamedQuery(name = "SieniAlumno.findAnioGradoNoMatriculadoActual", query = "SELECT s FROM SieniAlumno s ,SieniMatricula mat join fetch mat.idGrado gr where s.idAlumno=mat.idAlumno and s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and s.alEstado not in (:estado) and mat.mtEstado not in (:estado) and gr.grEstado not in (:estado) and (mat.mtAnio not in(:anio))"),
    @NamedQuery(name = "SieniAlumno.findAnioGradoSeccionNoMatriculadoActual", query = "SELECT s FROM SieniAlumno s  ,SieniMatricula mat join fetch mat.idGrado gr join fetch mat.idSeccion sec where s.idAlumno=mat.idAlumno and  s.alFechaIngreso>=:anioDesde and s.alFechaIngreso<=:anioHasta and s.alEstado not in (:estado) and mat.mtEstado not in (:estado) and gr.grEstado not in (:estado) and (mat.mtAnio not in(:anio) or mat.idMatricula IS NULL)"),
    @NamedQuery(name = "SieniAlumno.findByNombreCompleto", query = "SELECT s FROM SieniAlumno s where s.alNombreCompleto=:nombreCompleto"),
    @NamedQuery(name = "SieniAlumno.findSiguienteCorrelat", query = "SELECT max(s.alCorrelatCarnet) FROM SieniAlumno s where s.alCodigoCarnet=:codigo"),
    @NamedQuery(name = "SieniAlumno.findAlumnosActivos", query = "SELECT s FROM SieniAlumno s  WHERE s.alEstado='A' ORDER BY s.idAlumno"),
    @NamedQuery(name = "SieniAlumno.findAlumnosNoInactivos", query = "SELECT s FROM SieniAlumno s  WHERE s.alEstado not in (:estado)"),
    @NamedQuery(name = "SieniAlumno.findAlumnoUsuario", query = "SELECT s FROM SieniAlumno s  WHERE s.alUsuario=:usuario AND s.alContrasenia=:pass"),
//    @NamedQuery(name = "SieniAlumno.findAlumnosNoMatriculados", query = "SELECT s FROM SieniAlumno s LEFT JOIN s.sieniMatriculaList sr where sr.idMatricula IS NULL or sr.mtEstado=:estado and s.alEstado not in (:estado)"),
    @NamedQuery(name = "SieniAlumno.findAlumnosNoCursos", query = "SELECT s FROM SieniAlumno s ,SieniMatricula sr where s.idAlumno=sr.idAlumno and s.idAlumno not in(SELECT ca.idAlumno FROM SieniCursoAlumno ca WHERE ca.idCurso.idCurso=:idCurso and ca.craEstado ='A') AND sr.idGrado.idGrado=:idGrado AND s.alEstado not in (:alEstado) and sr.idSeccion.idSeccion=:idSeccion"),
    @NamedQuery(name = "SieniAlumno.findAlumnosInscritos", query = "SELECT s FROM SieniAlumno s, SieniCursoAlumno ca where s.idAlumno=ca.idAlumno and ca.idCurso.idCurso=:idCurso AND ca.craEstado not in(:alEstado) AND s.alEstado not in (:alEstado)"),
    @NamedQuery(name = "SieniAlumno.findAlumnosMatriculados", query = "SELECT s FROM SieniAlumno s ,SieniMatricula sr where s.idAlumno=sr.idAlumno and sr.mtEstado=:estado and s.alEstado in (:estado) and sr.mtAnio=:idAnio"),
//    @NamedQuery(name = "SieniAlumno.findAlumnosSinUsuario", query = "SELECT s FROM SieniAlumno s LEFT JOIN s.sieniAlumnRolList sr where sr.idAlumnRol IS NULL"),// or s.alEstado=3 eliminado
    @NamedQuery(name = "SieniAlumno.findAll", query = "SELECT s FROM SieniAlumno s"),
    @NamedQuery(name = "SieniAlumno.findAlumnosGradoSeccionAnio", query = "SELECT DISTINCT s FROM SieniAlumno s,SieniMatricula mat where mat.idAlumno=s.idAlumno and mat.idGrado.idGrado=:idGrado and mat.idSeccion.idSeccion=:idSeccion and mat.mtEstado not in (:estado) and s.alEstado not in (:estado)"),
//    @NamedQuery(name = "SieniAlumno.findByPuntosControl", query = "SELECT s FROM SieniAlumno s join fetch s.sieniPntosContrlList ctrl"),
    @NamedQuery(name = "SieniAlumno.findByIdAlumno", query = "SELECT s FROM SieniAlumno s WHERE s.idAlumno = :idAlumno"),
//    @NamedQuery(name = "SieniAlumno.findByAlPrimNombre", query = "SELECT s FROM SieniAlumno s WHERE s.alPrimNombre = :alPrimNombre"),
//    @NamedQuery(name = "SieniAlumno.findByAlSeguNombre", query = "SELECT s FROM SieniAlumno s WHERE s.alSeguNombre = :alSeguNombre"),
//    @NamedQuery(name = "SieniAlumno.findByAlTercNombre", query = "SELECT s FROM SieniAlumno s WHERE s.alTercNombre = :alTercNombre"),
//    @NamedQuery(name = "SieniAlumno.findByAlPrimApe", query = "SELECT s FROM SieniAlumno s WHERE s.alPrimApe = :alPrimApe"),
//    @NamedQuery(name = "SieniAlumno.findByAlSeguApe", query = "SELECT s FROM SieniAlumno s WHERE s.alSeguApe = :alSeguApe"),
//    @NamedQuery(name = "SieniAlumno.findByAlTercApe", query = "SELECT s FROM SieniAlumno s WHERE s.alTercApe = :alTercApe"),
//    @NamedQuery(name = "SieniAlumno.findByAlDireccion", query = "SELECT s FROM SieniAlumno s WHERE s.alDireccion = :alDireccion"),
//    @NamedQuery(name = "SieniAlumno.findByAlTelefonoEm1", query = "SELECT s FROM SieniAlumno s WHERE s.alTelefonoEm1 = :alTelefonoEm1"),
//    @NamedQuery(name = "SieniAlumno.findByAlTelefonoEm2", query = "SELECT s FROM SieniAlumno s WHERE s.alTelefonoEm2 = :alTelefonoEm2"),
//    @NamedQuery(name = "SieniAlumno.findByAlTelefonoEm3", query = "SELECT s FROM SieniAlumno s WHERE s.alTelefonoEm3 = :alTelefonoEm3"),
    @NamedQuery(name = "SieniAlumno.findByAlUsuario", query = "SELECT s FROM SieniAlumno s WHERE s.alUsuario = :alUsuario and s.alEstado='A'"),
//    @NamedQuery(name = "SieniAlumno.findByAlContrasenia", query = "SELECT s FROM SieniAlumno s WHERE s.alContrasenia = :alContrasenia"),
//    @NamedQuery(name = "SieniAlumno.findByAlCorreo", query = "SELECT s FROM SieniAlumno s WHERE s.alCorreo = :alCorreo"),
//    @NamedQuery(name = "SieniAlumno.findByAlFechaNacimiento", query = "SELECT s FROM SieniAlumno s WHERE s.alFechaNacimiento = :alFechaNacimiento"),
//    @NamedQuery(name = "SieniAlumno.findByAlEstado", query = "SELECT s FROM SieniAlumno s WHERE s.alEstado = :alEstado"),
    @NamedQuery(name = "SieniAlumno.findAlumnoById", query = "SELECT s FROM SieniAlumno s WHERE s.idAlumno = :id"),
    @NamedQuery(name = "SieniAlumno.findRptUsuariosAlumnos", query = "SELECT s FROM SieniAlumno s WHERE s.alUsuario IS NOT NULL AND s.alUsuario <> ''"),
    @NamedQuery(name = "SieniAlumno.findRptUsuariosAlumnosByEstado", query = "SELECT s FROM SieniAlumno s WHERE s.alUsuario IS NOT NULL AND s.alUsuario <> '' AND s.alEstado = :alEstado ")

})
public class SieniAlumno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_alumno")
    @SequenceGenerator(name = "sec_sieni_alumno", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_alumno")
    @Basic(optional = false)
    @Column(name = "id_alumno")
    private Long idAlumno;
    @Size(max = 50)
    @Column(name = "al_prim_nombre")
    private String alPrimNombre;
    @Size(max = 50)
    @Column(name = "al_segu_nombre")
    private String alSeguNombre;
    @Size(max = 50)
    @Column(name = "al_terc_nombre")
    private String alTercNombre;
    @Size(max = 50)
    @Column(name = "al_prim_ape")
    private String alPrimApe;
    @Size(max = 50)
    @Column(name = "al_segu_ape")
    private String alSeguApe;
    @Size(max = 50)
    @Column(name = "al_terc_ape")
    private String alTercApe;
    @Size(max = 200)
    @Column(name = "al_direccion")
    private String alDireccion;
    @Column(name = "al_telefono_em_1")
    private String alTelefonoEm1;
    @Column(name = "al_telefono_em_2")
    private String alTelefonoEm2;
    @Column(name = "al_telefono_em_3")
    private String alTelefonoEm3;
    @Size(max = 20)
    @Column(name = "al_usuario")
    private String alUsuario;
    @Size(max = 256)
    @Column(name = "al_contrasenia")
    private String alContrasenia;
    @Size(max = 50)
    @Column(name = "al_correo")
    private String alCorreo;
    @Column(name = "al_fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date alFechaNacimiento;
    @Column(name = "al_fecha_contrasenia")
    @Temporal(TemporalType.DATE)
    private Date alFechaContrasenia;
    @Column(name = "al_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date alFechaIngreso;
    @Size(max = 8)
    @Column(name = "al_carnet")
    private String alCarnet;
    @Column(name = "al_correlat_carnet")
    private Integer alCorrelatCarnet;
    @Size(max = 4)
    @Column(name = "al_codigo_carnet")
    private String alCodigoCarnet;
    @Column(name = "al_estado")
    private Character alEstado;
    @Column(name = "al_nombre_completo")
    private String alNombreCompleto;
    @Column(name = "al_fecha_baja")
    @Temporal(TemporalType.DATE)
    private Date alFechaBaja;
    @Column(name = "al_foto")
    private Long alFoto;
    /*@JoinTable(name = "alumno_recibe_noti", joinColumns = {
     @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")}, inverseJoinColumns = {
     @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion")})
     @ManyToMany
     private List<SieniNotificacion> sieniNotificacionList;
     */
//    @OneToMany(mappedBy = "idAlumno", fetch = FetchType.LAZY)
//    private List<SieniEvalRespAlumno> sieniEvalRespAlumnoList;
//    @OneToMany(mappedBy = "idAlumno", fetch = FetchType.LAZY)
//    private List<SieniCursoAlumno> sieniCursoAlumnoList;
//    @OneToMany(mappedBy = "idAlumno", fetch = FetchType.LAZY)
//    private List<SieniTemaDuda> sieniTemaDudaList;
//    @OneToMany(mappedBy = "idAlumno", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
//    private List<SieniAlumnRol> sieniAlumnRolList;
//    @OneToMany(mappedBy = "idAlumno", fetch = FetchType.LAZY)
//    private List<SieniPntosContrl> sieniPntosContrlList;
//    @OneToMany(mappedBy = "idAlumno", fetch = FetchType.LAZY)
//    private List<SieniMatricula> sieniMatriculaList;
//    @OneToMany(mappedBy = "idAlumno", fetch = FetchType.LAZY)
//    private List<SieniNota> sieniNotaList;
//    @OneToMany(mappedBy = "idAlumno", fetch = FetchType.LAZY)
//    private List<AlumnoRecibeNoti> notificacionesList;
    @Transient
    private String nombreCompleto;
    @Transient
    private String fechaNacimientoFiltrable;
    @Transient
    private SieniGrado gradoActual;

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

    public Long getAlFoto() {
        return alFoto;
    }

    public void setAlFoto(Long alFoto) {
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

//    @XmlTransient
//    public List<SieniTemaDuda> getSieniTemaDudaList() {
//        return sieniTemaDudaList;
//    }
//
//    public void setSieniTemaDudaList(List<SieniTemaDuda> sieniTemaDudaList) {
//        this.sieniTemaDudaList = sieniTemaDudaList;
//    }
//    @XmlTransient
//    public List<SieniAlumnRol> getSieniAlumnRolList() {
//        return sieniAlumnRolList;
//    }
//
//    public void setSieniAlumnRolList(List<SieniAlumnRol> sieniAlumnRolList) {
//        this.sieniAlumnRolList = sieniAlumnRolList;
//    }
//    @XmlTransient
//    public List<SieniPntosContrl> getSieniPntosContrlList() {
//        return sieniPntosContrlList;
//    }
//
//    public void setSieniPntosContrlList(List<SieniPntosContrl> sieniPntosContrlList) {
//        this.sieniPntosContrlList = sieniPntosContrlList;
//    }
//    @XmlTransient
//    public List<SieniMatricula> getSieniMatriculaList() {
//        return sieniMatriculaList;
//    }
//
//    public void setSieniMatriculaList(List<SieniMatricula> sieniMatriculaList) {
//        this.sieniMatriculaList = sieniMatriculaList;
//    }
//
//    @XmlTransient
//    public List<SieniNota> getSieniNotaList() {
//        return sieniNotaList;
//    }
//    public void setSieniNotaList(List<SieniNota> sieniNotaList) {
//        this.sieniNotaList = sieniNotaList;
//    }
//    @XmlTransient
//    public List<AlumnoRecibeNoti> getNotificacionesList() {
//        return notificacionesList;
//    }
//
//    public void setNotificacionesList(List<AlumnoRecibeNoti> notificacionesList) {
//        this.notificacionesList = notificacionesList;
//    }
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
        String nombre = this.alPrimNombre.trim() + (this.alSeguNombre != null && !this.alSeguNombre.isEmpty() ? " " + this.alSeguNombre.trim() : "") + (this.alTercNombre != null && !this.alTercNombre.isEmpty() ? " " + this.alTercNombre.trim() : "");
        String Apellido = " " + this.alPrimApe.trim() + (this.alSeguApe != null && !this.alSeguApe.isEmpty() ? " " + this.alSeguApe.trim() : "") + (this.alTercApe != null && !this.alTercApe.isEmpty() ? " " + this.alTercApe.trim() : "");
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

    public SieniMatricula getMatriculaActual() {
        SieniMatricula ret = null;
        int max = 0, anioActual = 0;
//        if (this.getSieniMatriculaList() != null) {
//            for (SieniMatricula actual : this.getSieniMatriculaList()) {
//                anioActual = Integer.parseInt(actual.getMtAnio());
//                if (max < anioActual) {
//                    max = anioActual;
//                    ret = actual;
//                }
//            }
//        }
        return ret;
    }

    public SieniGrado getGradoActual() {
        int max = 0, anioActual = 0;
//        for (SieniMatricula actual : this.getSieniMatriculaList()) {
//            anioActual = Integer.parseInt(actual.getMtAnio());
//            if (max < anioActual) {
//                max = anioActual;
//                gradoActual = actual.getIdGrado();
//            }
//        }
        return gradoActual;
    }

    public void setGradoActual(SieniGrado gradoActual) {
        this.gradoActual = gradoActual;
    }

    public Date getAlFechaBaja() {
        return alFechaBaja;
    }

    public void setAlFechaBaja(Date alFechaBaja) {
        this.alFechaBaja = alFechaBaja;
    }

    public Date getAlFechaIngreso() {
        return alFechaIngreso;
    }

    public void setAlFechaIngreso(Date alFechaIngreso) {
        this.alFechaIngreso = alFechaIngreso;
    }

    public String getAlCarnet() {
        return alCarnet;
    }

    public void setAlCarnet(String alCarnet) {
        this.alCarnet = alCarnet;
    }

    public Integer getAlCorrelatCarnet() {
        return alCorrelatCarnet;
    }

    public void setAlCorrelatCarnet(Integer alCorrelatCarnet) {
        this.alCorrelatCarnet = alCorrelatCarnet;
    }

    public String getAlCodigoCarnet() {
        return alCodigoCarnet;
    }

    public void setAlCodigoCarnet(String alCodigoCarnet) {
        this.alCodigoCarnet = alCodigoCarnet;
    }

    public String getAlNombreCompleto() {
        return alNombreCompleto;
    }

    public void setAlNombreCompleto(String alNombreCompleto) {
        this.alNombreCompleto = alNombreCompleto;
    }

    @XmlTransient
    public List<SieniCursoAlumno> getSieniCursoAlumnoList() {
        return null;
    }

    public void setSieniCursoAlumnoList(List<SieniCursoAlumno> sieniCursoAlumnoList) {
//        this.sieniCursoAlumnoList = sieniCursoAlumnoList;
    }

//    @XmlTransient
//    public List<SieniEvalRespAlumno> getSieniEvalRespAlumnoList() {
//        return sieniEvalRespAlumnoList;
//    }
//
//    public void setSieniEvalRespAlumnoList(List<SieniEvalRespAlumno> sieniEvalRespAlumnoList) {
//        this.sieniEvalRespAlumnoList = sieniEvalRespAlumnoList;
//    }
    public Date getAlFechaContrasenia() {
        return alFechaContrasenia;
    }

    public void setAlFechaContrasenia(Date alFechaContrasenia) {
        this.alFechaContrasenia = alFechaContrasenia;
    }
}
