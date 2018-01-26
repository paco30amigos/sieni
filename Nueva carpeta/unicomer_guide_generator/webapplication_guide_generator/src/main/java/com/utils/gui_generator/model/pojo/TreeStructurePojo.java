/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.model.pojo;

import java.util.List;

/**
 *
 * @author francisco_medina
 */
public class TreeStructurePojo {

    private String nodeName;
    private Object data;
    private List<TreeStructurePojo> children;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<TreeStructurePojo> getChildren() {
        return children;
    }

    public void setChildren(List<TreeStructurePojo> children) {
        this.children = children;
    }
}
