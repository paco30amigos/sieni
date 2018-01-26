/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "GGA_PLAYLIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GgaPlaylist.findAll", query = "SELECT g FROM GgaPlaylist g")
    , @NamedQuery(name = "GgaPlaylist.findByPlaylistId", query = "SELECT g FROM GgaPlaylist g WHERE g.playlistId = :playlistId")
    , @NamedQuery(name = "GgaPlaylist.findByPlaylistName", query = "SELECT g FROM GgaPlaylist g WHERE g.playlistName = :playlistName")
    , @NamedQuery(name = "GgaPlaylist.findByIsActive", query = "SELECT g FROM GgaPlaylist g WHERE g.isActive = :isActive")})
public class GgaPlaylist implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PLAYLIST_ID")
    private BigDecimal playlistId;
    @Size(max = 300)
    @Column(name = "PLAYLIST_NAME")
    private String playlistName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_ACTIVE")
    private Character isActive;
    @OneToMany(mappedBy = "playlistId",fetch = FetchType.LAZY)
    private Set<GgaPlaylistLink> ggaPlaylistLinkSet;
    @JoinColumn(name = "GENERAL_DATA_ID", referencedColumnName = "GENERAL_DATA_ID")
    @ManyToOne
    private GgaGeneralData generalDataId;

    public GgaPlaylist() {
    }

    public GgaPlaylist(BigDecimal playlistId) {
        this.playlistId = playlistId;
    }

    public GgaPlaylist(BigDecimal playlistId, Character isActive) {
        this.playlistId = playlistId;
        this.isActive = isActive;
    }

    public BigDecimal getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(BigDecimal playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public Character getIsActive() {
        return isActive;
    }

    public void setIsActive(Character isActive) {
        this.isActive = isActive;
    }

    @XmlTransient
    public Set<GgaPlaylistLink> getGgaPlaylistLinkSet() {
        return ggaPlaylistLinkSet;
    }

    public void setGgaPlaylistLinkSet(Set<GgaPlaylistLink> ggaPlaylistLinkSet) {
        this.ggaPlaylistLinkSet = ggaPlaylistLinkSet;
    }

    public GgaGeneralData getGeneralDataId() {
        return generalDataId;
    }

    public void setGeneralDataId(GgaGeneralData generalDataId) {
        this.generalDataId = generalDataId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playlistId != null ? playlistId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GgaPlaylist)) {
            return false;
        }
        GgaPlaylist other = (GgaPlaylist) object;
        if ((this.playlistId == null && other.playlistId != null) || (this.playlistId != null && !this.playlistId.equals(other.playlistId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicomer.webapplication_psdisr.GgaPlaylist[ playlistId=" + playlistId + " ]";
    }
    
}
