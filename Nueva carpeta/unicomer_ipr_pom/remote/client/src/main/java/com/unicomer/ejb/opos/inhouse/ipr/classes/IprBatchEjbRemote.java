/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.ejb.opos.inhouse.ipr.classes;

import com.unicomer.opos.inhouse.ipr.entities.IprBatch;
import java.util.List;
import javax.ejb.Remote;
/**
 *
 * @author francisco_medina
 */
@Remote
public interface IprBatchEjbRemote {

	IprBatch create(IprBatch iprBatch);

    void edit(IprBatch iprBatch);

    void remove(IprBatch iprBatch);

    List<IprBatch> findAll();

    List<IprBatch> findRange(int[] range);

    int count();
    
}
