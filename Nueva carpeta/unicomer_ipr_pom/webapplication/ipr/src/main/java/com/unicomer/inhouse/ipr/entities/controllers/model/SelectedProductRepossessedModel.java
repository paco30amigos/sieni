/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.model;

import com.unicomer.inhouse.ipr.entities.controllers.pojo.GenericHashListDataModel;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsRepossessed;
import java.util.List;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author francisco_medina
 */
public class SelectedProductRepossessedModel extends GenericHashListDataModel<IprProductsRepossessed> implements SelectableDataModel<IprProductsRepossessed> {

    public SelectedProductRepossessedModel(List<IprProductsRepossessed> list) {
        super(IprProductsRepossessed.class, list);
    }

    public SelectedProductRepossessedModel(List<IprProductsRepossessed> list, String methodGetIdName) {
        super(IprProductsRepossessed.class, list, methodGetIdName);
    }
}
