/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model;

/**
 *
 * @author francisco_medina
 */
public class ParameterData {

    private String name;
    private Object value;
    private Class valueClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Class getValueClass() {
        return valueClass;
    }

    public void setValueClass(Class valueClass) {
        this.valueClass = valueClass;
    }

    //evalua los parametros booleanos y devuelve la operacion OR de ellos
    public static Boolean evaluateFilters(ParameterData... parameters) {
        Boolean ret = null;
        if (parameters != null) {
            for (ParameterData actual : parameters) {
                if (actual != null) {
                    Object obj = actual.getValue();
                    if (obj instanceof Boolean) {
                        Boolean valueParse = (Boolean) obj;
                        //inicializa el retorno
                        if (ret == null) {
                            ret = false;
                        }
                        ret |= valueParse;
                    }
                }
            }
        }
        return ret;
    }

}
