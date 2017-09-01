/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers;

import com.unicomer.hr.utility.JsfUtil;
import java.math.BigInteger;
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

import com.unicomer.inhouse.ipr.entities.controllers.form.CatResponsableForm;
import com.unicomer.inhouse.ipr.entities.controllers.model.CatResponsableModel;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthAuditoryEjbRemote;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthDriverEjbRemote;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthFixedDataEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthI18nEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthWarehouseRespEjbRemote;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthDriver;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthFixedData;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthI18n;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthWarehouseResp;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.ipr.pojos.IprResponsable;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "CatResponsableController")
public class CatResponsableController extends
        CatResponsableForm {

    private static final Logger logger = Logger.getLogger(CatResponsableController.class);

    @Resource(lookup = JNDIUnicomerOthers.OthAuditoryEjbRemote)
    private OthAuditoryEjbRemote othAuditoryEjbRemote;

    @Resource(lookup = JNDIUnicomerOthers.OthFixedDataEjbLocal)
    private OthFixedDataEjbLocal othFixedDataEjbLocal;

    @Resource(lookup = JNDIUnicomerOthers.OthI18nEjbLocal)
    private OthI18nEjbLocal othI18nEjbLocal;

    @Resource(lookup = JNDIUnicomerOthers.OthDriverEjbLocal)
    private OthDriverEjbRemote othDriverEjbRemote;

    @Resource(lookup = JNDIUnicomerOthers.OthWarehouseResp)
    private OthWarehouseRespEjbRemote othWarehouseRespEjbRemote;

    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser uniUser;

    // Bundle de idioma
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.unicomer.inhouse.ipr.utilities.i18n.BundleIpr", FacesContext
            .getCurrentInstance().getViewRoot().getLocale());

    public BigInteger getOptionId() {
        return new BigInteger("56820");//OptionId from ADMIHSEC.UN_OPTION
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
            prepareHashAndList();
            loadResponsableList();
        } catch (Exception e) {
            logger.error("IPR Error: " + e.getMessage(), e);
        }
    }

    public void newResponsable() {

        //init blank variables
        setIsActive(true);
        setObservationResponsability("");
        setRespName("");
        setRespType(getRoleTypesList().get(0).getValue());//set first responsable type
        setRespCode(null);

        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('newResponsable').show();");

    }

    public void editResponsable() {
        try {
            if (getResponsableSelected() != null) {
                setIsActive(getResponsableSelected().getIsActive() != null && getResponsableSelected().getIsActive() == 'Y');
                setObservationResponsability(getResponsableSelected().getObservationResponsability());
                setRespName(getResponsableSelected().getName());
                setRespType(getResponsableSelected().getType());
                setRespCode(getResponsableSelected().getCode() != null&&!getResponsableSelected().getCode().isEmpty() ? new BigInteger(getResponsableSelected().getCode()) : null);
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('editResponsable').show();");
            } else {
                //seleccione una ubicacion
                String msg = rb.getString("ui_msg_errorSelectLocation");
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorLoadingResponsableData");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void saveEditResponsable() {
        try {
            OthDriver driver;
            OthWarehouseResp wrhr;
            if (validateEdit()) {
                switch (getRespType()) {
                    case "D":
                        driver = getResponsableSelected().getRespD();
                        driver.setIsActive(isIsActive() ? 'Y' : 'N');
                        driver.setDriverName(getRespName());
                        driver.setDriverCode(getRespCode());
                        driver.setDriverObserv(getObservationResponsability());
                        saveAuditory(driver.getAuditId(), "UPDATE RESPONSABLE", 'U', getOptionId());
                        othDriverEjbRemote.edit(driver);
                        break;
                    case "W":
                        wrhr = getResponsableSelected().getRespW();
                        wrhr.setIsActive(isIsActive() ? 'Y' : 'N');
                        wrhr.setWrhrName(getRespName());
                        wrhr.setWrhrCode(getRespCode());
                        wrhr.setWrhrObserv(getObservationResponsability());
                        saveAuditory(wrhr.getAuditId(), "UPDATE RESPONSABLE", 'U', getOptionId());
                        othWarehouseRespEjbRemote.edit(wrhr);
                        break;
                }
                String msg = rb.getString("ui_msg_responsableEditedSuccessfuly");
                JsfUtil.addSuccessMessage(msg);
            }
            loadResponsableList();
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingEditResp");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void saveNewResponsable() {
        try {
            OthDriver driver;
            OthWarehouseResp wrhr;
            BigInteger auditId;
            if (validateSave()) {
                switch (getRespType()) {
                    case "D":
                        driver = new OthDriver();
                        driver.setIsActive(isIsActive() ? 'Y' : 'N');
                        driver.setDriverName(getRespName());
                        driver.setDriverCode(getRespCode());
                        driver.setDriverObserv(getObservationResponsability());
                        driver.setCountryCode(uniUser.getCurrentCountry().getCountryCode());
                        auditId = saveAuditory(driver.getAuditId(), "CREATE RESPONSABLE", 'C', getOptionId());
                        driver.setAuditId(auditId);
                        othDriverEjbRemote.create(driver);
                        break;
                    case "W":
                        wrhr = new OthWarehouseResp();
                        wrhr.setIsActive(isIsActive() ? 'Y' : 'N');
                        wrhr.setWrhrName(getRespName());
                        wrhr.setWrhrCode(getRespCode());
                        wrhr.setWrhrObserv(getObservationResponsability());
                        wrhr.setCountryCode(uniUser.getCurrentCountry().getCountryCode());
                        auditId = saveAuditory(wrhr.getAuditId(), "CREATE RESPONSABLE", 'C', getOptionId());
                        wrhr.setAuditId(auditId);
                        othWarehouseRespEjbRemote.create(wrhr);
                        break;
                }
                loadResponsableList();
                String msg = rb.getString("ui_msg_responsableRegisteredSuccessfuly");
                JsfUtil.addSuccessMessage(msg);

            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingNewResp");
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

    public void prepareHashAndList() {
        //init hash values        
        setRoleTypesH(new HashMap<String, IprGenericListItem>());
        setRoleTypesList(getResponsableTypesToPojo(getRoleTypesH()));

    }

    public void loadResponsableList() {

        HashMap<String, IprGenericListItem> hash = new HashMap<>();
        List<IprResponsable> r = new ArrayList<>();
        setRespH(new HashMap<String, IprResponsable>());
        IprResponsable nuevo;
        getYesNoToPojo(hash);
        prepareHashAndList();
        List<OthDriver> drivers = othDriverEjbRemote.findByCountry(uniUser.getCurrentCountry().getCountryCode());
        for (OthDriver actual : drivers) {
            nuevo = new IprResponsable();
            StringBuffer id = new StringBuffer();
            id.append("D");
            id.append(actual.getDriverId());
            nuevo.setId(id.toString());
            nuevo.setType("D");
            nuevo.setTypeLabel(getRoleTypesH().get("D").getLabel());//get from hash of responsable type
            nuevo.setCode(actual.getDriverCode() != null ? actual.getDriverCode().toString() : "");
            nuevo.setName(actual.getDriverName());
            nuevo.setObservationResponsability(actual.getDriverObserv());
            nuevo.setRespD(actual);
            nuevo.setIsActive(actual.getIsActive());

            String key = actual.getIsActive().toString();//search for Y or N value
            IprGenericListItem value = hash.get(key);
            if (value != null) {
                nuevo.setActive(value.getLabel());
            } else {
                nuevo.setActive("");
            }
            r.add(nuevo);
            getRespH().put(nuevo.getId(), nuevo);
        }

        List<OthWarehouseResp> wrhr = othWarehouseRespEjbRemote.findByCountry(uniUser.getCurrentCountry().getCountryCode());
        for (OthWarehouseResp actual : wrhr) {
            nuevo = new IprResponsable();
            StringBuffer id = new StringBuffer();
            id.append("W");
            id.append(actual.getWrhrId());
            nuevo.setId(id.toString());
            nuevo.setType("W");
            nuevo.setTypeLabel(getRoleTypesH().get("W").getLabel());//get from hash of responsable type
            nuevo.setCode(actual.getWrhrCode().toString());
            nuevo.setName(actual.getWrhrName());
            nuevo.setObservationResponsability(actual.getWrhrObserv());
            nuevo.setRespW(actual);
            nuevo.setIsActive(actual.getIsActive());

            String key = actual.getIsActive().toString();//search for Y or N value
            IprGenericListItem value = hash.get(key);
            if (value != null) {
                nuevo.setActive(value.getLabel());
            } else {
                nuevo.setActive("");
            }
            r.add(nuevo);
//            getRespH().put(nuevo.getId(), nuevo);
        }
        setRespModel(new CatResponsableModel(r,"getId"));
//        getRespModel().setHash(getRespH());
    }

    public List<IprGenericListItem> getYesNoToPojo(HashMap<String, IprGenericListItem> hash) {
        return getI18n("IPR_YES_NO", hash);
    }

    public List<IprGenericListItem> getResponsableTypesToPojo(HashMap<String, IprGenericListItem> roleTypeRet) {
        List<IprGenericListItem> ret = getI18n("IPR_RESPONSABLE_TYPE", roleTypeRet);
        IprGenericListItem exludeEmployee = roleTypeRet.get("E");
        //remove employe from list
        if (exludeEmployee != null) {
            ret.remove(exludeEmployee);
            roleTypeRet.remove("E");
        }
        return ret;
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

    public List<IprGenericListItem> getI18nDataFixed(String groupName, HashMap<String, IprGenericListItem> hash) {
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
                nuevo.setValue(actual.getOthFixedDataId().toString());
                hash.put(actual.getOthFixedDataId().toString(), nuevo);
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
