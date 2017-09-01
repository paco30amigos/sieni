/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers;

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

import com.unicomer.ejb.opos.inhouse.ipr.classes.IprLocationRespEjbRemote;
import com.unicomer.hr.utility.JsfUtil;
import com.unicomer.inhouse.ipr.entities.controllers.form.CatLocationRespForm;
import com.unicomer.inhouse.ipr.entities.controllers.model.CatLocationRespModel;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationRespPojo;
import com.unicomer.inhouse.jndi.JNDIUnicomerIpr;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.inhouse.jndi.JNDIUnicomerSecurity;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthAuditoryEjbRemote;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthDriverEjbRemote;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthEmployeeEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthFixedDataEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthI18nEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthWarehouseRespEjbRemote;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthDriver;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthEmployee;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthFixedData;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthI18n;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthWarehouseResp;
import com.unicomer.opos.inhouse.ipr.entities.IprLocationResp;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.security.ejbs.UnRetailStoreDaoEjbLocal;
import com.unicomer.opos.inhouse.security.entities.UnRetailStore;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "CatLocationRespController")
public class CatLocationRespController extends
        CatLocationRespForm {

    private static final Logger logger = Logger.getLogger(CatLocationRespController.class);

    @Resource(lookup = JNDIUnicomerOthers.OthAuditoryEjbRemote)
    private OthAuditoryEjbRemote othAuditoryEjbRemote;

    @Resource(lookup = JNDIUnicomerOthers.OthDriverEjbLocal)
    private OthDriverEjbRemote othDriverEjbRemote;

    @Resource(lookup = JNDIUnicomerOthers.OthEmployeeEjbLocal)
    private OthEmployeeEjbLocal othEmployeeEjbLocal;

    @Resource(lookup = JNDIUnicomerOthers.OthWarehouseResp)
    private OthWarehouseRespEjbRemote othWarehouseRespEjbRemote;

    @Resource(lookup = JNDIUnicomerSecurity.UnRetailStoreDaoEjbLocal)
    private UnRetailStoreDaoEjbLocal unRetailStoreDaoEjbLocal;

    @Resource(lookup = JNDIUnicomerOthers.OthFixedDataEjbLocal)
    private OthFixedDataEjbLocal othFixedDataEjbLocal;

    @Resource(lookup = JNDIUnicomerOthers.OthI18nEjbLocal)
    private OthI18nEjbLocal othI18nEjbLocal;

    @Resource(lookup = JNDIUnicomerIpr.IprLocationRespEjbRemote)
    private IprLocationRespEjbRemote iprLocationRespEjbRemote;

    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser uniUser;

    // Bundle de idioma
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.unicomer.inhouse.ipr.utilities.i18n.BundleIpr", FacesContext
            .getCurrentInstance().getViewRoot().getLocale());

    public BigInteger getOptionId() {
        return new BigInteger("56810");//OptionId from ADMIHSEC.UN_OPTION
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
            prepareLists();
            prepareLocationRespList();
            //init using active values
            setResponsableD(new HashMap<String, IprGenericListItem>());
            setResponsableListD(driverToPojo(getResponsableD(), false));
            setResponsableW(new HashMap<String, IprGenericListItem>());
            setResponsableListW(warehouseRespToPojo(getResponsableW(), false));
            setResponsableE(new HashMap<String, IprGenericListItem>());
            setResponsableListE(employeeToPojo(getResponsableE(), false));
        } catch (Exception e) {
            logger.error("IPR Error: " + e.getMessage(), e);
        }
    }

    public void newLocationResponsable() {
        try {
            setActive(true);
            setDefaultResponsable(false);
            setLocationSelected(getLocations().get(0).getValue());
            setResponsableTypeSelected(getResponsableTypes().get(0).getValue());
            refreshLists(getResponsableTypes().get(0));
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('newLocationResp').show();");
        } catch (Exception e) {
            logger.error("IPR Error: " + e.getMessage(), e);
        }
    }

    public void editLocationResponsable() {
        try {
            if (getLocationRespSelected() != null) {
                setActive(getLocationRespSelected().getActive() != null && getLocationRespSelected().getActiveVal() == 'Y');
                setDefaultResponsable(getLocationRespSelected().getIsDefault() != null && getLocationRespSelected().getIsDefault() == 'Y');
                setLocationSelected(getLocationRespSelected().getLocationId());
                setResponsableTypeSelected(getLocationRespSelected().getIprLocationResp().getRespType().toString());
                OthFixedData rol;
                BigInteger fixedDataId = getLocationRespSelected().getIprLocationResp().getRespRole();
                setObservationResponsability(getLocationRespSelected().getObservationResponsability());
                switch (getResponsableTypeSelected()) {
                    case "D":
                        setResponsableSelected(getLocationRespSelected().getIprLocationResp().getDriverId().toString());
                        rol = (OthFixedData) getResponsableRoleD().get(fixedDataId.toString()).getSelected();
                        setResponsableRoleSelected(rol.getValue());
                        setResponsableRoles(getResponsableRoleListD());
                        setResponsables(getResponsableListD());
                        break;
                    case "W":
                        setResponsableSelected(getLocationRespSelected().getIprLocationResp().getWrhrId().toString());
                        rol = (OthFixedData) getResponsableRoleW().get(fixedDataId.toString()).getSelected();
                        setResponsableRoleSelected(rol.getValue());
                        setResponsableRoles(getResponsableRoleListW());
                        setResponsables(getResponsableListW());
                        break;
                    case "E":
                        setResponsableSelected(getLocationRespSelected().getIprLocationResp().getEmpId());
                        rol = (OthFixedData) getResponsableRoleE().get(fixedDataId.toString()).getSelected();
                        setResponsableRoleSelected(rol.getValue());
                        setResponsableRoles(getResponsableRoleListE());
                        setResponsables(getResponsableListE());
                        break;
                }
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute("PF('editLocationResp').show();");
            } else {
                //seleccione una ubicacion
                String msg = rb.getString("ui_msg_errorSelectLocation");
                JsfUtil.addErrorMessage(msg);
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorLoadingLocResp");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void saveEditLocationResponsable() {
        try {
            if (validateEdit()) {
                IprLocationResp editLocationRest = getLocationRespSelected().getIprLocationResp();
                editLocationRest.setIsDefault(isDefaultResponsable() ? 'Y' : 'N');
                editLocationRest.setIsActive(isActive() ? 'Y' : 'N');
                IprGenericListItem responsable;
                UnRetailStore store = (UnRetailStore) getLocationsH().get(getLocationSelected()).getSelected();
                OthFixedData rol;
                editLocationRest.setStoreId(store.getStoreId());
                String respId = null;
                if ("D".equals(getResponsableTypeSelected())) {
                    responsable = getResponsableD().get(getResponsableSelected());
                    editLocationRest.setRespType('D');
                    editLocationRest.setDriverId(((OthDriver) responsable.getSelected()).getDriverId());
                    editLocationRest.setEmpId(null);
                    editLocationRest.setWrhrId(null);
                    rol = (OthFixedData) getResponsableRoleD().get(getResponsableRoleSelected()).getSelected();
                    editLocationRest.setRespRole(rol.getOthFixedDataId());
                    respId = editLocationRest.getDriverId().toString();
                }
                if ("W".equals(getResponsableTypeSelected())) {
                    responsable = getResponsableW().get(getResponsableSelected());
                    editLocationRest.setRespType('W');
                    editLocationRest.setWrhrId(((OthWarehouseResp) responsable.getSelected()).getWrhrId());
                    editLocationRest.setDriverId(null);
                    editLocationRest.setEmpId(null);
                    rol = (OthFixedData) getResponsableRoleW().get(getResponsableRoleSelected()).getSelected();
                    editLocationRest.setRespRole(rol.getOthFixedDataId());
                    respId = editLocationRest.getWrhrId().toString();
                }
                if ("E".equals(getResponsableTypeSelected())) {
                    responsable = getResponsableE().get(getResponsableSelected());
                    editLocationRest.setRespType('E');
                    editLocationRest.setEmpId(((OthEmployee) responsable.getSelected()).getEmpId());
                    editLocationRest.setDriverId(null);
                    editLocationRest.setWrhrId(null);
                    rol = (OthFixedData) getResponsableRoleE().get(getResponsableRoleSelected()).getSelected();
                    editLocationRest.setRespRole(rol.getOthFixedDataId());
                    respId = editLocationRest.getEmpId();
                }

                saveAuditory(editLocationRest.getAuditId(), "EDIT CATALOG LOCATION RESPONSABLE", 'U', getOptionId());
                iprLocationRespEjbRemote.edit(editLocationRest);

                if (editLocationRest.getIsDefault() != null && editLocationRest.getIsDefault().equals('Y')) {
                    //update default responsable
                    iprLocationRespEjbRemote.updateLocationResponsableDefault(editLocationRest.getStoreId(), respId, editLocationRest.getRespType());
                }

                prepareLocationRespList();

                String msg = rb.getString("ui_msg_editLocationRespRegisteredSuccess");
                JsfUtil.addSuccessMessage(msg);

                //TODO update all location history registers having the same store, which have null value
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingEditLocResp");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void saveNewLocationResponsable() {
        try {
            if (validateSave()) {
                IprLocationResp newLocationResp = new IprLocationResp();
                newLocationResp.setIsDefault(isDefaultResponsable() ? 'Y' : 'N');
                newLocationResp.setIsActive(isActive() ? 'Y' : 'N');
                IprGenericListItem responsable;
                UnRetailStore store = (UnRetailStore) getLocationsH().get(getLocationSelected()).getSelected();
                OthFixedData rol;
                String respId = null;

                newLocationResp.setStoreId(store.getStoreId());
                if ("D".equals(getResponsableTypeSelected())) {
                    responsable = getResponsableD().get(getResponsableSelected());
                    newLocationResp.setRespType('D');
                    newLocationResp.setDriverId(((OthDriver) responsable.getSelected()).getDriverId());
                    newLocationResp.setEmpId(null);
                    newLocationResp.setWrhrId(null);
                    rol = (OthFixedData) getResponsableRoleD().get(getResponsableRoleSelected()).getSelected();
                    newLocationResp.setRespRole(rol.getOthFixedDataId());
                    respId = newLocationResp.getDriverId().toString();
                }
                if ("W".equals(getResponsableTypeSelected())) {
                    responsable = getResponsableW().get(getResponsableSelected());
                    newLocationResp.setRespType('W');
                    newLocationResp.setWrhrId(((OthWarehouseResp) responsable.getSelected()).getWrhrId());
                    newLocationResp.setDriverId(null);
                    newLocationResp.setEmpId(null);
                    rol = (OthFixedData) getResponsableRoleW().get(getResponsableRoleSelected()).getSelected();
                    newLocationResp.setRespRole(rol.getOthFixedDataId());
                    respId = newLocationResp.getWrhrId().toString();
                }
                if ("E".equals(getResponsableTypeSelected())) {
                    responsable = getResponsableE().get(getResponsableSelected());
                    newLocationResp.setRespType('E');
                    newLocationResp.setEmpId(((OthEmployee) responsable.getSelected()).getEmpId());
                    newLocationResp.setDriverId(null);
                    newLocationResp.setWrhrId(null);
                    rol = (OthFixedData) getResponsableRoleE().get(getResponsableRoleSelected()).getSelected();
                    newLocationResp.setRespRole(rol.getOthFixedDataId());
                    respId = newLocationResp.getEmpId();
                }
                BigInteger auditId = saveAuditory(newLocationResp.getAuditId(), "CREATE NEW CATALOG LOCATION RESPONSABLE", 'C', getOptionId());
                newLocationResp.setAuditId(auditId);
                iprLocationRespEjbRemote.create(newLocationResp);

                if (newLocationResp.getIsDefault() != null && newLocationResp.getIsDefault().equals('Y')) {
                    //update default responsable
                    iprLocationRespEjbRemote.updateLocationResponsableDefault(newLocationResp.getStoreId(), respId, newLocationResp.getRespType());
                }
                prepareLocationRespList();
                //TODO update all registers having the same store, which have null value

                String msg = rb.getString("ui_msg_newLocationRespRegisteredSuccess");
                JsfUtil.addSuccessMessage(msg);
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingNewLocResp");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void changeResponsableType() {
        String typeSelected = getResponsableTypeSelected();
        IprGenericListItem sel = getResponsableTypesH().get(typeSelected);//get selection
        refreshLists(sel);
        changeResponsable();
    }

    public void changeResponsable() {
        if ("D".equals(getResponsableTypeSelected())) {
            if (getResponsableD() != null && !getResponsableD().isEmpty()) {
                IprGenericListItem sel = getResponsableD().values().iterator().next();//get selection
                OthDriver driver = (OthDriver) sel.getSelected();
                setResponsableSelected(sel.getValue());
                setObservationResponsability(driver.getDriverObserv());
                if (getResponsableRoleD() != null && !getResponsableRoleD().isEmpty()) {
                    setResponsableRoleSelected(getResponsableRoleD().values().iterator().next().getValue());
                }
            } else {
                setObservationResponsability("--");
            }
        } else if ("W".equals(getResponsableTypeSelected())) {
            if (getResponsableW() != null && !getResponsableW().isEmpty()) {
                IprGenericListItem sel = getResponsableW().values().iterator().next();//get selection
                OthWarehouseResp driver = (OthWarehouseResp) sel.getSelected();
                setResponsableSelected(sel.getValue());
                setObservationResponsability(driver.getWrhrObserv());
                if (getResponsableRoleW() != null && !getResponsableRoleW().isEmpty()) {
                    setResponsableRoleSelected(getResponsableRoleW().values().iterator().next().getValue());
                }
            } else {
                IprGenericListItem sel = getResponsableE().values().iterator().next();//get selection
                setResponsableSelected(sel.getValue());
                setObservationResponsability("--");
                if (getResponsableRoleE() != null && !getResponsableRoleE().isEmpty()) {
                    setResponsableRoleSelected(getResponsableRoleE().values().iterator().next().getValue());
                }
            }
        } else if ("E".equals(getResponsableTypeSelected())) {
            setObservationResponsability("--");
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

    public void prepareLists() {
        HashMap<String, IprGenericListItem> allStores = new HashMap<>();
        HashMap<String, IprGenericListItem> activeStores = new HashMap<>();
        setAllLocationsH(allStores);//set hash object reference
        setAllLocations(getAllStores(allStores));
        setLocationsH(activeStores);
        setLocations(getActiveStores(getAllLocations(), activeStores));

        HashMap<String, IprGenericListItem> responsable = new HashMap<>();
        setResponsableTypesH(responsable);//set hash object reference
        setResponsableTypes(getResponsableTypesToPojo(responsable));//set list result value

        if (getResponsableTypes() != null && !getResponsableTypes().isEmpty()) {
            IprGenericListItem responsableTypeSelected = getResponsableTypes().get(0);
            refreshLists(responsableTypeSelected);
        } else {
            refreshLists(null);
        }
    }

    public void refreshLists(IprGenericListItem responsableTypeSelected) {
        //set first responsable type to load data
        if (responsableTypeSelected != null) {
            OthFixedData sel = (OthFixedData) responsableTypeSelected.getSelected();
            setResponsableTypeSelected(sel.getValue());
            loadResponsableList(getResponsableTypeSelected());
            if (getResponsables() != null && !getResponsables().isEmpty()) {
                //set default selected the first element
                setResponsableSelected(getResponsables().get(0).getValue());
            } else {
                setResponsableSelected(null);
            }
            loadResponsableRoles(getResponsableTypeSelected());
            if (getResponsableRoles() != null && !getResponsableRoles().isEmpty()) {
                //set default selected the first element
                setResponsableRoleSelected(getResponsableRoles().get(0).getValue());
            } else {
                setResponsableRoleSelected(null);
            }
        } else {
            //reset selected
            setResponsableTypeSelected(null);
            setResponsableSelected(null);
            setResponsableRoleSelected(null);
            //reset lists
            setResponsables(new ArrayList<IprGenericListItem>());
            setResponsableRoles(new ArrayList<IprGenericListItem>());
        }
    }

    public void loadResponsableRoles(String respType) {
        HashMap<String, IprGenericListItem> responsable;
        switch (respType) {
            case "E":
                responsable = new HashMap<>();
                setResponsableRolesH(responsable);//set hash object reference
                setResponsableRoles(getResponsablesEmployeeRoles(responsable, 2));//set list result value
                break;
            case "W":
                responsable = new HashMap<>();
                setResponsableRolesH(responsable);
                setResponsableRoles(getResponsablesWarehouseRespRoles(responsable, 2));
                break;
            case "D":
                responsable = new HashMap<>();
                setResponsableRolesH(responsable);
                setResponsableRoles(getResponsablesDriverRoles(responsable, 2));
                break;
            default:
                break;
        }
    }

    public void loadResponsableList(String respType) {
        HashMap<String, IprGenericListItem> responsable;
        switch (respType) {
            case "E":
                responsable = new HashMap<>();
//                setResponsablesH(responsable);//set hash object reference
                setResponsables(employeeToPojo(responsable, false));//set list result value
                break;
            case "W":
                responsable = new HashMap<>();
//                setResponsablesH(responsable);
                setResponsables(warehouseRespToPojo(responsable, false));
                break;
            case "D":
                responsable = new HashMap<>();
//                setResponsablesH(responsable);
                setResponsables(driverToPojo(responsable, false));
                break;
            default:
                break;
        }
    }

    public List<IprGenericListItem> getYesNoToPojo(HashMap<String, IprGenericListItem> hash) {
        return getI18n("IPR_YES_NO", hash);
    }

    public List<IprGenericListItem> getResponsableTypesToPojo(HashMap<String, IprGenericListItem> roleTypeRet) {
        return getI18n("IPR_RESPONSABLE_TYPE", roleTypeRet);
    }

    public List<IprGenericListItem> getResponsablesWarehouseRespRoles(HashMap<String, IprGenericListItem> roleTypeRet, int type) {
        if (type == 1) {
            return getI18n("IPR_ROLE_W_TYPE", roleTypeRet);
        } else {
            return getI18nDataFixed("IPR_ROLE_W_TYPE", roleTypeRet);
        }
    }

    public List<IprGenericListItem> getResponsablesDriverRoles(HashMap<String, IprGenericListItem> roleTypeRet, int type) {
        if (type == 1) {
            return getI18n("IPR_ROLE_D_TYPE", roleTypeRet);
        } else {
            return getI18nDataFixed("IPR_ROLE_D_TYPE", roleTypeRet);
        }
    }

    public List<IprGenericListItem> getResponsablesEmployeeRoles(HashMap<String, IprGenericListItem> roleTypeRet, int type) {
        if (type == 1) {
            return getI18n("IPR_ROLE_E_TYPE", roleTypeRet);
        } else {
            return getI18nDataFixed("IPR_ROLE_E_TYPE", roleTypeRet);
        }
    }

    //data list to fill form
    public ArrayList<IprGenericListItem> driverToPojo(HashMap<String, IprGenericListItem> hash, boolean all) {

        List<OthDriver> resp;
        if (all) {
            resp = othDriverEjbRemote.findByCountry(uniUser.getCurrentCountry().getCountryCode());
        } else {
            resp = othDriverEjbRemote.findByCountryActive(uniUser.getCurrentCountry().getCountryCode());
        }
        ArrayList<IprGenericListItem> ret = new ArrayList<>();
        IprGenericListItem nuevo;
        StringBuffer label;

        //set data list
        for (OthDriver actual : resp) {
            nuevo = new IprGenericListItem();
            nuevo.setSelected(actual);
            nuevo.setValue(actual.getDriverId().toString());//create list value

            label = new StringBuffer();//create list label
            label.append(actual.getDriverCode());
            label.append(" - ");
            label.append(actual.getDriverName());

            nuevo.setLabel(label.toString());
            ret.add(nuevo);
            hash.put(actual.getDriverId().toString(), nuevo);
        }

        return ret;
    }

    public ArrayList<IprGenericListItem> warehouseRespToPojo(HashMap<String, IprGenericListItem> hash, boolean all) {
        List<OthWarehouseResp> resp;
        if (all) {
            resp = othWarehouseRespEjbRemote.findByCountry(uniUser.getCurrentCountry().getCountryCode());
        } else {
            resp = othWarehouseRespEjbRemote.findByCountryActive(uniUser.getCurrentCountry().getCountryCode());
        }
        ArrayList<IprGenericListItem> ret = new ArrayList<>();
        IprGenericListItem nuevo;
        StringBuffer label;

        //set data list
        for (OthWarehouseResp actual : resp) {
            nuevo = new IprGenericListItem();
            nuevo.setSelected(actual);
            nuevo.setValue(actual.getWrhrCode().toString());//create list value

            label = new StringBuffer();//create list label
            label.append(actual.getWrhrCode());
            label.append(" - ");
            label.append(actual.getWrhrName());

            nuevo.setLabel(label.toString());
            ret.add(nuevo);
            hash.put(actual.getWrhrId().toString(), nuevo);
        }
        return ret;
    }

    public ArrayList<IprGenericListItem> employeeToPojo(HashMap<String, IprGenericListItem> hash, boolean all) {
        List<OthEmployee> resp;
        if (all) {
            resp = othEmployeeEjbLocal.findByCountryCode(uniUser.getCurrentCountry().getCountryCode());
        } else {
            resp = othEmployeeEjbLocal.findByIsActiveByCtry("Y", uniUser.getCurrentCountry().getCountryCode());
        }
        ArrayList<IprGenericListItem> ret = new ArrayList<>();
        IprGenericListItem nuevo;
        StringBuffer label;

        //set data list
        for (OthEmployee actual : resp) {
            nuevo = new IprGenericListItem();
            nuevo.setSelected(actual);
            nuevo.setValue(actual.getEmpId());//create list value

            label = new StringBuffer();//create list label
            label.append(actual.getEmpId());
            label.append(" - ");
            label.append(actual.getEmpName());

            nuevo.setLabel(label.toString());
            ret.add(nuevo);
            hash.put(actual.getEmpId(), nuevo);
        }

        return ret;
    }

    public List<IprGenericListItem> getAllStores(HashMap<String, IprGenericListItem> hash) {
        List<UnRetailStore> resp = unRetailStoreDaoEjbLocal.findByCountry(uniUser.getCurrentCountry().getCountryId());
        List<IprGenericListItem> ret = new ArrayList<>();
        IprGenericListItem nuevo;
        StringBuffer label;
        for (UnRetailStore actual : resp) {
            nuevo = new IprGenericListItem();
            label = new StringBuffer();//create list label
            label.append(actual.getStoreRiCode());
            label.append(" - ");
            label.append(actual.getRetailStoreName());

            nuevo.setLabel(label.toString());
            nuevo.setValue(actual.getStoreId());
            nuevo.setSelected(actual);
            ret.add(nuevo);
            hash.put(actual.getStoreId(), nuevo);
        }
        return ret;
    }

    public List<IprGenericListItem> getActiveStores(List<IprGenericListItem> allData, HashMap<String, IprGenericListItem> hash) {
        List<IprGenericListItem> ret = new ArrayList<IprGenericListItem>();
        for (IprGenericListItem actual : allData) {
            UnRetailStore store = (UnRetailStore) actual.getSelected();
            if ("S".equals(store.getIsActive())) {
                ret.add(actual);
                hash.put(actual.getValue(), actual);
            }
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

    public void prepareLocationRespList() {
        List<IprLocationRespPojo> locationRespP = new ArrayList<>();
        setLocationRespH(new HashMap<String, IprLocationRespPojo>());
        List<IprLocationResp> locationResp = iprLocationRespEjbRemote.findAll();
        IprLocationRespPojo nuevo;

        //trae los hash solo si las necesita
        HashMap<String, IprGenericListItem> responsableD = new HashMap<>();
        HashMap<String, IprGenericListItem> responsableW = new HashMap<>();
        HashMap<String, IprGenericListItem> responsableE = new HashMap<>();

        HashMap<String, IprGenericListItem> responsableRoleD = new HashMap<>();
        HashMap<String, IprGenericListItem> responsableRoleW = new HashMap<>();
        HashMap<String, IprGenericListItem> responsableRoleE = new HashMap<>();

        List<IprGenericListItem> responsableListD = new ArrayList<>();
        List<IprGenericListItem> responsableListW = new ArrayList<>();
        List<IprGenericListItem> responsableListE = new ArrayList<>();

        List<IprGenericListItem> responsableRoleListD = new ArrayList<>();
        List<IprGenericListItem> responsableRoleListW = new ArrayList<>();
        List<IprGenericListItem> responsableRoleListE = new ArrayList<>();

        OthDriver respD;
        OthWarehouseResp respW;
        OthEmployee respE;
        HashMap<String, IprGenericListItem> hash = new HashMap<>();
        getYesNoToPojo(hash);

        for (IprLocationResp actual : locationResp) {
            UnRetailStore store = (UnRetailStore) getAllLocationsH().get(actual.getStoreId()).getSelected();
            String respType = actual.getRespType().toString();
            IprGenericListItem rol = new IprGenericListItem();
            nuevo = new IprLocationRespPojo();
            switch (respType) {
                case "D":
                    if (responsableD.isEmpty()) {
                        responsableListD = driverToPojo(responsableD, true);
                    }
                    if (responsableRoleD.isEmpty()) {
                        responsableRoleListD = getResponsablesDriverRoles(responsableRoleD, 2);
                    }
                    respD = (OthDriver) responsableD.get(actual.getDriverId().toString()).getSelected();
                    nuevo.setResponsableCode(respD.getDriverCode().toString());
                    nuevo.setResponsableName(respD.getDriverName());
                    nuevo.setObservationResponsability(respD.getDriverObserv());
                    rol = responsableRoleD.get(actual.getRespRole().toString());
                    nuevo.setResponsableRole(rol.getLabel());

                    setResponsableD(responsableD);
                    setResponsableRoleD(responsableRoleD);
                    setResponsableListD(responsableListD);
                    setResponsableRoleListD(responsableRoleListD);
                    break;
                case "W":
                    if (responsableW.isEmpty()) {
                        responsableListW = warehouseRespToPojo(responsableW, true);
                    }
                    if (responsableRoleW.isEmpty()) {
                        responsableRoleListW = getResponsablesWarehouseRespRoles(responsableRoleW, 2);
                    }
                    respW = (OthWarehouseResp) responsableW.get(actual.getWrhrId().toString()).getSelected();
                    nuevo.setResponsableCode(respW.getWrhrCode().toString());
                    nuevo.setResponsableName(respW.getWrhrName());
                    nuevo.setObservationResponsability(respW.getWrhrObserv());
                    rol = responsableRoleW.get(actual.getRespRole().toString());
                    nuevo.setResponsableRole(rol.getLabel());

                    setResponsableW(responsableW);
                    setResponsableRoleW(responsableRoleW);
                    setResponsableListW(responsableListW);
                    setResponsableRoleListW(responsableRoleListW);
                    break;
                case "E":
                    if (responsableE.isEmpty()) {
                        responsableListE = employeeToPojo(responsableE, true);
                    }
                    if (responsableRoleE.isEmpty()) {
                        responsableRoleListE = getResponsablesEmployeeRoles(responsableRoleE, 2);
                    }
                    respE = (OthEmployee) responsableE.get(actual.getEmpId()).getSelected();
                    nuevo.setResponsableCode(respE.getEmpId());
                    nuevo.setResponsableName(respE.getEmpName());
                    nuevo.setObservationResponsability("--");
                    rol = responsableRoleE.get(actual.getRespRole().toString());
                    nuevo.setResponsableRole(rol.getLabel());

                    setResponsableE(responsableE);
                    setResponsableRoleE(responsableRoleE);
                    setResponsableListE(responsableListE);
                    setResponsableRoleListE(responsableRoleListE);
                    break;
            }
            nuevo.setResponsableType(getResponsableTypesH().get(respType).getLabel());
            nuevo.setIprLocationResp(actual);
            nuevo.setLocationCode(store.getStoreRiCode());
            nuevo.setLocationName(store.getRetailStoreName());
            nuevo.setLocationId(store.getStoreId());
            IprGenericListItem act = hash.get(actual.getIsActive().toString());
            nuevo.setActive(act != null ? act.getLabel() : "");
            nuevo.setActiveVal(actual.getIsActive());
            nuevo.setIsDefault(actual.getIsDefault());

            locationRespP.add(nuevo);
//            getLocationRespH().put(nuevo.getIprLocationResp().getLocationId().toString(), nuevo);

        }

        setLocationResp(new CatLocationRespModel(locationRespP,"getLocationId"));
//        getLocationResp().setHash(getLocationRespH());
    }

    // getter and setter session variables 
    public UnUser getUniUser() {
        return uniUser;
    }

    public void setUniUser(UnUser uniUser) {
        this.uniUser = uniUser;
    }

}
