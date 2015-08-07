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
    @NamedQuery(name = "SieniDocente.findByDfCorreo", query = "SELECT s FROM SieniDocente s WHERE s.dfCorreo = :dfCorreo")})
public class SieniDocente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
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
    @Column(name = "df_correo")
    private String dfCorreo;
    @JoinTable(name = "doc_recibe_noti", joinColumns = {
        @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")}, inverseJoinColumns = {
        @JoinColumn(name = "id_notificacion", referencedColumnName = "id_notificacion")})
    @ManyToMany
    private List<SieniNotificacion> sieniNotificacionList;
    @OneToMany(mappedBy = "idDocente")
    private List<SieniDocentRol> sieniDocentRolList;
    @OneToMany(mappedBy = "idDocente")
    private List<SieniDocentRDud> sieniDocentRDudList;
    @OneToMany(mappedBy = "idDocente")
    private List<SieniCurso> sieniCursoList;
    @OneToMany(mappedBy = "idDocente")
    private List<SieniTemaDuda> sieniTemaDudaList;

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

    public String getDfCorreo() {
        return dfCorreo;
    }

    public void setDfCorreo(String dfCorreo) {
        this.dfCorreo = dfCorreo;
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
    
}
