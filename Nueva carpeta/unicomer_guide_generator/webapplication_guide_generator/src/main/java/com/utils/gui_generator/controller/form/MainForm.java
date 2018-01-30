/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.controller.form;

import com.utils.gui_generator.model.GgaGeneraldataLink;
import com.utils.gui_generator.model.GgaLinkExtraData;
import java.io.Serializable;
import java.util.List;
import org.primefaces.model.TreeNode;

/**
 *
 * @author francisco_medina
 */
public class MainForm implements Serializable {

    private TreeNode root;
    private TreeNode selectedNode;
    private GgaGeneraldataLink currentLinkSelected;
    private List<GgaLinkExtraData> currentLinkExtraData;

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public TreeNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode selectedNode) {
        this.selectedNode = selectedNode;
    }

    public GgaGeneraldataLink getCurrentLinkSelected() {
        return currentLinkSelected;
    }

    public void setCurrentLinkSelected(GgaGeneraldataLink currentLinkSelected) {
        this.currentLinkSelected = currentLinkSelected;
    }

    public List<GgaLinkExtraData> getCurrentLinkExtraData() {
        return currentLinkExtraData;
    }

    public void setCurrentLinkExtraData(List<GgaLinkExtraData> currentLinkExtraData) {
        this.currentLinkExtraData = currentLinkExtraData;
    }

}
