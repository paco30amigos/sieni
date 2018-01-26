/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "GGA_LINK_EXTRA_DATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GgaLinkExtraData.findAll", query = "SELECT g FROM GgaLinkExtraData g")
    , @NamedQuery(name = "GgaLinkExtraData.findByLinkExtraDataId", query = "SELECT g FROM GgaLinkExtraData g WHERE g.linkExtraDataId = :linkExtraDataId")
    , @NamedQuery(name = "GgaLinkExtraData.findByShowAt", query = "SELECT g FROM GgaLinkExtraData g WHERE g.showAt = :showAt")
    , @NamedQuery(name = "GgaLinkExtraData.findByPauseOnShow", query = "SELECT g FROM GgaLinkExtraData g WHERE g.pauseOnShow = :pauseOnShow")})
public class GgaLinkExtraData implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "LINK_EXTRA_DATA_ID")
    private BigDecimal linkExtraDataId;
    @Lob
    @Column(name = "CONTENT")
    private String content;
    @Column(name = "SHOW_AT")
    private BigInteger showAt;
    @Column(name = "PAUSE_ON_SHOW")
    private BigInteger pauseOnShow;
    @JoinColumn(name = "LINK_ID", referencedColumnName = "LINK_ID")
    @ManyToOne
    private GgaLink linkId;

    public GgaLinkExtraData() {
    }

    public GgaLinkExtraData(BigDecimal linkExtraDataId) {
        this.linkExtraDataId = linkExtraDataId;
    }

    public BigDecimal getLinkExtraDataId() {
        return linkExtraDataId;
    }

    public void setLinkExtraDataId(BigDecimal linkExtraDataId) {
        this.linkExtraDataId = linkExtraDataId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigInteger getShowAt() {
        return showAt;
    }

    public void setShowAt(BigInteger showAt) {
        this.showAt = showAt;
    }

    public BigInteger getPauseOnShow() {
        return pauseOnShow;
    }

    public void setPauseOnShow(BigInteger pauseOnShow) {
        this.pauseOnShow = pauseOnShow;
    }

    public GgaLink getLinkId() {
        return linkId;
    }

    public void setLinkId(GgaLink linkId) {
        this.linkId = linkId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (linkExtraDataId != null ? linkExtraDataId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GgaLinkExtraData)) {
            return false;
        }
        GgaLinkExtraData other = (GgaLinkExtraData) object;
        if ((this.linkExtraDataId == null && other.linkExtraDataId != null) || (this.linkExtraDataId != null && !this.linkExtraDataId.equals(other.linkExtraDataId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.unicomer.webapplication_psdisr.GgaLinkExtraData[ linkExtraDataId=" + linkExtraDataId + " ]";
    }
    
}
