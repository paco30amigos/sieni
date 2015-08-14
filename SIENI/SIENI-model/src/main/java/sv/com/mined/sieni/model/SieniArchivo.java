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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_archivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniArchivo.findAll", query = "SELECT s FROM SieniArchivo s"),
    @NamedQuery(name = "SieniArchivo.findByIdArchivo", query = "SELECT s FROM SieniArchivo s WHERE s.idArchivo = :idArchivo"),
    @NamedQuery(name = "SieniArchivo.findByArRuta", query = "SELECT s FROM SieniArchivo s WHERE s.arRuta = :arRuta"),
    @NamedQuery(name = "SieniArchivo.findByArTipo", query = "SELECT s FROM SieniArchivo s WHERE s.arTipo = :arTipo"),
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

    public void setArEstado(String arEstado) {
        this.arEstado = arEstado;
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
