/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model.container;

import com.unicomer.psdisr.generator.model.ConnectionData;
import com.unicomer.psdisr.generator.model.ParameterData;
import com.unicomer.psdisr.generator.model.TableData;
import java.util.HashMap;

/**
 *
 * @author francisco_medina
 */
public class TableContainer {

    private TableData table;
    private HashMap<String, ParameterData> parameters;
    private StringBuilder script;
    private ConnectionData connectionData;

    public HashMap<String, ParameterData> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, ParameterData> parameters) {
        this.parameters = parameters;
    }

    public TableData getTable() {
        return table;
    }

    public void setTable(TableData table) {
        this.table = table;
    }

    public StringBuilder getScript() {
        return script;
    }

    public void setScript(StringBuilder script) {
        this.script = script;
    }

    public ConnectionData getConnectionData() {
        return connectionData;
    }

    public void setConnectionData(ConnectionData connectionData) {
        this.connectionData = connectionData;
    }

}
