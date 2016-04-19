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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_notas_mod_hist")
@NamedQueries({
    @NamedQuery(name = "SieniNotasModHist.findByFecha", query = "SELECT s FROM SieniNotasModHist s where s.ntFechaMod>= :fi and s.ntFechaMod<= :ff"),
    @NamedQuery(name = "SieniNotasModHist.findAll", query = "SELECT s FROM SieniNotasModHist s")})
public class SieniNotasModHist implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_notas_mod_hist")
    @SequenceGenerator(name = "sec_sieni_notas_mod_hist", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_notas_mod_hist")
    @NotNull
    @Column(name = "id_notas_mod_hist")
    private Long idNotasModHist;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "id_nota")
    private Long idNotas;
    @Column(name = "nt_docente")
    private Long ntDocente;
    @Column(name = "nt_calificacion")
    private Double ntCalificacion;
    @Column(name = "nt_fecha_mod")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ntFechaMod;
    @Size(max = 20)
    @Column(name = "nt_tipo_modificacion")
    private String ntTipoModificacion;

    public SieniNotasModHist() {
    }

    public SieniNotasModHist(Long idNotasModHist) {
        this.idNotasModHist = idNotasModHist;
    }

    public Long getIdNotasModHist() {
        return idNotasModHist;
    }

    public void setIdNotasModHist(Long idNotasModHist) {
        this.idNotasModHist = idNotasModHist;
    }

    public Double getNtCalificacion() {
        return ntCalificacion;
    }

    public void setNtCalificacion(Double ntCalificacion) {
        this.ntCalificacion = ntCalificacion;
    }

    public Date getNtFechaMod() {
        return ntFechaMod;
    }

    public void setNtFechaMod(Date ntFechaMod) {
        this.ntFechaMod = ntFechaMod;
    }

    public String getNtTipoModificacion() {
        return ntTipoModificacion;
    }

    public void setNtTipoModificacion(String ntTipoModificacion) {
        this.ntTipoModificacion = ntTipoModificacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotasModHist != null ? idNotasModHist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniNotasModHist)) {
            return false;
        }
        SieniNotasModHist other = (SieniNotasModHist) object;
        if ((this.idNotasModHist == null && other.idNotasModHist != null) || (this.idNotasModHist != null && !this.idNotasModHist.equals(other.idNotasModHist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniNotasModHist[ idNotasModHist=" + idNotasModHist + " ]";
    }

    public Long getIdNotas() {
        return idNotas;
    }

    public void setIdNotas(Long idNotas) {
        this.idNotas = idNotas;
    }

    public Long getNtDocente() {
        return ntDocente;
    }

    public void setNtDocente(Long ntDocente) {
        this.ntDocente = ntDocente;
    }

}
