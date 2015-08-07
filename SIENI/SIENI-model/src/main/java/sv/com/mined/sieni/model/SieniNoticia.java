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
import javax.persistence.Id;
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
@Table(name = "sieni_noticia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniNoticia.findAll", query = "SELECT s FROM SieniNoticia s"),
    @NamedQuery(name = "SieniNoticia.findByIdNoticia", query = "SELECT s FROM SieniNoticia s WHERE s.idNoticia = :idNoticia"),
    @NamedQuery(name = "SieniNoticia.findByNcMensaje", query = "SELECT s FROM SieniNoticia s WHERE s.ncMensaje = :ncMensaje"),
    @NamedQuery(name = "SieniNoticia.findByNcPrioridad", query = "SELECT s FROM SieniNoticia s WHERE s.ncPrioridad = :ncPrioridad"),
    @NamedQuery(name = "SieniNoticia.findByNcEstado", query = "SELECT s FROM SieniNoticia s WHERE s.ncEstado = :ncEstado"),
    @NamedQuery(name = "SieniNoticia.findByNcTipo", query = "SELECT s FROM SieniNoticia s WHERE s.ncTipo = :ncTipo")})
public class SieniNoticia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_noticia")
    private Long idNoticia;
    @Column(name = "nc_mensaje")
    private String ncMensaje;
    @Column(name = "nc_prioridad")
    private Integer ncPrioridad;
    @Column(name = "nc_estado")
    private Character ncEstado;
    @Column(name = "nc_tipo")
    private Character ncTipo;
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    @ManyToOne
    private SieniCurso idCurso;

    public SieniNoticia() {
    }

    public SieniNoticia(Long idNoticia) {
        this.idNoticia = idNoticia;
    }

    public Long getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(Long idNoticia) {
        this.idNoticia = idNoticia;
    }

    public String getNcMensaje() {
        return ncMensaje;
    }

    public void setNcMensaje(String ncMensaje) {
        this.ncMensaje = ncMensaje;
    }

    public Integer getNcPrioridad() {
        return ncPrioridad;
    }

    public void setNcPrioridad(Integer ncPrioridad) {
        this.ncPrioridad = ncPrioridad;
    }

    public Character getNcEstado() {
        return ncEstado;
    }

    public void setNcEstado(Character ncEstado) {
        this.ncEstado = ncEstado;
    }

    public Character getNcTipo() {
        return ncTipo;
    }

    public void setNcTipo(Character ncTipo) {
        this.ncTipo = ncTipo;
    }

    public SieniCurso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(SieniCurso idCurso) {
        this.idCurso = idCurso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNoticia != null ? idNoticia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniNoticia)) {
            return false;
        }
        SieniNoticia other = (SieniNoticia) object;
        if ((this.idNoticia == null && other.idNoticia != null) || (this.idNoticia != null && !this.idNoticia.equals(other.idNoticia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniNoticia[ idNoticia=" + idNoticia + " ]";
    }
    
}
