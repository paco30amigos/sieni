/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprStatus;
import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprStatusEjbRemote {

	IprStatus create(IprStatus iprStatus);

    void edit(IprStatus iprStatus);

    void remove(IprStatus iprStatus);

    List<IprStatus> findAll();

    List<IprStatus> findRange(int[] range);

    int count();
    
}
