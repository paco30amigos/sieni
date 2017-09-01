package com.unicomer.opos.inhouse.gface.ejb.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.xml.rpc.ServiceException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.apache.log4j.Logger;

import com.oracle.xmlns.oxp.service.PublicReportService.AccessDeniedException;
import com.oracle.xmlns.oxp.service.PublicReportService.InvalidParametersException;
import com.oracle.xmlns.oxp.service.PublicReportService.OperationFailedException;
import com.oracle.xmlns.oxp.service.PublicReportService.ParamNameValue;
import com.oracle.xmlns.oxp.service.PublicReportService.ReportRequest;
import com.oracle.xmlns.oxp.service.PublicReportService.ReportResponse;
import com.unicomer.inhouse.jndi.JNDIUnicomerOthers;
import com.unicomer.inhouse.jndi.JNDIUnicomerSecurity;
import com.unicomer.opos.inhouse.admiHoTh.ejbs.UnParameterImplEjbLocal;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthAnalysisCenterEbs;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthChains;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthCostCenterEbs;
import com.unicomer.opos.inhouse.bi.util.BiReporterUtil;
import com.unicomer.opos.inhouse.gface.ejb.GfaceOthersMigrationEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceReportEjbLocal;
import com.unicomer.opos.inhouse.security.entities.UnCountry;
import com.unicomer.opos.inhouse.security.entities.UnUser;
import com.unicomer.opos.inhouse.services.ejbs.SystemParametersEjbLocal;

@Transactional(value = TxType.REQUIRED)
@Stateless(name = "GfaceReportEjbLocalImpl", mappedName = "ejb/GfaceReportEjbLocalImpl")
@Remote(GfaceReportEjbLocal.class)
public class GfaceReportEjbLocalImpl implements GfaceReportEjbLocal {

	private static final Logger logger = Logger.getLogger(GfaceReportEjbLocalImpl.class);
	
	//******************************************************************
    // Vars
    //******************************************************************
	//Reporting vars
	private String UGBP;
	private String PGBP;
    private String sessionBi;
	private BiReporterUtil biRepUtil;
	
	//******************************************************************
    // EJBs
    //******************************************************************
	@Resource(lookup = JNDIUnicomerOthers.UnParameterImplEjbLocal)
	private UnParameterImplEjbLocal parameterImplFacade;
    
    @Resource(lookup = JNDIUnicomerSecurity.SystemParametersEjbLocal)
    private SystemParametersEjbLocal systemParamsFacade;
    
	//Default Constructor
	public GfaceReportEjbLocalImpl() {}

	//******************************************************************
  	// REPORTS
  	//******************************************************************	
	@Override
	public StreamedContent callReportStatus(Date stDate, Date endDate,
			UnUser uniUser, String format, String storeId, String fscl_ct_ide,
			BigDecimal corr_num, String docType, BigInteger rgs_st_code) {
		
		String formattedstDate = new SimpleDateFormat("dd/MM/yyyy").format(stDate);
  		String formattedendDate = new SimpleDateFormat("dd/MM/yyyy").format(endDate);
  		
		// String voucherName = (withholdingRate==13) ? "iva" : "isr"; 
		ParamNameValue[] paramNameValue = new ParamNameValue[7];
		int i=0;
		//Add the date ini		
		paramNameValue[i] = new ParamNameValue();
		paramNameValue[i].setName("FECHA_INI");
		paramNameValue[i].setDataType("Date");
		paramNameValue[i].setDateFormatString("dd/MM/yyyy");
		paramNameValue[i].setValues(new String[] { formattedstDate });
		i++;

		// Add the date end
		paramNameValue[i] = new ParamNameValue();
		paramNameValue[i].setName("FECHA_FIN");
		paramNameValue[i].setDataType("Date");
		paramNameValue[i].setDateFormatString("dd/MM/yyyy");
		paramNameValue[i].setValues(new String[] { formattedendDate });
		i++;
		
				
		//Add store id
		paramNameValue[i] = new ParamNameValue();
		paramNameValue[i].setName("STORE_ID");
		if(storeId!=null&&!storeId.isEmpty()){
		paramNameValue[i].setValues(new String[]{storeId});
		}else{
			paramNameValue[i].setUseNullForAll(true);
		}
		i++;
		
		//Add CUSTOMER NIT
		paramNameValue[i] = new ParamNameValue();
		paramNameValue[i].setName("FSCL_CT_IDE");
		if(fscl_ct_ide!=null&&!fscl_ct_ide.isEmpty()){
			paramNameValue[i].setValues(new String[]{fscl_ct_ide});
		}else{
			paramNameValue[i].setUseNullForAll(true);
		}
		i++;		
		
		//Add DOC TYPE
		paramNameValue[i] = new ParamNameValue();
		paramNameValue[i].setName("CORR_NUM");
		if(corr_num!=null){
		paramNameValue[i].setValues(new String[]{corr_num.toString()});
		}else{
			paramNameValue[i].setUseNullForAll(true);
		}
		i++;
		
		//Add DOC TYPE
		paramNameValue[i] = new ParamNameValue();
		paramNameValue[i].setName("DOC_TYPE");
		if(docType!=null&&!docType.isEmpty()){
		paramNameValue[i].setValues(new String[]{docType});
		}else{
			paramNameValue[i].setUseNullForAll(true);
		}
		i++;
		
		//Add DOC TYPE
		paramNameValue[i] = new ParamNameValue();
		paramNameValue[i].setName("RGS_ST_CODE");
		if(rgs_st_code!=null){
			paramNameValue[i].setValues(new String[]{rgs_st_code.toString()});
		}else{
			paramNameValue[i].setUseNullForAll(true);
		}
		i++;	
		Long correlat=new Date().getTime();
		return callReport("/INHOUSE/GFACE/RPT/RP_StatusDoc.xdo", "StatusDoc-"+correlat+".", paramNameValue, uniUser.getCurrentCountry(),format);
	}
	
	//******************************************************************
  	// GENERAL FUNCTIONS
  	//******************************************************************
	//General Function for calling reports
	public StreamedContent callReport(String reportAbsolutePath, String reportName ,ParamNameValue[] paramNameValue, UnCountry ctry, String format){		
		ReportRequest req = new ReportRequest(); //Instantiate ReportRequest object
		req.setAttributeFormat(format); //Set a report output format
		req.setAttributeLocale("en-US"); //Set a report locale
		req.setAttributeTemplate("Simple"); //Set a layout template name
		req.setReportAbsolutePath(reportAbsolutePath); //Set a report path
		req.setSizeOfDataChunkDownload(-1);		 
		req.setParameterNameValues(paramNameValue); //Set the params
		
		ReportResponse res = new ReportResponse(); //Instantiate ReportResponse object		
		
		try {
			loginBI(ctry);
			res = biRepUtil.getPublicReportService().runReportInSession(req, sessionBi);			
						
		} catch (InvalidParametersException e) {logger.error(e, e);}
		catch (AccessDeniedException e) {logger.error(e, e);}
		catch (OperationFailedException e) {logger.error(e, e);}
		catch (RemoteException e) {logger.error(e, e);}
		catch (Exception e) {logger.error(e, e);}
		
		InputStream  bis = new ByteArrayInputStream(res.getReportBytes());
		
		return new DefaultStreamedContent(bis, res.getReportContentType(), reportName+getExtension(format));

	}
	
	
	//Private Functions 
	private void loginBI(UnCountry ctry) throws ServiceException{
		biRepUtil = new BiReporterUtil();
		//1. Get the User Params 4 BI connection
		String ambientCode = systemParamsFacade.getSystemParametersConfig().get("un_actual_ambient_code").toString();
		UGBP = parameterImplFacade.find("UGBP", ambientCode, "Caja Chica", ctry.getCountryCode()).getParamValue();
		PGBP = parameterImplFacade.find("PGBP", ambientCode, "Caja Chica", ctry.getCountryCode()).getParamValue();		
		biRepUtil.setBiUser(UGBP);
		biRepUtil.setBiPass(PGBP);
		
		try {
		  sessionBi = biRepUtil.login();
		} catch (AccessDeniedException e) {
			logger.error(e, e);
		} catch (RemoteException e) {
			logger.error(e, e);
		}
	}
	
	private String getExtension(String reportFormat) {
		String extension = "";
		
		if (reportFormat.equals("pdf"))
			extension = "pdf";
		
		if (reportFormat.equals("rtf"))
			extension = "rtf";
		
		if (reportFormat.equals("excel"))
			extension ="xls"; //Changed xlsx to xls
		
		if (reportFormat.equals("xls"))
			extension ="xls";
		
		if (reportFormat.equals("html"))
			extension = "html";

		if (reportFormat.equals("xml"))
			extension = "xml";		
		
		return extension;
	}

	@Override
	public StreamedContent callTaxBookInconcistencyConsolidated(
			OthChains chain, OthAnalysisCenterEbs analysisCenter,
			OthCostCenterEbs costCenter, Date stDate, Date endDate,
			UnUser uniUser, BigDecimal batchNumber, String format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StreamedContent callTaxBookInconcistencyDetailed(OthChains chain,
			OthAnalysisCenterEbs analysisCenter, OthCostCenterEbs costCenter,
			Date stDate, Date endDate, UnUser uniUser, BigDecimal batchNumber,
			String format) {
		// TODO Auto-generated method stub
		return null;
	}	
	
}
