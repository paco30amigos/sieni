/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.model;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "GGA_PLAYLIST_LINK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GgaPlaylistLink.findAll", query = "SELECT g FROM GgaPlaylistLink g")
    , @NamedQuery(name = "GgaPlaylistLink.findByPlaylistLinkId", query = "SELECT g FROM GgaPlaylistLink g WHERE g.playlistLinkId = :playlistLinkId")
    , @NamedQuery(name = "GgaPlaylistLink.findByOrderNum", query = "SELECT g FROM GgaPlaylistLink g WHERE g.orderNum = :orderNum")})
public class GgaPlaylistLink implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "PLAYLIST_LINK_ID")
    private BigDecimal playlistLinkId;
    @Column(name = "ORDER_NUM")
    private BigInteger orderNum;
    @JoinColumn(name = "LINK_ID", referencedColumnName = "LINK_ID")
    @ManyToOne
    private GgaLink linkId;
    @JoinColumn(name = "PLAYLIST_ID", referencedColumnName = "PLAYLIST_ID")
    @ManyToOne
    private GgaPlaylist playlistId;

    public GgaPlaylistLink() {
    }

    public GgaPlaylistLink(BigDecimal playlistLinkId) {
        this.playlistLinkId = playlistLinkId;
    }

    public BigDecimal getPlaylistLinkId() {
        return playlistLinkId;
    }

    public void setPlaylistLinkId(BigDecimal playlistLinkId) {
        this.playlistLinkId = playlistLinkId;
    }

    public BigInteger getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(BigInteger orderNum) {
        this.orderNum = orderNum;
    }

    public GgaLink getLinkId() {
        return linkId;
    }

    public void setLinkId(GgaLink linkId) {
        this.linkId = linkId;
    }

    public GgaPlaylist getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(GgaPlaylist playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playlistLinkId != null ? playlistLinkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GgaPlaylistLink)) {
            return false;
        }
        GgaPlaylistLink other = (GgaPlaylistLink) object;
        if ((this.playlistLinkId == null && other.playlistLinkId != null) || (this.playlistLinkId != null && !this.playlistLinkId.equals(other.playlistLinkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utils.gui_generator.model.GgaPlaylistLink[ playlistLinkId=" + playlistLinkId + " ]";
    }
    
}
