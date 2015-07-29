/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bugtraq
 */
@Entity
@Table(name = "sieni_pntos_contrl", catalog = "BD_SIENI", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_pntos_contrl"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniPntosContrl.findAll", query = "SELECT s FROM SieniPntosContrl s"),
    @NamedQuery(name = "SieniPntosContrl.findByIdPntosContrl", query = "SELECT s FROM SieniPntosContrl s WHERE s.idPntosContrl = :idPntosContrl"),
    @NamedQuery(name = "SieniPntosContrl.findByPcTipo", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcTipo = :pcTipo"),
    @NamedQuery(name = "SieniPntosContrl.findByPcIdentificador", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcIdentificador = :pcIdentificador"),
    @NamedQuery(name = "SieniPntosContrl.findByPcDescripcion", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcDescripcion = :pcDescripcion"),
    @NamedQuery(name = "SieniPntosContrl.findByPcTiempo", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcTiempo = :pcTiempo"),
    @NamedQuery(name = "SieniPntosContrl.findByPcSiguiente", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcSiguiente = :pcSiguiente"),
    @NamedQuery(name = "SieniPntosContrl.findByPcAnterior", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcAnterior = :pcAnterior"),
    @NamedQuery(name = "SieniPntosContrl.findByPcUltimo", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcUltimo = :pcUltimo"),
    @NamedQuery(name = "SieniPntosContrl.findByPcEstado", query = "SELECT s FROM SieniPntosContrl s WHERE s.pcEstado = :pcEstado")})
public class SieniPntosContrl implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_pntos_contrl", nullable = false)
    private Long idPntosContrl;
    @Column(name = "pc_tipo", length = 2)
    private String pcTipo;
    @Column(name = "pc_identificador", length = 50)
    private String pcIdentificador;
    @Column(name = "pc_descripcion", length = 100)
    private String pcDescripcion;
    @Column(name = "pc_tiempo")
    @Temporal(TemporalType.TIME)
    private Date pcTiempo;
    @Column(name = "pc_siguiente")
    private BigInteger pcSiguiente;
    @Column(name = "pc_anterior")
    private BigInteger pcAnterior;
    @Column(name = "pc_ultimo")
    private BigInteger pcUltimo;
    @Column(name = "pc_estado")
    private Character pcEstado;
    @JoinColumn(name = "id_clase", referencedColumnName = "id_clase")
    @ManyToOne
    private SieniClase idClase;
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno")
    @ManyToOne
    private SieniAlumno idAlumno;

    public SieniPntosContrl() {
    }

    public SieniPntosContrl(Long idPntosContrl) {
        this.idPntosContrl = idPntosContrl;
    }

    public Long getIdPntosContrl() {
        return idPntosContrl;
    }

    public void setIdPntosContrl(Long idPntosContrl) {
        this.idPntosContrl = idPntosContrl;
    }

    public String getPcTipo() {
        return pcTipo;
    }

    public void setPcTipo(String pcTipo) {
        this.pcTipo = pcTipo;
    }

    public String getPcIdentificador() {
        return pcIdentificador;
    }

    public void setPcIdentificador(String pcIdentificador) {
        this.pcIdentificador = pcIdentificador;
    }

    public String getPcDescripcion() {
        return pcDescripcion;
    }

    public void setPcDescripcion(String pcDescripcion) {
        this.pcDescripcion = pcDescripcion;
    }

    public Date getPcTiempo() {
        return pcTiempo;
    }

    public void setPcTiempo(Date pcTiempo) {
        this.pcTiempo = pcTiempo;
    }

    public BigInteger getPcSiguiente() {
        return pcSiguiente;
    }

    public void setPcSiguiente(BigInteger pcSiguiente) {
        this.pcSiguiente = pcSiguiente;
    }

    public BigInteger getPcAnterior() {
        return pcAnterior;
    }

    public void setPcAnterior(BigInteger pcAnterior) {
        this.pcAnterior = pcAnterior;
    }

    public BigInteger getPcUltimo() {
        return pcUltimo;
    }

    public void setPcUltimo(BigInteger pcUltimo) {
        this.pcUltimo = pcUltimo;
    }

    public Character getPcEstado() {
        return pcEstado;
    }

    public void setPcEstado(Character pcEstado) {
        this.pcEstado = pcEstado;
    }

    public SieniClase getIdClase() {
        return idClase;
    }

    public void setIdClase(SieniClase idClase) {
        this.idClase = idClase;
    }

    public SieniAlumno getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(SieniAlumno idAlumno) {
        this.idAlumno = idAlumno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPntosContrl != null ? idPntosContrl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniPntosContrl)) {
            return false;
        }
        SieniPntosContrl other = (SieniPntosContrl) object;
        if ((this.idPntosContrl == null && other.idPntosContrl != null) || (this.idPntosContrl != null && !this.idPntosContrl.equals(other.idPntosContrl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniPntosContrl[ idPntosContrl=" + idPntosContrl + " ]";
    }
    
}