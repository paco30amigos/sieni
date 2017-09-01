/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprTransaction;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsResend;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprTransactionEjbRemote {

    IprTransaction create(IprTransaction iprTransaction);

    void edit(IprTransaction iprTransaction);

    void remove(IprTransaction iprTransaction);

    List<IprTransaction> findAll();

    List<IprTransaction> findRange(int[] range);

    int count();

    List<IprTransaction> findByFilters(HashMap<String, Object> params);

    List<IprProductsResend> findProductSendByFilters(HashMap<String, Object> params);

}
