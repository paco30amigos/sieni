/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprBatchDet;
import java.util.List;
import javax.ejb.Remote;


/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprBatchDetEjbRemote {

	IprBatchDet create(IprBatchDet iprBatchDet);

    void edit(IprBatchDet iprBatchDet);

    void remove(IprBatchDet iprBatchDet);

    List<IprBatchDet> findAll();

    List<IprBatchDet> findRange(int[] range);

    int count();
    
}
