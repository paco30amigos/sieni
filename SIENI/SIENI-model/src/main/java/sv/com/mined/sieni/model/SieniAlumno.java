/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_alumno", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_alumno"})})
@XmlRootElement
@NamedQueries({
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
    @NamedQuery(name = "SieniAlumno.findByAlCorreo", query = "SELECT s FROM SieniAlumno s WHERE s.alCorreo = :alCorreo")})
public class SieniAlumno implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_alumno", nullable = false)
    private Long idAlumno;
    @Column(name = "al_prim_nombre", length = 50)
    private String alPrimNombre;
    @Column(name = "al_segu_nombre", length = 50)
    private String alSeguNombre;
    @Column(name = "al_terc_nombre", length = 50)
    private String alTercNombre;
    @Column(name = "al_prim_ape", length = 50)
    private String alPrimApe;
    @Column(name = "al_segu_ape", length = 50)
    private String alSeguApe;
    @Column(name = "al_terc_ape", length = 50)
    private String alTercApe;
    @Column(name = "al_direccion", length = 200)
    private String alDireccion;
    @Column(name = "al_telefono_em_1", length = 8)
    private String alTelefonoEm1;
    @Column(name = "al_telefono_em_2", length = 8)
    private String alTelefonoEm2;
    @Column(name = "al_telefono_em_3", length = 8)
    private String alTelefonoEm3;
    @Column(name = "al_usuario", length = 20)
    private String alUsuario;
    @Column(name = "al_contrasenia", length = 256)
    private String alContrasenia;
    @Column(name = "al_correo", length = 50)
    private String alCorreo;
    @JoinTable(name = "tema_duda", joinColumns = {
        @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "id_tema_duda", referencedColumnName = "id_tema_duda", nullable = false)})
    @ManyToMany
    private List<SieniTemaDuda> sieniTemaDudaList;
    @JoinTable(name = "alumno_recibe_noti", joinColumns = {
        @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion", nullable = false)})
    @ManyToMany
    private List<SieniNotificacion> sieniNotificacionList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniMatricula> sieniMatriculaList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniAlumnRDud> sieniAlumnRDudList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniNota> sieniNotaList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniPntosContrl> sieniPntosContrlList;
    @OneToMany(mappedBy = "idAlumno")
    private List<SieniAlumnRol> sieniAlumnRolList;

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
    public List<SieniMatricula> getSieniMatriculaList() {
        return sieniMatriculaList;
    }

    public void setSieniMatriculaList(List<SieniMatricula> sieniMatriculaList) {
        this.sieniMatriculaList = sieniMatriculaList;
    }

    @XmlTransient
    public List<SieniAlumnRDud> getSieniAlumnRDudList() {
        return sieniAlumnRDudList;
    }

    public void setSieniAlumnRDudList(List<SieniAlumnRDud> sieniAlumnRDudList) {
        this.sieniAlumnRDudList = sieniAlumnRDudList;
    }

    @XmlTransient
    public List<SieniNota> getSieniNotaList() {
        return sieniNotaList;
    }

    public void setSieniNotaList(List<SieniNota> sieniNotaList) {
        this.sieniNotaList = sieniNotaList;
    }

    @XmlTransient
    public List<SieniPntosContrl> getSieniPntosContrlList() {
        return sieniPntosContrlList;
    }

    public void setSieniPntosContrlList(List<SieniPntosContrl> sieniPntosContrlList) {
        this.sieniPntosContrlList = sieniPntosContrlList;
    }

    @XmlTransient
    public List<SieniAlumnRol> getSieniAlumnRolList() {
        return sieniAlumnRolList;
    }

    public void setSieniAlumnRolList(List<SieniAlumnRol> sieniAlumnRolList) {
        this.sieniAlumnRolList = sieniAlumnRolList;
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
    
}
