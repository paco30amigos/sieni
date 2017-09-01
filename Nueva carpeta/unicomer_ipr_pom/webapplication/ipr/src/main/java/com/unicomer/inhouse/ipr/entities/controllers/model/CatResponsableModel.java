package com.unicomer.inhouse.ipr.entities.controllers.model;

import com.unicomer.inhouse.ipr.entities.controllers.pojo.GenericHashListDataModel;
import java.util.List;


import org.primefaces.model.SelectableDataModel;

import com.unicomer.opos.inhouse.ipr.pojos.IprResponsable;

public class CatResponsableModel extends GenericHashListDataModel<IprResponsable> implements SelectableDataModel<IprResponsable> {

    public CatResponsableModel(List<IprResponsable> list) {
        super(IprResponsable.class, list);
    }

    public CatResponsableModel(List<IprResponsable> list, String methodGetIdName) {
        super(IprResponsable.class, list, methodGetIdName);
    }
}
