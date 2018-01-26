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
import com.unicomer.psdisr.generator.model.container.SequenceContainer;
import com.unicomer.psdisr.generator.model.SequenceData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author francisco_medina
 */
public class SequenceGenerator {

    Properties properties;

    public SequenceGenerator(Properties p) {
        properties = p;
    }

    public HashMap<String, StringBuilder> generateSequenceScript(HashMap<String, SequenceContainer> seqOri,
            HashMap<String, SequenceContainer> seqDest,
            HashMap<String, ParameterData> globalParams,
            Connection conDest,
            GenericData data) {
        HashMap<String, StringBuilder> ret = new HashMap<>();
        SequenceContainer destinoActual;
        SequenceContainer origenActual;
        Boolean evaluateFilters = null;
        StringBuilder query = new StringBuilder();
        GenericData nodoSecuencia = GenericData.getNodeSequence();
        StringBuilder actualScript = new StringBuilder();

        for (String actual : seqOri.keySet()) {
            destinoActual = seqDest.get(actual);
            origenActual = seqOri.get(actual);
            //secuencia que no existe en BD destino
            if (destinoActual == null) {
                //crea la la secuencia en destino, tomando de base la de origen, si el parametro es true
                ParameterData paramCreateNewSequence = origenActual.getParameters()
                        .get(Constants.F_CREATE_SEQUENCE);
                //Crea todas las secuencias que no existen en destino, tomando de base las de origen, si el parametro es true
                ParameterData paramCreateAllNewSequence = globalParams
                        .get(Constants.F_CREATE_ALL_SEQUENCE);
                //Busca en destino (esquema.tabla.campo), el maximo valor ingresado para asignarle el siguiente a la secuencia a modificar, si el parametro es true
                ParameterData paramSearchMaxDestinationValueTable = origenActual.getParameters()
                        .get(Constants.F_SEARCH_MAX_DESTINATION_VALUE_TABLE);
                //evalua los filtros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramCreateAllNewSequence,
                        paramCreateNewSequence,
                        paramSearchMaxDestinationValueTable);
                //si se ha seleccionado el parametro para crear secuencias si no existen en destino
                if (evaluateFilters != null && evaluateFilters) {
                    Long lastValue = null;
                    //si se ha seleccionado la la busqueda del maximo valor ingresado para asignarle a la secuencia
                    if (paramSearchMaxDestinationValueTable != null && ParameterData.evaluateFilters(paramSearchMaxDestinationValueTable)
                            //busca si se ha ingresado una tabla para esta secuencia, si es asi busca su ultimo valor
                            && origenActual.getSequence().getSchema() != null
                            && origenActual.getSequence().getTableSequence() != null
                            && origenActual.getSequence().getColumnTableSequence() != null) {
                        //busca el id maximo de la secuencia en la tabla asignada
                        lastValue = getLastSequenceValue(origenActual.getSequence().getSchema(),
                                origenActual.getSequence().getTableSequence(),
                                origenActual.getSequence().getColumnTableSequence(),
                                conDest);
                    }
                    //crear secuencia
                    String createSequenceQuery = properties.getProperty(Constants.Q_SEQUENCE_CREATE_QUERY);
                    actualScript = new StringBuilder(origenActual.getSequence().createSequenceScript(createSequenceQuery, lastValue, null))
                            .append(Constants.QUERY_LINE_END);
                    query.append(actualScript);
                    origenActual.setScript(actualScript);
                    //agrega al arbol el objeto actual 
                    setSequenceTree(origenActual, null, nodoSecuencia, Constants.ACTION_CREATE, globalParams);
                }
                //compara las secuencias por sus valores definidos en el equals del Objeto SequenceData
            } else if (origenActual.getSequence().equals(destinoActual.getSequence())) {
                //no es necesario ejecutar cambios
//                query = new StringBuilder();
            } else {
                //Busca en destino (esquema.tabla.campo), el maximo valor ingresado para asignarle el siguiente a la secuencia a modificar, si el parametro es true
                ParameterData paramSearchMaxDestinationValueTable = origenActual.getParameters()
                        .get(Constants.F_SEARCH_MAX_DESTINATION_VALUE_TABLE);
                //evalua los parametros para ver si se cumple una de las condiciones
                evaluateFilters = ParameterData.evaluateFilters(paramSearchMaxDestinationValueTable);

                //si se ha seleccionado el parametro para buscar el ultimo registro de la tabla destino
                if ((evaluateFilters != null && evaluateFilters)
                        //busca si se ha ingresado una tabla para esta secuencia, si es asi busca su ultimo valor
                        && origenActual.getSequence().getSchema() != null
                        && origenActual.getSequence().getTableSequence() != null
                        && origenActual.getSequence().getColumnTableSequence() != null) {
                    //busca el id maximo de la secuencia en la tabla asignada
                    Long destMaxId = getLastSequenceValue(origenActual.getSequence().getSchema(),
                            origenActual.getSequence().getTableSequence(),
                            origenActual.getSequence().getColumnTableSequence(),
                            conDest);
                    //verifica si la secuencia no esta alineada con el ultimo insert de la tabla a la que pertenece
                    if (!destMaxId.equals(origenActual.getSequence().getLastNumber() - 1L)) {
                        //incremento = maximo id registrado - ultimo valor de la secuencia actual -1
                        Long increment = destMaxId - origenActual.getSequence().getLastNumber() - 1L;

                        //genera script para modificar el valor de la secuencia usando incrementos
                        String alterSequenceQuery = properties.getProperty(Constants.Q_SEQUENCE_ALTER_QUERY);
                        actualScript = new StringBuilder(origenActual.getSequence().alterSequenceScript(alterSequenceQuery, increment)).
                                append(Constants.QUERY_LINE_END);
                        String incrementSequenceQuery = properties.getProperty(Constants.Q_SEQUENCE_QUERY_INCREMENT);
                        actualScript.append(origenActual.getSequence().incrementSequenceScript(incrementSequenceQuery))
                                .append(Constants.QUERY_LINE_END);
                        alterSequenceQuery = properties.getProperty(Constants.Q_SEQUENCE_ALTER_QUERY);
                        actualScript.append(origenActual.getSequence().alterSequenceScript(alterSequenceQuery,
                                origenActual.getSequence().getIncrementBy()))
                                .append(Constants.QUERY_LINE_END);
                        query.append(actualScript);
                        origenActual.setScript(actualScript);

                        //agrega al arbol el objeto actual 
                        setSequenceTree(origenActual, destinoActual, nodoSecuencia, Constants.ACTION_UPDATE, globalParams);
                    }
                } else {
                    //Modifica la secuencia actual para que tengan sus valores iguales en destino, tomando de base el origen, si el parametro es true
                    ParameterData paramSetEqualsSequences = origenActual.getParameters()
                            .get(Constants.F_SET_EQUALS_SEQUENCES);
                    //Modifica todas las secuencias para que tengan sus valores iguales en destino, tomando de base el origen, si el parametro es true
                    ParameterData paramSetAllEqualsSequences = globalParams
                            .get(Constants.F_SET_ALL_EQUALS_SEQUENCES);
                    //evalua los parametros para ver si se cumple una de las condiciones
                    evaluateFilters = ParameterData.evaluateFilters(paramSetEqualsSequences,
                            paramSetAllEqualsSequences);
                    //si se ha seleccionado el parametro para igualar las secuencias considerando como base el origen
                    if (evaluateFilters != null && evaluateFilters
                            //verifica si las secuencias son diferentes en el origen y destino
                            && !origenActual.getSequence().getLastNumber().equals(destinoActual.getSequence().getLastNumber())) {
                        //incremento = ultimo valor de la secuencia origen - ultimo valor de la secuencia destino -1
                        Long increment = origenActual.getSequence().getLastNumber() - destinoActual.getSequence().getLastNumber() - 1L;
                        //genera script para modificar el valor de la secuencia usando incrementos
                        String alterSequenceQuery = properties.getProperty(Constants.Q_SEQUENCE_ALTER_QUERY);
                        actualScript = new StringBuilder(origenActual.getSequence().alterSequenceScript(alterSequenceQuery, increment))
                                .append(Constants.QUERY_LINE_END);
                        String incrementSequenceQuery = properties.getProperty(Constants.Q_SEQUENCE_QUERY_INCREMENT);
                        actualScript.append(origenActual.getSequence().incrementSequenceScript(incrementSequenceQuery))
                                .append(Constants.QUERY_LINE_END);
                        alterSequenceQuery = properties.getProperty(Constants.Q_SEQUENCE_ALTER_QUERY);
                        actualScript.append(origenActual.getSequence().alterSequenceScript(alterSequenceQuery,
                                origenActual.getSequence().getIncrementBy()))
                                .append(Constants.QUERY_LINE_END);

                        query.append(actualScript);
                        origenActual.setScript(actualScript);
                        //agrega al arbol el objeto actual 
                        setSequenceTree(origenActual, destinoActual, nodoSecuencia, Constants.ACTION_UPDATE, globalParams);
                    }
                }
            }
        }

        if (!nodoSecuencia.getChildren().isEmpty()) {
            data.getChildren().add(nodoSecuencia);
        }

        ret.put(Constants.GROUP_DATA, query);
        return ret;
    }

    public void setSequenceTree(SequenceContainer origenActual, SequenceContainer destinoActual, GenericData data, String action, HashMap<String, ParameterData> globalParam) {
        Boolean evaluateFilters;
        //generar objetos diferentes, para columnas
        ParameterData paramGetDiferentColumn = globalParam
                .get(Constants.F_GET_DIFERENT_SEQUENCES);
        //evalua los filtros para ver si se cumple una de las condiciones
        evaluateFilters = ParameterData.evaluateFilters(paramGetDiferentColumn);
        if (evaluateFilters != null && evaluateFilters) {
            GenericData ret = data.convertSequenceContainerToGenericData(origenActual, action);
            if (destinoActual != null) {
                ret.setTarget(destinoActual);
            }
            ret.setParent(data);
            data.getChildren().add(ret);
        }
    }

    public Long getLastSequenceValue(String tableSchema, String tableName, String seqColumnName, Connection con) {
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
            ret = 0L;
        }

        return ret;
    }

    public HashMap<String, SequenceContainer> getSequencesLastValueHash(String sequenceOwner, String sequenceName, Connection connection, ConnectionData connectionData) {
        HashMap<String, SequenceContainer> ret = new HashMap<>();
        try {

            String seqQuery = properties.getProperty(Constants.Q_SEQUENCE_QUERY);
            //obtener los elementos del query que son opcionales, el primero fragmento siempre es obligatorio
            String[] optionalFilter = seqQuery.split(Constants.P_SPLIT_CHARACTER);

            //query obligatorio
            StringBuilder actualString = new StringBuilder(optionalFilter[0]);
            //agrega parametros obligatorios
            actualString = new StringBuilder(actualString.toString().replace(Constants.P_SEQUENCE_OWNER, sequenceOwner));

            //si tiene parametros opcionales
            if (optionalFilter != null && optionalFilter.length > 1) {
                if (sequenceName != null && !sequenceName.isEmpty()) {
                    StringBuilder optionalParam = new StringBuilder(optionalFilter[1]);
                    optionalParam = new StringBuilder(optionalParam.toString().replace(Constants.P_SEQUENCE_NAME, sequenceName));
                    actualString.append(optionalParam);
                }
            }
            seqQuery = actualString.toString();

            PreparedStatement statement = connection.prepareStatement(seqQuery);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SequenceContainer val = new SequenceContainer();
                val.setSequence(new SequenceData());
                val.getSequence().setSequenceName(rs.getString(Constants.SEQUENCE_NAME));
                val.getSequence().setLastNumber(rs.getLong(Constants.LAST_NUMBER));
                val.getSequence().setIncrementBy(rs.getLong(Constants.INCREMENT_BY));
                val.getSequence().setSchema(sequenceOwner);
                val.setParameters(new HashMap<>());
                val.setConnectionData(connectionData);
                ret.put(val.getSequence().getSequenceName(), val);
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
        }
        return ret;
    }
}
