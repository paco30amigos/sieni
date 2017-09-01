/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItmProp;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprTrxLineItmPropEjbRemote {

    IprTrxLineItmProp create(IprTrxLineItmProp iprTrxLineItmProp);

    void edit(IprTrxLineItmProp iprTrxLineItmProp);

    void remove(IprTrxLineItmProp iprTrxLineItmProp);

    List<IprTrxLineItmProp> findAll();

    List<IprTrxLineItmProp> findRange(int[] range);

    int count();

    public IprTrxLineItmProp findByLineItemId(BigInteger lineItemId);

}
