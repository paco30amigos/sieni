package com.unicomer.opos.inhouse.gface.util.validations;

import java.util.HashMap;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.unicomer.opos.inhouse.gface.pojo.ValidationPojo;

public interface GfaceFormatValidationEjbLocal {
	public List<ValidationPojo> validateCargaExcel(HashMap<String, Object> params, XSSFSheet hssfSheet,Object facesContext);
	public List<ValidationPojo> validateHeader(Object key);
	public List<ValidationPojo> validateDetail(Object key);
	public List<ValidationPojo> validateDocuments();
}
