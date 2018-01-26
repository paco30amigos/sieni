/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model.container;

import com.unicomer.psdisr.generator.model.ColumnData;
import com.unicomer.psdisr.generator.model.ParameterData;
import java.util.HashMap;

/**
 *
 * @author francisco_medina
 */
public class ColumnContainer {

    private ColumnData column;
    private HashMap<String, ParameterData> parameters;
    private StringBuilder script;
    private StringBuilder comments;

    public HashMap<String, ParameterData> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, ParameterData> parameters) {
        this.parameters = parameters;
    }

    public ColumnData getColumn() {
        return column;
    }

    public void setColumn(ColumnData column) {
        this.column = column;
    }    

    public StringBuilder getScript() {
        return script;
    }

    public void setScript(StringBuilder script) {
        this.script = script;
    }

    public StringBuilder getComments() {
        return comments;
    }

    public void setComments(StringBuilder comments) {
        this.comments = comments;
    }

}
