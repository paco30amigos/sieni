/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author ever
 */
@Entity
@Table(name = "sieni_evaluacion_item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvaluacionItem.findAll", query = "SELECT s FROM SieniEvaluacionItem s"),
    @NamedQuery(name = "SieniEvaluacionItem.findByIdEvaluacionItem", query = "SELECT s FROM SieniEvaluacionItem s WHERE s.idEvaluacionItem = :idEvaluacionItem"),
    @NamedQuery(name = "SieniEvaluacionItem.findByEiPregunta", query = "SELECT s FROM SieniEvaluacionItem s WHERE s.eiPregunta = :eiPregunta"),
    @NamedQuery(name = "SieniEvaluacionItem.findByIdEvaluacion", query = "SELECT s FROM SieniEvaluacionItem s WHERE s.idEvaluacion.idEvaluacion = :idEvaluacion AND s.eiEstado='A'"),
    @NamedQuery(name = "SieniEvaluacionItem.findByEiFechaIngreso", query = "SELECT s FROM SieniEvaluacionItem s WHERE s.eiFechaIngreso = :eiFechaIngreso"),
    @NamedQuery(name = "SieniEvaluacionItem.findByEiEstado", query = "SELECT s FROM SieniEvaluacionItem s WHERE s.eiEstado = :eiEstado"),
    @NamedQuery(name = "SieniEvaluacionItem.findByEiTipoResp", query = "SELECT s FROM SieniEvaluacionItem s WHERE s.eiTipoResp = :eiTipoResp")})
public class SieniEvaluacionItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sec_sieni_evaluacion_item")
    @SequenceGenerator(name = "sec_sieni_evaluacion_item", initialValue = 1, allocationSize = 1, sequenceName = "sec_sieni_evaluacion_item")
    @Basic(optional = false)
    @Column(name = "id_evaluacion_item")
    private Long idEvaluacionItem;
    @Size(max = 2147483647)
    @Column(name = "ei_pregunta")
    private String eiPregunta;
    @Lob
    @Column(name = "ei_imagen")
    private byte[] eiImagen;
    @Column(name = "ei_fecha_ingreso")
    @Temporal(TemporalType.DATE)
    private Date eiFechaIngreso;
    @Column(name = "ei_estado")
    private Character eiEstado;
     @Column(name = "ei_ponderacion")
    private Double eiPonderacion;
    @Size(max = 2)
    @Column(name = "ei_tipo_resp")
    private String eiTipoResp;
    @JoinColumn(name = "id_evaluacion", referencedColumnName = "id_evaluacion")
    @ManyToOne
    private SieniEvaluacion idEvaluacion;
    @OneToMany(mappedBy = "idEvaluacionItem")
    private List<SieniEvalRespAlumno> sieniEvalRespAlumnoList;
    @OneToMany(mappedBy = "idEvaluacionItem",fetch = FetchType.EAGER)
    private List<SieniEvalRespItem> sieniEvalRespItemList;

    public SieniEvaluacionItem() {
    }

    public SieniEvaluacionItem(Long idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    public Long getIdEvaluacionItem() {
        return idEvaluacionItem;
    }

    public void setIdEvaluacionItem(Long idEvaluacionItem) {
        this.idEvaluacionItem = idEvaluacionItem;
    }

    public String getEiPregunta() {
        return eiPregunta;
    }

    public void setEiPregunta(String eiPregunta) {
        this.eiPregunta = eiPregunta;
    }

    public byte[] getEiImagen() {
        return eiImagen;
    }

    public void setEiImagen(byte[] eiImagen) {
        this.eiImagen = eiImagen;
    }

    public Date getEiFechaIngreso() {
        return eiFechaIngreso;
    }

    public void setEiFechaIngreso(Date eiFechaIngreso) {
        this.eiFechaIngreso = eiFechaIngreso;
    }

    public Character getEiEstado() {
        return eiEstado;
    }

    public void setEiEstado(Character eiEstado) {
        this.eiEstado = eiEstado;
    }

    public String getEiTipoResp() {
        return eiTipoResp;
    }

    public void setEiTipoResp(String eiTipoResp) {
        this.eiTipoResp = eiTipoResp;
    }

    public SieniEvaluacion getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(SieniEvaluacion idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public Double getEiPonderacion() {
        return eiPonderacion;
    }

    public void setEiPonderacion(Double eiPonderacion) {
        this.eiPonderacion = eiPonderacion;
    }

    @XmlTransient
    public List<SieniEvalRespAlumno> getSieniEvalRespAlumnoList() {
        return sieniEvalRespAlumnoList;
    }

    public void setSieniEvalRespAlumnoList(List<SieniEvalRespAlumno> sieniEvalRespAlumnoList) {
        this.sieniEvalRespAlumnoList = sieniEvalRespAlumnoList;
    }

    @XmlTransient
    public List<SieniEvalRespItem> getSieniEvalRespItemList() {
        return sieniEvalRespItemList;
    }

    public void setSieniEvalRespItemList(List<SieniEvalRespItem> sieniEvalRespItemList) {
        this.sieniEvalRespItemList = sieniEvalRespItemList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvaluacionItem != null ? idEvaluacionItem.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvaluacionItem)) {
            return false;
        }
        SieniEvaluacionItem other = (SieniEvaluacionItem) object;
        if ((this.idEvaluacionItem == null && other.idEvaluacionItem != null) || (this.idEvaluacionItem != null && !this.idEvaluacionItem.equals(other.idEvaluacionItem))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvaluacionItem[ idEvaluacionItem=" + idEvaluacionItem + " ]";
    }
    
}
