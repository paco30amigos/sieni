package com.unicomer.opos.inhouse.gface.util;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

import com.unicomer.hr.utility.JsfUtil;
import com.unicomer.opos.inhouse.gface.pojo.ValidationPojo;

public class GfaceUtilities {

	public static boolean printErrores(List<ValidationPojo> errores) {
        boolean ret = false;
        for (ValidationPojo actual : errores) {
            if (actual.isResultado()) {
                ret = true;
                printMsj(actual.getMensaje(), actual.getTipo());
            }
        }
        return ret;
    }

    public static void printMsj(String msj, Severity severity) {
    	if(severity==FacesMessage.SEVERITY_ERROR){
    		JsfUtil.addErrorMessage(msj);
    	}
    	if(severity==FacesMessage.SEVERITY_INFO){
    		JsfUtil.addSuccessMessage(msj);
    	}
    	if(severity==FacesMessage.SEVERITY_WARN){
    		JsfUtil.addWarnMessage(msj);
    	}
    	//error por defecto
    	if(severity==null){
    		JsfUtil.addErrorMessage(msj);
    	}
    }
}
