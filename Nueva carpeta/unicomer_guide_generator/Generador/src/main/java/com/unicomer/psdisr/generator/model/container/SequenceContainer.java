/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model.container;

import com.unicomer.psdisr.generator.model.ConnectionData;
import com.unicomer.psdisr.generator.model.SequenceData;
import com.unicomer.psdisr.generator.model.ParameterData;
import java.util.HashMap;

/**
 *
 * @author francisco_medina
 */
public class SequenceContainer {

    private SequenceData sequence;
    private HashMap<String, ParameterData> parameters;
    private StringBuilder script;
    private ConnectionData connectionData;

    public SequenceData getSequence() {
        return sequence;
    }

    public void setSequence(SequenceData sequence) {
        this.sequence = sequence;
    }

    public HashMap<String, ParameterData> getParameters() {
        if(parameters==null){
            parameters=new HashMap<>();
        }
        return parameters;
    }

    public void setParameters(HashMap<String, ParameterData> parameters) {
        this.parameters = parameters;
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
