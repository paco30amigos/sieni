/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprStatusProd;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprStatusProdEjbRemote {

    IprStatusProd create(IprStatusProd iprStatusProd);

    void edit(IprStatusProd iprStatusProd);

    void remove(IprStatusProd iprStatusProd);

    List<IprStatusProd> findAll();

    List<IprStatusProd> findRange(int[] range);

    int count();

    public List<IprStatusProd> findAllOrdered();

    public List<IprStatusProd> findActiveOrdered();

}
