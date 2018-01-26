/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model;

import java.util.Objects;

/**
 *
 * @author francisco_medina
 */
public class SequenceData {

    private String schema;
    private String sequenceName;
    private Long lastNumber;
    private Long incrementBy;
    private String tableSequence;
    private String columnTableSequence;

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public Long getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(Long lastNumber) {
        this.lastNumber = lastNumber;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public Long getIncrementBy() {
        return incrementBy;
    }

    public void setIncrementBy(Long incrementBy) {
        this.incrementBy = incrementBy;
    }

    public String getTableSequence() {
        return tableSequence;
    }

    public void setTableSequence(String tableSequence) {
        this.tableSequence = tableSequence;
    }

    public String getColumnTableSequence() {
        return columnTableSequence;
    }

    public void setColumnTableSequence(String columnTableSequence) {
        this.columnTableSequence = columnTableSequence;
    }

    @Override
    public boolean equals(Object t) {
        boolean ret = false;
        if (t != null) {
            if (t instanceof SequenceData) {
                SequenceData actual = ((SequenceData) t);
                if (this.getSequenceName().equals(actual.getSequenceName())
                        && this.getSchema().equals(actual.getSchema())
                        && this.getLastNumber().equals(actual.getLastNumber())
                        && this.getIncrementBy().equals(actual.getIncrementBy())) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.schema);
        hash = 59 * hash + Objects.hashCode(this.sequenceName);
        hash = 59 * hash + Objects.hashCode(this.lastNumber);
        return hash;
    }

    public String createSequenceScript(String baseScript, Long value, Long increment) {
        value = value != null ? value : this.lastNumber;
        increment = increment != null ? increment : incrementBy != null ? incrementBy : 1;
        StringBuilder seqName = new StringBuilder(this.schema).append(".").append(sequenceName);
        String ret = baseScript.replace("p_seq_name", seqName)
                .replace("p_seq_value", value.toString())
                .replace("p_seq_increment", increment.toString());
        return ret;
    }

    public String alterSequenceScript(String baseScript, Long increment) {
        increment = increment != null ? increment : incrementBy != null ? incrementBy : 1;
        StringBuilder seqName = new StringBuilder(this.schema).append(".").append(sequenceName);
        String ret = baseScript.replace("p_seq_name", seqName)
                .replace("p_seq_increment", increment.toString());
        return ret;
    }

    public String incrementSequenceScript(String baseScript) {
        StringBuilder seqName = new StringBuilder(this.schema).append(".").append(sequenceName);
        String ret = baseScript.replace("p_seq_name", seqName);
        return ret;
    }
}
