package com.unicomer.inhouse.ipr.entities.controllers.pojo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import javax.faces.model.ListDataModel;

/**
 *
 * @author francisco_medina
 * @param <T>
 */
public abstract class GenericHashListDataModel<T> extends ListDataModel<T> {

    private final Class<T> entityClass;
    HashMap<String, T> map;
    List<T> list;
    String methodGetIdName;
    Method methodGetId;

    public GenericHashListDataModel(Class<T> entityClass, List<T> list) {
        //for ListDataModel
        super(list);
        this.entityClass = entityClass;
        this.methodGetIdName = "getId";
        this.list = list;
        findIdConfig();
        setMapFromList();
    }

    public GenericHashListDataModel(Class<T> entityClass, List<T> list, String methodGetIdName) {
        //for ListDataModel
        super(list);
        this.entityClass = entityClass;
        this.methodGetIdName = methodGetIdName;
        this.list = list;
        findIdConfig();
        setMapFromList();
    }

    private void findIdConfig() {
        Method[] allMethods = entityClass.getMethods();
        for (Method actual : allMethods) {
            if (methodGetIdName.equals(actual.getName())) {
                if (Modifier.isPrivate(actual.getModifiers())) {
                    actual.setAccessible(true);
                }
                methodGetId = actual;
                break;
            }
        }
    }

    public Object getRowKey(T object) {
        Object value = null;
        try {
            value = methodGetId.invoke(object);
        } catch (Exception e) {
            //field not found

        }
        return value;
    }

    public T getRowData(String rowKey) {
        return map.get(rowKey);
    }

    private void setMapFromList() {
        map = new HashMap<>();
        if (list != null) {
            for (T actual : list) {
                String value = null;
                try {
                    value = (String) methodGetId.invoke(actual);
                    map.put(value, actual);
                } catch (Exception e) {
                    //id bad configured
                }
            }
        } else {
            map = null;
        }
    }
}
