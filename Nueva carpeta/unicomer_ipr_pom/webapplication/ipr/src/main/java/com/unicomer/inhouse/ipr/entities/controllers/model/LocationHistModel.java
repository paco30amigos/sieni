/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.model;

import com.unicomer.inhouse.ipr.entities.controllers.pojo.GenericHashListDataModel;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationDet;
import java.util.List;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author francisco_medina
 */
public class LocationHistModel extends GenericHashListDataModel<IprLocationDet> implements SelectableDataModel<IprLocationDet> {
    
    public LocationHistModel(List<IprLocationDet> list) {
        super(IprLocationDet.class, list);
    }

    public LocationHistModel(List<IprLocationDet> list, String methodGetIdName) {
        super(IprLocationDet.class, list, methodGetIdName);
    }
}
