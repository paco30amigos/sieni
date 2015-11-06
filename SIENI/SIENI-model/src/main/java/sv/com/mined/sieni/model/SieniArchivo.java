/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import static javax.persistence.FetchType.LAZY;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_archivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniArchivo.findAllNoInactivos", query = "SELECT s FROM SieniArchivo s where s.arEstado NOT IN (:estado) and s.arTipo in ('A','V','I') ORDER BY s.idArchivo"),
    @NamedQuery(name = "SieniArchivo.findByIdSuperComp", query = "SELECT s FROM SieniArchivo s,SieniComponente c where s.idArchivo=c.idArchivo and c.idSuperCompon.idSuperCompon=:idSuperCompon and s.arEstado NOT IN (:estado) ORDER BY C.cpOrden"),
    @NamedQuery(name = "SieniArchivo.findArchivoLazy", query = "SELECT s.arArchivo FROM SieniArchivo s where s.idArchivo=:idArchivo and s.arEstado NOT IN (:estado)"),
    @NamedQuery(name = "SieniArchivo.findByNombre", query = "SELECT s FROM SieniArchivo s where s.arNombre=:nombre"),
    @NamedQuery(name = "SieniArchivo.findAll", query = "SELECT s FROM SieniArchivo s"),
    @NamedQuery(name = "SieniArchivo.findByIdArchivo", query = "SELECT s FROM SieniArchivo s WHERE s.idArchivo = :idArchivo"),
    @NamedQuery(name = "SieniArchivo.findByArRuta", query = "SELECT s FROM SieniArchivo s WHERE s.arRuta = :arRuta"),
    @NamedQuery(name = "SieniArchivo.findByArTipoActivo", query = "SELECT s FROM SieniArchivo s WHERE s.arTipo = :arTipo and s.arEstado='A'"),
    @NamedQuery(name = "SieniArchivo.findByArNombre", query = "SELECT s FROM SieniArchivo s WHERE s.arNombre = :arNombre"),
    @NamedQuery(name = "SieniArchivo.findByArEstado", query = "SELECT s FROM SieniArchivo s WHERE s.arEstado = :arEstado")})
public class SieniArchivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_alumno")
    @SequenceGenerator(name = "sec_sieni_archivo", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_archivo")
    @Basic(optional = false)
    @Column(name = "id_archivo")
    private Long idArchivo;
    @Column(name = "ar_ruta")
    private String arRuta;
    @Column(name = "ar_tipo")
    private Character arTipo;
    @Basic(fetch = LAZY)
    @Lob
    @Column(name = "ar_archivo")
    private byte[] arArchivo;
    @Column(name = "ar_nombre")
    private String arNombre;
    @Column(name = "ar_estado")
    private String arEstado;
    @JoinColumn(name = "id_componente", referencedColumnName = "id_componente")
    @ManyToOne
    private SieniComponente idComponente;
    @Transient
    private String estado;
    @Transient
    private String tipoArchivo;

    public SieniArchivo() {
    }

    public SieniArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public Long getIdArchivo() {
        return idArchivo;
    }

    public void setIdArchivo(Long idArchivo) {
        this.idArchivo = idArchivo;
    }

    public String getArRuta() {
        return arRuta;
    }

    public void setArRuta(String arRuta) {
        this.arRuta = arRuta;
    }

    public Character getArTipo() {
        return arTipo;
    }

    public void setArTipo(Character arTipo) {
        this.arTipo = arTipo;
    }

    public byte[] getArArchivo() {
        return arArchivo;
    }

    public void setArArchivo(byte[] arArchivo) {
        this.arArchivo = arArchivo;
    }

    public String getArNombre() {
        return arNombre;
    }

    public void setArNombre(String arNombre) {
        this.arNombre = arNombre;
    }

    public String getArEstado() {
        return arEstado;
    }

    public void setArEstado(String estado) {
        this.arEstado = estado;
    }

    public String getEstado() {
        if (arEstado != null) {
            switch (arEstado) {
                case "A":
                    estado = "Activo";
                    break;
                case "T":
                    estado = "Trabajando";
                    break;
                case "I":
                    estado = "Eliminado";
                    break;
            }
        }
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = arEstado;
    }

    public String getTipoArchivo() {
        if (arTipo != null) {
            switch (arTipo) {
                case 'I':
                    tipoArchivo = "Imagen";
                    break;
                case 'A':
                    tipoArchivo = "Audio";
                    break;
                case 'V':
                    tipoArchivo = "Video";
                    break;
            }
        }
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public SieniComponente getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(SieniComponente idComponente) {
        this.idComponente = idComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idArchivo != null ? idArchivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniArchivo)) {
            return false;
        }
        SieniArchivo other = (SieniArchivo) object;
        if ((this.idArchivo == null && other.idArchivo != null) || (this.idArchivo != null && !this.idArchivo.equals(other.idArchivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniArchivo[ idArchivo=" + idArchivo + " ]";
    }

}
