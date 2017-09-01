/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.opos.inhouse.gface.util.validations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import javax.annotation.Resource;
import com.unicomer.inhouse.jndi.JNDIUnicomerGface;
import com.unicomer.inhouse.gface.model.util.ExcelParser;
import com.unicomer.opos.inhouse.gface.ejb.GfaceAssocDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceDtlDocEjbLocal;
import com.unicomer.opos.inhouse.gface.ejb.GfaceHdrDocEjbLocal;
import com.unicomer.opos.inhouse.gface.pojo.AssocDocPojo;
import com.unicomer.opos.inhouse.gface.pojo.DtlDocPojo;
import com.unicomer.opos.inhouse.gface.pojo.ErrorExcelPojo;
import com.unicomer.opos.inhouse.gface.pojo.HdrDocPojo;
import com.unicomer.opos.inhouse.gface.pojo.ValidationPojo;

/**
 *
 * @author francisco_medina
 */
@Stateless
public class GfaceFormatValidationEjbLocalImpl implements GfaceFormatValidationEjbLocal {

	@Resource(lookup = JNDIUnicomerGface.GfaceHdrDocEjbLocal)
    private GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal;
	@Resource(lookup = JNDIUnicomerGface.GfaceDtlDocEjbLocal)
    private GfaceDtlDocEjbLocal gfaceDtlDocEjbLocal;
	@Resource(lookup = JNDIUnicomerGface.GfaceAssocDocEjbLocal)
    private GfaceAssocDocEjbLocal gfaceAssocDocEjbLocal;

    public boolean formatValidation() {
        boolean ban = true;
        List<ValidationPojo> test = new ArrayList<ValidationPojo>();
//        test.add(new ValidationPojo("Formato de encabezado no valido", validateHeader(), new BigInteger("1")));
//        test.add(new ValidationPojo("Formato de detalle no valido", validateDetail(), new BigInteger("2")));
        //test.add(new ValidationPojo("Formato de documentos", validateDocuments(), new BigInteger("3")));
        test.add(new ValidationPojo("Formato de Totales", validateTotals(), new BigInteger("3")));
        test.add(new ValidationPojo("Tipo de dato no valido", validateDataType(), new BigInteger("4")));
        return ban;
    }
    
    public List<ValidationPojo> validateHeader(Object key){
    	List<ValidationPojo> val=validateHeader_(key);
    	return val;
    }

    public List<ValidationPojo> validateHeader_(Object key) {
    	List<ValidationPojo> ret=new ArrayList<ValidationPojo>();    	
    	Object[] data = (Object[]) key;
        Object o = (Object) data[0];
        List<Object> id = (List<Object>) data[1];
        
        StringBuffer sb=new StringBuffer();
        HdrDocPojo nuevoHdr = (HdrDocPojo) o;
        ret.add(new ValidationPojo(sb.toString() + ", No se ha ingresado 'Fecha de Documento'", nuevoHdr.getDocDate() == null));
        ret.add(new ValidationPojo(sb.toString() + ", No se ha ingresado 'Tipo de Documento'", nuevoHdr.getDocType() == null));
        //validar existencia en BD
        //validar id ingresado para cada registro
        if (id != null && !id.isEmpty()) {
            try {
                String stringValue = id.get(0).toString();
                Double doubleValue = Double.parseDouble(stringValue);
                Integer intValue = new Integer(doubleValue.intValue());
                BigInteger idCampo = new BigInteger(intValue.toString());
                if (gfaceHdrDocEjbLocal.find(idCampo) != null) {
                    ret.add(new ValidationPojo(sb.toString() + ", Identificador  no encontrado en la BD", false));
                }
            } catch (Exception e) {
                ret.add(new ValidationPojo(sb.toString() + ", Identificador no valido", false));
            }
        } else {
            ret.add(new ValidationPojo(sb.toString() + ", Identificador no valido", false));
        }
        return ret;
    }

    public List<ValidationPojo> validateDetail(Object key){
    	List<ValidationPojo> val=validateDetail_(key);
    	return val;
    }
    public List<ValidationPojo> validateDetail_(Object key) {
    	List<ValidationPojo> ret=new ArrayList<ValidationPojo>();
        Object[] data = (Object[]) key;
        Object o = (Object) data[0];
        List<Object> id = (List<Object>) data[1];
        
        StringBuffer sb=new StringBuffer();
        DtlDocPojo nuevoDet = (DtlDocPojo) o;
        ret.add(new ValidationPojo(sb.toString() + ", No se ha ingresado 'Precio Unitario'", nuevoDet.getUnitPrice() == null));
        ret.add(new ValidationPojo(sb.toString() + ", No se ha ingresado 'Cantidad'", nuevoDet.getQty() == null));
        ret.add(new ValidationPojo(sb.toString() + ", No se ha ingresado 'Codigo de producto'", nuevoDet.getHistProdId() == null));
        //validar existencia en BD
        //validar id ingresado para cada registro
        if (id != null && !id.isEmpty()) {
            try {
                String stringValue = id.get(0).toString();
                Double doubleValue = Double.parseDouble(stringValue);
                Integer intValue = new Integer(doubleValue.intValue());
                BigInteger idCampo = new BigInteger(intValue.toString());
                if (gfaceDtlDocEjbLocal.find(idCampo) != null) {
                    ret.add(new ValidationPojo(sb.toString() + ", Identificador no encontrado en la BD", false));
                }
            } catch (Exception e) {
                ret.add(new ValidationPojo(sb.toString() + ", Identificador no valido", false));
            }
        } else {
            ret.add(new ValidationPojo(sb.toString() + ", Identificador no valido", false));
        }
        return ret;
    }

    public List<ValidationPojo> validateDocuments() {
        boolean ban = true;
        List<ValidationPojo> ret=new ArrayList<ValidationPojo>();
        return ret;
    }

    public boolean validateTotals() {
        boolean ban = true;
        return ban;
    }

    public boolean validateOthers() {
        boolean ban = true;
        return ban;
    }

    public boolean validateDataType( //            Object stringValue, Class<?> clase
            ) {
        boolean ban = true;

//        if (!clase.equals(value.getClass())) {
//            this.getClass().getClassLoader().loadClass(clase);
//            ban = false;
//        }
        return ban;
    }

    public List<ValidationPojo> validateCargaExcel(HashMap<String, Object> params, XSSFSheet hssfSheet,Object facesContext) {
        boolean ban = true;
        List<ValidationPojo> ret = new ArrayList<ValidationPojo>();
        try {
            ExcelParser eu = new ExcelParser();
            List<ErrorExcelPojo> msjErrores = eu.validateExcelSheetToObject(hssfSheet, params);
            StringBuffer sb = new StringBuffer();
            for (ErrorExcelPojo actual : msjErrores) {
                if (actual.getRow() != null) {
                    sb = new StringBuffer();
                    sb.append("Tipo de dato no valido para: ");
                    sb.append("Fila=");
                    sb.append(actual.getRow());
                    sb.append(", Columna=");
                    sb.append(actual.getColumn());
                    sb.append(", Tipo de Dato=");
                    sb.append(actual.getDataType());
                    sb.append(", Valor=");
                    sb.append(actual.getValue().toString());
                    ret.add(new ValidationPojo(sb.toString(), true));//true xq si es error
                } else {
                    //mensaje que no son tipo de datos
                    ret.add(new ValidationPojo(actual.getMsj(), true));//true xq si es error
                }
            }
            if (msjErrores == null || msjErrores.isEmpty()) {
                //validar campos obligatorios
                List<Object> res = eu.getExcelSheetToObject(hssfSheet, params);
                int i = 1;
                for (Object key : res) {
                    sb = new StringBuffer();
//                    eu.getExcelSheetToObject(hssfSheet, params);
                    Object[] data = (Object[]) key;
                    Object o = (Object) data[0];
                    List<Object> id = (List<Object>) data[1];
                    sb.append("Fila=");
                    sb.append(i);
                    //validar datos obligatorios
                    if (o instanceof HdrDocPojo) {
                        
                    }
                    if (o instanceof DtlDocPojo) {
                        
                    }
                    if (o instanceof AssocDocPojo) {
                        AssocDocPojo nuevoDoc = (AssocDocPojo) o;
                        ret.add(new ValidationPojo(sb.toString() + ", No se ha ingresado 'Tipo de Documento'", nuevoDoc.getDocTy() == null));
                        //validar existencia en BD
                        //validar id ingresado para cada registro
                        if (id != null && !id.isEmpty()) {
                            try {
                                String stringValue = id.get(0).toString();
                                Double doubleValue = Double.parseDouble(stringValue);
                                Integer intValue = new Integer(doubleValue.intValue());
                                BigInteger idCampo = new BigInteger(intValue.toString());
                                if (gfaceAssocDocEjbLocal.find(idCampo) != null) {
                                    ret.add(new ValidationPojo(sb.toString() + ", Identificador no encontrado en la BD", false));
                                }
                            } catch (Exception e) {
                                ret.add(new ValidationPojo(sb.toString() + ", Identificador no valido", false));
                            }
                        } else {
                            ret.add(new ValidationPojo(sb.toString() + ", Identificador no valido", false));
                        }
                    }
                    i++;
                }
            }
        } catch (Exception e) {
            ban = false;
        }
//        if(!ValidationPojo.isValid(ret)){
//            ValidationPojo.printErrores(ret);
//            ban=false;
//        }
        
        return ret;
    }

    public boolean validarTipoDatoExcel() {
        boolean ban = true;
        return ban;
    }

    public GfaceHdrDocEjbLocal getGfaceHdrDocEjbLocal() {
        return gfaceHdrDocEjbLocal;
    }

    public void setGfaceHdrDocEjbLocal(GfaceHdrDocEjbLocal gfaceHdrDocEjbLocal) {
        this.gfaceHdrDocEjbLocal = gfaceHdrDocEjbLocal;
    }

    public GfaceDtlDocEjbLocal getGfaceDtlDocEjbLocal() {
        return gfaceDtlDocEjbLocal;
    }

    public void setGfaceDtlDocEjbLocal(GfaceDtlDocEjbLocal gfaceDtlDocEjbLocal) {
        this.gfaceDtlDocEjbLocal = gfaceDtlDocEjbLocal;
    }

    public GfaceAssocDocEjbLocal getGfaceAssocDocEjbLocal() {
        return gfaceAssocDocEjbLocal;
    }

    public void setGfaceAssocDocEjbLocal(GfaceAssocDocEjbLocal gfaceAssocDocEjbLocal) {
        this.gfaceAssocDocEjbLocal = gfaceAssocDocEjbLocal;
    }
}
