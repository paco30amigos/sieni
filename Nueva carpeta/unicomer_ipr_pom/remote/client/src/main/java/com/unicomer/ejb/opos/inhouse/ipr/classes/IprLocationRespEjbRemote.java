/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprLocationResp;
import com.unicomer.opos.inhouse.security.entities.UnRetailStore;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprLocationRespEjbRemote {

    IprLocationResp create(IprLocationResp iprLocationResp);

    void edit(IprLocationResp iprLocationResp);

    void remove(IprLocationResp iprLocationResp);

    List<IprLocationResp> findAll();

    List<IprLocationResp> findRange(int[] range);

    int count();

    public List<UnRetailStore> findStoresConfigured(String countryCode);

    public IprLocationResp findId(BigInteger id);

    boolean updateLocationResponsableDefault(String storeId, String respId, Character respType);

}
