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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ever
 */
@Entity
@Table(name = "sieni_eval_resp_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvalRespItem.findAll", query = "SELECT s FROM SieniEvalRespItem s"),
    @NamedQuery(name = "SieniEvalRespItem.findByIdEvalRespItem", query = "SELECT s FROM SieniEvalRespItem s WHERE s.idEvalRespItem = :idEvalRespItem"),
    @NamedQuery(name = "SieniEvalRespItem.findByErRespuesta", query = "SELECT s FROM SieniEvalRespItem s WHERE s.erRespuesta = :erRespuesta"),
    @NamedQuery(name = "SieniEvalRespItem.findByIdEvalItem", query = "SELECT s FROM SieniEvalRespItem s WHERE s.idEvaluacionItem.idEvaluacionItem = :idEvaluacionItem AND s.erEstado='A'"),
    @NamedQuery(name = "SieniEvalRespItem.findByErEstado", query = "SELECT s FROM SieniEvalRespItem s WHERE s.erEstado = :erEstado"),
    @NamedQuery(name = "SieniEvalRespItem.findByErRespCorrecta", query = "SELECT s FROM SieniEvalRespItem s WHERE s.erRespCorrecta = :erRespCorrecta"),
    @NamedQuery(name = "SieniEvalRespItem.findByErFechaIngreso", query = "SELECT s FROM SieniEvalRespItem s WHERE s.erFechaIngreso = :erFechaIngreso")})
public class SieniEvalRespItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_eval_resp_item")
    @SequenceGenerator(name = "sec_sieni_eval_resp_item", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_eval_resp_item")
    @Basic(optional = false)    
    @Column(name = "id_eval_resp_item")
    private Long idEvalRespItem;
    @Size(max = 2147483647)
    @Column(name = "er_respuesta")
    private String erRespuesta;
    @Lob
    @Column(name = "er_imagen")
    private byte[] erImagen;
    @Column(name = "er_estado")
    private Character erEstado;
    @Size(max = 2147483647)
    @Column(name = "er_resp_correcta")
    private String erRespCorrecta;    
    @Size(max = 2147483647)
    @Column(name = "er_tipo_input")
    private String erTipoInput;
    @Column(name = "er_fecha_ingreso",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date erFechaIngreso;
      
    @PrePersist
    protected void onCreate() {
        erFechaIngreso = new Date();
    }
    
    
    @JoinColumn(name = "id_evaluacion_item", referencedColumnName = "id_evaluacion_item")
    @ManyToOne
    private SieniEvaluacionItem idEvaluacionItem;

    public SieniEvalRespItem() {
    }

    public SieniEvalRespItem(Long idEvalRespItem) {
        this.idEvalRespItem = idEvalRespItem;
    }

    public Long getIdEvalRespItem() {
        return idEvalRespItem;
    }

    public void setIdEvalRespItem(Long idEvalRespItem) {
        this.idEvalRespItem = idEvalRespItem;
    }

    public String getErRespuesta() {
        return erRespuesta;
    }

    public void setErRespuesta(String erRespuesta) {
        this.erRespuesta = erRespuesta;
    }

    public byte[] getErImagen() {
        return erImagen;
    }

    public void setErImagen(byte[] erImagen) {
        this.erImagen = erImagen;
    }

    public Character getErEstado() {
        return erEstado;
    }

    public void setErEstado(Character erEstado) {
        this.erEstado = erEstado;
    }

    public String getErRespCorrecta() {
        return erRespCorrecta;
    }

    public void setErRespCorrecta(String erRespCorrecta) {
        this.erRespCorrecta = erRespCorrecta;
    }

    public Date getErFechaIngreso() {
        return erFechaIngreso;
    }

    public void setErFechaIngreso(Date erFechaIngreso) {
        this.erFechaIngreso = erFechaIngreso;
    }

    public SieniEvaluacionItem getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(SieniEvaluacionItem idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    public String getErTipoInput() {
        return erTipoInput;
    }

    public void setErTipoInput(String erTipoInput) {
        this.erTipoInput = erTipoInput;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvalRespItem != null ? idEvalRespItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvalRespItem)) {
            return false;
        }
        SieniEvalRespItem other = (SieniEvalRespItem) object;
        if ((this.idEvalRespItem == null && other.idEvalRespItem != null) || (this.idEvalRespItem != null && !this.idEvalRespItem.equals(other.idEvalRespItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvalRespItem[ idEvalRespItem=" + idEvalRespItem + " ]";
    }
    
}
