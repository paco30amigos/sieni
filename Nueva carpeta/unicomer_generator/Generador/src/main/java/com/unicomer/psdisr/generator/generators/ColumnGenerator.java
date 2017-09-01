/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.generators;

import com.unicomer.psdisr.generator.model.utils.Constants;
import com.unicomer.psdisr.generator.model.ParameterData;
import com.unicomer.psdisr.generator.model.container.ColumnContainer;
import com.unicomer.psdisr.generator.model.ColumnData;
import com.unicomer.psdisr.generator.model.GenericData;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author francisco_medina
 */
public class ColumnGenerator {

    Properties properties;

    public ColumnGenerator(Properties p) {
        properties = p;
    }

    public HashMap<String, StringBuilder> generateColumnScript(HashMap<String, ColumnContainer> colOri,
            HashMap<String, ColumnContainer> colDest,
            HashMap<String, ParameterData> globalParams,
            Connection conDest, GenericData data) {
        HashMap<String, StringBuilder> ret = new HashMap<>();
        StringBuilder query = new StringBuilder();
        StringBuilder comments = new StringBuilder();
        ColumnContainer destinoActual;
        ColumnContainer origenActual;
        Boolean evaluateFilters = null;
        GenericData nodoColumna = GenericData.getNodeColumn();
        GenericData nodoActual;
        StringBuilder actualScript = new StringBuilder();
        StringBuilder actualComments = new StringBuilder();

        for (String actual : colOri.keySet()) {
            destinoActual = colDest.get(actual);
            origenActual = colOri.get(actual);
            nodoActual = null;
            //secuencia que no existe en BD destino
            if (destinoActual == null) {
                //crea una columna en destino, tomando de base la origen, si el parametro es true
                ParameterData paramCreateNewColumn = origenActual.getParameters()
                        .get(Constants.F_CREATE_COLUMN);
                ParameterData paramCreateAllColumn = globalParams
                        .get(Constants.F_CREATE_ALL_COLUMN);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramCreateNewColumn,
                        paramCreateAllColumn);
                //si se ha seleccionado el parametro para crear secuencias si no existen en destino
                if (evaluateFilters != null && evaluateFilters) {
                    //crear columna
                    String createColumnQuery = properties.getProperty(Constants.Q_COLUMN_CREATE_QUERY);
                    actualScript = new StringBuilder(origenActual.getColumn().createColumnScript(createColumnQuery,
                            fixTypeName(origenActual.getColumn().getTypeName()),
                            getColumnDataLenght(origenActual.getColumn()),
                            nullableValue(origenActual.getColumn())))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    nodoActual = setColumnTree(origenActual, null, nodoColumna, Constants.ACTION_CREATE, globalParams);
                }
            } else if (origenActual.getColumn().equals(destinoActual.getColumn())) {
                //no es necesario ejecutar cambios
            } else {
                //elimina una columna existente de destino, que tambien existe en origen, si el parametro es true
                ParameterData paramDropColumn = origenActual.getParameters()
                        .get(Constants.F_DROP_COLUMN);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramDropColumn);

//                //si se ha seleccionado el parametro para buscar el ultimo registro de la tabla destino
                if ((evaluateFilters != null && evaluateFilters)) {
                    //si se ha seleccionado el parametro para eliminar columnas que no existen en origen
                    String createColumnQuery = properties.getProperty(Constants.Q_COLUMN_DROP_QUERY);
                    actualScript = new StringBuilder(origenActual.getColumn().dropColumnScript(createColumnQuery))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setColumnTree(origenActual, destinoActual, nodoColumna, Constants.ACTION_DROP, globalParams);
                } else {
                    //iguala columna de destino, tomando de base la origen, si el parametro es true
                    ParameterData paramAlterColumn = origenActual.getParameters()
                            .get(Constants.F_ALTER_COLUMN);
                    //aplica el alter table a todas las columnas, si el parametro es true
                    ParameterData paramAlterAllColumn = globalParams
                            .get(Constants.F_ALTER_ALL_COLUMN);
                    //crea una columna si el valor cambia de notnull hacia null
                    ParameterData paramAlterColumnNotNullToNull = globalParams
                            .get(Constants.F_ALTER_COLUMN_NOTNULL_TO_NULL);
                    //crea una columna si el valor cambia de notnull hacia null
                    ParameterData paramAlterColumnNullToNotNull = globalParams
                            .get(Constants.F_ALTER_COLUMN_NULL_TO_NOTNULL);
                    //evalua los filtros para ver si se cumple una de las condiciones
                    evaluateFilters = ParameterData.evaluateFilters(paramAlterColumn,
                            paramAlterAllColumn,
                            paramAlterColumnNotNullToNull,
                            paramAlterColumnNullToNotNull);
                    if ((evaluateFilters != null && evaluateFilters)) {
                        boolean generate = true;
                        if (paramAlterColumnNullToNotNull != null) {
                            generate = nullToNotNull(origenActual, destinoActual);
                        }
                        if (paramAlterColumnNotNullToNull != null) {
                            generate = nullToNotNull(destinoActual, origenActual);
                        }
                        if (generate) {
                            //crear alter de columna
                            String createColumnQuery = properties.getProperty(Constants.Q_COLUMN_ALTER_QUERY);
                            actualScript = new StringBuilder(origenActual.getColumn().createColumnScript(createColumnQuery,
                                    fixTypeName(origenActual.getColumn().getTypeName()),
                                    getColumnDataLenght(origenActual.getColumn()),
                                    nullableValue(origenActual.getColumn())))
                                    .append(Constants.QUERY_LINE_END);

                            query.append(actualScript);
                            origenActual.setScript(actualScript);
                            //agrega al arbol el objeto actual 
                            nodoActual = setColumnTree(origenActual, destinoActual, nodoColumna, Constants.ACTION_UPDATE, globalParams);
                        }
                    }
                }
            }
            //Establece el comentario de la columna
            ParameterData paramCreateColumnRemarks = origenActual.getParameters()
                    .get(Constants.F_CREATE_ALTER_COLUMN_REMARKS);
            ParameterData paramCreateAllColumnRemarks = globalParams
                    .get(Constants.F_CREATE_ALL_ALTER_COLUMN_REMARKS);
            //evalua los filtros para ver si se cumple una de las condiciones
            evaluateFilters = ParameterData.evaluateFilters(paramCreateColumnRemarks,
                    paramCreateAllColumnRemarks);
            if ((evaluateFilters != null && evaluateFilters)) {
                if (origenActual.getColumn().getRemark() != null) {
                    //crear alter para agregar el comentario a la columna actual
                    String createColumnQuery = properties.getProperty(Constants.Q_COLUMN_CREATE_REMARKS);
                    actualComments = new StringBuilder(origenActual.getColumn().createColumnRemarksScript(createColumnQuery,
                            origenActual.getColumn().getRemark()))
                            .append(Constants.QUERY_LINE_END);
                    comments.append(actualComments);
                    origenActual.setComments(actualComments);
                    //si no se agrego nodo de modificacion
                    if (nodoActual == null) {
                        setColumnTree(origenActual, destinoActual, nodoColumna, Constants.ACTION_UPDATE_COMMENTS, globalParams);
                    } else {
                        nodoActual.setComment(actualComments.toString());
                    }
                }
            }
        }

        //elimina todas las columnas que no existen en origen que están en destino, si el parametro es true
        ParameterData paramDropColumn = globalParams
                .get(Constants.F_DROP_ALL_COLUMN_NOT_FOUND_ON_ORIG);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramDropColumn);

        if (evaluateFilters != null && evaluateFilters) {
            for (String actual : colDest.keySet()) {
                origenActual = colOri.get(actual);
                destinoActual = colDest.get(actual);
                if (origenActual == null) {
                    //crear alter de columna
                    String createColumnQuery = properties.getProperty(Constants.Q_COLUMN_DROP_QUERY);
                    actualScript = new StringBuilder(destinoActual.getColumn().dropColumnScript(createColumnQuery))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    destinoActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setColumnTree(destinoActual, origenActual, nodoColumna, Constants.ACTION_DROP, globalParams);
                }
            }
        }
        if (!nodoColumna.getChildren().isEmpty()) {
            data.getChildren().add(nodoColumna);
        }

        ret.put(Constants.GROUP_DATA, query);
        ret.put(Constants.GROUP_COMMENTS, comments);

        return ret;
    }

    public HashMap<String, StringBuilder> generateColumnScriptForTable(HashMap<String, ColumnContainer> seqOri,
            HashMap<String, ParameterData> globalParams,
            GenericData data) {
        HashMap<String, StringBuilder> ret = new HashMap<>();
        StringBuilder query = new StringBuilder();
        StringBuilder comments = new StringBuilder();
        StringBuilder actualComments = new StringBuilder();
        ColumnContainer origenActual;
        Boolean evaluateFilters = null;
        int cont = 0;
        HashMap<String, Boolean> booleanParams = new HashMap<>();
        GenericData nodoColumna = GenericData.getNodeColumn();
        GenericData nodoActual;

        //crea una columna en destino, tomando de base la origen, si el parametro es true
        ParameterData paramCreateNewColumnForTable = globalParams
                .get(Constants.F_CREATE_COLUMN_FOR_TABLE);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramCreateNewColumnForTable);
        if (evaluateFilters != null) {
            booleanParams.put(Constants.F_CREATE_COLUMN_FOR_TABLE, true);
        }

        Boolean createColumnForTable = booleanParams.get(Constants.F_CREATE_COLUMN_FOR_TABLE);

        for (String actual : seqOri.keySet()) {
            nodoActual = null;
            origenActual = seqOri.get(actual);
            cont++;

            if (createColumnForTable != null && createColumnForTable) {
                //crear columna
                String createColumnQuery = properties.getProperty(Constants.Q_COLUMN_CREATE_FOR_TABLE_QUERY);
                query.append(origenActual.getColumn().createColumnForTableScript(createColumnQuery,
                        fixTypeName(origenActual.getColumn().getTypeName()),
                        getColumnDataLenght(origenActual.getColumn()),
                        nullableValue(origenActual.getColumn())));
                //si es el ultimo elemento, no agrega el separador
                if (cont < seqOri.size()) {
                    query.append(Constants.QUERY_TABLE_COLUMN_SEPARATOR);
                }
                //agrega al arbol el objeto actual 
                nodoActual = setColumnTree(origenActual, null, nodoColumna, Constants.ACTION_CREATE, globalParams);
            }

            //Establece el comentario de la columna
            ParameterData paramCreateColumnRemarks = origenActual.getParameters()
                    .get(Constants.F_CREATE_ALTER_COLUMN_REMARKS);
            ParameterData paramCreateAllColumnRemarks = globalParams
                    .get(Constants.F_CREATE_ALL_ALTER_COLUMN_REMARKS);
            //evalua los filtros para ver si se cumple una de las condiciones
            evaluateFilters = ParameterData.evaluateFilters(paramCreateColumnRemarks,
                    paramCreateAllColumnRemarks);
            if ((evaluateFilters != null && evaluateFilters)) {
                if (origenActual.getColumn().getRemark() != null) {
                    //crear alter para agregar el comentario a la columna actual
                    String createColumnQuery = properties.getProperty(Constants.Q_COLUMN_CREATE_REMARKS);
                    actualComments=new StringBuilder(origenActual.getColumn().createColumnRemarksScript(createColumnQuery,
                            origenActual.getColumn().getRemark()))
                            .append(Constants.QUERY_LINE_END);
                    comments.append(actualComments);
                    origenActual.setComments(actualComments);
                    //si no se agrego nodo de modificacion
                    if (nodoActual == null) {
                        setColumnTree(origenActual, null, nodoColumna, Constants.ACTION_UPDATE_COMMENTS, globalParams); 
                    } else {
                        nodoActual.setComment(actualComments.toString());
                    }
                }
            }
        }

        if (!nodoColumna.getChildren().isEmpty()) {
            data.getChildren().add(nodoColumna);
        }

        ret.put(Constants.GROUP_DATA, query);
        ret.put(Constants.GROUP_COMMENTS, comments);

        return ret;
    }

    public GenericData setColumnTree(ColumnContainer origenActual, ColumnContainer destinoActual, GenericData data, String action, HashMap<String, ParameterData> globalParam) {
        GenericData ret = null;
        Boolean evaluateFilters;
        //generar objetos diferentes, para columnas
        ParameterData paramGetDiferentColumn = globalParam
                .get(Constants.F_GET_DIFERENT_COLUMNS);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramGetDiferentColumn);
        if (evaluateFilters != null && evaluateFilters) {
            ret = data.convertColumnContainerToGenericData(origenActual, action);
            if (destinoActual != null) {
                ret.setTarget(destinoActual);
            }
            data.getChildren().add(ret);
        }
        return ret;
    }

    public Long getLastColumnValue(String tableSchema, String tableName, String seqColumnName, Connection con) {
        Long ret = 0L;
        String lastSeqValQuery = properties.getProperty(Constants.Q_SEQUENCE_LAST_TABLE_VALUE);
        lastSeqValQuery = lastSeqValQuery
                .replace(Constants.P_SEQ_ID_COLUMN, seqColumnName)
                .replace(Constants.P_TABLE_SCHEMA, tableSchema)
                .replace(Constants.P_TABLE_NAME, tableName);
        try {
            PreparedStatement statement = con.prepareStatement(lastSeqValQuery);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ret = rs.getLong(Constants.P_SEQ_VAL);
            }
        } catch (Exception e) {
        }

        return ret;
    }

    public HashMap<String, ColumnContainer> getTableColumnsHash(String schema, String tableName, String columnName, Connection connection) {
        HashMap<String, ColumnContainer> ret = new HashMap<>();
        try {
            HashMap<String, String> comments = getComment(tableName, connection);
            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getColumns(null, schema, tableName, columnName != null ? columnName : null);
            while (rs.next()) {
                ColumnContainer val = new ColumnContainer();
                val.setColumn(new ColumnData());
                val.getColumn().setTableSchema(schema);
                val.getColumn().setTableName(tableName);
                val.getColumn().setColumnSize(rs.getString("COLUMN_SIZE"));
                val.getColumn().setDataType(rs.getString("DATA_TYPE"));//java.sql.Types
                val.getColumn().setTypeName(rs.getString("TYPE_NAME"));
                val.getColumn().setColumnName(rs.getString("COLUMN_NAME"));
                val.getColumn().setDecimalDigits(rs.getString("DECIMAL_DIGITS"));
                val.getColumn().setNumPrecRadix(rs.getString("NUM_PREC_RADIX"));
                val.getColumn().setNullable(rs.getString("IS_NULLABLE").equals("YES"));
                val.getColumn().setRemark(comments.get(val.getColumn().getColumnName()));
                val.setParameters(new HashMap<>());
                ret.put(val.getColumn().getColumnName(), val);
            }
            rs.close();
        } catch (Exception e) {
        }
        return ret;
    }

    public HashMap<String, String> getComment(String tableName, Connection connection) {
        HashMap<String, String> ret = new HashMap<>();
        String getCommentQuery = properties.getProperty(Constants.Q_COLUMN_COMMENT);
        getCommentQuery = getCommentQuery
                .replace(Constants.P_TABLE_NAME, tableName);
        try {
            PreparedStatement statement = connection.prepareStatement(getCommentQuery);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ret.put(rs.getString(Constants.P_COL_NAME_VAL),
                        rs.getString(Constants.P_COMMENTS_VAL).replaceAll("'", "''").replaceAll("\n", ""));
            }
        } catch (Exception e) {
        }

        return ret;
    }

    public String getColumnDataLenght(ColumnData column) {
        StringBuilder ret = new StringBuilder();
        String fixedTypeName = fixTypeName(column.getTypeName());

        //tipos de datos que no tienen definido un tamanio al crear la columna
        StringBuilder sb = new StringBuilder();
        int parameters = 0, contador = 0;
        boolean finRecorrido;
        boolean encontrado = true;

        while (parameters <= 2) {
            finRecorrido = false;
            contador = 0;
            while (!finRecorrido) {
                sb = new StringBuilder();
                contador++;
                //crea el string correspondiente al tipo de dato, ej: data_type_size_0_1=DATE
                sb.append(Constants.DATA_TYPE_SIZE_PREFIX).append(parameters).append("_").append(contador);
                String val = properties.getProperty(sb.toString());
                if (val != null) {
                    if (val.equals(fixedTypeName)) {
                        encontrado = true;
                        finRecorrido = true;
                        break;
                    }
                } else {
                    encontrado = false;
                    finRecorrido = true;
                }
            }
            //termina la busqueda si se encontro la cantidad de parametros y
            if (encontrado) {
                break;
            }
            parameters++;
        }

        switch (parameters) {
            case 0:
                ret = new StringBuilder();
                break;
            case 1:
                ret.append(column.getColumnSize());
                break;
            case 2:
                try {
                    Long decDigits = 0L, colSize = 0L;
                    if (column.getDecimalDigits() != null) {
                        decDigits = Long.parseLong(column.getDecimalDigits());
                    }
                    if (column.getColumnSize() != null) {
                        colSize = Long.parseLong(column.getColumnSize());
                    }
                    if (decDigits > 0 && colSize > 0) {
                        ret.append(column.getColumnSize()).append(",").append(column.getDecimalDigits());
                    } else if (colSize > 0 && decDigits <= 0) {
                        ret.append(column.getColumnSize());
                    }
                } catch (Exception e) {
                }

                break;
        }

        return ret.toString();
    }

    //quita los parentesis y los valores dentro de parentesis de los nombres de tipo de dato
    public String fixTypeName(String typeName) {
        StringBuilder ret = new StringBuilder();
        int ini = typeName.indexOf("(");
        int end = typeName.indexOf("(");
        if (ini != -1 && end != -1) {
            String initFragment = typeName.substring(0, ini);
            String endFragment = typeName.substring(end, typeName.length());
            ret.append(initFragment).append(endFragment);
        } else {
            ret.append(typeName);
        }
        return ret.toString();
    }

    public String nullableValue(ColumnData column) {
        String ret = "";
        if (column.isNullable()) {
            ret = properties.getProperty(Constants.P_COLUMN_NULLABLE_VALUE);
        } else {
            ret = properties.getProperty(Constants.P_COLUMN_NOT_NULLABLE_VALUE);
        }
        return ret;
    }

    private boolean nullToNotNull(ColumnContainer colOrig, ColumnContainer colDest) {
        boolean ban = false;
        if (colOrig.getColumn().isNullable() && !colDest.getColumn().isNullable()) {
            ban = true;
        }
        return ban;
    }
}
