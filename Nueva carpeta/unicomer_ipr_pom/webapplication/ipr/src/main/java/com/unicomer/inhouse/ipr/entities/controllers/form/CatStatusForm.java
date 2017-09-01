/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.form;

import java.util.HashMap;

import com.unicomer.inhouse.ipr.entities.controllers.model.CatStatusModel;
import com.unicomer.opos.inhouse.ipr.entities.IprStatusProd;
import java.io.Serializable;

/**
 *
 * @author francisco_medina
 */
public class CatStatusForm implements Serializable{

    private String statusDesc;
    private boolean active;

    private CatStatusModel statusModel;
    private HashMap<String, IprStatusProd> statusH;

    private IprStatusProd statusSelected;

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public IprStatusProd getStatusSelected() {
        return statusSelected;
    }

    public void setStatusSelected(IprStatusProd statusSelected) {
        this.statusSelected = statusSelected;
    }

    public HashMap<String, IprStatusProd> getStatusH() {
        return statusH;
    }

    public void setStatusH(HashMap<String, IprStatusProd> statusH) {
        this.statusH = statusH;
    }

    public CatStatusModel getStatusModel() {
        return statusModel;
    }

    public void setStatusModel(CatStatusModel statusModel) {
        this.statusModel = statusModel;
    }
}
