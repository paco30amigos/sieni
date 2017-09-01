/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.form;

import com.unicomer.inhouse.ipr.entities.controllers.model.ResendProductModel;
import java.util.HashMap;

import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsResend;
import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class ResendProductsForm {

    //tab 1
    private ResendProductModel resendProductModel;
    private HashMap<String, IprProductsResend> hash;
    private IprProductsResend searchFields;
    private List<IprProductsResend> trxSelected;
    private List<IprGenericListItem> statusList;

    //tab 2
    private int tabIndex;
    private boolean enableTab1;

    public ResendProductModel getResendProductModel() {
        return resendProductModel;
    }

    public void setResendProductModel(ResendProductModel resendProductModel) {
        this.resendProductModel = resendProductModel;
    }

    public HashMap<String, IprProductsResend> getHash() {
        return hash;
    }

    public void setHash(HashMap<String, IprProductsResend> hash) {
        this.hash = hash;
    }

    public IprProductsResend getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(IprProductsResend searchFields) {
        this.searchFields = searchFields;
    }

    public List<IprProductsResend> getTrxSelected() {
        return trxSelected;
    }

    public void setTrxSelected(List<IprProductsResend> trxSelected) {
        this.trxSelected = trxSelected;
    }

   

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }

    public boolean isEnableTab1() {
        return enableTab1;
    }

    public void setEnableTab1(boolean enableTab1) {
        this.enableTab1 = enableTab1;
    }

    public List<IprGenericListItem> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<IprGenericListItem> statusList) {
        this.statusList = statusList;
    }

}
