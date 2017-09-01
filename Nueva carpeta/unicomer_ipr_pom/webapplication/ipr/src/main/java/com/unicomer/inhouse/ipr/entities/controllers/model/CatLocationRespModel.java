package com.unicomer.inhouse.ipr.entities.controllers.model;

import com.unicomer.inhouse.ipr.entities.controllers.pojo.GenericHashListDataModel;
import java.util.List;

import org.primefaces.model.SelectableDataModel;

import com.unicomer.opos.inhouse.ipr.pojos.IprLocationRespPojo;

public class CatLocationRespModel extends GenericHashListDataModel<IprLocationRespPojo> implements SelectableDataModel<IprLocationRespPojo> {

    public CatLocationRespModel(List<IprLocationRespPojo> list) {
        super(IprLocationRespPojo.class, list);
    }

    public CatLocationRespModel(List<IprLocationRespPojo> list, String methodGetIdName) {
        super(IprLocationRespPojo.class, list, methodGetIdName);
    }
}
