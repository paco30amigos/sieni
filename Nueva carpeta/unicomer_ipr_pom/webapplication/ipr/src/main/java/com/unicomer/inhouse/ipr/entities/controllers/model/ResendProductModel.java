/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.model;

import com.unicomer.inhouse.ipr.entities.controllers.pojo.GenericHashListDataModel;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsResend;
import java.util.List;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author francisco_medina
 */
public class ResendProductModel extends GenericHashListDataModel<IprProductsResend> implements SelectableDataModel<IprProductsResend> {

    public ResendProductModel(List<IprProductsResend> list) {
        super(IprProductsResend.class, list);
    }

    public ResendProductModel(List<IprProductsResend> list, String methodGetIdName) {
        super(IprProductsResend.class, list, methodGetIdName);
    }
}
