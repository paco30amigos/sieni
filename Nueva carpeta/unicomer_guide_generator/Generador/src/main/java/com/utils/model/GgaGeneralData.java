/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "GGA_GENERAL_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GgaGeneralData.findAll", query = "SELECT g FROM GgaGeneralData g")
    , @NamedQuery(name = "GgaGeneralData.findByGeneralDataId", query = "SELECT g FROM GgaGeneralData g WHERE g.generalDataId = :generalDataId")
    , @NamedQuery(name = "GgaGeneralData.findByTitle", query = "SELECT g FROM GgaGeneralData g WHERE g.title = :title")
    , @NamedQuery(name = "GgaGeneralData.findByDescription", query = "SELECT g FROM GgaGeneralData g WHERE g.description = :description")})
public class GgaGeneralData implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "GENERAL_DATA_ID")
    private BigDecimal generalDataId;
    @Basic(optional = false)
    @Column(name = "TITLE")
    private String title;
    @Column(name = "DESCRIPTION")
    private String description;
    @OneToMany(mappedBy = "generalDataId")
    private Set<GgaPlaylist> ggaPlaylistSet;

    public GgaGeneralData() {
    }

    public GgaGeneralData(BigDecimal generalDataId) {
        this.generalDataId = generalDataId;
    }

    public GgaGeneralData(BigDecimal generalDataId, String title) {
        this.generalDataId = generalDataId;
        this.title = title;
    }

    public BigDecimal getGeneralDataId() {
        return generalDataId;
    }

    public void setGeneralDataId(BigDecimal generalDataId) {
        this.generalDataId = generalDataId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Set<GgaPlaylist> getGgaPlaylistSet() {
        return ggaPlaylistSet;
    }

    public void setGgaPlaylistSet(Set<GgaPlaylist> ggaPlaylistSet) {
        this.ggaPlaylistSet = ggaPlaylistSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (generalDataId != null ? generalDataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GgaGeneralData)) {
            return false;
        }
        GgaGeneralData other = (GgaGeneralData) object;
        if ((this.generalDataId == null && other.generalDataId != null) || (this.generalDataId != null && !this.generalDataId.equals(other.generalDataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utils.gui_generator.model.GgaGeneralData[ generalDataId=" + generalDataId + " ]";
    }
    
}
