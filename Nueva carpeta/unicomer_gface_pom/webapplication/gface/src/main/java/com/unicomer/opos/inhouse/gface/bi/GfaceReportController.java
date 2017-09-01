package com.unicomer.opos.inhouse.gface.bi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.annotation.Resource;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import org.primefaces.model.StreamedContent;
import org.apache.log4j.Logger;
import com.unicomer.hr.utility.JsfUtil;
import com.unicomer.opos.inhouse.gface.ejb.GfaceReportEjbLocal;
import com.unicomer.opos.inhouse.security.entities.UnUser;

@ManagedBean(name = "GfaceReportController")
@ViewScoped
public class GfaceReportController implements Serializable {
    //******************************************************************
    // VARS - Initialize the vars
    //******************************************************************
	private static final Logger logger = Logger.getLogger(GfaceReportController.class);

    private static final long serialVersionUID = 1L;

    private String reportFormat;
    private String applicationFormat;
    private String extension;
    private String typeRport;
    private String typeDoc;
    private String serie;
    private BigDecimal numero;
    private String nitComprador;
    private String tienda;
    private BigInteger status;

    //reports
    private StreamedContent reportStatus;
    private StreamedContent taxBookInconcistencyConsolidated;
    private StreamedContent taxBookInconcistencyDetailed;
    private StreamedContent taxBookInconcistency;
    
    //typeDoc,serie,numero,nitComprador,tienda,status

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public BigDecimal getNumero() {
        return numero;
    }

    public void setNumero(BigDecimal numero) {
        this.numero = numero;
    }

    public String getNitComprador() {
        return nitComprador;
    }

    public void setNitComprador(String nitComprador) {
        this.nitComprador = nitComprador;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    //Shared Report Parameters      
    private Date stDate;
    private Date endDate;

    //Message Bundle  	
    ResourceBundle rb;
    ResourceBundle rbPca;

    //******************************************************************
    // EJBs & Managed Properties
    //******************************************************************
    
    @Resource(lookup = JNDIUnicomerGface.GfaceReportEjbLocal)
    private GfaceReportEjbLocal gfaceReportEjbLocal;

    //Managed Properties 
    @ManagedProperty(value = "#{UnUserController.uniUser}")
    private UnUser uniUser;
    //******************************************************************
    // METHODS
    //******************************************************************

    @PostConstruct
    public void init() {
        //Load Messages Bundle
        rb = ResourceBundle.getBundle("com.unicomer.inhouse.utilities.i18n.Bundle", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        rbPca = ResourceBundle.getBundle("com.unicomer.opos.inhouse.gface.util.i18n.Bundlegface", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    }

    public void loadLists() {

    }

    //******************************************************************
    // Getters & Setters
    //******************************************************************
    public String getReportFormat() {
        return reportFormat;
    }

    public void setReportFormat(String reportFormat) {
        this.reportFormat = reportFormat;
    }

    public String getApplicationFormat() {

        if (reportFormat.equals("pdf")) {
            applicationFormat = "application/pdf";
        }

        if (reportFormat.equals("rtf")) {
            applicationFormat = "application/RTF";
        }

        if (reportFormat.equals("excel")) {
            applicationFormat = "application/vnd.ms-excel";
        }

        if (reportFormat.equals("html")) {
            applicationFormat = "text/html";
        }

        if (reportFormat.equals("xml")) {
            applicationFormat = "text/xml";
        }

        return applicationFormat;
    }

    public void setApplicationFormat(String applicationFormat) {
        this.applicationFormat = applicationFormat;
    }

    public String getExtension() {

        if (reportFormat.equals("pdf")) {
            extension = "pdf";
        }

        if (reportFormat.equals("rtf")) {
            extension = "rtf";
        }

        if (reportFormat.equals("excel")) {
            extension = "xlsx";
        }

        if (reportFormat.equals("xlsx")) {
            extension = "xlsx";
        }

        if (reportFormat.equals("html")) {
            extension = "html";
        }

        if (reportFormat.equals("xml")) {
            extension = "xml";
        }

        return extension;
    }

    //Reports
    
    public StreamedContent getTaxBookInconcistency() {
    	try {
        	//taxBookInconcistencyConsolidated=gfaceReportEjbLocal.callTaxBookInconcistencyConsolidated(stDate,endDate,uniUser,typeRport,tienda,nitComprador,numero,typeDoc,status);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(rb.getString("ui_error"), rbPca.getString("ui_reports_failed"));
            logger.error(e, e);
        }
        return taxBookInconcistencyConsolidated;
    }
    
    public StreamedContent getTaxBookInconcistencyConsolidated() {
    	try {
        	//taxBookInconcistencyConsolidated=gfaceReportEjbLocal.callTaxBookInconcistencyConsolidated(stDate,endDate,uniUser,typeRport,tienda,nitComprador,numero,typeDoc,status);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(rb.getString("ui_error"), rbPca.getString("ui_reports_failed"));
            logger.error(e, e);
        }
        return taxBookInconcistencyConsolidated;
    }
    
    public StreamedContent getTaxBookInconcistencyDetailed() {
    	try {
        	//taxBookInconcistencyDetailed=gfaceReportEjbLocal.callTaxBookInconcistencyDetailed(stDate,endDate,uniUser,typeRport,tienda,nitComprador,numero,typeDoc,status);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(rb.getString("ui_error"), rbPca.getString("ui_reports_failed"));
            logger.error(e, e);
        }
        return taxBookInconcistencyDetailed;
    }
    
    public StreamedContent getReportStatus() {
        try {
        	reportStatus=gfaceReportEjbLocal.callReportStatus(stDate,endDate,uniUser,reportFormat,tienda,nitComprador,numero,typeDoc,status);
        } catch (Exception e) {
            JsfUtil.addErrorMessage(rb.getString("ui_error"), rbPca.getString("ui_reports_failed"));
            logger.error(e, e);
        }
        return reportStatus;
    }

    public String getTypeRport() {
        return typeRport;
    }

    public void setTypeRport(String typeRport) {
        this.typeRport = typeRport;
    }

    public Date getStDate() {
        return stDate;
    }

    public void setStDate(Date stDate) {
        this.stDate = stDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public UnUser getUniUser() {
        return uniUser;
    }

    public void setUniUser(UnUser uniUser) {
        this.uniUser = uniUser;
    }

    public BigInteger getStatus() {
        return status;
    }

    public void setStatus(BigInteger status) {
        this.status = status;
    }

    public String getTypeDoc() {
        return typeDoc;
    }

    public void setTypeDoc(String typeDoc) {
        this.typeDoc = typeDoc;
    }
    public Date getFechaActual(){
    	return new Date();
    }
   

}
