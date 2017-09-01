/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.generators;

import com.unicomer.psdisr.generator.model.ConnectionData;
import com.unicomer.psdisr.generator.model.GenericData;
import com.unicomer.psdisr.generator.model.utils.Constants;
import com.unicomer.psdisr.generator.model.ParameterData;
import com.unicomer.psdisr.generator.model.TableData;
import com.unicomer.psdisr.generator.model.container.ColumnContainer;
import com.unicomer.psdisr.generator.model.container.ConstraintContainer;
import com.unicomer.psdisr.generator.model.container.TableContainer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author francisco_medina
 */
public class TableGenerator {

    Properties properties;

    public TableGenerator(Properties p) {
        properties = p;
    }

    public HashMap<String, StringBuilder> generateTableScript(HashMap<String, TableContainer> seqOri,
            HashMap<String, TableContainer> seqDest,
            HashMap<String, ParameterData> globalParams,
            Connection conOrig,
            Connection conDest,
            GenericData data) {
        HashMap<String, StringBuilder> ret = new HashMap<>();
        StringBuilder query = new StringBuilder();
        StringBuilder comments = new StringBuilder();
        TableContainer destinoActual;
        TableContainer origenActual;
        Boolean evaluateFilters = null;
        GenericData dActual = null;
        GenericData tActual = null;
        StringBuilder actualScript = new StringBuilder();

        //agrega el nodo de tablas para el esquema actual
        GenericData nodoTabla = GenericData.getNodeTable();

        for (String actual : seqOri.keySet()) {
            tActual = null;
            destinoActual = seqDest.get(actual);
            origenActual = seqOri.get(actual);
            //secuencia que no existe en BD destino
            if (destinoActual == null) {
                //crea una tabla en destino, tomando de base la origen, si el parametro es true
                ParameterData paramCreateNewTable = origenActual.getParameters()
                        .get(Constants.F_CREATE_TABLE);
                ParameterData paramCreateAllTable = globalParams
                        .get(Constants.F_CREATE_ALL_TABLE);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramCreateNewTable,
                        paramCreateAllTable);
                //si se ha seleccionado el parametro para crear secuencias si no existen en destino
                if (evaluateFilters != null && evaluateFilters) {
                    dActual = new GenericData();
                    HashMap<String, StringBuilder> colsScript = getColumnScriptForTable(origenActual, globalParams, conOrig, dActual);
                    //crear tabla
                    String createTableQuery = properties.getProperty(Constants.Q_TABLE_CREATE_QUERY);
                    actualScript = new StringBuilder(origenActual.getTable().createTableScript(createTableQuery,
                            colsScript.get(Constants.GROUP_DATA).toString()))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    comments.append(colsScript.get(Constants.GROUP_COMMENTS));

                    //si hay cambios en columnas
                    if (!dActual.getChildren().isEmpty()) {
                        GenericData nuevoNodo = null;
                        if (tActual == null) {
                            nuevoNodo = setTableTree(origenActual, nodoTabla, Constants.ACTION_CREATE, globalParams);
                            tActual = nuevoNodo;
                        } else {
                            nuevoNodo = tActual;
                        }
                        if (nuevoNodo != null) {
                            //agregar a la tabla actual el nodo de columnas
                            nuevoNodo.getChildren().addAll(dActual.getChildren());
                        }
                    }
                }

                //crear llaves primarias
                //crear llaves foraneas
                //crear constraint de tabla
                //
                //compara las tablas por sus valores definidos en el equals del Objeto TableData
            } else {
                if(actual.equals("UN_COUNTRY")){
                    int i=0;
                }
                //agrega al arbol el objeto actual 
                dActual = new GenericData();
                HashMap<String, StringBuilder> colsScript = getColumnScriptForTable(origenActual, destinoActual, globalParams, conOrig, conDest, dActual);
                query.append(colsScript.get(Constants.GROUP_DATA).toString());
                //si esta habilitada la opcion de generar comentarios
                comments.append(colsScript.get(Constants.GROUP_COMMENTS));

                //si hay cambios en columnas
                if (!dActual.getChildren().isEmpty()) {
                    GenericData nuevoNodo = null;
                    if (tActual == null) {
                        nuevoNodo = setTableTree(origenActual, nodoTabla, Constants.ACTION_UPDATE, globalParams);
                        tActual = nuevoNodo;
                    } else {
                        nuevoNodo = tActual;
                    }
                    if (nuevoNodo != null) {
                        //agregar a la tabla actual el nodo de columnas
                        nuevoNodo.getChildren().addAll(dActual.getChildren());
                    }
                }

            }

            dActual = new GenericData();
            //verifica si se ha habilitado la opcion de generar script para llaves primarias
            HashMap<String, StringBuilder> pkScript = getPkScriptForTable(origenActual, globalParams, conOrig, conDest, dActual);
            query.append(pkScript.get(Constants.GROUP_DATA).toString());

            //si hay cambios en columnas
            if (!dActual.getChildren().isEmpty()) {
                GenericData nuevoNodo = null;
                if (tActual == null) {
                    nuevoNodo = setTableTree(origenActual, nodoTabla, Constants.ACTION_UPDATE, globalParams);
                    tActual = nuevoNodo;
                } else {
                    nuevoNodo = tActual;
                }
                if (nuevoNodo != null) {
                    //agregar a la tabla actual el nodo de PK
                    nuevoNodo.getChildren().addAll(dActual.getChildren());
                }
            }

            dActual = new GenericData();
            //verifica si se ha habilitado la opcion de generar script para llaves foraneas
            HashMap<String, StringBuilder> fkScript = getFkScriptForTable(origenActual, globalParams, conOrig, conDest, dActual);
            query.append(fkScript.get(Constants.GROUP_DATA).toString());
            //si hay cambios en columnas
            if (!dActual.getChildren().isEmpty()) {
                GenericData nuevoNodo = null;
                if (tActual == null) {
                    nuevoNodo = setTableTree(origenActual, nodoTabla, Constants.ACTION_UPDATE, globalParams);
                    tActual = nuevoNodo;
                } else {
                    nuevoNodo = tActual;
                }
                if (nuevoNodo != null) {
                    //agregar a la tabla actual el nodo de FK
                    nuevoNodo.getChildren().addAll(dActual.getChildren());
                }
            }
        }
        if (!nodoTabla.getChildren().isEmpty()) {
            data.getChildren().add(nodoTabla);
        }

        ret.put(Constants.GROUP_DATA, query);
        ret.put(Constants.GROUP_COMMENTS, comments);

        return ret;
    }

    public GenericData setTableTree(TableContainer origenActual, GenericData data, String action, HashMap<String, ParameterData> globalParam) {
        GenericData ret = null;
        Boolean evaluateFilters;
        //generar objetos diferentes, para tablas
        ParameterData paramGetDiferentColumn = globalParam
                .get(Constants.F_GET_DIFERENT_TABLES);
        ParameterData paramGetDiferentPk = globalParam
                .get(Constants.F_GET_DIFERENT_PK);
        ParameterData paramGetDiferentFk = globalParam
                .get(Constants.F_GET_DIFERENT_FK);
        ParameterData paramGetDiferentColumns = globalParam
                .get(Constants.F_GET_DIFERENT_COLUMNS);

        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramGetDiferentColumn,
                paramGetDiferentPk,
                paramGetDiferentFk,
                paramGetDiferentColumns);
        if (evaluateFilters != null && evaluateFilters) {
            ret = data.convertTableContainerToGenericData(origenActual, action);
            data.getChildren().add(ret);
        }
        return ret;
    }

    public HashMap<String, StringBuilder> getColumnScriptForTable(TableContainer tableOrig, HashMap<String, ParameterData> globalParams, Connection conOri, GenericData data) {
        ColumnGenerator cg = new ColumnGenerator(properties);
        HashMap<String, ParameterData> localParams = new HashMap<>();
        ParameterData p = new ParameterData();
        p.setValue(true);
        localParams.putAll(globalParams);
        localParams.put(Constants.F_CREATE_COLUMN_FOR_TABLE, p);

        HashMap<String, ColumnContainer> colsTable1 = cg.getTableColumnsHash(tableOrig.getTable().getTableSchema(),
                tableOrig.getTable().getTableName(),
                null,
                conOri);
        HashMap<String, StringBuilder> ret = cg.generateColumnScriptForTable(colsTable1, localParams, data);
        return ret;
    }

    public HashMap<String, StringBuilder> getColumnScriptForTable(TableContainer tableOrig, TableContainer tableDest, HashMap<String, ParameterData> globalParams, Connection conOri, Connection conDest, GenericData data) {
        ColumnGenerator cg = new ColumnGenerator(properties);
        HashMap<String, ColumnContainer> colsTable1 = cg.getTableColumnsHash(tableOrig.getTable().getTableSchema(),
                tableOrig.getTable().getTableName(),
                null,
                conOri);

        HashMap<String, ColumnContainer> colsTable2 = cg.getTableColumnsHash(tableDest.getTable().getTableSchema(),
                tableDest.getTable().getTableName(),
                null,
                conDest);

        HashMap<String, StringBuilder> ret = cg.generateColumnScript(colsTable1, colsTable2, globalParams, conDest, data);
        return ret;
    }

    public HashMap<String, StringBuilder> getPkScriptForTable(TableContainer tableOrig, HashMap<String, ParameterData> globalParams, Connection conOri, Connection conDest, GenericData data) {
        ConstraintGenerator cg = new ConstraintGenerator(properties);
        HashMap<String, ConstraintContainer> constraints1 = cg.getPkTableHash(tableOrig.getTable().getTableSchema(),
                tableOrig.getTable().getTableName(),
                conOri);

        HashMap<String, ConstraintContainer> constraints2 = cg.getPkTableHash(tableOrig.getTable().getTableSchema(),
                tableOrig.getTable().getTableName(),
                conDest);

        HashMap<String, StringBuilder> ret = cg.generatePkConstraintScript(constraints1, constraints2, globalParams, conDest, data);
        return ret;
    }

    public HashMap<String, StringBuilder> getFkScriptForTable(TableContainer tableOrig, HashMap<String, ParameterData> globalParams, Connection conOri, Connection conDest, GenericData data) {
        ConstraintGenerator cg = new ConstraintGenerator(properties);
        HashMap<String, ConstraintContainer> constraints1 = cg.getFkTableHash(tableOrig.getTable().getTableSchema(),
                tableOrig.getTable().getTableName(),
                conOri);

        HashMap<String, ConstraintContainer> constraints2 = cg.getFkTableHash(tableOrig.getTable().getTableSchema(),
                tableOrig.getTable().getTableName(),
                conDest);

        HashMap<String, StringBuilder> ret = cg.generateFkConstraintScript(constraints1, constraints2, globalParams, conDest, data);
        return ret;
    }

    public HashMap<String, TableContainer> getTableHash(String schema, String tableName, Connection connection,ConnectionData connectionData) {
        HashMap<String, TableContainer> ret = new HashMap<>();
        try {

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getTables(null, schema, tableName != null ? tableName : null, new String[]{"TABLE"});
            while (rs.next()) {
                TableContainer val = new TableContainer();
                val.setTable(new TableData());
                val.getTable().setTableName(rs.getString("TABLE_NAME"));
                val.getTable().setTableSchema(rs.getString("TABLE_SCHEM"));
                val.setParameters(new HashMap<>());
                val.setConnectionData(connectionData);
                ret.put(val.getTable().getTableName(), val);
            }
            rs.close();
        } catch (Exception e) {
        }
        return ret;
    }
}
