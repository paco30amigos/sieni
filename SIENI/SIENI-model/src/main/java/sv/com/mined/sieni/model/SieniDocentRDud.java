/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_docent_r_dud", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_docent_r_dud_"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniDocentRDud.findAll", query = "SELECT s FROM SieniDocentRDud s"),
    @NamedQuery(name = "SieniDocentRDud.findByIdDocentRDud", query = "SELECT s FROM SieniDocentRDud s WHERE s.idDocentRDud = :idDocentRDud"),
    @NamedQuery(name = "SieniDocentRDud.findByFResolDuda", query = "SELECT s FROM SieniDocentRDud s WHERE s.fResolDuda = :fResolDuda")})
public class SieniDocentRDud implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_docent_r_dud_", nullable = false, length = 1)
    private String idDocentRDud;
    @Column(name = "f_resol_duda_")
    private BigInteger fResolDuda;
    @JoinColumn(name = "id_docente", referencedColumnName = "id_docente")
    @ManyToOne
    private SieniDocente idDocente;

    public SieniDocentRDud() {
    }

    public SieniDocentRDud(String idDocentRDud) {
        this.idDocentRDud = idDocentRDud;
    }

    public String getIdDocentRDud() {
        return idDocentRDud;
    }

    public void setIdDocentRDud(String idDocentRDud) {
        this.idDocentRDud = idDocentRDud;
    }

    public BigInteger getFResolDuda() {
        return fResolDuda;
    }

    public void setFResolDuda(BigInteger fResolDuda) {
        this.fResolDuda = fResolDuda;
    }

    public SieniDocente getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(SieniDocente idDocente) {
        this.idDocente = idDocente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDocentRDud != null ? idDocentRDud.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniDocentRDud)) {
            return false;
        }
        SieniDocentRDud other = (SieniDocentRDud) object;
        if ((this.idDocentRDud == null && other.idDocentRDud != null) || (this.idDocentRDud != null && !this.idDocentRDud.equals(other.idDocentRDud))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniDocentRDud[ idDocentRDud=" + idDocentRDud + " ]";
    }
    
}
