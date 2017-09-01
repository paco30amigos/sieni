/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.webapplication_psdisr;

import com.unicomer.psdisr.generator.Generador;
import com.unicomer.psdisr.generator.model.GenericData;
import com.unicomer.psdisr.generator.model.ConnectionData;
import com.unicomer.psdisr.generator.model.container.ColumnContainer;
import com.unicomer.psdisr.generator.model.container.ConstraintContainer;
import com.unicomer.psdisr.generator.model.container.SequenceContainer;
import com.unicomer.psdisr.generator.model.container.TableContainer;
import com.unicomer.psdisr.generator.model.utils.Constants;
import com.unicomer.webapplication_psdisr.controller.form.GeneratorForm;
import com.unicomer.webapplication_psdisr.controller.form.utils.DinamicTableElement;
import com.unicomer.webapplication_psdisr.controller.form.utils.ObjectTypeTree;
import com.unicomer.webapplication_psdisr.controller.form.utils.SchemaPojo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author francisco_medina
 */
@ViewScoped
@ManagedBean(name = "generator")
public class Generator extends GeneratorForm implements Serializable {

    Generador g = new Generador();

    ResourceBundle rb = ResourceBundle.getBundle(
            "com.unicomer.psdisr.utilities.i18n.BundlePsdisr",
            FacesContext.getCurrentInstance().getViewRoot().getLocale());

    @PostConstruct
    public void init() {
        this.setTableTarget(new ArrayList<ConnectionData>());
        this.setTargetVal(new ConnectionData());
        this.setSourceVal(new ConnectionData());
        this.setSelectionConfigItemsSelected(new ArrayList<String>());
        this.setGlobalConfigItemsSelected(new ArrayList<String>());
        this.setSquemaVal(new SchemaPojo());
        this.setSquemaTable(new ArrayList<SchemaPojo>());
        this.setRoot(new DefaultTreeNode());
        this.setCurrentTab(0);
        setSelectAllGlobalConfig(Boolean.TRUE);
        setSelectAllselectionConfig(Boolean.TRUE);
        initDefaultLists();
        this.setTargetScriptsData(new HashMap<String, GenericData>());
        setTableTargetH(new HashMap<String, ConnectionData>());
        this.setTargetSelected("");
        this.setTableHSelected("");

        setDefaultVariablesData();
    }

    public void initDefaultLists() {
        this.setGlobalOptionsToSelect(new ArrayList<String>());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_CREATE_ALL_TABLE());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_DROP_ALL_TABLE_NOT_FOUND_ON_DEST());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_CREATE_ALL_COLUMN());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_ALTER_ALL_COLUMN());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_CREATE_ALL_ALTER_COLUMN_REMARKS());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_SET_ALL_EQUALS_SEQUENCES());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_CREATE_ALL_CONSTRAINT());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_CREATE_ALL_SEQUENCE());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_CREATE_ALL_PK());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_ALTER_ALL_PK());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_CREATE_ALL_FK());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_ALTER_ALL_FK());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_ALTER_COLUMN_NOTNULL_TO_NULL());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_ALTER_COLUMN_NULL_TO_NOTNULL());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_DROP_ALL_FK_NOT_FOUND_ON_ORIG());
        this.getGlobalOptionsToSelect().add(getConstantes().getF_DROP_ALL_PK_NOT_FOUND_ON_ORIG());

        this.setSelectionConfigToSelect(new ArrayList<String>());
        this.getSelectionConfigToSelect().add(getConstantes().getF_GET_DIFERENT_COLUMNS());
        this.getSelectionConfigToSelect().add(getConstantes().getF_GET_DIFERENT_FK());
        this.getSelectionConfigToSelect().add(getConstantes().getF_GET_DIFERENT_PK());
        this.getSelectionConfigToSelect().add(getConstantes().getF_GET_DIFERENT_SEQUENCES());
        this.getSelectionConfigToSelect().add(getConstantes().getF_GET_DIFERENT_TABLES());
    }

    public void selectAllGlobalItems() {
        this.setGlobalConfigItemsSelected(getGlobalOptionsToSelect());
    }

    public void selectAllSelectionConfigItems() {
        this.setSelectionConfigItemsSelected(getSelectionConfigToSelect());
    }

    public void deselectAllGlobalItems() {
        this.getGlobalConfigItemsSelected();
        this.setGlobalConfigItemsSelected(new ArrayList<String>());
    }

    public void deselectAllSelectionConfigItems() {
        this.setSelectionConfigItemsSelected(new ArrayList<String>());
    }

    public void setDefaultVariablesData() {
        ConnectionData source = new ConnectionData();
        source.setName("InHouse Oracle 11g TEST");
        source.setConectionUrl("jdbc:oracle:thin:@192.168.130.231:1521:inhdmst");
        source.setConectionUsr("ADMIHOTH");
        source.setConectionPass("admihoth2014");
        this.setSourceVal(source);

        ConnectionData target = new ConnectionData();
        target.setName("InHouse Oracle 12c UAT");
        target.setConectionUrl("jdbc:oracle:thin:@uinhdbracuatscan.unicomer.com:1521/IHDBUAT");
        target.setConectionUsr("ADMIHOTH");
        target.setConectionPass("welcome1");
        this.setTargetVal(target);
        this.getTableTarget().add(target);
        this.setTargetVal(new ConnectionData());

        selectAllGlobalItems();
        selectAllSelectionConfigItems();

        this.getSquemaVal().setName("ADMIHOTH");
        this.getSquemaTable().add(getSquemaVal());
        this.setSquemaVal(new SchemaPojo());
    }

    public void findDiferences() {
        GenericData genericNodeTarget;
        GenericData genericNodeSchema;
        GenericData dataTree;
        GenericData sequenceTree;
        TreeNode nodoRoot, nodoDest, nodoEsquema;
        GenericData initRootGenericData = new GenericData();

        nodoRoot = new DefaultTreeNode(initRootGenericData, null);

        for (ConnectionData actualTarget : this.getTableTarget()) {

            genericNodeTarget = new GenericData();
            genericNodeTarget.setObjectType(Constants.OBJECT_TYPE_TARGET);
            genericNodeTarget.setName(actualTarget.getName());
            genericNodeTarget.setParent(initRootGenericData);
            nodoDest = new DefaultTreeNode(genericNodeTarget, nodoRoot);

            for (SchemaPojo actualSchema : this.getSquemaTable()) {
                dataTree = new GenericData();
                sequenceTree = new GenericData();
                genericNodeSchema = new GenericData();
                genericNodeSchema.setParent(genericNodeTarget);
                genericNodeSchema.setObjectType(Constants.OBJECT_TYPE_SCHEMA);
                genericNodeSchema.setName(actualSchema.getName());
                //establece los datos de conexion para el nodo del esquema
                genericNodeSchema.setSource(getSource());
                genericNodeSchema.setTarget(actualTarget);

                nodoEsquema = new DefaultTreeNode(genericNodeSchema, nodoDest);

                //obtiene los valores para las estructuras de las tablas
                g.getTableScript(this.getGlobalConfigItemsSelected(),
                        this.getSelectionConfigItemsSelected(),
                        this.getSourceVal(),
                        actualTarget,
                        actualSchema.getName(),
                        dataTree
                );

                //agrega el arbol de secuencias a la lista
                g.getSequenceScript(this.getGlobalConfigItemsSelected(),
                        this.getSelectionConfigItemsSelected(),
                        this.getSourceVal(),
                        actualTarget,
                        actualSchema.getName(),
                        sequenceTree
                );

                ObjectTypeTree ott = new ObjectTypeTree();
                ott.convertGenericDataToTreeNode(dataTree, nodoEsquema);
                ott.convertGenericDataToTreeNode(sequenceTree, nodoEsquema);

                //agrega el nodo como hijo para generar el arbol de scripts en la pantalla para el esquema
                genericNodeSchema.getChildren().add(sequenceTree);
                genericNodeSchema.getChildren().add(dataTree);
                //establece como nodo padre al nodo esquema
                dataTree.setParent(genericNodeSchema);
                sequenceTree.setParent(genericNodeSchema);
            }
            //agrega el nodo como hijo para generar el arbol de scripts en la pantalla para en el nodo target
            initRootGenericData.getChildren().add(genericNodeTarget);
            this.getTargetScriptsData().put(actualTarget.getName(), genericNodeTarget);
        }
        this.setRoot(nodoRoot);
        this.setCurrentTab(2);
        setSelectedNodes(null);
        resetFinalScripts();
    }

    public void resetFinalScripts() {
        this.setTargetSelected("");
        this.setFinalScript("");
        this.setFinalCommentsScript("");
    }

    public void setFinalScripts() {
        String key = getTargetSelected();
        if (key != null && !key.isEmpty()) {
            this.setFinalScript(fixScript(generateCompleteScriptBySelectedTreeNodes(getSelectedNodes(), key)));
            this.setFinalCommentsScript(fixScript(generateCompleteCommentScriptBySelectedTreeNodes(getSelectedNodes(), key)));
        } else {
            resetFinalScripts();
        }
    }

    public String fixScript(String script) {
        script = script.replaceAll("\n", "<br />");
        return script;
    }

    public void updateScriptRet(HashMap<String, StringBuilder> ret, HashMap<String, StringBuilder> currentRet) {
        //inicializa la el script final
        if (ret.get(Constants.GROUP_DATA) == null) {
            ret.put(Constants.GROUP_DATA, new StringBuilder());
        }
        if (ret.get(Constants.GROUP_COMMENTS) == null) {
            ret.put(Constants.GROUP_COMMENTS, new StringBuilder());
        }

        //si se retornaron cadenas se agregan al script
        if (currentRet.get(Constants.GROUP_DATA) != null && ret.get(Constants.GROUP_DATA) != null) {
            ret.get(Constants.GROUP_DATA).append(currentRet.get(Constants.GROUP_DATA));
        }
        //agrega los comentarios si hay
        if (currentRet.get(Constants.GROUP_COMMENTS) != null) {
            ret.get(Constants.GROUP_COMMENTS).append(currentRet.get(Constants.GROUP_COMMENTS));
        }
    }

    public List<String> getDinamicColumns(Object data) {
        List<String> columns = new ArrayList<>();
        if (data != null) {
            if (data instanceof TableContainer) {
                columns.add(rb.getString("ui_psdisr_selectionConfig_schema"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_tableName"));
            } else if (data instanceof ColumnContainer) {
                columns.add(rb.getString("ui_psdisr_selectionConfig_schema"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_tableName"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_column"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_dataType"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_size"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_nullable"));
            } else if (data instanceof ConstraintContainer) {
                ConstraintContainer c = (ConstraintContainer) data;
                //es llave foranea
                if (c.getConstraint().getFkTableName() != null) {
                    columns.add(rb.getString("ui_psdisr_selectionConfig_fkName"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_schemaPK"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_tablePK"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_columnPK"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_schemaFK"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_tableFK"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_columnFK"));
                } else {//es llave primaria
                    columns.add(rb.getString("ui_psdisr_selectionConfig_pkName"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_schemaPK"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_tablePK"));
                    columns.add(rb.getString("ui_psdisr_selectionConfig_columnPK"));
                }
            } else if (data instanceof SequenceContainer) {
                columns.add(rb.getString("ui_psdisr_selectionConfig_schema"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_sequenceName"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_sequenceCurrentVal"));
                columns.add(rb.getString("ui_psdisr_selectionConfig_sequenceIncrement"));
            }
        }
        return columns;
    }

    public DinamicTableElement getDinamicTableValues(Object data) {
        DinamicTableElement ret = new DinamicTableElement();
        List<String> columns = new ArrayList<>();
        if (data != null) {
            if (data instanceof TableContainer) {
                TableContainer v = (TableContainer) data;
                columns.add(v.getTable().getTableSchema());
                columns.add(v.getTable().getTableName());
                ret.setValues(columns);
            } else if (data instanceof ColumnContainer) {
                Generador g = new Generador();
                ColumnContainer v = (ColumnContainer) data;
                columns.add(v.getColumn().getTableSchema());
                columns.add(v.getColumn().getTableName());
                columns.add(v.getColumn().getColumnName());
                columns.add(g.cg.fixTypeName(v.getColumn().getTypeName()));
                columns.add(g.cg.getColumnDataLenght(v.getColumn()));
                columns.add(g.cg.nullableValue(v.getColumn()));
                ret.setValues(columns);
            } else if (data instanceof ConstraintContainer) {
                ConstraintContainer c = (ConstraintContainer) data;
                //es llave foranea
                if (c.getConstraint().getFkTableName() != null) {
                    columns.add(c.getConstraint().getConstraintName());
                    columns.add(c.getConstraint().getPkTableSchema());
                    columns.add(c.getConstraint().getPkTableName());
                    columns.add(c.getConstraint().getPkColumnName());
                    columns.add(c.getConstraint().getFkTableSchema());
                    columns.add(c.getConstraint().getFkTableName());
                    columns.add(c.getConstraint().getFkColumnName());
                } else {//es llave primaria
                    columns.add(c.getConstraint().getConstraintName());
                    columns.add(c.getConstraint().getPkTableSchema());
                    columns.add(c.getConstraint().getPkTableName());
                    columns.add(c.getConstraint().getPkColumnName());
                }
                ret.setValues(columns);
            } else if (data instanceof SequenceContainer) {
                SequenceContainer v = (SequenceContainer) data;
                columns.add(v.getSequence().getSchema());
                columns.add(v.getSequence().getSequenceName());
                columns.add(v.getSequence().getLastNumber().toString());
                columns.add(v.getSequence().getIncrementBy().toString());
                ret.setValues(columns);
            }
        }
        return ret;
    }

    public void getDetails(GenericData data) {

        this.setDetSelected(data);
        this.setCurrentDetailTab(0);
        List<String> columns = getDinamicColumns(data.getSource());
        DinamicTableElement source = getDinamicTableValues(data.getSource());
        DinamicTableElement target = getDinamicTableValues(data.getTarget());
        this.setSource(source);
        this.setTarget(target);
        this.setColumns(columns);
        this.getDetSelected().setCompleteScript(fixScript(generateCompleteScript(data)));
        this.getDetSelected().setCompleteCommentScript(fixScript(generateCompleteCommentScript(data)));

        //habilitar variable de tab para buscar maximo valor de una columna en una tabla
        if (this.getDetSelected().getParent() != null
                && this.getDetSelected().getParent().getObjectType() != null
                && this.getDetSelected().getParent().getObjectType().equals(Constants.OBJECT_TYPE_SEQUENCE)) {
            this.setEnableSequenceTableMaxVal(Boolean.TRUE);
            SequenceContainer seq = (SequenceContainer) data.getSource();
            //llenar listado de tablas disponibles en el esquema
            HashMap<String, TableContainer> table = g.getTableDestList(new ArrayList<String>(),
                    new ArrayList<String>(),
                    seq.getConnectionData(),
                    seq.getSequence().getSchema(),
                    new GenericData());
            if (!table.isEmpty()) {
                setTableH(table);
                setTableC(table.values());
                if (this.getDetSelected().getValuesConfigured() != null) {
                    //busca la tabla seleccionada
                    String tableValSelected = (String) this.getDetSelected().getValuesConfigured().get(Constants.P_GENERIC_DATA_TABLE);
                    //verifica que la tabla seleccionada exista en el listado de tablas de la BD
                    if (tableValSelected != null && table.containsKey(tableValSelected)) {
                        setTableHSelected(tableValSelected);
                        //actualiza las columna para la tabla seleccionada
                        refreshTableColumns();
                    } else {//si la tabla no existe lo pone en blanco
                        setTableHSelected("");
                        setColumnHSelected("");
                        this.setMaxColValue(null);
                    }
                } else {
                    setTableHSelected("");
                    setColumnHSelected("");
                    this.setMaxColValue(null);
                }
            } else {
                setTableH(new HashMap<String, TableContainer>());
                setTableHSelected("");
                setTableC(new ArrayList<TableContainer>());
                setColumnHSelected("");
                this.setMaxColValue(null);
            }
        } else {
            this.setEnableSequenceTableMaxVal(Boolean.FALSE);
        }
        RequestContext.getCurrentInstance().execute("PF('selectionConfigDlgDetail').show()");
    }

    //generar metodo para obtener campos de las tablas cuando se seleccione la tabla
    public void refreshTableColumns() {
        if (getTableHSelected() != null && !getTableHSelected().isEmpty()) {
            TableContainer tableSelected = getTableH().get(getTableHSelected());
            HashMap<String, ColumnContainer> columns = g.getColumnDestList(new ArrayList<String>(),
                    new ArrayList<String>(),
                    tableSelected.getConnectionData(),
                    tableSelected.getTable().getTableSchema(),
                    tableSelected.getTable().getTableName(),
                    new GenericData());
            setColumnH(columns);
            setColumnC(columns.values());
            if (this.getDetSelected().getValuesConfigured() != null) {
                //busca la columna seleccionada
                String colValSelected = (String) this.getDetSelected().getValuesConfigured().get(Constants.P_GENERIC_DATA_COLUMN);
                //verifica que la columna seleccionada exista en el listado de columna de la BD
                if (colValSelected != null && columns.containsKey(colValSelected)) {
                    setColumnHSelected(colValSelected);
                    refreshTableColumnVal();
                } else {//si la tabla no existe lo pone en blanco
                    setColumnHSelected("");
                }
            } else {
                setColumnHSelected("");
            }
        } else {
            setColumnH(new HashMap<String, ColumnContainer>());
            setColumnHSelected("");
            setColumnC(new ArrayList<ColumnContainer>());
        }
    }

    //generar metodo para obtener campos de las tablas cuando se seleccione la tabla
    public void refreshTableColumnVal() {
        if (getTableHSelected() != null && !getTableHSelected().isEmpty()) {
            if (this.getDetSelected().getValuesConfigured() != null) {
                //busca la columna seleccionada
                Long colValSelected = (Long) this.getDetSelected().getValuesConfigured().get(Constants.P_GENERIC_DATA_COLUMN_VALUE);
                //verifica que la columna seleccionada exista en el listado de columna de la BD
                if (colValSelected != null) {
                    setMaxColValue(colValSelected);
                } else {//si no se ha buscado el valor maximo lo pone en blanco
                    setMaxColValue(null);
                }
            } else {
                setMaxColValue(null);
            }
        } else {
            setColumnH(new HashMap<String, ColumnContainer>());
            setColumnHSelected("");
            setColumnC(new ArrayList<ColumnContainer>());
        }
    }

    public void searchMaxColValForSequence() {
        if (this.getTableHSelected() != null && !this.getTableHSelected().isEmpty()
                && this.getColumnHSelected() != null && !this.getColumnHSelected().isEmpty()) {
            SequenceContainer sec = (SequenceContainer) getDetSelected().getSource();
            //busca el nodo esquema para obtener los parametros de conexion
            GenericData schemaNode = g.searchParentObjectDataType(getDetSelected(), Constants.OBJECT_TYPE_SCHEMA);
            ConnectionData target = (ConnectionData) schemaNode.getTarget();
            Long val = g.getLastSequenceValue(sec.getSequence().getSchema(),
                    getTableHSelected(),
                    getColumnHSelected(),
                    //establece la conexion destino para buscar el maximo valor
                    target);
            this.setMaxColValue(val);
        }

    }

    //actualizar script
    public void updateSequenceScript() {
        HashMap<String, StringBuilder> newValSeq = g.getSequenceScriptMaxTableVal(new ArrayList<String>(),
                new ArrayList<String>(),
                getDetSelected(),
                this.getTableHSelected(),
                this.getColumnHSelected(),
                new GenericData());
        //establece la tabla seleccionada como parametro del generic_data
        this.getDetSelected().getValuesConfigured().put(Constants.P_GENERIC_DATA_TABLE, getTableHSelected());
        //establece la columna seleccionada como parametro del generic_data
        this.getDetSelected().getValuesConfigured().put(Constants.P_GENERIC_DATA_COLUMN, getColumnHSelected());
        //establece el valor de la columna encontrado
        this.getDetSelected().getValuesConfigured().put(Constants.P_GENERIC_DATA_COLUMN_VALUE, getMaxColValue());

        getDetSelected().setScript(newValSeq.get(Constants.GROUP_DATA));
        getDetSelected().setAction(Constants.ACTION_UPDATE_SEQUENCE_USING_TABLE_COLUMN);
        this.getDetSelected().setCompleteScript(fixScript(generateCompleteScript(this.getDetSelected())));
        this.getDetSelected().setCompleteCommentScript(fixScript(generateCompleteCommentScript(this.getDetSelected())));
        this.setCurrentDetailTab(1);
    }

    //  recargar arbol con nodos actualizados
    //guardar configuracion de secuencias
    public String generateCompleteScript(GenericData data) {
        StringBuilder ret = new StringBuilder();
        if (data.getObjectType() == null) {
            ret.append(data.getScript() != null ? data.getScript() : "");
        }
        if (!data.getChildren().isEmpty()) {
            for (GenericData actual : data.getChildren()) {
                String h = generateCompleteScript(actual);
                ret.append(h != null ? h : "");
            }
        }

        return ret.toString();
    }

    public String generateCompleteScriptBySelectedTreeNodes(TreeNode[] selected, String target) {
        StringBuilder ret = new StringBuilder();
        //crea una lista de campos unicos para evitar multiplicacion de script cuando se selecciona un nodo padre y sus nodos hijos tambien estan seleccionados
        List<GenericData> unique = new ArrayList<>();
        for (TreeNode actual : selected) {
            GenericData data = (GenericData) actual.getData();
            if (!unique.contains(data)) {
                unique.add(data);
                GenericData parentTarget = g.searchParentObjectDataType(data, Constants.OBJECT_TYPE_TARGET);
                //si el nodo seleccionado esta en el nodo destino, agrega el script
                if (data.getObjectType() == null
                        && parentTarget != null
                        && parentTarget.getName() != null
                        && parentTarget.getName().equals(target)) {
                    ret.append(data.getScript() != null ? data.getScript() : "");
                } else if (!data.getChildren().isEmpty()
                        && parentTarget != null
                        && parentTarget.getName() != null
                        && parentTarget.getName().equals(target)) {
                    for (GenericData a : data.getChildren()) {
                        if (!unique.contains(a)) {
                            unique.add(a);
                            ret.append(generateCompleteScript(a));
                        }
                    }
                }
            }
        }
        return ret.toString();
    }

    public String generateCompleteCommentScript(GenericData data) {
        StringBuilder ret = new StringBuilder();
        if (data.getObjectType() == null && data.getComment() != null) {
            ret.append(data.getComment() != null ? data.getComment() : "");
        }
        if (!data.getChildren().isEmpty()) {
            for (GenericData actual : data.getChildren()) {
                String h = generateCompleteCommentScript(actual);
                ret.append(h != null ? h : "");
            }
        }

        return ret.toString();
    }

    public String generateCompleteCommentScriptBySelectedTreeNodes(TreeNode[] selected, String target) {
        StringBuilder ret = new StringBuilder();
        for (TreeNode actual : selected) {
            GenericData data = (GenericData) actual.getData();
            GenericData parentTarget = g.searchParentObjectDataType(data, Constants.OBJECT_TYPE_TARGET);
            //si el nodo seleccionado esta en el nodo destino, agrega el script
            if (data.getObjectType() == null
                    && parentTarget != null
                    && parentTarget.getName() != null
                    && parentTarget.getName().equals(target)) {
                ret.append(data.getComment() != null ? data.getComment() : "");
            } else if (!data.getChildren().isEmpty()) {
                for (GenericData a : data.getChildren()) {
                    ret.append(generateCompleteCommentScript(a));
                }
            }
        }
        return ret.toString();
    }

    public String reinit() {
        if (this.getTableTargetH().get(getTargetVal().getName()) == null) {
            this.getTableTargetH().put(getTargetVal().getName(), getTargetVal());
        }
        this.setTargetVal(new ConnectionData());
        return null;
    }

    public void clearCompleteScript() {
        this.setDetSelected(new GenericData());
    }

    public void updateData() {
        getGlobalConfigItemsSelected();
        getSelectionConfigItemsSelected();
    }

    public void selectGlobalItemsBooleanAction() {
        if (this.getSelectAllGlobalConfig() != null && this.getSelectAllGlobalConfig()) {
            selectAllGlobalItems();
        } else {
            deselectAllGlobalItems();
        }
    }

    public void selectionConfigBooleanAction() {
        if (this.getSelectAllselectionConfig() != null && this.getSelectAllselectionConfig()) {
            selectAllSelectionConfigItems();
        } else {
            deselectAllSelectionConfigItems();
        }
    }
}
