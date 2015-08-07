/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_eval_sup_comp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvalSupComp.findAll", query = "SELECT s FROM SieniEvalSupComp s"),
    @NamedQuery(name = "SieniEvalSupComp.findByIdEvalSupComp", query = "SELECT s FROM SieniEvalSupComp s WHERE s.sieniEvalSupCompPK.idEvalSupComp = :idEvalSupComp"),
    @NamedQuery(name = "SieniEvalSupComp.findByFSupComp", query = "SELECT s FROM SieniEvalSupComp s WHERE s.sieniEvalSupCompPK.fSupComp = :fSupComp")})
public class SieniEvalSupComp implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SieniEvalSupCompPK sieniEvalSupCompPK;
    @JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion")
    @ManyToOne
    private SieniEvaluacion idEvaluacion;

    public SieniEvalSupComp() {
    }

    public SieniEvalSupComp(SieniEvalSupCompPK sieniEvalSupCompPK) {
        this.sieniEvalSupCompPK = sieniEvalSupCompPK;
    }

    public SieniEvalSupComp(long idEvalSupComp, long fSupComp) {
        this.sieniEvalSupCompPK = new SieniEvalSupCompPK(idEvalSupComp, fSupComp);
    }

    public SieniEvalSupCompPK getSieniEvalSupCompPK() {
        return sieniEvalSupCompPK;
    }

    public void setSieniEvalSupCompPK(SieniEvalSupCompPK sieniEvalSupCompPK) {
        this.sieniEvalSupCompPK = sieniEvalSupCompPK;
    }

    public SieniEvaluacion getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(SieniEvaluacion idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sieniEvalSupCompPK != null ? sieniEvalSupCompPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvalSupComp)) {
            return false;
        }
        SieniEvalSupComp other = (SieniEvalSupComp) object;
        if ((this.sieniEvalSupCompPK == null && other.sieniEvalSupCompPK != null) || (this.sieniEvalSupCompPK != null && !this.sieniEvalSupCompPK.equals(other.sieniEvalSupCompPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvalSupComp[ sieniEvalSupCompPK=" + sieniEvalSupCompPK + " ]";
    }
    
}
