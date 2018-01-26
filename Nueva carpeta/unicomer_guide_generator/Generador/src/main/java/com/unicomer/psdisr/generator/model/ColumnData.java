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
public class ColumnData {

    private String tableSchema;
    private String tableName;
    private String columnName;
    private String dataType;
    private String typeName;
    private String columnSize;
    private String decimalDigits;
    private String numPrecRadix;
    private boolean nullable;
    private String remark;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }

    public String getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(String columnSize) {
        this.columnSize = columnSize;
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(String decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public String getNumPrecRadix() {
        return numPrecRadix;
    }

    public void setNumPrecRadix(String numPrecRadix) {
        this.numPrecRadix = numPrecRadix;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object t) {
        boolean ret = false;
        if (t instanceof ColumnData) {
            ColumnData actual = ((ColumnData) t);
            if (this.getTableSchema().equals(actual.getTableSchema())
                    && this.getTableName().equals(actual.getTableName())
                    && this.getColumnName().equals(actual.getColumnName())
                    && ((this.getColumnSize() == null && actual.getColumnSize() == null) || this.getColumnSize().equals(actual.getColumnSize()))
                    && ((this.getDecimalDigits() == null && actual.getDecimalDigits() == null) || this.getDecimalDigits().equals(actual.getDecimalDigits()))
                    && ((this.getNumPrecRadix() == null && actual.getNumPrecRadix() == null) || this.getNumPrecRadix().equals(actual.getNumPrecRadix()))
                    && this.getDataType().equals(actual.getDataType())) {
                ret = true;
            }
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.tableSchema);
        hash = 71 * hash + Objects.hashCode(this.tableName);
        hash = 71 * hash + Objects.hashCode(this.columnName);
        hash = 71 * hash + Objects.hashCode(this.dataType);
        hash = 71 * hash + Objects.hashCode(this.columnSize);
        hash = 71 * hash + (this.nullable ? 1 : 0);
        return hash;
    }

    public String createColumnScript(String baseScript, String dataType, String columnLenght, String nullable) {
        nullable = nullable != null ? nullable : "";
        StringBuilder columnLenghtString = new StringBuilder();
        if (columnLenght != null && !columnLenght.isEmpty()) {
            columnLenghtString.append("(").append(columnLenght).append(")");
            columnLenght = columnLenghtString.toString();
        } else {
            columnLenght = "";
        }

        StringBuilder pTableName = new StringBuilder(this.tableSchema).append(".").append(this.tableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                .replace(Constants.P_COLUMN_NAME, this.columnName)
                .replace(Constants.P_COLUMN_DATA_TYPE, dataType)
                .replace(Constants.P_COLUMN_LENGHT, columnLenght)
                .replace(Constants.P_COLUMN_NULLABLE, nullable);
        return ret;
    }

    public String alterColumnScript(String baseScript, String dataType, String columnLenght, String nullable) {

        nullable = nullable != null ? nullable : "";
        StringBuilder columnLenghtString = new StringBuilder();
        if (columnLenght != null) {
            columnLenghtString.append("(").append(columnLenght).append(")");
            columnLenght = columnLenghtString.toString();
        } else {
            columnLenght = "";
        }

        StringBuilder pTableName = new StringBuilder(this.tableSchema).append(".").append(this.tableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                .replace(Constants.P_COLUMN_NAME, this.columnName)
                .replace(Constants.P_COLUMN_DATA_TYPE, dataType)
                .replace(Constants.P_COLUMN_LENGHT, columnLenght)
                .replace(Constants.P_COLUMN_NULLABLE, nullable);
        return ret;
    }

    public String dropColumnScript(String baseScript) {
        StringBuilder pTableName = new StringBuilder(this.tableSchema).append(".").append(this.tableName);
        String ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                .replace(Constants.P_COLUMN_NAME, this.columnName);
        return ret;
    }

    public String createColumnRemarksScript(String baseScript, String remarks) {
        String ret = "";
        if (remarks != null) {
            StringBuilder pTableName = new StringBuilder(this.tableSchema).append(".").append(this.tableName);
            ret = baseScript.replace(Constants.P_TABLE_NAME, pTableName)
                    .replace(Constants.P_COLUMN_NAME, this.columnName)
                    .replace(Constants.P_COLUMN_REMARKS, remarks);
        }
        return ret;
    }

    public String createColumnForTableScript(String baseScript, String dataType, String columnLenght, String nullable) {
        nullable = nullable != null ? nullable : "";
        StringBuilder columnLenghtString = new StringBuilder();
        if (columnLenght != null && !columnLenght.isEmpty()) {
            columnLenghtString.append("(").append(columnLenght).append(")");
            columnLenght = columnLenghtString.toString();
        } else {
            columnLenght = "";
        }

        String ret = baseScript.replace(Constants.P_COLUMN_NAME, this.columnName)
                .replace(Constants.P_COLUMN_DATA_TYPE, dataType)
                .replace(Constants.P_COLUMN_LENGHT, columnLenght)
                .replace(Constants.P_COLUMN_NULLABLE, nullable);
        return ret;
    }
}
