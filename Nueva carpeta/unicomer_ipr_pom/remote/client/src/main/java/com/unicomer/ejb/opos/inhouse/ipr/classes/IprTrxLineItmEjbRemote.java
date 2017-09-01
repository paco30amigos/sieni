/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm;
import com.unicomer.opos.inhouse.ipr.pojos.IprProduct;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Remote;


/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprTrxLineItmEjbRemote {

	IprTrxLineItm create(IprTrxLineItm iprTrxLineItm);

    void edit(IprTrxLineItm iprTrxLineItm);

    void remove(IprTrxLineItm iprTrxLineItm);

    List<IprTrxLineItm> findAll();

    List<IprTrxLineItm> findRange(int[] range);

    int count();
    
    public void finishReadyProducts();
    
    public List<IprTrxLineItm> findItemsByStatusAndTransac(List<BigInteger> status,BigInteger transactionId);
    
    public List<IprProduct> findProductList(List<BigInteger> status, BigInteger transactionId, String locale);
    
    public List<IprProduct> findProductByStatus(List<BigInteger> status,String locale);
    
    public List<IprTrxLineItm> merge(List<IprTrxLineItm> items);
    
    List<IprTrxLineItm> findByLineItemId(List<BigInteger> lineItemIdList);
    
}
