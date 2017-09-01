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

import com.unicomer.ejb.opos.inhouse.ipr.classes.IprLocationHistEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprLocationRespEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprStatusProdEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTransactionEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTrxLineItmEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTrxLineItmPropEjbRemote;
import com.unicomer.hr.utility.JsfUtil;
import com.unicomer.inhouse.ipr.entities.controllers.form.TrxProductRepossessedForm;
import com.unicomer.inhouse.ipr.entities.controllers.model.LocationHistModel;
import com.unicomer.inhouse.ipr.entities.controllers.model.SelectedProductRepossessedModel;
import com.unicomer.inhouse.jndi.JNDIUnicomerIpr;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthAuditoryEjbRemote;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthCurrencyEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthFixedDataEjbLocal;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthI18nEjbLocal;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthCurrency;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthFixedData;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthI18n;
import com.unicomer.opos.inhouse.ipr.entities.IprLocationHist;
import com.unicomer.opos.inhouse.ipr.entities.IprStatusProd;
import com.unicomer.opos.inhouse.ipr.entities.IprTransaction;
import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm;
import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItmProp;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationDet;
import com.unicomer.opos.inhouse.ipr.pojos.IprProduct;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsRepossessed;
import com.unicomer.opos.inhouse.ipr.pojos.IprResponsableInfo;
import com.unicomer.opos.inhouse.security.entities.UnRetailStore;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import java.io.File;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "TrxProductRepossessedController")
public class TrxProductRepossessedController extends
        TrxProductRepossessedForm {

    static Logger logger = Logger.getLogger(TrxProductRepossessedController.class);
    @Resource(lookup = JNDIUnicomerOthers.OthAuditoryEjbRemote)
    private OthAuditoryEjbRemote othAuditoryEjbRemote;

    @Resource(lookup = JNDIUnicomerIpr.IprTransactionEjbRemote)
    private IprTransactionEjbRemote iprTransactionEjbRemote;

    @Resource(lookup = JNDIUnicomerIpr.IprTrxLineItmEjbRemote)
    private IprTrxLineItmEjbRemote iprTrxLineItmEjbRemote;

    @Resource(lookup = JNDIUnicomerIpr.IprLocationHistEjbRemote)
    private IprLocationHistEjbRemote iprLocationHistEjbRemote;

    @Resource(lookup = JNDIUnicomerOthers.OthCurrencyEjbLocal)
    private OthCurrencyEjbLocal othCurrencyEjbLocal;

    @Resource(lookup = JNDIUnicomerIpr.IprLocationRespEjbRemote)
    private IprLocationRespEjbRemote iprLocationRespEjbRemote;

    @Resource(lookup = JNDIUnicomerOthers.OthFixedDataEjbLocal)
    private OthFixedDataEjbLocal othFixedDataEjbLocal;

    @Resource(lookup = JNDIUnicomerOthers.OthI18nEjbLocal)
    private OthI18nEjbLocal othI18nEjbLocal;

    @Resource(lookup = JNDIUnicomerIpr.IprStatusProdEjbRemote)
    private IprStatusProdEjbRemote iprStatusProdEjbRemote;

    @Resource(lookup = JNDIUnicomerIpr.IprTrxLineItmPropEjbRemote)
    private IprTrxLineItmPropEjbRemote iprTrxLineItmPropEjbRemote;

    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser uniUser;

    // Bundle de idioma
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.unicomer.inhouse.ipr.utilities.i18n.BundleIpr", FacesContext
            .getCurrentInstance().getViewRoot().getLocale());

    public BigInteger getOptionId() {
        return new BigInteger("57210");//OptionId from ADMIHSEC.UN_OPTION
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
        setTabIndex(0);
        setEnableTab1(false);
        setSearchFields(new IprProductsRepossessed());
        getSearchFields().setTrx(new ArrayList<IprProductsRepossessed>());
        setCurrencyH(new HashMap<String, OthCurrency>());
        setTrxSelected(new IprProductsRepossessed());
        getTrxSelected().setProductList(new ArrayList<IprProduct>());
        setProductsToFinish(new ArrayList<IprProduct>());
    }

    public void search() {
        try {

            List<BigInteger> statusId=new ArrayList<>();
            statusId.add(BigInteger.ONE);
            statusId.add(new BigInteger("2"));
            statusId.add(new BigInteger("3"));
            HashMap<String, Object> params = new HashMap<String, Object>();

            params.put("accNum", getSearchFields().getAccNum());
            params.put("transactionId", getSearchFields().getTrxNum());
            params.put("mainCtIde", getSearchFields().getMainIdeNum());
            params.put("fsclCtIde", getSearchFields().getFiscalIde());
            params.put("dateMin", getSearchFields().getDateMin());
            params.put("dateMax", getSearchFields().getDateMax());
            params.put("statusId", statusId);

            List<IprTransaction> trx = iprTransactionEjbRemote
                    .findByFilters(params);
            List<IprProductsRepossessed> trxLs = new ArrayList<IprProductsRepossessed>();
            HashMap<String, IprProductsRepossessed> hash = new HashMap<>();

            setEnableTab1(false);
            setTabIndex(0);

            if (trx == null || trx.isEmpty()) {
                trxLs = new ArrayList<IprProductsRepossessed>();
                setTrxSelected(null);
            } else {
                for (IprTransaction actual : trx) {
                    IprProductsRepossessed elem = trxToPojo(actual);
                    trxLs.add(elem);
                    List<IprLocationDet> locations = iprLocationHistEjbRemote.getFirstLocationByIprTrx(actual.getIprTransacId());
                    HashMap<BigInteger, IprLocationDet> locH = getHashFirstLocations(locations);
                    if (locations != null && !locations.isEmpty()) {
                        IprLocationDet first = locH.get(actual.getIprTransacId());
                        if (first != null) {
                            // obtener los datos de codigo, nombre y responsable historico de la
                            // primera tienda
                            elem.setStoreCode(first.getStoreId());
                            elem.setStoreRiCode(new BigInteger(first.getStoreRiCode()));
                            elem.setStoreName(first.getStoreName());
                            elem.setStoreResponsable(first.getStoreResponsable());//get selected
                            elem.setLastLocation(first.getStoreName());
                        }
                    }
                    hash.put(actual.getIprTransacId().toString(), elem);
                }
                //set the first element of list as selected
                setTrxSelected(trxLs.get(0));
            }

            setSelectedProductRepossessedModel(new SelectedProductRepossessedModel(trxLs,"getTrxNum"));
//            getSelectedProductRepossessedModel().setHash(hash);
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorWhileSearch");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public HashMap<BigInteger, IprLocationDet> getHashFirstLocations(List<IprLocationDet> locations) {
        HashMap<BigInteger, IprLocationDet> ret = new HashMap<BigInteger, IprLocationDet>();
        for (IprLocationDet actual : locations) {
            if (!ret.containsKey(actual.getIprTrxId())) {
                ret.put(actual.getIprTrxId(), actual);
                break;
            }
        }
        return ret;
    }

    public IprProductsRepossessed trxToPojo(IprTransaction trx) {
        IprProductsRepossessed elem = new IprProductsRepossessed();
        elem.setIprTrx(trx);
        elem.setTrxNum(trx.getTransactionId());
        elem.setCreditNoteNumber(trx.getCreditNote());
        elem.setAccNum(trx.getAccNum());
        elem.setFarNumber(trx.getFarNum());
        elem.setTrxDate(trx.getTransactionDate());
        elem.setTransactionAmount(trx.getTrxMnt());
        elem.setMainIdeNum(trx.getMainCtIde());
        elem.setFiscalIde(trx.getFsclCtIde());
        StringBuffer currency = new StringBuffer();
        currency.append("(");
        currency.append(trx.getCurrency());
        currency.append(")");
        elem.setCurrency(currency.toString());
        OthCurrency c = new OthCurrency();
        if (!getCurrencyH().containsKey(trx.getCurrency())) {
            c = othCurrencyEjbLocal.findByCurrencyCode(trx.getCurrency());
            getCurrencyH().put(trx.getCurrency(), c);
        }

        c = getCurrencyH().get(trx.getCurrency());
        if (c != null) {
            StringBuffer currencySimbol = new StringBuffer();
            currency.append("(");
            currency.append(c.getSymbol());
            currency.append(")");
            elem.setCurrencySimbol(currencySimbol.toString());
        } else {
            elem.setCurrencySimbol("");
        }
        elem.setCustomerName(trx.getNameCt());
        return elem;
    }

    public void getProductInformation(BigInteger transactionId) {
        try {

            List<BigInteger> statusList = new ArrayList<>();
            statusList.add(new BigInteger("1"));
            statusList.add(new BigInteger("2"));
            statusList.add(new BigInteger("3"));
            //busca los productos que no han sido seleccionados como recogidos
            List<IprProduct> itm = iprTrxLineItmEjbRemote.findProductList(statusList, transactionId, FacesContext
                    .getCurrentInstance().getViewRoot().getLocale().getLanguage());
            List<IprTrxLineItm> itmE = iprTrxLineItmEjbRemote.findItemsByStatusAndTransac(statusList, transactionId);
            HashMap<BigInteger, IprTrxLineItm> itmHM = new HashMap<BigInteger, IprTrxLineItm>();

            //los agrega a un hash para busqueda eficiente
            for (IprTrxLineItm actual : itmE) {
                itmHM.put(actual.getLineItemId(), actual);
            }
            //guarda la entidad en cada elemento de la lista, para poder actualizarla si se hacen modificaciones
            for (IprProduct actual : itm) {
                actual.setLineItem(itmHM.get(actual.getLineItemId()));
            }

            if (itm != null && !itm.isEmpty()) {
                //show first product location history
                showProductInformation(itm.get(0).getLineItemId());
                getTrxSelected().setProductSelected(itm.get(0));
            } else {
                getTrxSelected().setProductSelected(new IprProduct());
            }
            getTrxSelected().setProductList(itm);
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorGettingProductInformation");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void showTransactionDetail() {
        if (getTrxSelected() != null && getTrxSelected().getIprTrx().getIprTransacId() != null) {
            BigInteger transactionId = getTrxSelected().getIprTrx().getIprTransacId();
            getProductInformation(transactionId);
            setTabIndex(1);
            setEnableTab1(true);
        } else {
            String msg = rb.getString("ui_msg_errorGettingProductInformation");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void onRowProductSelect() {
        BigInteger id = getTrxSelected().getProductSelected().getLineItemId();
        showProductInformation(id);
    }

    public void showProductInformation(BigInteger lineItemId) {
        List<IprLocationDet> ret = iprLocationHistEjbRemote.getAllLocationByTrxLineItm(lineItemId, FacesContext
                .getCurrentInstance().getViewRoot().getLocale().getLanguage());
        HashMap<String, IprLocationDet> map = new HashMap<>();
        setLocationHistSelected(null);

        //init statusList
        List<IprStatusProd> statusProdList = iprStatusProdEjbRemote.findActiveOrdered();
        setStatusProd(new ArrayList<IprGenericListItem>());
        setStatusProdH(new HashMap<String, IprGenericListItem>());
        IprGenericListItem nuevo;
        for (IprStatusProd actual : statusProdList) {
            nuevo = new IprGenericListItem();
            nuevo.setLabel(actual.getStatusDesc());
            nuevo.setSelected(actual);
            nuevo.setValue(actual.getStatusProdId().toString());
            getStatusProd().add(nuevo);
            getStatusProdH().put(nuevo.getValue(), nuevo);
        }
        //

        //init colours
        setColorH(new HashMap<String, IprGenericListItem>());
        setColor(getColours(getColorH()));

        if (getStatusProd() != null && !getStatusProd().isEmpty()) {
            setItemProp(iprTrxLineItmPropEjbRemote.findByLineItemId(lineItemId));
            if (getItemProp() != null) {
                //if a item prop have been stored
                setAccesories(getItemProp().getAccesDesc());
                setObsservations(getItemProp().getObsDesc());
                setColorSelected(getItemProp().getColorId().toString());
                setStatusSelected(getItemProp().getStatusProdId().getStatusProdId().toString());
            } else {
                if (getColor() != null && !getColor().isEmpty()) {
                    setColorSelected(getColor().get(0).getValue());
                } else {
                    setColorSelected(null);
                }
                if (getStatusProd() != null && !getStatusProd().isEmpty()) {
                    setStatusSelected(getStatusProd().get(0).getValue());
                } else {
                    setStatusSelected(null);
                }
                setAccesories("");
                setObsservations("");
            }
        }

        if (ret != null && !ret.isEmpty()) {
            setCurrentHistoryLocations(new LocationHistModel(ret,"getLocHistId"));
            for (IprLocationDet actual : ret) {
                map.put(actual.getLocHistId().toString(), actual);
            }
//            getCurrentHistoryLocations().setHash(map);
        } else {
            setCurrentHistoryLocations(new LocationHistModel(null));
        }
    }

    public List<IprGenericListItem> getColours(HashMap<String, IprGenericListItem> hash) {
        return getI18nDataFixed("IPR_COLOURS", hash);
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

    public List<IprGenericListItem> getAllStores(HashMap<String, IprGenericListItem> hash) {
        List<UnRetailStore> resp = iprLocationRespEjbRemote.findStoresConfigured(uniUser.getCurrentCountry().getCountryCode());
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

    public void loadStores() {
        HashMap<String, IprGenericListItem> allStores = new HashMap<>();
        HashMap<String, IprGenericListItem> activeStores = new HashMap<>();
        setAllLocationsH(allStores);//set hash object reference
        setAllLocations(getAllStores(allStores));
        setLocationsH(activeStores);
        setLocations(getActiveStores(getAllLocations(), activeStores));
    }

    public void saveNewLocation() {

        try {

            IprLocationHist nuevo = new IprLocationHist();
            IprGenericListItem respSelected = getResponsableLocationH().get(getStoreResposable());
            IprResponsableInfo respInfo = (IprResponsableInfo) respSelected.getSelected();

            if (respInfo != null) {
                if (validateNewLocation()) {
                    nuevo.setLineItemId(getTrxSelected().getProductSelected().getLineItem());
                    nuevo.setLocationId(iprLocationRespEjbRemote.findId(new BigInteger(respInfo.getStoreRespId())));
                    nuevo.setIsActive('Y');
                    nuevo.setRespType(respInfo.getRespType());
                    nuevo.setStoreId(new BigInteger(respInfo.getStoreId()));
                    nuevo.setRespRole(respInfo.getResponsableRoleId());
                    switch (respInfo.getRespType()) {
                        case 'D':
                            nuevo.setDriverId(new BigInteger(respInfo.getStoreRespId()));
                            break;
                        case 'W':
                            nuevo.setWrhrId(new BigInteger(respInfo.getStoreRespId()));
                            break;
                        case 'E':
                            nuevo.setEmpId(respInfo.getStoreRespId());
                            break;
                    }
                    BigInteger auditId = saveAuditory(nuevo.getAuditId(), "CREATE NEW REPOSSESSED PRODUCT LOCATION", 'C', getOptionId());
                    nuevo.setAuditId(auditId);
                    iprLocationHistEjbRemote.create(nuevo);
                    //refresh table data
                    onRowProductSelect();
                    getTrxSelected().getProductSelected().getLineItem().setStatusId(new BigInteger("3"));
                    iprTrxLineItmEjbRemote.edit(getTrxSelected().getProductSelected().getLineItem());

                    String msg = rb.getString("ui_msg_LocationCreatedSuccessfuly");
                    JsfUtil.addSuccessMessage(msg);
                }
            }
//        List<IprTrxLineItm> toUpdate = new ArrayList<IprTrxLineItm>();
//        for (IprProduct actual : getTrxSelected().getProductSelected()) {
//            actual.getLineItem().setStatusId(BigInteger.ONE);//articulo recogido
//            toUpdate.add(actual.getLineItem());
//        }
//        iprTrxLineItmEjbRemote.merge(toUpdate);
//        showTransactionDetail();
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingNewLocation");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public boolean validateNewLocation() {
        boolean ban = true;
        return ban;
    }

    public boolean validateEditLocation() {
        boolean ban = true;
        return ban;
    }

    public void saveEditLocation() {

        try {

            if (getLocationHistSelected() != null) {
                IprLocationHist edit = iprLocationHistEjbRemote.findById(getLocationHistSelected().getLocHistId());
                if (edit != null) {
                    IprGenericListItem respSelected = getResponsableLocationH().get(getStoreResposable());
                    IprResponsableInfo respInfo = (IprResponsableInfo) respSelected.getSelected();
                    if (validateEditLocation() && respInfo != null) {
                        edit.setLocationId(iprLocationRespEjbRemote.findId(new BigInteger(respInfo.getStoreRespId())));
                        edit.setIsActive('Y');
                        edit.setRespType(respInfo.getRespType());
                        edit.setStoreId(new BigInteger(respInfo.getStoreId()));
                        switch (respInfo.getRespType()) {
                            case 'D':
                                edit.setDriverId(new BigInteger(respInfo.getStoreRespId()));
                                break;
                            case 'W':
                                edit.setWrhrId(new BigInteger(respInfo.getStoreRespId()));
                                break;
                            case 'E':
                                edit.setEmpId(respInfo.getStoreRespId());
                                break;
                        }
                        saveAuditory(edit.getAuditId(), "UPDATE REPOSSESSED PRODUCT LOCATION", 'U', getOptionId());
                        iprLocationHistEjbRemote.edit(edit);
                        //refresh table data
                        onRowProductSelect();
                        getTrxSelected().getProductSelected().getLineItem().setStatusId(new BigInteger("3"));
                        iprTrxLineItmEjbRemote.edit(getTrxSelected().getProductSelected().getLineItem());

                        String msg = rb.getString("ui_msg_changedSavedSuccessfuly");
                        JsfUtil.addSuccessMessage(msg);
                    } else {
                        //invalid data
                        String msg = rb.getString("ui_msg_errorSavingInvalidData");
                        JsfUtil.addErrorMessage(msg);
                    }
                } else {
                    //item not found
                    String msg = rb.getString("ui_msg_errorSavingProductNotFound");
                    JsfUtil.addErrorMessage(msg);
                }
            }
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingEditLocation");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void saveEditProductProp() {
        try {
            if (getItemProp() == null) {
                setItemProp(new IprTrxLineItmProp());
            }
            getItemProp().setAccesDesc(getAccesories());
            getItemProp().setObsDesc(getObsservations());
            getItemProp().setColorId(new BigInteger(getColorSelected()));
            getItemProp().setStatusProdId((IprStatusProd) getStatusProdH().get(getStatusSelected()).getSelected());
            getItemProp().setLineItemId(getTrxSelected().getProductSelected().getLineItem());
            getItemProp().setIsActive('Y');

            if (getItemProp().getAccesoId() == null) {
                BigInteger auditId = saveAuditory(getItemProp().getAuditId(), "CREATE REPOSSESSED PRODUCT PROPERTY", 'C', getOptionId());
                getItemProp().setAuditId(auditId);
                iprTrxLineItmPropEjbRemote.create(getItemProp());
            } else {
                saveAuditory(getItemProp().getAuditId(), "UPDATE REPOSSESSED PRODUCT PROPERTY", 'U', getOptionId());
                iprTrxLineItmPropEjbRemote.edit(getItemProp());
            }
            getItemProp().getLineItemId().setStatusId(new BigInteger("2"));
            iprTrxLineItmEjbRemote.edit(getItemProp().getLineItemId());
            //refresh table data
            getProductInformation(getTrxSelected().getIprTrx().getIprTransacId());

            String msg = rb.getString("ui_msg_changedSavedSuccessfuly");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorSavingProductProp");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    public void addLocation() {
        loadStores();
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlg-add-location-wb').show();");
        if (getLocations() != null && !getLocations().isEmpty()) {
            //select first element of list
            setStore(getLocations().get(0).getValue());
        }
        storeResponsableChange(getStore());
        setDefaultValues();

        //load code - retail stores
        //load code - store responsable
    }

    public void editLocation() {
        if (getLocationHistSelected() != null) {
            loadStores();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg-edit-location-wb').show();");
            if (getLocations() != null && !getLocations().isEmpty()) {
                storeResponsableChange(getLocationHistSelected().getStoreId().toString());
                //select first element of list
                IprGenericListItem info = getResponsableLocationH().get(getLocationHistSelected().getStoreRespId().toString());
                IprResponsableInfo respInfo = (IprResponsableInfo) info.getSelected();
                setStoreResposable(respInfo.getStoreRespId());
                setStoreResposableRole(respInfo.getResponsableRole());
                setObservationResponsability(respInfo.getObservationResponsability());
                setStore(respInfo.getStoreId());
//            setObservationResponsability();
            } else {
                String msg = rb.getString("ui_msg_errorNoStoreConfigured");
                JsfUtil.addErrorMessage(msg);
            }
        }else{
            String msg = rb.getString("ui_msg_errorNoLocationHistSelected");
                JsfUtil.addErrorMessage(msg);
        }
        //load code - retail stores
        //load code - store responsable
    }

    public void storeResponsableChange() {
        try {
            storeResponsableChange(getStore());
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorNoResponsableStore");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void storeResponsableChange(String store) {
        List<IprGenericListItem> responsablesByStore = new ArrayList<>();
        HashMap<String, IprGenericListItem> hash = new HashMap<>();
        if (store != null) {
            List<IprResponsableInfo> resp = iprLocationHistEjbRemote.getAllResponsableByStore(new BigInteger(store), FacesContext
                    .getCurrentInstance().getViewRoot().getLocale().getLanguage());
            IprGenericListItem nuevo;
            for (IprResponsableInfo actual : resp) {
                nuevo = new IprGenericListItem();
                StringBuffer sb = new StringBuffer();
                sb.append(actual.getStoreRespCode());
                sb.append(" - ");
                sb.append(actual.getStoreResponsable());
                nuevo.setLabel(sb.toString());
                nuevo.setValue(actual.getStoreRespId());
                nuevo.setSelected(actual);
                responsablesByStore.add(nuevo);
                hash.put(actual.getStoreRespId(), nuevo);
            }
            setResponsableLocationList(resp);
            setResponsableLocation(responsablesByStore);
            setResponsableLocationH(hash);

            //load code -rol responsable
            //load observation responsability
        } else {
            //error, store not selected
            String msg = rb.getString("ui_msg_errorNoStoreSelected");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void setDefaultValues() {
        if (getResponsableLocationList() != null && !getResponsableLocationList().isEmpty()) {
            //Set init values
            setObservationResponsability(getResponsableLocationList().get(0).getObservationResponsability());
            setStoreResposableRole(getResponsableLocationList().get(0).getResponsableRole());
            setStoreResposable(getResponsableLocationList().get(0).getStoreRespId());
        }
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

    public void finishEditTabChange(TabChangeEvent e) {
        //select finish tab
        if (e.getTab().getId().equals("endUpdateProductTab")) {
            initFinishProductList();
        }
    }

    public void initFinishProductList() {
        List<BigInteger> statusList = new ArrayList<>();
        statusList.add(new BigInteger("2"));
        statusList.add(new BigInteger("3"));
        setProductsToFinish(iprTrxLineItmEjbRemote.findProductByStatus(statusList, FacesContext
                .getCurrentInstance().getViewRoot().getLocale().getLanguage()));
    }

    public void finishProductRepossessedReady() {
        try {
            iprTrxLineItmEjbRemote.finishReadyProducts();
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg-finishAllProductsReady-wb').hide();");
            String msg = rb.getString("ui_msg_productsFinishedSuccessfuly");
            JsfUtil.addSuccessMessage(msg);
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_errorFinishingProductList");
            JsfUtil.addErrorMessage(msg);
        }
    }

    public void finishProductRepossessedSelected() {
        try {
            if (getProductsToFinish() != null && !getProductsToFinish().isEmpty()) {
                iprTrxLineItmEjbRemote.finishReadyProducts();
                initFinishProductList();
                setEnableTab1(false);
                setTabIndex(0);
                search();
                String msg = rb.getString("ui_msg_productsFinishedSuccessfuly");
                JsfUtil.addSuccessMessage(msg);
            } else {
                String msg = rb.getString("ui_msg_errorNoProductsToFinish");
                JsfUtil.addErrorMessage(msg);
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg-confirmSelectedProducts-wb').hide();");
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_trxProductFinishError");
            JsfUtil.addErrorMessage(msg);
            logger.error(msg, e);
        }
    }

    // getter and setter session variables 
    public UnUser getUniUser() {
        return uniUser;
    }

    public void setUniUser(UnUser uniUser) {
        this.uniUser = uniUser;
    }

    public Date getCurrentDate() {
        return new Date();
    }

}
