/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.form;

import com.unicomer.inhouse.ipr.entities.controllers.model.SelectedProductRepossessedModel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

import com.unicomer.opos.inhouse.adminHoTh.entities.OthCurrency;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsRepossessed;

/**
 *
 * @author francisco_medina
 */
public class SelectedProductRepossessedForm {

    //tab 1
    private SelectedProductRepossessedModel selectedProductRepossessedModel;
    private HashMap<String, IprProductsRepossessed> hash;
    private IprProductsRepossessed searchFields;
    private IprProductsRepossessed trxSelected;
    private HashMap<String, OthCurrency> currencyH;

    //tab 2
    private int tabIndex;
    private boolean enableTab1;

    public SelectedProductRepossessedModel getSelectedProductRepossessedModel() {
        return selectedProductRepossessedModel;
    }

    public void setSelectedProductRepossessedModel(SelectedProductRepossessedModel selectedProductRepossessedModel) {
        this.selectedProductRepossessedModel = selectedProductRepossessedModel;
    }

    public HashMap<String, IprProductsRepossessed> getHash() {
        return hash;
    }

    public void setHash(HashMap<String, IprProductsRepossessed> hash) {
        this.hash = hash;
    }

    public IprProductsRepossessed getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(IprProductsRepossessed search) {
        this.searchFields = search;
    }

    public IprProductsRepossessed getTrxSelected() {
        return trxSelected;
    }

    public void setTrxSelected(IprProductsRepossessed trxSelected) {
        this.trxSelected = trxSelected;
    }

    public HashMap<String, OthCurrency> getCurrencyH() {
        return currencyH;
    }

    public void setCurrencyH(HashMap<String, OthCurrency> currencyH) {
        this.currencyH = currencyH;
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

    
}
