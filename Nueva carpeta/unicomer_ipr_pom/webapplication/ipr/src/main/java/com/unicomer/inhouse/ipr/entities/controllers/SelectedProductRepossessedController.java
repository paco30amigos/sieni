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
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTransactionEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTrxLineItmEjbRemote;
import com.unicomer.hr.utility.JsfUtil;
import com.unicomer.inhouse.ipr.entities.controllers.form.SelectedProductRepossessedForm;
import com.unicomer.inhouse.ipr.entities.controllers.model.SelectedProductRepossessedModel;
import com.unicomer.inhouse.jndi.JNDIUnicomerIpr;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthAuditoryEjbRemote;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthCurrencyEjbLocal;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthCurrency;
import com.unicomer.opos.inhouse.ipr.entities.IprTransaction;
import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm;
import com.unicomer.opos.inhouse.ipr.pojos.IprLocationDet;
import com.unicomer.opos.inhouse.ipr.pojos.IprProduct;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsRepossessed;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "SelectedProductRepossessedController")
public class SelectedProductRepossessedController extends
        SelectedProductRepossessedForm {

    static Logger logger = Logger.getLogger(SelectedProductRepossessedController.class);

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

    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser uniUser;

    // Bundle de idioma
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.unicomer.inhouse.ipr.utilities.i18n.BundleIpr", FacesContext
            .getCurrentInstance().getViewRoot().getLocale());

    public BigInteger getOptionId() {
        return new BigInteger("57200");//OptionId from ADMIHSEC.UN_OPTION
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
        getSearchFields().setDateMin(new Date());
        getSearchFields().setDateMax(new Date());
    }

    public void search() {
        try {

            HashMap<String, Object> params = new HashMap<String, Object>();
            List<BigInteger> status=new ArrayList<>();
            status.add(BigInteger.ZERO);
            params.put("accNum", getSearchFields().getAccNum());
            params.put("transactionId", getSearchFields().getTrxNum());
            params.put("mainCtIde", getSearchFields().getMainIdeNum());
            params.put("fsclCtIde", getSearchFields().getFiscalIde());
            params.put("dateMin", getSearchFields().getDateMin());
            params.put("dateMax", getSearchFields().getDateMax());
            params.put("statusId", status);

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
        List<BigInteger> statusList = new ArrayList<>();
        statusList.add(new BigInteger("0"));
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
        getTrxSelected().setProductList(itm);
    }

    public void showTransactionDetail() {
        if (getTrxSelected() == null) {
            JsfUtil.addErrorMessage(rb.getString("ui_msg_errorSelectProductItemSelected"));
        } else {
            BigInteger transactionId = getTrxSelected().getIprTrx().getIprTransacId();
            getProductInformation(transactionId);
            setTabIndex(1);
            setEnableTab1(true);
        }
    }

    public void saveProductSelected() {
        try {
            if (getTrxSelected().getProductsSelected() != null && !getTrxSelected().getProductsSelected().isEmpty()) {
                List<IprTrxLineItm> toUpdate = new ArrayList<IprTrxLineItm>();
                for (IprProduct actual : getTrxSelected().getProductsSelected()) {
                    actual.getLineItem().setStatusId(BigInteger.ONE);//articulo recogido
                    toUpdate.add(actual.getLineItem());
                }
                iprTrxLineItmEjbRemote.merge(toUpdate);
                //update first table
//                search();
                //update detail information
                showTransactionDetail();

                JsfUtil.addSuccessMessage(rb.getString("ui_msg_selectProductSaveSuccessfuly"));
            } else {
                JsfUtil.addErrorMessage(rb.getString("ui_msg_errorNoSelectedItems"));
            }
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlg-confirmSelectedProducts-wb').hide();");
        } catch (Exception e) {
            String msg = rb.getString("ui_msg_selectProductErrorSaveSelectedProducts");
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
