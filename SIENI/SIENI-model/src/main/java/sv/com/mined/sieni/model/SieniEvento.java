/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.com.mined.sieni.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Laptop
 */
@Entity
@Table(name = "sieni_evento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SieniEvento.findByTipoSuperComponente", query = "SELECT s FROM SieniEvento s where s.idTipoSuperCompon.idTipoSuperCompon=:idTipoSuperComponente"),
    @NamedQuery(name = "SieniEvento.findAll", query = "SELECT s FROM SieniEvento s"),
    @NamedQuery(name = "SieniEvento.findByIdEvento", query = "SELECT s FROM SieniEvento s WHERE s.idEvento = :idEvento"),
    @NamedQuery(name = "SieniEvento.findByEvDescripcion", query = "SELECT s FROM SieniEvento s WHERE s.evDescripcion = :evDescripcion"),
    @NamedQuery(name = "SieniEvento.findByEvCodigo", query = "SELECT s FROM SieniEvento s WHERE s.evCodigo = :evCodigo")})
public class SieniEvento implements Serializable {
    @OneToMany(mappedBy = "ieEventoC2")
    private List<SieniInteEntrComp> sieniInteEntrCompList;
    @OneToMany(mappedBy = "ieEventoC1")
    private List<SieniInteEntrComp> sieniInteEntrCompList1;
    @OneToMany(mappedBy = "idEvento")
    private List<SieniCompInteraccion> sieniCompInteraccionList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_evento")
    private Long idEvento;
    @Size(max = 100)
    @Column(name = "ev_descripcion")
    private String evDescripcion;
    @Size(max = 4000)
    @Column(name = "ev_codigo")
    private String evCodigo;
    @JoinColumn(name = "id_tipo_super_compon", referencedColumnName = "id_tipo_super_compon")
    @ManyToOne
    private SieniTipoSuperCompon idTipoSuperCompon;

    public SieniEvento() {
    }

    public SieniEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public Long getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Long idEvento) {
        this.idEvento = idEvento;
    }

    public String getEvDescripcion() {
        return evDescripcion;
    }

    public void setEvDescripcion(String evDescripcion) {
        this.evDescripcion = evDescripcion;
    }

    public String getEvCodigo() {
        return evCodigo;
    }

    public void setEvCodigo(String evCodigo) {
        this.evCodigo = evCodigo;
    }

    public SieniTipoSuperCompon getIdTipoSuperCompon() {
        return idTipoSuperCompon;
    }

    public void setIdTipoSuperCompon(SieniTipoSuperCompon idTipoSuperCompon) {
        this.idTipoSuperCompon = idTipoSuperCompon;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SieniEvento)) {
            return false;
        }
        SieniEvento other = (SieniEvento) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.com.mined.sieni.model.SieniEvento[ idEvento=" + idEvento + " ]";
    }

    public List<SieniCompInteraccion> getSieniCompInteraccionList() {
        return sieniCompInteraccionList;
    }

    public void setSieniCompInteraccionList(List<SieniCompInteraccion> sieniCompInteraccionList) {
        this.sieniCompInteraccionList = sieniCompInteraccionList;
    }

    public List<SieniInteEntrComp> getSieniInteEntrCompList() {
        return sieniInteEntrCompList;
    }

    public void setSieniInteEntrCompList(List<SieniInteEntrComp> sieniInteEntrCompList) {
        this.sieniInteEntrCompList = sieniInteEntrCompList;
    }

    public List<SieniInteEntrComp> getSieniInteEntrCompList1() {
        return sieniInteEntrCompList1;
    }

    public void setSieniInteEntrCompList1(List<SieniInteEntrComp> sieniInteEntrCompList1) {
        this.sieniInteEntrCompList1 = sieniInteEntrCompList1;
    }

}
