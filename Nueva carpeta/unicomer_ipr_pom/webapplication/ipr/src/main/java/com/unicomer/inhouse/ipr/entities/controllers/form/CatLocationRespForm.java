/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.unicomer.inhouse.ipr.entities.controllers.model.CatLocationRespModel;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationRespPojo;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;

/**
 *
 * @author francisco_medina
 */
public class CatLocationRespForm {

    //tab 1
    private List<IprGenericListItem> locations;
    private List<IprGenericListItem> allLocations;
    private List<IprGenericListItem> responsableTypes;
    private List<IprGenericListItem> responsables;
    private List<IprGenericListItem> responsableRoles;

    private HashMap<String, IprGenericListItem> locationsH;
    private HashMap<String, IprGenericListItem> allLocationsH;
    private HashMap<String, IprGenericListItem> responsableTypesH;
    private HashMap<String, IprGenericListItem> responsablesH;
    private HashMap<String, IprGenericListItem> responsableRolesH;
    private HashMap<String, IprLocationRespPojo> locationRespH;

    List<IprGenericListItem> responsableListD = new ArrayList<>();
    List<IprGenericListItem> responsableListW = new ArrayList<>();
    List<IprGenericListItem> responsableListE = new ArrayList<>();

    List<IprGenericListItem> responsableRoleListD = new ArrayList<>();
    List<IprGenericListItem> responsableRoleListW = new ArrayList<>();
    List<IprGenericListItem> responsableRoleListE = new ArrayList<>();

    HashMap<String, IprGenericListItem> responsableD = new HashMap<>();
    HashMap<String, IprGenericListItem> responsableW = new HashMap<>();
    HashMap<String, IprGenericListItem> responsableE = new HashMap<>();

    HashMap<String, IprGenericListItem> responsableRoleD = new HashMap<>();
    HashMap<String, IprGenericListItem> responsableRoleW = new HashMap<>();
    HashMap<String, IprGenericListItem> responsableRoleE = new HashMap<>();

    private CatLocationRespModel locationResp;

    private String locationSelected;
    private String responsableTypeSelected;
    private String responsableSelected;
    private String responsableRoleSelected;
    private IprLocationRespPojo locationRespSelected;
    private String observationResponsability;
    private boolean active;
    private Character activeVal;
    private boolean defaultResponsable;

    public List<IprGenericListItem> getResponsableListD() {
        return responsableListD;
    }

    public void setResponsableListD(List<IprGenericListItem> responsableListD) {
        this.responsableListD = responsableListD;
    }

    public List<IprGenericListItem> getResponsableListW() {
        return responsableListW;
    }

    public void setResponsableListW(List<IprGenericListItem> responsableListW) {
        this.responsableListW = responsableListW;
    }

    public List<IprGenericListItem> getResponsableListE() {
        return responsableListE;
    }

    public void setResponsableListE(List<IprGenericListItem> responsableListE) {
        this.responsableListE = responsableListE;
    }

    public List<IprGenericListItem> getResponsableRoleListD() {
        return responsableRoleListD;
    }

    public void setResponsableRoleListD(
            List<IprGenericListItem> responsableRoleListD) {
        this.responsableRoleListD = responsableRoleListD;
    }

    public List<IprGenericListItem> getResponsableRoleListW() {
        return responsableRoleListW;
    }

    public void setResponsableRoleListW(
            List<IprGenericListItem> responsableRoleListW) {
        this.responsableRoleListW = responsableRoleListW;
    }

    public List<IprGenericListItem> getResponsableRoleListE() {
        return responsableRoleListE;
    }

    public void setResponsableRoleListE(
            List<IprGenericListItem> responsableRoleListE) {
        this.responsableRoleListE = responsableRoleListE;
    }

    public HashMap<String, IprGenericListItem> getResponsableD() {
        return responsableD;
    }

    public void setResponsableD(HashMap<String, IprGenericListItem> responsableD) {
        this.responsableD = responsableD;
    }

    public HashMap<String, IprGenericListItem> getResponsableW() {
        return responsableW;
    }

    public void setResponsableW(HashMap<String, IprGenericListItem> responsableW) {
        this.responsableW = responsableW;
    }

    public HashMap<String, IprGenericListItem> getResponsableE() {
        return responsableE;
    }

    public void setResponsableE(HashMap<String, IprGenericListItem> responsableE) {
        this.responsableE = responsableE;
    }

    public HashMap<String, IprGenericListItem> getResponsableRoleD() {
        return responsableRoleD;
    }

    public void setResponsableRoleD(
            HashMap<String, IprGenericListItem> responsableRoleD) {
        this.responsableRoleD = responsableRoleD;
    }

    public HashMap<String, IprGenericListItem> getResponsableRoleW() {
        return responsableRoleW;
    }

    public void setResponsableRoleW(
            HashMap<String, IprGenericListItem> responsableRoleW) {
        this.responsableRoleW = responsableRoleW;
    }

    public HashMap<String, IprGenericListItem> getResponsableRoleE() {
        return responsableRoleE;
    }

    public void setResponsableRoleE(
            HashMap<String, IprGenericListItem> responsableRoleE) {
        this.responsableRoleE = responsableRoleE;
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

    public List<IprGenericListItem> getLocations() {
        return locations;
    }

    public void setLocations(List<IprGenericListItem> locations) {
        this.locations = locations;
    }

    public List<IprGenericListItem> getResponsableTypes() {
        return responsableTypes;
    }

    public void setResponsableTypes(List<IprGenericListItem> responsableTypes) {
        this.responsableTypes = responsableTypes;
    }

    public List<IprGenericListItem> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<IprGenericListItem> responsables) {
        this.responsables = responsables;
    }

    public List<IprGenericListItem> getResponsableRoles() {
        return responsableRoles;
    }

    public void setResponsableRoles(List<IprGenericListItem> responsableRoles) {
        this.responsableRoles = responsableRoles;
    }

    public HashMap<String, IprGenericListItem> getLocationsH() {
        return locationsH;
    }

    public void setLocationsH(HashMap<String, IprGenericListItem> locationsH) {
        this.locationsH = locationsH;
    }

    public HashMap<String, IprGenericListItem> getResponsableTypesH() {
        return responsableTypesH;
    }

    public void setResponsableTypesH(HashMap<String, IprGenericListItem> responsableTypesH) {
        this.responsableTypesH = responsableTypesH;
    }

    public HashMap<String, IprGenericListItem> getResponsablesH() {
        return responsablesH;
    }

    public void setResponsablesH(HashMap<String, IprGenericListItem> responsablesH) {
        this.responsablesH = responsablesH;
    }

    public HashMap<String, IprGenericListItem> getResponsableRolesH() {
        return responsableRolesH;
    }

    public void setResponsableRolesH(HashMap<String, IprGenericListItem> responsableRolesH) {
        this.responsableRolesH = responsableRolesH;
    }

    public HashMap<String, IprLocationRespPojo> getLocationRespH() {
        return locationRespH;
    }

    public void setLocationRespH(HashMap<String, IprLocationRespPojo> locationRespH) {
        this.locationRespH = locationRespH;
    }

    public CatLocationRespModel getLocationResp() {
        return locationResp;
    }

    public void setLocationResp(CatLocationRespModel locationResp) {
        this.locationResp = locationResp;
    }

    public String getLocationSelected() {
        return locationSelected;
    }

    public void setLocationSelected(String locationSelected) {
        this.locationSelected = locationSelected;
    }

    public String getResponsableTypeSelected() {
        return responsableTypeSelected;
    }

    public void setResponsableTypeSelected(String responsableTypeSelected) {
        this.responsableTypeSelected = responsableTypeSelected;
    }

    public String getResponsableSelected() {
        return responsableSelected;
    }

    public void setResponsableSelected(String responsableSelected) {
        this.responsableSelected = responsableSelected;
    }

    public String getResponsableRoleSelected() {
        return responsableRoleSelected;
    }

    public void setResponsableRoleSelected(String responsableRoleSelected) {
        this.responsableRoleSelected = responsableRoleSelected;
    }

    public IprLocationRespPojo getLocationRespSelected() {
        return locationRespSelected;
    }

    public void setLocationRespSelected(IprLocationRespPojo locationRespSelected) {
        this.locationRespSelected = locationRespSelected;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDefaultResponsable() {
        return defaultResponsable;
    }

    public void setDefaultResponsable(boolean defaultResponsable) {
        this.defaultResponsable = defaultResponsable;
    }

    public String getObservationResponsability() {
        return observationResponsability;
    }

    public void setObservationResponsability(String observationResponsability) {
        this.observationResponsability = observationResponsability;
    }

    public Character getActiveVal() {
        return activeVal;
    }

    public void setActiveVal(Character activeVal) {
        this.activeVal = activeVal;
    }

}
