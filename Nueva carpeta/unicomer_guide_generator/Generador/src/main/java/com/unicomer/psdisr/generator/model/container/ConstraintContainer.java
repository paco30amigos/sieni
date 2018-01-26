/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model.container;

import com.unicomer.psdisr.generator.model.ConstraintData;
import com.unicomer.psdisr.generator.model.ParameterData;
import java.util.HashMap;

/**
 *
 * @author francisco_medina
 */
public class ConstraintContainer {

    private ConstraintData constraint;
    private HashMap<String, ParameterData> parameters;
    private StringBuilder script;

    public HashMap<String, ParameterData> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, ParameterData> parameters) {
        this.parameters = parameters;
    }

    public ConstraintData getConstraint() {
        return constraint;
    }

    public void setConstraint(ConstraintData constraint) {
        this.constraint = constraint;
    }

    public StringBuilder getScript() {
        return script;
    }

    public void setScript(StringBuilder script) {
        this.script = script;
    }

}
