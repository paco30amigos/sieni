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
public class ConstraintData {

    private String pkTableSchema;
    private String pkTableName;
    private String constraintName;
    private String pkColumnName;
    private String fkTableSchema;
    private String fkTableName;
    private String fkColumnName;

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public void setTableSchema(String tableSchema) {
        this.pkTableSchema = tableSchema;
    }

    public String getFkTableSchema() {
        return fkTableSchema;
    }

    public void setFkTableSchema(String fkTableSchema) {
        this.fkTableSchema = fkTableSchema;
    }

    public String getFkTableName() {
        return fkTableName;
    }

    public void setFkTableName(String fkTableName) {
        this.fkTableName = fkTableName;
    }

    public String getFkColumnName() {
        return fkColumnName;
    }

    public void setFkColumnName(String fkColumnName) {
        this.fkColumnName = fkColumnName;
    }

    public String getPkTableSchema() {
        return pkTableSchema;
    }

    public void setPkTableSchema(String pkTableSchema) {
        this.pkTableSchema = pkTableSchema;
    }

    public String getPkTableName() {
        return pkTableName;
    }

    public void setPkTableName(String pkTableName) {
        this.pkTableName = pkTableName;
    }

    public String getPkColumnName() {
        return pkColumnName;
    }

    public void setPkColumnName(String pkColumnName) {
        this.pkColumnName = pkColumnName;
    }

    @Override
    public boolean equals(Object t) {
        boolean ret = false;
        if (t != null) {
            if (t instanceof ConstraintData) {
                ConstraintData actual = ((ConstraintData) t);
                if (this.getPkColumnName().equals(actual.getPkColumnName())
                        && this.getConstraintName().equals(actual.getConstraintName())
                        && this.getPkTableName().equals(actual.getPkTableName())
                        && this.getPkTableSchema().equals(actual.getPkTableSchema())
                        //condiciones no obligatorias
                        && ((this.getFkColumnName() == null && actual.getFkColumnName() == null)
                        || (this.getFkColumnName() != null && this.getFkColumnName().equals(actual.getFkColumnName())))
                        && ((this.getFkTableName() == null && actual.getFkTableName() == null)
                        || (this.getFkTableName() != null && this.getFkTableName().equals(actual.getFkTableName())))
                        && ((this.getFkTableSchema() == null && actual.getFkTableSchema() == null)
                        || (this.getFkTableSchema() != null && this.getFkTableSchema().equals(actual.getFkTableSchema())))) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.pkTableSchema);
        hash = 89 * hash + Objects.hashCode(this.pkTableName);
        hash = 89 * hash + Objects.hashCode(this.constraintName);
        hash = 89 * hash + Objects.hashCode(this.pkColumnName);
        return hash;
    }

    public String createPkScript(String baseScript) {
        StringBuilder pTableName = new StringBuilder(this.pkTableSchema).append(".").append(this.pkTableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                .replace(Constants.P_CONSTRAINT_NAME, this.getConstraintName())
                .replace(Constants.P_CONSTRAINT_COLUMNS, this.getPkColumnName());
        return ret;
    }

    public String dropConstraintScript(String baseScript, String constrName) {
        StringBuilder pTableName = new StringBuilder(this.pkTableSchema).append(".").append(this.pkTableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                .replace(Constants.P_CONSTRAINT_NAME, constrName);
        return ret;
    }

    public String createFkScript(String baseScript) {
        StringBuilder pTableName = new StringBuilder(this.pkTableSchema).append(".").append(this.pkTableName);
        StringBuilder pParentTableName = new StringBuilder(this.fkTableSchema).append(".").append(this.fkTableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                .replace(Constants.P_CONSTRAINT_NAME, this.getConstraintName())
                .replace(Constants.P_COLUMN_NAME, this.getPkColumnName())
                .replace(Constants.P_PARENT_TABLE_NAME, pParentTableName)
                .replace(Constants.P_CONSTRAINT_COLUMNS, this.getFkColumnName());
        return ret;
    }

    public String grantDiferentSchemaFK(String baseScript) {
        StringBuilder pParentTableName = new StringBuilder(this.fkTableSchema).append(".").append(this.fkTableName);
        String ret = baseScript.replace(Constants.P_PARENT_TABLE_SCHEMA, this.getFkTableSchema())
                .replace(Constants.P_PARENT_TABLE_NAME, pParentTableName)
                .replace(Constants.P_CONSTRAINT_COLUMNS, this.getFkColumnName());
        return ret;
    }
}
