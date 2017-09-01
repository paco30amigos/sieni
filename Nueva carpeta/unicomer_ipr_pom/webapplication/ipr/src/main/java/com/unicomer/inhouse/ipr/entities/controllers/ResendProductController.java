/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.inhouse.ipr.entities.controllers;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.unicomer.ejb.opos.inhouse.ipr.classes.IprStatusEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTransactionEjbRemote;
import com.unicomer.ejb.opos.inhouse.ipr.classes.IprTrxLineItmEjbRemote;
import com.unicomer.hr.utility.JsfUtil;
import com.unicomer.inhouse.ipr.entities.controllers.form.ResendProductsForm;
import com.unicomer.inhouse.ipr.entities.controllers.model.ResendProductModel;
import com.unicomer.inhouse.jndi.JNDIUnicomerIpr;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.OthAuditoryEjbRemote;
import com.unicomer.opos.inhouse.ipr.entities.IprStatus;
import com.unicomer.opos.inhouse.ipr.entities.IprTrxLineItm;
import com.unicomer.opos.inhouse.ipr.pojos.IprGenericListItem;
import com.unicomer.opos.inhouse.ipr.pojos.IprProductsResend;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "ResendProductController")
public class ResendProductController extends
        ResendProductsForm {

    static Logger logger = Logger.getLogger(ResendProductController.class);

    @Resource(lookup = JNDIUnicomerOthers.OthAuditoryEjbRemote)
    private OthAuditoryEjbRemote othAuditoryEjbRemote;

    @Resource(lookup = JNDIUnicomerIpr.IprTransactionEjbRemote)
    private IprTransactionEjbRemote iprTransactionEjbRemote;

    @Resource(lookup = JNDIUnicomerIpr.IprStatusEjbRemote)
    private IprStatusEjbRemote iprStatusEjbRemote;

    @Resource(lookup = JNDIUnicomerIpr.IprTrxLineItmEjbRemote)
    private IprTrxLineItmEjbRemote iprTrxLineItmEjbRemote;

    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser uniUser;

    // Bundle de idioma
    ResourceBundle rb = ResourceBundle.getBundle(
            "com.unicomer.inhouse.ipr.utilities.i18n.BundleIpr", FacesContext
            .getCurrentInstance().getViewRoot().getLocale());

    public BigInteger getOptionId() {
        return new BigInteger("57220");//OptionId from ADMIHSEC.UN_OPTION
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
        setSearchFields(new IprProductsResend());
        List<IprStatus> allStatus = iprStatusEjbRemote.findAll();
        List<BigInteger> statusAfterSend = new ArrayList<>();
        statusAfterSend.add(new BigInteger("5"));//Enviado
        statusAfterSend.add(new BigInteger("6"));//Productos procesados exitosamente en RI
        statusAfterSend.add(new BigInteger("7"));//Error al procesar productos en RI
        statusAfterSend.add(new BigInteger("8"));//Reenviado
        setStatusList(new ArrayList<IprGenericListItem>());
        IprGenericListItem nuevo;
        for (IprStatus actual : allStatus) {
            if (statusAfterSend.contains(actual.getStatusId())) {
                nuevo = new IprGenericListItem();
                nuevo.setLabel(actual.getStatusDesc());
                nuevo.setSelected(actual);
                nuevo.setValue(actual.getStatusId().toString());
                getStatusList().add(nuevo);
            }
        }
        if (!getStatusList().isEmpty()) {
            getSearchFields().setStatusId(getStatusList().get(0).getValue());
        }else{
            getSearchFields().setStatusId(null);
        }
    }

    public void search() {
        HashMap<String, Object> params = new HashMap<String, Object>();

        params.put("locale", FacesContext
                .getCurrentInstance().getViewRoot().getLocale().getLanguage());
        params.put("batchId", getSearchFields().getBatchNum());
        if (getSearchFields().getTransactionId() != null && !getSearchFields().getTransactionId().isEmpty()) {
            params.put("transactionId", getSearchFields().getTransactionId());
        }
        params.put("dateMin", getSearchFields().getDateMin());
        params.put("dateMax", getSearchFields().getDateMax());
        params.put("statusId", new BigInteger(getSearchFields().getStatusId()));

        List<IprProductsResend> trx = iprTransactionEjbRemote
                .findProductSendByFilters(params);
        HashMap<String, IprProductsResend> hash = new HashMap<>();

        setEnableTab1(false);
        setTabIndex(0);

//        for (IprProductsResend actual : trx) {
//            hash.put(actual.getBatchDetId().toString(), actual);
//        }
        setResendProductModel(new ResendProductModel(trx, "getBatchDetId"));
//        getResendProductModel().setHash(hash);
    }

    public void resendSelected() {
        if (getTrxSelected() == null || getTrxSelected().isEmpty()) {
            JsfUtil.addErrorMessage(rb.getString("ui_msg_resendNoSelectedItems"));
        } else {
            try {
                List<BigInteger> productDistinct = new ArrayList<>();
                for (IprProductsResend actual : getTrxSelected()) {
                    if (!productDistinct.contains(actual.getLineItemId())) {
                        productDistinct.add(actual.getLineItemId());
                    }
                }

                //get products to update
                List<IprTrxLineItm> items = iprTrxLineItmEjbRemote.findByLineItemId(productDistinct);
                for (IprTrxLineItm actual : items) {
                    actual.setStatusId(new BigInteger("4"));
                }
                iprTrxLineItmEjbRemote.merge(items);
                search();
            } catch (Exception e) {
                String error = rb.getString("ui_msg_resendError");
                JsfUtil.addErrorMessage(rb.getString("ui_msg_resendError"));
                logger.error(error, e);
            }
            JsfUtil.addSuccessMessage(rb.getString("ui_msg_resendSuccess"));
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
