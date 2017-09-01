package com.unicomer.inhouse.ipr.entities.controllers.model;

import com.unicomer.inhouse.ipr.entities.controllers.pojo.GenericHashListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.unicomer.opos.inhouse.ipr.entities.IprStatusProd;
import java.util.List;

public class CatStatusModel extends GenericHashListDataModel<IprStatusProd> implements SelectableDataModel<IprStatusProd> {

    public CatStatusModel(List<IprStatusProd> list) {
        super(IprStatusProd.class, list);
    }

    public CatStatusModel(List<IprStatusProd> list, String methodGetIdName) {
        super(IprStatusProd.class, list, methodGetIdName);
    }
}
