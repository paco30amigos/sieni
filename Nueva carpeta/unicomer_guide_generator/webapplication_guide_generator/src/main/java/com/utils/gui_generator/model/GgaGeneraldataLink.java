/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement; 

/**
 *
 * @author francisco_medina
 */
@Entity
@Table(name = "GGA_GENERALDATA_LINK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GgaGeneraldataLink.findAll", query = "SELECT g FROM GgaGeneraldataLink g") 
    , @NamedQuery(name = "GgaGeneraldataLink.findByParentId", query = "SELECT g FROM GgaGeneraldataLink g join fetch g.parentLinkId pl WHERE pl.generaldataLinkId=:parentLinkId ")
    , @NamedQuery(name = "GgaGeneraldataLink.findByGeneraldataLinkId", query = "SELECT g FROM GgaGeneraldataLink g WHERE g.generaldataLinkId = :generaldataLinkId")})
public class GgaGeneraldataLink implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "GENERALDATA_LINK_ID")
    private BigDecimal generaldataLinkId;
    @JoinColumn(name = "GENERAL_DATA_ID", referencedColumnName = "GENERAL_DATA_ID")
    @ManyToOne
    private GgaGeneralData generalDataId;
    @OneToOne(mappedBy = "nextLinkId")
    private GgaGeneraldataLink ggaGeneraldataLink;
    @JoinColumn(name = "NEXT_LINK_ID", referencedColumnName = "GENERALDATA_LINK_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private GgaGeneraldataLink nextLinkId;
    @OneToOne(mappedBy = "backLinkId")
    private GgaGeneraldataLink ggaGeneraldataLink1;
    @JoinColumn(name = "BACK_LINK_ID", referencedColumnName = "GENERALDATA_LINK_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private GgaGeneraldataLink backLinkId;
    @JoinColumn(name = "LINK_ID", referencedColumnName = "LINK_ID")
    @ManyToOne
    private GgaLink linkId;
    @JoinColumn(name = "PARENT_LINK_ID", referencedColumnName = "GENERALDATA_LINK_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private GgaGeneraldataLink parentLinkId;

    public GgaGeneraldataLink() {
    }

    public GgaGeneraldataLink(BigDecimal generaldataLinkId) {
        this.generaldataLinkId = generaldataLinkId;
    }

    public BigDecimal getGeneraldataLinkId() {
        return generaldataLinkId;
    }

    public void setGeneraldataLinkId(BigDecimal generaldataLinkId) {
        this.generaldataLinkId = generaldataLinkId;
    }

    public GgaGeneralData getGeneralDataId() {
        return generalDataId;
    }

    public void setGeneralDataId(GgaGeneralData generalDataId) {
        this.generalDataId = generalDataId;
    }

    public GgaGeneraldataLink getGgaGeneraldataLink() {
        return ggaGeneraldataLink;
    }

    public void setGgaGeneraldataLink(GgaGeneraldataLink ggaGeneraldataLink) {
        this.ggaGeneraldataLink = ggaGeneraldataLink;
    }

    public GgaGeneraldataLink getNextLinkId() {
        return nextLinkId;
    }

    public void setNextLinkId(GgaGeneraldataLink nextLinkId) {
        this.nextLinkId = nextLinkId;
    }

    public GgaGeneraldataLink getGgaGeneraldataLink1() {
        return ggaGeneraldataLink1;
    }

    public void setGgaGeneraldataLink1(GgaGeneraldataLink ggaGeneraldataLink1) {
        this.ggaGeneraldataLink1 = ggaGeneraldataLink1;
    }

    public GgaGeneraldataLink getBackLinkId() {
        return backLinkId;
    }

    public void setBackLinkId(GgaGeneraldataLink backLinkId) {
        this.backLinkId = backLinkId;
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
        hash += (generaldataLinkId != null ? generaldataLinkId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GgaGeneraldataLink)) {
            return false;
        }
        GgaGeneraldataLink other = (GgaGeneraldataLink) object;
        if ((this.generaldataLinkId == null && other.generaldataLinkId != null) || (this.generaldataLinkId != null && !this.generaldataLinkId.equals(other.generaldataLinkId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.utils.gui_generator.model.GgaGeneraldataLink[ generaldataLinkId=" + generaldataLinkId + " ]";
    }

    public GgaGeneraldataLink getParentLinkId() {
        return parentLinkId;
    }

    public void setParentLinkId(GgaGeneraldataLink parentLinkId) {
        this.parentLinkId = parentLinkId;
    }

}
