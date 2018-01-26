/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model;

import com.unicomer.psdisr.generator.model.container.ColumnContainer;
import com.unicomer.psdisr.generator.model.container.ConstraintContainer;
import com.unicomer.psdisr.generator.model.container.SequenceContainer;
import com.unicomer.psdisr.generator.model.container.TableContainer;
import com.unicomer.psdisr.generator.model.utils.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class GenericData {

    private String objectType;
    private String name;
    private String comment;
    private String action;
    private Object source;
    private Object target;
    private StringBuilder script;
    private String completeScript;
    private String completeCommentScript;
    private List<GenericData> children;
    private HashMap<String, Object> valuesConfigured;
    private GenericData parent;

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<GenericData> getChildren() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void setChildren(List<GenericData> children) {
        this.children = children;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public void addColumnElement(ColumnContainer colOrig, ColumnContainer colDest) {
        GenericData orig = new GenericData();
        GenericData dest = new GenericData();

    }

    public GenericData convertColumnContainerToGenericData(ColumnContainer col, String action) {
        GenericData data = new GenericData();
        data.setName(col.getColumn().getColumnName());
        data.setComment(col.getColumn().getRemark());
        data.setAction(action);
        data.setSource(col);
        data.setScript(col.getScript());
        data.setComment(col.getComments() != null ? col.getComments().toString() : "");
        return data;
    }

    public GenericData convertSequenceContainerToGenericData(SequenceContainer seq, String action) {
        GenericData data = new GenericData();
        data.setName(seq.getSequence().getSequenceName());
        data.setAction(action);
        data.setSource(seq);
        data.setScript(seq.getScript());
        return data;
    }

    public GenericData convertTableContainerToGenericData(TableContainer table, String action) {
        GenericData data = new GenericData();
        data.setName(table.getTable().getTableName());
        data.setComment(table.getTable().getRemark());
        data.setAction(action);
        data.setSource(table);
        data.setScript(table.getScript());
        return data;
    }

    public GenericData convertPkContainerToGenericData(ConstraintContainer constraint, String action) {
        GenericData data = new GenericData();
        data.setName(constraint.getConstraint().getConstraintName());
        data.setAction(action);
        data.setSource(constraint);
        data.setScript(constraint.getScript());
        return data;
    }

    public GenericData convertFkContainerToGenericData(ConstraintContainer constraint, String action) {
        GenericData data = new GenericData();
        data.setName(constraint.getConstraint().getConstraintName());
        data.setAction(action);
        data.setSource(constraint);
        data.setScript(constraint.getScript());
        return data;
    }

    public Object getSource() {
        return source;
    }

    public static GenericData getNodeColumn() {
        GenericData data = new GenericData();
        data.setObjectType(Constants.OBJECT_TYPE_COLUMN);
        return data;
    }

    public static GenericData getNodeTable() {
        GenericData data = new GenericData();
        data.setObjectType(Constants.OBJECT_TYPE_TABLE);
        return data;
    }

    public static GenericData getNodePk() {
        GenericData data = new GenericData();
        data.setObjectType(Constants.OBJECT_TYPE_PK);
        return data;
    }

    public static GenericData getNodeFk() {
        GenericData data = new GenericData();
        data.setObjectType(Constants.OBJECT_TYPE_FK);
        return data;
    }

    public static GenericData getNodeSequence() {
        GenericData data = new GenericData();
        data.setObjectType(Constants.OBJECT_TYPE_SEQUENCE);
        return data;
    }

    public StringBuilder getScript() {
        return script;
    }

    public void setScript(StringBuilder script) {
        this.script = script;
    }

    public String getCompleteScript() {
        return completeScript;
    }

    public void setCompleteScript(String completeScript) {
        this.completeScript = completeScript;
    }

    public String getCompleteCommentScript() {
        return completeCommentScript;
    }

    public void setCompleteCommentScript(String completeCommentScript) {
        this.completeCommentScript = completeCommentScript;
    }

    public GenericData getParent() {
        return parent;
    }

    public void setParent(GenericData parent) {
        this.parent = parent;
    }

    public HashMap<String, Object> getValuesConfigured() {
        if (valuesConfigured == null) {
            valuesConfigured = new HashMap<>();
        }
        return valuesConfigured;
    }

    public void setValuesConfigured(HashMap<String, Object> valuesConfigured) {
        this.valuesConfigured = valuesConfigured;
    }
}
