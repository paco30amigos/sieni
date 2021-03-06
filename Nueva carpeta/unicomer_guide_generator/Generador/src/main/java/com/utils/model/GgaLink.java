/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Table(name = "GGA_LINK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GgaLink.findAll", query = "SELECT g FROM GgaLink g")
    , @NamedQuery(name = "GgaLink.findByLinkId", query = "SELECT g FROM GgaLink g WHERE g.linkId = :linkId")
    , @NamedQuery(name = "GgaLink.findByUrlVideo", query = "SELECT g FROM GgaLink g WHERE g.urlVideo = :urlVideo")
    , @NamedQuery(name = "GgaLink.findByStartsAt", query = "SELECT g FROM GgaLink g WHERE g.startsAt = :startsAt")
    , @NamedQuery(name = "GgaLink.findByEndsAt", query = "SELECT g FROM GgaLink g WHERE g.endsAt = :endsAt")})
public class GgaLink implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "LINK_ID")
    private BigDecimal linkId;
    @Column(name = "URL_VIDEO")
    private String urlVideo;
    @Column(name = "STARTS_AT")
    private BigInteger startsAt;
    @Column(name = "ENDS_AT")
    private BigInteger endsAt;
    @OneToMany(mappedBy = "linkId")
    private Set<GgaPlaylistLink> ggaPlaylistLinkSet;
    @OneToMany(mappedBy = "linkId")
    private Set<GgaLinkExtraData> ggaLinkExtraDataSet;

    public GgaLink() {
    }

    public GgaLink(BigDecimal linkId) {
        this.linkId = linkId;
    }

    public BigDecimal getLinkId() {
        return linkId;
    }

    public void setLinkId(BigDecimal linkId) {
        this.linkId = linkId;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public BigInteger getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(BigInteger startsAt) {
        this.startsAt = startsAt;
    }

    public BigInteger getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(BigInteger endsAt) {
        this.endsAt = endsAt;
    }

    @XmlTransient
    public Set<GgaPlaylistLink> getGgaPlaylistLinkSet() {
        return ggaPlaylistLinkSet;
    }

    public void setGgaPlaylistLinkSet(Set<GgaPlaylistLink> ggaPlaylistLinkSet) {
        this.ggaPlaylistLinkSet = ggaPlaylistLinkSet;
    }

    @XmlTransient
    public Set<GgaLinkExtraData> getGgaLinkExtraDataSet() {
        return ggaLinkExtraDataSet;
    }

    public void setGgaLinkExtraDataSet(Set<GgaLinkExtraData> ggaLinkExtraDataSet) {
        this.ggaLinkExtraDataSet = ggaLinkExtraDataSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (linkId != null ? linkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GgaLink)) {
            return false;
        }
        GgaLink other = (GgaLink) object;
        if ((this.linkId == null && other.linkId != null) || (this.linkId != null && !this.linkId.equals(other.linkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utils.gui_generator.model.GgaLink[ linkId=" + linkId + " ]";
    }
    
}
