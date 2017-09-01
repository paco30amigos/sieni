/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers;

import com.unicomer.ejb.opos.inhouse.ipr.classes.IprStatusProdEjbRemote;
import com.unicomer.hr.utility.JsfUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import com.unicomer.inhouse.ipr.entities.controllers.form.CatStatusForm;
import com.unicomer.inhouse.ipr.entities.controllers.model.CatStatusModel;
import com.unicomer.inhouse.jndi.JNDIUnicomerIpr;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthAuditoryEjbRemote;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthFixedDataEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthI18nEjbLocal;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthFixedData;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthI18n;
import com.unicomer.opos.inhouse.ipr.entities.IprStatusProd;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import java.math.BigInteger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "CatStatusController")
public class CatStatusController extends
        CatStatusForm {

    private static final Logger logger = Logger.getLogger(CatStatusController.class);

    @Resource(lookup = JNDIUnicomerOthers.OthAuditoryEjbRemote)
    private OthAuditoryEjbRemote othAuditoryEjbRemote;

    @Resource(lookup = JNDIUnicomerOthers.OthFixedDataEjbLocal)
    private OthFixedDataEjbLocal othFixedDataEjbLocal;

    @Resource(lookup = JNDIUnicomerOthers.OthI18nEjbLocal)
    private OthI18nEjbLocal othI18nEjbLocal;

    @Resource(lookup = JNDIUnicomerIpr.IprStatusProdEjbRemote)
    private IprStatusProdEjbRemote iprStatusProdEjbRemote;

    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser uniUser;

//    // Bundle de idioma
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.unicomer.inhouse.ipr.utilities.i18n.BundleIpr", FacesContext
            .getCurrentInstance().getViewRoot().getLocale());

    public BigInteger getOptionId() {
        return new BigInteger("56800");//OptionId from ADMIHSEC.UN_OPTION
    }

    public BigInteger saveAuditory(BigInteger auditId, String auditDesc, Character auditType, BigInteger optionId) {
        HashMap<String, Object> map = new HashMap<>();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        map.put("P_AUDIT_ID", auditId);
        map.put("P_USER_ID", new BigInteger(uniUser.getUserId().toString()));
        map.put("P_IP_CLIENT", ipAddress);
        map.put("P_AUDIT_DESC", auditDesc);
        map.put("P_AUDIT_TYPE", auditType);
        map.put("P_OPTION_ID", optionId);//57210

        return othAuditoryEjbRemote.auditorySave(map);
    }

    @PostConstruct
    public void init() {
        try {
            loadStatusList();
        } catch (Exception e) {
            logger.error("IPR Error: " + e.getMessage(), e);
        }
    }

    public void newStatus() {
        try {
            setActive(true);
            setStatusDesc("");
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('newStatus').show();");
        } catch (Exception e) {
            logger.error("IPR Error: " + e.getMessage(), e);
        }
    }

    public void editStatus() {
        try {
            if (getStatusSelected() != null) {
                setActive(getStatusSelected().getIsActive() != null && getStatusSelected().getIsActive() == 'Y');
                setStatusDesc(getStatusSelected().getStatusDesc());
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('editStatus').show();");
            } else {
                //seleccione una ubicacion
                String msg = rb.getString("ui_msg_errorSelectStatus");
            JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorLoadingStatusInfo");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void saveEditStatus() {
        try {
            if (validateEdit()) {
                IprStatusProd editStatus = getStatusSelected();
                editStatus.setIsActive(isActive() ? 'Y' : 'N');
                editStatus.setStatusDesc(getStatusDesc());
                saveAuditory(editStatus.getAuditId(), "UPDATE STATUS PROD", 'U', getOptionId());
                iprStatusProdEjbRemote.edit(editStatus);
                loadStatusList();
                String msg = rb.getString("ui_msg_statusUpdatedSuccessfuly");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingEditStatus");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void saveNewStatus() {
        try {
            if (validateSave()) {
                IprStatusProd newStatus = new IprStatusProd();
                newStatus.setIsActive(isActive() ? 'Y' : 'N');
                newStatus.setStatusDesc(getStatusDesc());
                BigInteger auditId = saveAuditory(newStatus.getAuditId(), "CRETE STATUS PROD", 'C', getOptionId());
                newStatus.setAuditId(auditId);
                iprStatusProdEjbRemote.create(newStatus);
                loadStatusList();
                String msg = rb.getString("ui_msg_statusRegisteredSuccessfuly");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingNewStatus");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public boolean validateEdit() {
        boolean ret = true;
        return ret;
    }

    public boolean validateSave() {
        boolean ret = true;
        return ret;
    }

    public void loadStatusList() {
        HashMap<String, IprGenericListItem> hash = new HashMap<>();
        setStatusH(new HashMap<String, IprStatusProd>());
        List<IprGenericListItem> yn = getYesNoToPojo(hash);
        List<IprStatusProd> statusList = iprStatusProdEjbRemote.findAllOrdered();
        for (IprStatusProd actual : statusList) {
            Character key = actual.getIsActive();//search for Y or N value
            IprGenericListItem value = hash.get(key != null ? key.toString() : "");
            if (value != null) {
                actual.setActive(value.getLabel());
            } else {
                actual.setActive("");
            }
            getStatusH().put(actual.getStatusProdId().toString(), actual);
        }
        setStatusModel(new CatStatusModel(statusList,"getStatusProdId"));
//        getStatusModel().setHash(getStatusH());
    }

    public List<IprGenericListItem> getYesNoToPojo(HashMap<String, IprGenericListItem> hash) {
        return getI18n("IPR_YES_NO", hash);
    }

    public List<IprGenericListItem> getI18n(String groupName, HashMap<String, IprGenericListItem> hash) {
        List<IprGenericListItem> ret = new ArrayList();
        IprGenericListItem nuevo;
        List<OthFixedData> fixedData;
        fixedData = othFixedDataEjbLocal.findByGroupNameCountry(groupName, uniUser.getCurrentCountry().getCountryId());
        if (fixedData != null && !fixedData.isEmpty()) {
            for (OthFixedData actual : fixedData) {
                nuevo = new IprGenericListItem();
                OthI18n label = othI18nEjbLocal.findByRef("com.unicomer.opos.inhouse.adminHoTh.entities.OthFixedData", actual.getOthFixedDataId(), FacesContext
                        .getCurrentInstance().getViewRoot().getLocale().getLanguage());
                nuevo.setLabel(label.getLabel());
                nuevo.setSelected(actual);
                nuevo.setValue(actual.getValue());
                hash.put(actual.getValue(), nuevo);
                ret.add(nuevo);
            }
        }
        return ret;
    }

    // getter and setter session variables 
    public UnUser getUniUser() {
        return uniUser;
    }

    public void setUniUser(UnUser uniUser) {
        this.uniUser = uniUser;
    }

}
