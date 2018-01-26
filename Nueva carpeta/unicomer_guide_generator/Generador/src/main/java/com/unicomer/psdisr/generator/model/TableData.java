/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model;

import com.unicomer.psdisr.generator.model.utils.Constants;
import java.util.Objects;

/**
 *
 * @author francisco_medina
 */
public class TableData {

    private String tableName;
    private String tableSchema;
    private String remark;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    @Override
    public boolean equals(Object t) {
        boolean ret = false;
        if (t != null) {
            if (t instanceof TableData) {
                TableData actual = ((TableData) t);
                if (this.getTableName().equals(actual.getTableName())
                        && this.getTableSchema().equals(actual.getTableSchema())) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.tableName);
        hash = 41 * hash + Objects.hashCode(this.tableSchema);
        return hash;
    }

    public String createTableScript(String baseScript, String colsDefinition) {
        StringBuilder pTableName = new StringBuilder(this.tableSchema).append(".").append(this.tableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                .replace(Constants.P_TABLE_COLUMNS, colsDefinition);
        return ret;
    }

    public String dropTableScript(String baseScript) {
        StringBuilder pTableName = new StringBuilder(this.tableSchema).append(".").append(this.tableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName);
        return ret;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
