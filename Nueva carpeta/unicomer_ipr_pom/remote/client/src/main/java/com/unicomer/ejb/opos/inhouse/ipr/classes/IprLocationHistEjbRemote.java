/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;

import com.unicomer.opos.inhouse.ipr.entities.IprLocationHist;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationDet;
import com.unicomer.opos.inhouse.ipr.pojos.IprResponsableInfo;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprLocationHistEjbRemote {

    IprLocationHist create(IprLocationHist iprLocationHist);

    void edit(IprLocationHist iprLocationHist);

    void remove(IprLocationHist iprLocationHist);

    List<IprLocationHist> findAll();

    List<IprLocationHist> findRange(int[] range);

    int count();

    List<IprLocationDet> getLastLocationByTrxLineItm(BigInteger trxLineItmId);

    public List<IprLocationDet> getAllLocationByTrxLineItm(BigInteger lineItemId, String locale);

    public List<IprResponsableInfo> getAllResponsableByStore(BigInteger storeId, String locale);

    List<IprLocationDet> getFirstLocationByIprTrx(BigInteger iprTrxId);
    
    public IprLocationHist findById(BigInteger id);

}
