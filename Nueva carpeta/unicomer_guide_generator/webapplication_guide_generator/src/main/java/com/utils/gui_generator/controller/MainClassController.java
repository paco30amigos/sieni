/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils.gui_generator.controller;

import com.utils.gui_generator.controller.form.MainForm;
import com.utils.gui_generator.ejb.local.GgaGeneralDataEjbImplLocal;
import com.utils.gui_generator.ejb.local.GgaGeneraldataLinkEjbImplLocal;
import com.utils.gui_generator.model.GgaGeneralData;
import com.utils.gui_generator.model.GgaGeneraldataLink;
import com.utils.gui_generator.model.GgaLink;
import com.utils.gui_generator.model.GgaLinkExtraData;
import com.utils.gui_generator.model.pojo.TreeStructurePojo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "MainClassController")
public class MainClassController extends MainForm {

    @EJB
    private GgaGeneralDataEjbImplLocal ggaGeneralDataEjbImplLocal;

    @EJB
    private GgaGeneraldataLinkEjbImplLocal ggaGeneraldataLinkEjbImplLocal;

    @PostConstruct
    public void init() {
        List<GgaGeneralData> list = ggaGeneralDataEjbImplLocal.getAllGeneralDataFetch();
        createTreeStructure(list);
        resetCurentLinkSelected();
    }

    public void createTreeStructure(List<GgaGeneralData> data) {
        this.setRoot(new DefaultTreeNode());
        //create root nodes
        for (GgaGeneralData actual : data) {
            TreeStructurePojo detNode = new TreeStructurePojo();
            detNode.setNodeName(actual.getTitle());
            detNode.setData(actual);
            TreeNode parentNode = new DefaultTreeNode(detNode, getRoot());
            for (GgaGeneraldataLink childNode : actual.getGgaGeneraldataLinkSet()) {
                if (childNode.getParentLinkId() == null) {
                    getChildren(childNode, parentNode);
                }
            }
        }
    }

    public void getChildren(GgaGeneraldataLink currentNode, TreeNode parent) {
        TreeStructurePojo detNode = new TreeStructurePojo();
        detNode.setNodeName(currentNode.getLinkId().getLinkName());
        detNode.setData(currentNode);
        TreeNode parentNode = new DefaultTreeNode(detNode, parent);

        List<GgaGeneraldataLink> children = ggaGeneraldataLinkEjbImplLocal.findByParentId(currentNode.getGeneraldataLinkId());
        if (children != null && !children.isEmpty()) {
            for (GgaGeneraldataLink actual : children) {
                getChildren(actual, parentNode);
            }
        }
    }

    public void selectCurrentNode() {
        TreeStructurePojo data = (TreeStructurePojo) getSelectedNode().getData();

        //if node is not a general data
        if (data.getData() instanceof GgaGeneraldataLink) {
            GgaGeneraldataLink selectedNode = (GgaGeneraldataLink) data.getData();
            setCurrentLinkSelected(selectedNode);
            setCurrentLinkExtraData(new ArrayList<GgaLinkExtraData>());
            getCurrentLinkExtraData().addAll(getCurrentLinkSelected().getLinkId().getGgaLinkExtraDataSet());
        } else {
            resetCurentLinkSelected();
        }
    }

    public void resetCurentLinkSelected() {
        setCurrentLinkSelected(new GgaGeneraldataLink());
        getCurrentLinkSelected().setLinkId(new GgaLink());
        setCurrentLinkExtraData(new ArrayList<GgaLinkExtraData>());
    }

}
