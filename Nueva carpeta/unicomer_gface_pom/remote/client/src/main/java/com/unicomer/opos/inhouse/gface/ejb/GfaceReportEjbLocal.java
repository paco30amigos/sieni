package com.unicomer.opos.inhouse.gface.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.ejb.Remote;

import org.primefaces.model.StreamedContent;

import com.unicomer.opos.inhouse.adminHoTh.entities.OthAnalysisCenterEbs;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthChains;
import com.unicomer.opos.inhouse.adminHoTh.entities.OthCostCenterEbs;
import com.unicomer.opos.inhouse.security.entities.UnUser;

@Remote
public interface GfaceReportEjbLocal {
	StreamedContent callReportStatus(Date stDate, Date endDate,UnUser uniUser, String format,String storeId,String fscl_ct_ide,BigDecimal corr_num,String docType,BigInteger rgs_st_code);
	StreamedContent callTaxBookInconcistencyConsolidated(OthChains chain, OthAnalysisCenterEbs analysisCenter, OthCostCenterEbs costCenter, Date stDate, Date endDate, UnUser uniUser, BigDecimal batchNumber, String format);
	StreamedContent callTaxBookInconcistencyDetailed(OthChains chain, OthAnalysisCenterEbs analysisCenter, OthCostCenterEbs costCenter, Date stDate, Date endDate, UnUser uniUser, BigDecimal batchNumber, String format);
	
}
