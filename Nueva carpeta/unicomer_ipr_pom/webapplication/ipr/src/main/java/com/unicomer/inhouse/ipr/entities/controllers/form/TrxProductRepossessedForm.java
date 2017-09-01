/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.form;

import com.unicomer.inhouse.ipr.entities.controllers.model.LocationHistModel;
import com.unicomer.inhouse.ipr.entities.controllers.model.SelectedProductRepossessedModel;
import java.util.HashMap;

import com.unicomer.opos.inhouse.adminHoTh.entities.OthCurrency;
import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItmProp;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationDet;
import com.unicomer.opos.inhouse.ipr.pojos.IprProduct;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsRepossessed;
import com.unicomer.opos.inhouse.ipr.pojos.IprResponsableInfo;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class TrxProductRepossessedForm {

    //tab 1
    private SelectedProductRepossessedModel selectedProductRepossessedModel;
    private HashMap<String, IprProductsRepossessed> hash;
    private IprProductsRepossessed searchFields;
    private IprProductsRepossessed trxSelected;
    private HashMap<String, OthCurrency> currencyH;
    private LocationHistModel currentHistoryLocations;
    
    private List<IprProduct> productsToFinish;

    private String store;
    private String storeResposable;
    private String storeResposableRole;
    private String observationResponsability;

    private List<IprGenericListItem> locations;
    private List<IprGenericListItem> allLocations;
    private List<IprGenericListItem> statusProd;
    private List<IprGenericListItem> color;
    private HashMap<String, IprGenericListItem> locationsH;
    private HashMap<String, IprGenericListItem> allLocationsH;
    private HashMap<String, IprGenericListItem> statusProdH;
    private HashMap<String, IprGenericListItem> colorH;
    
    private List<IprGenericListItem> responsableLocation;
    private List<IprResponsableInfo> responsableLocationList;
    private HashMap<String,IprGenericListItem> responsableLocationH;
    
    private IprTrxLineItmProp ItemProp;
    
    private String colorSelected;
    private String statusSelected;
    private String accesories;
    private String obsservations;

    //tab 2
    private int tabIndex;
    private boolean enableTab1;
    
    private IprLocationDet locationHistSelected;

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

    public LocationHistModel getCurrentHistoryLocations() {
        return currentHistoryLocations;
    }

    public void setCurrentHistoryLocations(LocationHistModel currentHistoryLocations) {
        this.currentHistoryLocations = currentHistoryLocations;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getStoreResposable() {
        return storeResposable;
    }

    public void setStoreResposable(String storeResposable) {
        this.storeResposable = storeResposable;
    }

    public String getStoreResposableRole() {
        return storeResposableRole;
    }

    public void setStoreResposableRole(String storeResposableRole) {
        this.storeResposableRole = storeResposableRole;
    }

    public String getObservationResponsability() {
        return observationResponsability;
    }

    public void setObservationResponsability(String observationResponsability) {
        this.observationResponsability = observationResponsability;
    }

    public List<IprGenericListItem> getLocations() {
        return locations;
    }

    public void setLocations(List<IprGenericListItem> locations) {
        this.locations = locations;
    }

    public HashMap<String, IprGenericListItem> getLocationsH() {
        return locationsH;
    }

    public void setLocationsH(HashMap<String, IprGenericListItem> locationsH) {
        this.locationsH = locationsH;
    }

    public List<IprGenericListItem> getAllLocations() {
        return allLocations;
    }

    public void setAllLocations(List<IprGenericListItem> allLocations) {
        this.allLocations = allLocations;
    }

    public HashMap<String, IprGenericListItem> getAllLocationsH() {
        return allLocationsH;
    }

    public void setAllLocationsH(HashMap<String, IprGenericListItem> allLocationsH) {
        this.allLocationsH = allLocationsH;
    }

    public List<IprGenericListItem> getResponsableLocation() {
        return responsableLocation;
    }

    public void setResponsableLocation(List<IprGenericListItem> responsableLocation) {
        this.responsableLocation = responsableLocation;
    }

    public HashMap<String,IprGenericListItem> getResponsableLocationH() {
        return responsableLocationH;
    }

    public void setResponsableLocationH(HashMap<String,IprGenericListItem> responsableLocationH) {
        this.responsableLocationH = responsableLocationH;
    }

    public IprLocationDet getLocationHistSelected() {
        return locationHistSelected;
    }

    public void setLocationHistSelected(IprLocationDet locationHistSelected) {
        this.locationHistSelected = locationHistSelected;
    }

    public List<IprResponsableInfo> getResponsableLocationList() {
        return responsableLocationList;
    }

    public void setResponsableLocationList(List<IprResponsableInfo> responsableLocationList) {
        this.responsableLocationList = responsableLocationList;
    }

    public List<IprGenericListItem> getStatusProd() {
        return statusProd;
    }

    public void setStatusProd(List<IprGenericListItem> statusProd) {
        this.statusProd = statusProd;
    }

    public HashMap<String, IprGenericListItem> getStatusProdH() {
        return statusProdH;
    }

    public void setStatusProdH(HashMap<String, IprGenericListItem> statusProdH) {
        this.statusProdH = statusProdH;
    }

    public IprTrxLineItmProp getItemProp() {
        return ItemProp;
    }

    public void setItemProp(IprTrxLineItmProp ItemProp) {
        this.ItemProp = ItemProp;
    }

    public List<IprGenericListItem> getColor() {
        return color;
    }

    public void setColor(List<IprGenericListItem> color) {
        this.color = color;
    }

    public HashMap<String, IprGenericListItem> getColorH() {
        return colorH;
    }

    public void setColorH(HashMap<String, IprGenericListItem> colorH) {
        this.colorH = colorH;
    }

    public String getColorSelected() {
        return colorSelected;
    }

    public void setColorSelected(String colorSelected) {
        this.colorSelected = colorSelected;
    }

    public String getStatusSelected() {
        return statusSelected;
    }

    public void setStatusSelected(String statusSelected) {
        this.statusSelected = statusSelected;
    }

    public String getAccesories() {
        return accesories;
    }

    public void setAccesories(String accesories) {
        this.accesories = accesories;
    }

    public String getObsservations() {
        return obsservations;
    }

    public void setObsservations(String obsservations) {
        this.obsservations = obsservations;
    }

    public List<IprProduct> getProductsToFinish() {
        return productsToFinish;
    }

    public void setProductsToFinish(List<IprProduct> productsToFinish) {
        this.productsToFinish = productsToFinish;
    }

    

}
