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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_bitacora")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniBitacora.getBitacorasRangoFecha", query = "SELECT s FROM SieniBitacora s  WHERE s.bitFechaHoraIngreso>=:desde and s.bitFechaHoraIngreso<=:hasta"),
    @NamedQuery(name = "SieniBitacora.findAll", query = "SELECT s FROM SieniBitacora s"),
    @NamedQuery(name = "SieniBitacora.findByIdBitacora", query = "SELECT s FROM SieniBitacora s WHERE s.idBitacora = :idBitacora"),
    @NamedQuery(name = "SieniBitacora.findByBitFechaHoraIngreso", query = "SELECT s FROM SieniBitacora s WHERE s.bitFechaHoraIngreso = :bitFechaHoraIngreso"),
    @NamedQuery(name = "SieniBitacora.findByBitAccion", query = "SELECT s FROM SieniBitacora s WHERE s.bitAccion = :bitAccion"),
    @NamedQuery(name = "SieniBitacora.findByBitTabla", query = "SELECT s FROM SieniBitacora s WHERE s.bitTabla = :bitTabla"),
    @NamedQuery(name = "SieniBitacora.findByBitIdUsuario", query = "SELECT s FROM SieniBitacora s WHERE s.bitIdUsuario = :bitIdUsuario"),
    @NamedQuery(name = "SieniBitacora.findByBitTipoUsuario", query = "SELECT s FROM SieniBitacora s WHERE s.bitTipoUsuario = :bitTipoUsuario")})
public class SieniBitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_bitacora")
    @SequenceGenerator(name = "sec_sieni_bitacora", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_bitacora")
    @Basic(optional = false)
    @Column(name = "id_bitacora")
    private Long idBitacora;
    @Column(name = "bit_fecha_hora_ingreso")
    @Temporal(TemporalType.DATE)
    private Date bitFechaHoraIngreso;
    @Column(name = "bit_accion")
    private String bitAccion;
    @Column(name = "bit_tabla")
    private String bitTabla;
    @Column(name = "bit_id_usuario")
    private Long bitIdUsuario;
    @Column(name = "bit_tipo_usuario")
    private Character bitTipoUsuario;

    public SieniBitacora() {
    }

    public SieniBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
    }

    public Date getBitFechaHoraIngreso() {
        return bitFechaHoraIngreso;
    }

    public void setBitFechaHoraIngreso(Date bitFechaHoraIngreso) {
        this.bitFechaHoraIngreso = bitFechaHoraIngreso;
    }

    public String getBitAccion() {
        return bitAccion;
    }

    public void setBitAccion(String bitAccion) {
        this.bitAccion = bitAccion;
    }

    public String getBitTabla() {
        return bitTabla;
    }

    public void setBitTabla(String bitTabla) {
        this.bitTabla = bitTabla;
    }

    public Long getBitIdUsuario() {
        return bitIdUsuario;
    }

    public void setBitIdUsuario(Long bitIdUsuario) {
        this.bitIdUsuario = bitIdUsuario;
    }

    public Character getBitTipoUsuario() {
        return bitTipoUsuario;
    }

    public void setBitTipoUsuario(Character bitTipoUsuario) {
        this.bitTipoUsuario = bitTipoUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniBitacora)) {
            return false;
        }
        SieniBitacora other = (SieniBitacora) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniBitacora[ idBitacora=" + idBitacora + " ]";
    }

    public SieniBitacora(Date bitFechaHoraIngreso, String bitAccion, String bitTabla, Long bitIdUsuario, Character bitTipoUsuario) {
        this.bitFechaHoraIngreso = bitFechaHoraIngreso;
        this.bitAccion = bitAccion;
        this.bitTabla = bitTabla;
        this.bitIdUsuario = bitIdUsuario;
        this.bitTipoUsuario = bitTipoUsuario;
    }

}
