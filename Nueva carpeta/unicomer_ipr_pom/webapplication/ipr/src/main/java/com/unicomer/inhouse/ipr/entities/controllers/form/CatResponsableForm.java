/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers.form;

import com.unicomer.inhouse.ipr.entities.controllers.model.CatResponsableModel;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.ipr.pojos.IprResponsable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class CatResponsableForm {

    private CatResponsableModel respModel;
    
    private HashMap<String, IprResponsable> respH;
    
    private IprResponsable responsableSelected;
    
    private String respType;
    private String respName;
    private BigInteger respCode;
    private String observationResponsability;
    private boolean isActive;
    
    private HashMap<String, IprGenericListItem> roleTypesH;
    private List<IprGenericListItem> roleTypesList;

    public String getRespType() {
        return respType;
    }

    public void setRespType(String respType) {
        this.respType = respType;
    }

    public String getRespName() {
        return respName;
    }

    public void setRespName(String respName) {
        this.respName = respName;
    }

    public String getObservationResponsability() {
        return observationResponsability;
    }

    public void setObservationResponsability(String observationResponsability) {
        this.observationResponsability = observationResponsability;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<IprGenericListItem> getRoleTypesList() {
        return roleTypesList;
    }

    public void setRoleTypesList(List<IprGenericListItem> roleTypesList) {
        this.roleTypesList = roleTypesList;
    }


    public CatResponsableModel getRespModel() {
		return respModel;
	}

	public void setRespModel(CatResponsableModel respModel) {
		this.respModel = respModel;
	}

	public HashMap<String, IprGenericListItem> getRoleTypesH() {
        return roleTypesH;
    }

    public void setRoleTypesH(HashMap<String, IprGenericListItem> roleTypesH) {
        this.roleTypesH = roleTypesH;
    }

    public IprResponsable getResponsableSelected() {
        return responsableSelected;
    }

    public void setResponsableSelected(IprResponsable responsableSelected) {
        this.responsableSelected = responsableSelected;
    }

    public BigInteger getRespCode() {
        return respCode;
    }

    public void setRespCode(BigInteger respCode) {
        this.respCode = respCode;
    }

	public HashMap<String, IprResponsable> getRespH() {
		return respH;
	}

	public void setRespH(HashMap<String, IprResponsable> respH) {
		this.respH = respH;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
