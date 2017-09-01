/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.webapplication_psdisr.controller.form.utils;

import com.unicomer.psdisr.generator.model.GenericData;
import org.primefaces.model.TreeNode;
import org.primefaces.model.DefaultTreeNode;

/**
 *
 * @author francisco_medina
 */
public class ObjectTypeTree {

    public TreeNode convertGenericDataToTreeNode(GenericData r, TreeNode parent) {
        TreeNode root = null;
        if (!r.getChildren().isEmpty()) {
            for (GenericData actual : r.getChildren()) {
                actual.setParent(r);
                root = new DefaultTreeNode(actual, parent);//agrega nodo hijo
                convertGenericDataToTreeNode(actual, root); //ciclo recursivo
            }
        }
        return root;
    }

}
