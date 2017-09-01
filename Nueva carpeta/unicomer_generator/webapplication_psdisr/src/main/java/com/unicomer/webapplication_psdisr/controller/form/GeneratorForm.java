/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.webapplication_psdisr.controller.form;

import com.unicomer.psdisr.generator.model.ConnectionData;
import com.unicomer.psdisr.generator.model.GenericData;
import com.unicomer.psdisr.generator.model.container.ColumnContainer;
import com.unicomer.psdisr.generator.model.container.TableContainer;
import com.unicomer.psdisr.generator.model.utils.ExtendedConstansts;
import com.unicomer.webapplication_psdisr.controller.form.utils.DinamicTableElement;
import com.unicomer.webapplication_psdisr.controller.form.utils.SchemaPojo;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.primefaces.model.TreeNode;

/**
 *
 * @author francisco_medina
 */
public class GeneratorForm implements Serializable {

    private ExtendedConstansts constantes = new ExtendedConstansts();
    private TreeNode root;
    private TreeNode[] selectedNodes;
    private ConnectionData sourceVal;
    private ConnectionData targetVal;
    private List<String> selectionConfigItemsSelected;
    private List<String> globalConfigItemsSelected;
    private SchemaPojo squemaVal;
    private List<SchemaPojo> squemaTable;
    private List<ConnectionData> tableTarget;
    private HashMap<String, ConnectionData> tableTargetH;
    private int currentTab;
    private int currentDetailTab;
    private String finalScript;
    private String finalCommentsScript;
    private String targetFinalScript;
    private String targetSelected;

    private DinamicTableElement source;
    private DinamicTableElement target;
    private List<String> columns;

    private List<String> globalOptionsToSelect;
    private List<String> selectionConfigToSelect;
    private List<String> columnOptionsToSelect;
    private List<String> constraintOptionsToSelect;

    private GenericData detSelected;
    private Boolean selectAllGlobalConfig;
    private Boolean selectAllselectionConfig;
    private Boolean enableSequenceTableMaxVal;
    private HashMap<String, TableContainer> tableH;
    private Collection<TableContainer> tableC;
    private HashMap<String, ColumnContainer> columnH;
    private Collection<ColumnContainer> columnC;
    private String tableHSelected;
    private String columnHSelected;
    private Long maxColValue;

    private HashMap<String, GenericData> targetScriptsData;

    public ConnectionData getSourceVal() {
        return sourceVal;
    }

    public void setSourceVal(ConnectionData sourceVal) {
        this.sourceVal = sourceVal;
    }

    public ConnectionData getTargetVal() {
        return targetVal;
    }

    public void setTargetVal(ConnectionData targetVal) {
        this.targetVal = targetVal;
    }

    public List<ConnectionData> getTableTarget() {
        return tableTarget;
    }

    public void setTableTarget(List<ConnectionData> tableTarget) {
        this.tableTarget = tableTarget;
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public ExtendedConstansts getConstantes() {
        return constantes;
    }

    public void setConstantes(ExtendedConstansts constantes) {
        this.constantes = constantes;
    }

    public List<String> getSelectionConfigItemsSelected() {
        return selectionConfigItemsSelected;
    }

    public void setSelectionConfigItemsSelected(List<String> selectionConfigItemsSelected) {
        this.selectionConfigItemsSelected = selectionConfigItemsSelected;
    }

    public List<String> getGlobalConfigItemsSelected() {
        return globalConfigItemsSelected;
    }

    public void setGlobalConfigItemsSelected(List<String> globalConfigItemsSelected) {
        this.globalConfigItemsSelected = globalConfigItemsSelected;
    }

    public SchemaPojo getSquemaVal() {
        return squemaVal;
    }

    public void setSquemaVal(SchemaPojo squemaVal) {
        this.squemaVal = squemaVal;
    }

    public List<SchemaPojo> getSquemaTable() {
        return squemaTable;
    }

    public void setSquemaTable(List<SchemaPojo> squemaTable) {
        this.squemaTable = squemaTable;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public void setCurrentTab(int currentTab) {
        this.currentTab = currentTab;
    }

    public String getFinalScript() {
        return finalScript;
    }

    public void setFinalScript(String finalScript) {
        this.finalScript = finalScript;
    }

    public DinamicTableElement getSource() {
        return source;
    }

    public void setSource(DinamicTableElement source) {
        this.source = source;
    }

    public DinamicTableElement getTarget() {
        return target;
    }

    public void setTarget(DinamicTableElement target) {
        this.target = target;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public GenericData getDetSelected() {
        return detSelected;
    }

    public void setDetSelected(GenericData detSelected) {
        this.detSelected = detSelected;
    }

    public List<String> getGlobalOptionsToSelect() {
        return globalOptionsToSelect;
    }

    public void setGlobalOptionsToSelect(List<String> globalOptionsToSelect) {
        this.globalOptionsToSelect = globalOptionsToSelect;
    }

    public List<String> getColumnOptionsToSelect() {
        return columnOptionsToSelect;
    }

    public void setColumnOptionsToSelect(List<String> columnOptionsToSelect) {
        this.columnOptionsToSelect = columnOptionsToSelect;
    }

    public List<String> getConstraintOptionsToSelect() {
        return constraintOptionsToSelect;
    }

    public void setConstraintOptionsToSelect(List<String> constraintOptionsToSelect) {
        this.constraintOptionsToSelect = constraintOptionsToSelect;
    }

    public List<String> getSelectionConfigToSelect() {
        return selectionConfigToSelect;
    }

    public void setSelectionConfigToSelect(List<String> selectionConfigToSelect) {
        this.selectionConfigToSelect = selectionConfigToSelect;
    }

    public Boolean getSelectAllGlobalConfig() {
        return selectAllGlobalConfig;
    }

    public void setSelectAllGlobalConfig(Boolean selectAllGlobalConfig) {
        this.selectAllGlobalConfig = selectAllGlobalConfig;
    }

    public Boolean getSelectAllselectionConfig() {
        return selectAllselectionConfig;
    }

    public void setSelectAllselectionConfig(Boolean selectAllselectionConfig) {
        this.selectAllselectionConfig = selectAllselectionConfig;
    }

    public String getFinalCommentsScript() {
        return finalCommentsScript;
    }

    public void setFinalCommentsScript(String finalCommentsScript) {
        this.finalCommentsScript = finalCommentsScript;
    }

    public String getTargetFinalScript() {
        return targetFinalScript;
    }

    public void setTargetFinalScript(String targetFinalScript) {
        this.targetFinalScript = targetFinalScript;
    }

    public HashMap<String, GenericData> getTargetScriptsData() {
        return targetScriptsData;
    }

    public void setTargetScriptsData(HashMap<String, GenericData> targetScriptsData) {
        this.targetScriptsData = targetScriptsData;
    }

    public String getTargetSelected() {
        return targetSelected;
    }

    public void setTargetSelected(String targetSelected) {
        this.targetSelected = targetSelected;
    }

    public Boolean getEnableSequenceTableMaxVal() {
        return enableSequenceTableMaxVal;
    }

    public void setEnableSequenceTableMaxVal(Boolean enableSequenceTableMaxVal) {
        this.enableSequenceTableMaxVal = enableSequenceTableMaxVal;
    }

    public HashMap<String, TableContainer> getTableH() {
        return tableH;
    }

    public void setTableH(HashMap<String, TableContainer> tableH) {
        this.tableH = tableH;
    }

    public HashMap<String, ColumnContainer> getColumnH() {
        return columnH;
    }

    public void setColumnH(HashMap<String, ColumnContainer> columnH) {
        this.columnH = columnH;
    }

    public String getTableHSelected() {
        return tableHSelected;
    }

    public void setTableHSelected(String tableHSelected) {
        this.tableHSelected = tableHSelected;
    }

    public String getColumnHSelected() {
        return columnHSelected;
    }

    public void setColumnHSelected(String columnHSelected) {
        this.columnHSelected = columnHSelected;
    }

    public Collection<TableContainer> getTableC() {
        return tableC;
    }

    public void setTableC(Collection<TableContainer> tableC) {
        this.tableC = tableC;
    }

    public Collection<ColumnContainer> getColumnC() {
        return columnC;
    }

    public void setColumnC(Collection<ColumnContainer> columnC) {
        this.columnC = columnC;
    }

    public Long getMaxColValue() {
        return maxColValue;
    }

    public void setMaxColValue(Long maxColValue) {
        this.maxColValue = maxColValue;
    }

    public int getCurrentDetailTab() {
        return currentDetailTab;
    }

    public void setCurrentDetailTab(int currentDetailTab) {
        this.currentDetailTab = currentDetailTab;
    }

    public TreeNode[] getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(TreeNode[] selectedNodes) {
        this.selectedNodes = selectedNodes;
    }

    public HashMap<String, ConnectionData> getTableTargetH() {
        return tableTargetH;
    }

    public void setTableTargetH(HashMap<String, ConnectionData> tableTargetH) {
        this.tableTargetH = tableTargetH;
    }

}
