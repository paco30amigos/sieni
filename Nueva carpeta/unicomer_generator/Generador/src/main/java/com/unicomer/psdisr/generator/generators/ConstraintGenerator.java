/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.generators;

import com.unicomer.psdisr.generator.model.utils.Constants;
import com.unicomer.psdisr.generator.model.ParameterData;
import com.unicomer.psdisr.generator.model.ConstraintData;
import com.unicomer.psdisr.generator.model.GenericData;
import com.unicomer.psdisr.generator.model.container.ConstraintContainer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author francisco_medina
 */
public class ConstraintGenerator {

    Properties properties;

    public ConstraintGenerator(Properties p) {
        properties = p;
    }

    public HashMap<String, StringBuilder> generatePkConstraintScript(HashMap<String, ConstraintContainer> valOri,
            HashMap<String, ConstraintContainer> valDest,
            HashMap<String, ParameterData> globalParams,
            Connection conDest,
            GenericData data) {
        HashMap<String, StringBuilder> ret = new HashMap<>();
        StringBuilder query = new StringBuilder();
        StringBuilder comments = new StringBuilder();
        ConstraintContainer destinoActual;
        ConstraintContainer origenActual;
        Boolean evaluateFilters = null;
        GenericData nodoPk = GenericData.getNodePk();
        StringBuilder actualScript = new StringBuilder();

        for (String actual : valOri.keySet()) {
            destinoActual = valDest.get(actual);
            origenActual = valOri.get(actual);
            //llave primaria que no existe en BD destino
            if (destinoActual == null) {
                //crea una columna en destino, tomando de base la origen, si el parametro es true
                ParameterData paramCreateNewColumn = origenActual.getParameters()
                        .get(Constants.F_CREATE_PK);
                ParameterData paramCreateAllColumn = globalParams
                        .get(Constants.F_CREATE_ALL_PK);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramCreateNewColumn,
                        paramCreateAllColumn);
                //si se ha seleccionado el parametro para crear secuencias si no existen en destino
                if (evaluateFilters != null && evaluateFilters) {
                    //crear columna
                    String createPkQuery = properties.getProperty(Constants.Q_CONSTRAINT_CREATE_PK_QUERY);
                    actualScript = new StringBuilder(origenActual.getConstraint().createPkScript(createPkQuery))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setConstraintPkTree(origenActual, null, nodoPk, Constants.ACTION_CREATE, globalParams);
                }
                //compara las columnas por sus valores definidos en el equals del Objeto ColumnData
            } else if (origenActual.getConstraint().equals(destinoActual.getConstraint())) {
                //no es necesario ejecutar cambios
            } else {
                //elimina la llave primaria de la tabla actual en destino
                ParameterData paramDropColumn = origenActual.getParameters()
                        .get(Constants.F_DROP_PK);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramDropColumn);

//                //si se ha seleccionado el parametro para buscar el ultimo registro de la tabla destino
                if ((evaluateFilters != null && evaluateFilters)) {
                    //si se ha seleccionado el parametro para eliminar columnas que no existen en origen
                    String dropConstraintQuery = properties.getProperty(Constants.Q_CONSTRAINT_DROP_QUERY);
                    actualScript = new StringBuilder(origenActual.getConstraint().dropConstraintScript(dropConstraintQuery, origenActual.getConstraint().getConstraintName()))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setConstraintPkTree(origenActual, destinoActual, nodoPk, Constants.ACTION_DROP, globalParams);
                } else {
                    //iguala llave primaria destino, tomando de base la origen
                    ParameterData paramAlterPk = origenActual.getParameters()
                            .get(Constants.F_ALTER_PK);
                    //aplica el alter table a todas las llaves primarias
                    ParameterData paramAlterAllPk = globalParams
                            .get(Constants.F_ALTER_ALL_PK);
                    //evalua los filtros para ver si se cumple una de las condiciones
                    evaluateFilters = ParameterData.evaluateFilters(paramAlterPk,
                            paramAlterAllPk);
                    if ((evaluateFilters != null && evaluateFilters)) {
                        //eliminar constraint, para sobreescribirlo despues
                        String dropConstraintQuery = properties.getProperty(Constants.Q_CONSTRAINT_DROP_QUERY);
                        actualScript=new StringBuilder(origenActual.getConstraint().dropConstraintScript(dropConstraintQuery, origenActual.getConstraint().getConstraintName()))
                                .append(Constants.QUERY_LINE_END);
                        query.append(actualScript);

                        //crear nuevo constraint, con los parametros correctos
                        String createConstraint = properties.getProperty(Constants.Q_CONSTRAINT_CREATE_PK_QUERY);
                        actualScript.append(origenActual.getConstraint().createPkScript(createConstraint))
                                .append(Constants.QUERY_LINE_END);
                        query.append(actualScript);
                        origenActual.setScript(actualScript);
                        //agrega al arbol el objeto actual 
                        setConstraintPkTree(origenActual, destinoActual, nodoPk, Constants.ACTION_UPDATE, globalParams);
                    }
                }
            }
        }

        //elimina todas las columnas que no existen en origen que están en destino, si el parametro es true
        ParameterData paramDropColumn = globalParams
                .get(Constants.F_DROP_ALL_PK_NOT_FOUND_ON_ORIG);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramDropColumn);

        if (evaluateFilters != null && evaluateFilters) {
            for (String actual : valDest.keySet()) {
                origenActual = valOri.get(actual);
                destinoActual = valDest.get(actual);
                if (origenActual == null) {
                    //crear alter de columna
                    String dropConstraintQuery = properties.getProperty(Constants.Q_CONSTRAINT_DROP_QUERY);
                    actualScript = new StringBuilder(destinoActual.getConstraint().dropConstraintScript(dropConstraintQuery,
                            destinoActual.getConstraint().getConstraintName()))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    destinoActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setConstraintPkTree(destinoActual, origenActual, nodoPk, Constants.ACTION_DROP, globalParams);
                }
            }
        }

        if (!nodoPk.getChildren().isEmpty()) {
            data.getChildren().add(nodoPk);
        }

        ret.put(Constants.GROUP_DATA, query);
        ret.put(Constants.GROUP_COMMENTS, comments);

        return ret;
    }

    public HashMap<String, StringBuilder> generateFkConstraintScript(HashMap<String, ConstraintContainer> valOri,
            HashMap<String, ConstraintContainer> valDest,
            HashMap<String, ParameterData> globalParams,
            Connection conDest,
            GenericData data) {
        HashMap<String, StringBuilder> ret = new HashMap<>();
        StringBuilder query = new StringBuilder();
        StringBuilder comments = new StringBuilder();
        ConstraintContainer destinoActual;
        ConstraintContainer origenActual;
        Boolean evaluateFilters = null;
        GenericData nodoFk = GenericData.getNodeFk();
        StringBuilder actualScript = new StringBuilder();

        for (String actual : valOri.keySet()) {
            destinoActual = valDest.get(actual);
            origenActual = valOri.get(actual);
            //llave primaria que no existe en BD destino
            if (destinoActual == null) {
                //crea una columna en destino, tomando de base la origen, si el parametro es true
                ParameterData paramCreateNewColumn = origenActual.getParameters()
                        .get(Constants.F_CREATE_FK);
                ParameterData paramCreateAllColumn = globalParams
                        .get(Constants.F_CREATE_ALL_FK);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramCreateNewColumn,
                        paramCreateAllColumn);
                //si se ha seleccionado el parametro para crear secuencias si no existen en destino
                if (evaluateFilters != null && evaluateFilters) {
                    //crear columna
                    String createFkQuery = properties.getProperty(Constants.Q_CONSTRAINT_CREATE_FK_QUERY);
                    actualScript = new StringBuilder(origenActual.getConstraint().createFkScript(createFkQuery))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setConstraintFkTree(origenActual, null, nodoFk, Constants.ACTION_CREATE, globalParams);
                }
                //crea el grant si el esquema de la llave foranea no es del mismo esquema
                getScriptDiferentSchemaFK(origenActual);
                //compara las columnas por sus valores definidos en el equals del Objeto ColumnData
            } else if (origenActual.getConstraint().equals(destinoActual.getConstraint())) {
                //no es necesario ejecutar cambios
            } else {
                //elimina la llave primaria de la tabla actual en destino
                ParameterData paramDropColumn = origenActual.getParameters()
                        .get(Constants.F_DROP_FK);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramDropColumn);

//                //si se ha seleccionado el parametro para buscar el ultimo registro de la tabla destino
                if ((evaluateFilters != null && evaluateFilters)) {
                    //si se ha seleccionado el parametro para eliminar columnas que no existen en origen
                    String dropConstraintQuery = properties.getProperty(Constants.Q_CONSTRAINT_DROP_QUERY);
                    actualScript = new StringBuilder(origenActual.getConstraint().dropConstraintScript(dropConstraintQuery, origenActual.getConstraint().getConstraintName()))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setConstraintFkTree(origenActual, destinoActual, nodoFk, Constants.ACTION_DROP, globalParams);
                } else {
                    //iguala llave primaria destino, tomando de base la origen
                    ParameterData paramAlterFk = origenActual.getParameters()
                            .get(Constants.F_ALTER_FK);
                    //aplica el alter table a todas las llaves primarias
                    ParameterData paramAlterAllFk = globalParams
                            .get(Constants.F_ALTER_ALL_FK);
                    //evalua los filtros para ver si se cumple una de las condiciones
                    evaluateFilters = ParameterData.evaluateFilters(paramAlterFk,
                            paramAlterAllFk);
                    if ((evaluateFilters != null && evaluateFilters)) {
                        //eliminar constraint, para sobreescribirlo despues
                        String dropConstraintQuery = properties.getProperty(Constants.Q_CONSTRAINT_DROP_QUERY);
                        actualScript = new StringBuilder(origenActual.getConstraint().dropConstraintScript(dropConstraintQuery, origenActual.getConstraint().getConstraintName()))
                                .append(Constants.QUERY_LINE_END);

                        //crear nuevo constraint, con los parametros correctos
                        String createConstraint = properties.getProperty(Constants.Q_CONSTRAINT_CREATE_FK_QUERY);
                        actualScript.append(origenActual.getConstraint().createFkScript(createConstraint))
                                .append(Constants.QUERY_LINE_END);
                        query.append(actualScript);
                        origenActual.setScript(actualScript);
                        //agrega al arbol el objeto actual 
                        setConstraintFkTree(origenActual, destinoActual, nodoFk, Constants.ACTION_UPDATE, globalParams);

                        //crea el grant si el esquema de la llave foranea no es del mismo esquema
                        getScriptDiferentSchemaFK(origenActual);
                    }
                }
            }
        }

        //elimina todas las columnas que no existen en origen que están en destino, si el parametro es true
        ParameterData paramDropColumn = globalParams
                .get(Constants.F_DROP_ALL_FK_NOT_FOUND_ON_ORIG);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramDropColumn);

        if (evaluateFilters != null && evaluateFilters) {
            for (String actual : valDest.keySet()) {
                origenActual = valOri.get(actual);
                destinoActual = valDest.get(actual);
                if (origenActual == null) {
                    //crear alter de columna
                    String dropConstraintQuery = properties.getProperty(Constants.Q_CONSTRAINT_DROP_QUERY);
                    actualScript = new StringBuilder(destinoActual.getConstraint().dropConstraintScript(dropConstraintQuery,
                            destinoActual.getConstraint().getConstraintName()))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    destinoActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setConstraintFkTree(destinoActual, origenActual, nodoFk, Constants.ACTION_DROP, globalParams);
                }
            }
        }

        if (!nodoFk.getChildren().isEmpty()) {
            data.getChildren().add(nodoFk);
        }
        ret.put(Constants.GROUP_DATA, query);
        ret.put(Constants.GROUP_COMMENTS, comments);

        return ret;
    }

    public StringBuilder getScriptDiferentSchemaFK(ConstraintContainer fk) {
        StringBuilder ret = new StringBuilder();
        if (fk.getConstraint().getFkTableSchema() != null && fk.getConstraint().getPkTableSchema() != null
                && !fk.getConstraint().getFkTableSchema().equals(fk.getConstraint().getPkTableSchema())) {
            String grantFkOtherSchemaQuery = properties.getProperty(Constants.Q_GRANT_FK_OTHER_SCHEMA);
            ret.append(fk.getConstraint().grantDiferentSchemaFK(grantFkOtherSchemaQuery)).
                    append(Constants.QUERY_LINE_END);
        }
        return ret;
    }

    public void setConstraintPkTree(ConstraintContainer origenActual, ConstraintContainer destinoActual, GenericData data, String action, HashMap<String, ParameterData> globalParam) {
        Boolean evaluateFilters;
        //generar objetos diferentes, para columnas
        ParameterData paramGetDiferentColumn = globalParam
                .get(Constants.F_GET_DIFERENT_PK);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramGetDiferentColumn);
        if (evaluateFilters != null && evaluateFilters) {
            GenericData ret = data.convertPkContainerToGenericData(origenActual, action);
            if (destinoActual != null) {
                ret.setTarget(destinoActual);
            }
            data.getChildren().add(ret);
        }
    }

    public void setConstraintFkTree(ConstraintContainer origenActual, ConstraintContainer destinoActual, GenericData data, String action, HashMap<String, ParameterData> globalParam) {
        Boolean evaluateFilters;
        //generar objetos diferentes, para columnas
        ParameterData paramGetDiferentColumn = globalParam
                .get(Constants.F_GET_DIFERENT_FK);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramGetDiferentColumn);
        if (evaluateFilters != null && evaluateFilters) {
            GenericData ret = data.convertFkContainerToGenericData(origenActual, action);
            if (destinoActual != null) {
                ret.setTarget(destinoActual);
            }
            data.getChildren().add(ret);
        }
    }

    public HashMap<String, ConstraintContainer> getPkTableHash(String schema, String tableName, Connection connection) {
        HashMap<String, ConstraintContainer> ret = new HashMap<>();
        try {

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getPrimaryKeys(null, schema, tableName);
            while (rs.next()) {
                ConstraintContainer val = new ConstraintContainer();
                val.setConstraint(new ConstraintData());
                val.getConstraint().setTableSchema(schema);
                val.getConstraint().setPkTableName(tableName);
                val.getConstraint().setConstraintName(rs.getString("PK_NAME"));
                val.getConstraint().setPkColumnName(rs.getString("COLUMN_NAME"));
                val.setParameters(new HashMap<>());
                ret.put(val.getConstraint().getPkColumnName(), val);
            }
            rs.close();
        } catch (Exception e) {
        }
        return ret;
    }

    //trae las llaves foraneas de las que depende esta tabla (hijo->padre)
    public HashMap<String, ConstraintContainer> getFkTableHash(String schema, String tableName, Connection connection) {
        HashMap<String, ConstraintContainer> ret = new HashMap<>();
        try {

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getImportedKeys(null, schema, tableName);
            while (rs.next()) {
                ConstraintContainer val = new ConstraintContainer();
                val.setConstraint(new ConstraintData());
                val.getConstraint().setTableSchema(schema);
                val.getConstraint().setPkTableName(tableName);
                val.getConstraint().setPkColumnName(rs.getString("PKCOLUMN_NAME"));
                val.getConstraint().setFkTableSchema(rs.getString("FKTABLE_SCHEM"));
                val.getConstraint().setFkTableName(rs.getString("FKTABLE_NAME"));
                val.getConstraint().setFkColumnName(rs.getString("FKCOLUMN_NAME"));
                val.getConstraint().setConstraintName(rs.getString("FK_NAME"));
                val.setParameters(new HashMap<>());
                ret.put(val.getConstraint().getPkColumnName(), val);
            }
            rs.close();
        } catch (Exception e) {
        }
        return ret;
    }

    //relacion inversa, trae las tablas que tienen a la tabla actual como foranea (padre->hijo)
    public HashMap<String, ConstraintContainer> getFkParentsTableHash(String schema, String tableName, Connection connection) {
        HashMap<String, ConstraintContainer> ret = new HashMap<>();
        try {

            DatabaseMetaData meta = connection.getMetaData();
            ResultSet rs = meta.getImportedKeys(null, schema, tableName);
            while (rs.next()) {
                ConstraintContainer val = new ConstraintContainer();
                val.getConstraint().setTableSchema(schema);
                val.getConstraint().setPkTableName(tableName);
                val.getConstraint().setPkColumnName(rs.getString("PKCOLUMN_NAME"));
                val.getConstraint().setFkTableSchema(rs.getString("FKTABLE_SCHEM"));
                val.getConstraint().setFkTableName(rs.getString("FKTABLE_NAME"));
                val.getConstraint().setFkColumnName(rs.getString("FKCOLUMN_NAME"));
                val.getConstraint().setConstraintName(rs.getString("FK_NAME"));
                val.setParameters(new HashMap<>());
                ret.put(val.getConstraint().getPkColumnName(), val);
            }
            rs.close();
        } catch (Exception e) {
        }
        return ret;
    }
}
