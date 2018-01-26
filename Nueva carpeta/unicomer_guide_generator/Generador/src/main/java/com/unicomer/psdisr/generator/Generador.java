/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator;

import com.unicomer.psdisr.generator.generators.ColumnGenerator;
import com.unicomer.psdisr.generator.model.utils.Constants;
import com.unicomer.psdisr.generator.model.ParameterData;
import com.unicomer.psdisr.generator.generators.SequenceGenerator;
import com.unicomer.psdisr.generator.generators.TableGenerator;
import com.unicomer.psdisr.generator.model.ConnectionData;
import com.unicomer.psdisr.generator.model.GenericData;
import com.unicomer.psdisr.generator.model.container.ColumnContainer;
import com.unicomer.psdisr.generator.model.container.SequenceContainer;
import com.unicomer.psdisr.generator.model.container.TableContainer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import oracle.jdbc.pool.OracleDataSource;

/**
 *
 * @author francisco_medina
 */
public class Generador {

    Properties properties = new Properties();

    public Generador() {
        try {
            properties.load(Generador.class.getClassLoader().getResourceAsStream(Constants.PROPERTIES_RESOURCE_PATH));
            tg = new TableGenerator(properties);
            cg = new ColumnGenerator(properties);
            sg = new SequenceGenerator(properties);
        } catch (Exception e) {
        }
    }

    private GenericData result;
    public TableGenerator tg;
    public ColumnGenerator cg;
    public SequenceGenerator sg;

    public GenericData getResult() {
        return result;
    }

    public void setResult(GenericData result) {
        this.result = result;
    }

    public void v() {
        String JDBC_URL = "jdbc:oracle:thin:@10.100.130.186:1521:inhoused";
        String JDBC_USERNAME = "ADMIHCASH";
        String JDBC_PASSWORD = "welcome234";

        String JDBC_URL2 = "jdbc:oracle:thin:@192.168.130.231:1521:inhdmst";
        String JDBC_USERNAME2 = "jinhouset";
        String JDBC_PASSWORD2 = "temporal01";
        Connection connection = null;
        Connection connection2 = null;
//        String JDBC_SELECT = "select * from admihsec.un_user";
        try {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(JDBC_URL);
            ods.setUser(JDBC_USERNAME);
            ods.setPassword(JDBC_PASSWORD);
            //            Connection conn = em.unwrap(Session.class).connection();
            connection = ods.getConnection();

            OracleDataSource ods2 = new OracleDataSource();
            ods2.setURL(JDBC_URL2);
            ods2.setUser(JDBC_USERNAME2);
            ods2.setPassword(JDBC_PASSWORD2);
            connection2 = ods2.getConnection();

            DatabaseMetaData meta = connection.getMetaData();

            SequenceGenerator sg = new SequenceGenerator(properties);
            ColumnGenerator cg = new ColumnGenerator(properties);

//            HashMap<String, SequenceContainer> seq1 = sg.getSequencesLastValueHash("ADMIHSEC", null, connection);
//            ParameterData p = new ParameterData();
//            p.setValue(true);
//            seq1.get("UN_RET_HIERARCHYGROUP_SEQ").getParameters().put(Constants.F_SET_EQUALS_SEQUENCES, p);
//            seq1.get("UN_RET_HIERARCHYGROUP_SEQ").getParameters().put(Constants.F_SEARCH_MAX_DESTINATION_VALUE_TABLE, p);
//            seq1.get("UN_RET_HIERARCHYGROUP_SEQ").getParameters().put(Constants.F_CREATE_SEQUENCE, p);
//            seq1.get("UN_RET_HIERARCHYGROUP_SEQ").getParameters().put(Constants.F_CREATE_ALTER_COLUMN_REMARKS, p);
//            seq1.get("UN_RET_HIERARCHYGROUP_SEQ").getSequence().setSchema("ADMIHSEC");
//            seq1.get("UN_RET_HIERARCHYGROUP_SEQ").getSequence().setTableSequence("UN_HIERARCHY_GROUP");
//            seq1.get("UN_RET_HIERARCHYGROUP_SEQ").getSequence().setColumnTableSequence("HIERARCHY_GROUP_ID");
//
//            HashMap<String, SequenceContainer> seq2 = sg.getSequencesLastValueHash("ADMIHSEC", null, connection2);
//            HashMap<String, StringBuilder> script = sg.generateSequenceScript(seq1, seq2, new HashMap<String, ParameterData>(), connection2);
            //probar columnas
            ParameterData pCols = new ParameterData();
            pCols.setValue(true);
            HashMap<String, ParameterData> global = new HashMap<>();
            global.put(Constants.F_ALTER_ALL_COLUMN, pCols);
            global.put(Constants.F_CREATE_ALL_COLUMN, pCols);
//            global.put(Constants.F_DROP_ALL_COLUMN_NOT_FOUND_ON_ORIG, pCols);

            HashMap<String, ColumnContainer> col = cg.getTableColumnsHash("ADMIHSEC", "UN_HIERARCHY_GROUP", null, connection);
            HashMap<String, ColumnContainer> col2 = cg.getTableColumnsHash("ADMIHSEC", "UN_HIERARCHY_GROUP", null, connection2);
            GenericData d = new GenericData();
            HashMap<String, StringBuilder> scriptCol = cg.generateColumnScript(col, col2, global, connection2, d);

            //probar tablas
            global.put(Constants.F_CREATE_ALL_TABLE, pCols);
            global.put(Constants.F_CREATE_ALL_COLUMN, pCols);

            global.put(Constants.F_CREATE_ALL_PK, pCols);
            global.put(Constants.F_CREATE_ALL_FK, pCols);
            global.put(Constants.F_DROP_ALL_FK_NOT_FOUND_ON_ORIG, pCols);
            global.put(Constants.F_DROP_ALL_PK_NOT_FOUND_ON_ORIG, pCols);
            global.put(Constants.F_CREATE_ALL_ALTER_COLUMN_REMARKS, pCols);
            global.put(Constants.F_GET_DIFERENT_COLUMNS, pCols);
            global.put(Constants.F_GET_DIFERENT_TABLES, pCols);
            global.put(Constants.F_GET_DIFERENT_PK, pCols);
            global.put(Constants.F_GET_DIFERENT_FK, pCols);

            TableGenerator tg = new TableGenerator(properties);
            HashMap<String, TableContainer> tables1 = tg.getTableHash("ADMIHCASH", null, connection, new ConnectionData());
            HashMap<String, TableContainer> tables2 = tg.getTableHash("ADMIHCASH", null, connection2, new ConnectionData());
            d = new GenericData();
            HashMap<String, StringBuilder> scriptTables = tg.generateTableScript(tables1, tables2, global, connection, connection2, d);

            result = d;
            //probar las llaves foraneas y primarias
//            ConstraintGenerator constraintG = new ConstraintGenerator(properties);
//
//            HashMap<String, ColumnContainer> cols = cg.getTableColumnsHash("ADMIHSEC", "UN_HIERARCHY_GROUP", null, connection);
//            HashMap<String, ConstraintContainer> pks = constraintG.getPkTableHash("ADMIHSEC", "NUEVATABLA", connection);
//            HashMap<String, ConstraintContainer> pks2 = constraintG.getPkTableHash("ADMIHSEC", "NUEVATABLA", connection2);
//            d = new GenericData();
//            HashMap<String, StringBuilder> scriptPk = constraintG.generatePkConstraintScript(pks, pks2, global, connection2, d);
//
//            HashMap<String, ConstraintContainer> fks = constraintG.getFkTableHash("ADMIHSEC", "NUEVATABLA", connection);
//            HashMap<String, ConstraintContainer> fks2 = constraintG.getFkTableHash("ADMIHSEC", "NUEVATABLA", connection2);
//            d = new GenericData();
//            HashMap<String, StringBuilder> scriptFk = constraintG.generateFkConstraintScript(fks, fks2, global, connection2, d);

            connection.close();
            connection2.close();
        } catch (Exception e) {
            try {
                e.getStackTrace();
                connection.close();
                connection2.close();
            } catch (Exception e2) {
            }
            System.err.println(e.getStackTrace());
        }
    }

    public HashMap<String, StringBuilder> getTableScript(List<String> globalParams, List<String> generateObjectsFilter, ConnectionData source, ConnectionData target, String schema, GenericData dataTree) {
        HashMap<String, ParameterData> params = getParameters(globalParams);
        HashMap<String, ParameterData> filters = getParameters(generateObjectsFilter);
        HashMap<String, StringBuilder> scriptTables = null;
        //los agrega como parametros globales para procesarlos
        params.putAll(filters);
        Connection conOrig = getConection(source);
        Connection conDest = getConection(target);
        if (conDest != null && conOrig != null) {
            HashMap<String, TableContainer> tables1 = tg.getTableHash(schema, null, conOrig, source);
            HashMap<String, TableContainer> tables2 = tg.getTableHash(schema, null, conDest, target);
            scriptTables = tg.generateTableScript(tables1, tables2, params, conOrig, conDest, dataTree);
        }
        closeConnection(conOrig);
        closeConnection(conDest);
        return scriptTables;

    }

    public HashMap<String, TableContainer> getTableDestList(List<String> globalParams, List<String> generateObjectsFilter, ConnectionData target, String schema, GenericData dataTree) {
        HashMap<String, ParameterData> params = getParameters(globalParams);
        HashMap<String, ParameterData> filters = getParameters(generateObjectsFilter);
        HashMap<String, TableContainer> tables = null;
        //los agrega como parametros globales para procesarlos
        params.putAll(filters);
        Connection conDest = getConection(target);
        if (conDest != null) {
            tables = tg.getTableHash(schema, null, conDest, target);
        }
        closeConnection(conDest);
        return tables;
    }

    public HashMap<String, ColumnContainer> getColumnDestList(List<String> globalParams, List<String> generateObjectsFilter, ConnectionData target, String schema, String table, GenericData dataTree) {
        HashMap<String, ParameterData> params = getParameters(globalParams);
        HashMap<String, ParameterData> filters = getParameters(generateObjectsFilter);
        HashMap<String, ColumnContainer> columns = null;
        //los agrega como parametros globales para procesarlos
        params.putAll(filters);
        Connection conDest = getConection(target);
        if (conDest != null) {
            columns = cg.getTableColumnsHash(schema, table, null, conDest);
        }
        closeConnection(conDest);
        return columns;
    }

    public HashMap<String, StringBuilder> getSequenceScriptMaxTableVal(List<String> globalParams, List<String> generateObjectsFilter, GenericData sequenceData, String columnTableSequence, String tableName, GenericData dataTree) {
        HashMap<String, ParameterData> params = getParameters(globalParams);
        HashMap<String, ParameterData> filters = getParameters(generateObjectsFilter);
        HashMap<String, StringBuilder> scriptTables = null;
        //los agrega como parametros globales para procesarlos
        params.putAll(filters);
        SequenceContainer sqo = (SequenceContainer) sequenceData.getSource();
        SequenceContainer sqd = (SequenceContainer) sequenceData.getTarget();
        GenericData schema = searchParentObjectDataType(sequenceData, Constants.OBJECT_TYPE_SCHEMA);
        Connection conDest = getConection((ConnectionData) schema.getTarget());
        HashMap<String, SequenceContainer> seq1 = new HashMap<>();
        HashMap<String, SequenceContainer> seq2 = new HashMap<>();
        if (conDest != null) {
            SequenceGenerator sg = new SequenceGenerator(properties);
            if (sqo != null) {
                ParameterData p = new ParameterData();
                p.setValue(true);
                sqo.getParameters().put(Constants.F_SEARCH_MAX_DESTINATION_VALUE_TABLE, p);
                sqo.getSequence().setColumnTableSequence(tableName);
                sqo.getSequence().setTableSequence(columnTableSequence);
                seq1.put(sqo.getSequence().getSequenceName(), sqo);
            }
            if (sqd != null) {
                ParameterData p = new ParameterData();
                p.setValue(true);
                sqd.getParameters().put(Constants.F_SEARCH_MAX_DESTINATION_VALUE_TABLE, p);
                sqd.getSequence().setColumnTableSequence(tableName);
                sqd.getSequence().setTableSequence(columnTableSequence);
                seq2.put(sqd.getSequence().getSequenceName(), sqd);
            }
            scriptTables = sg.generateSequenceScript(seq1, seq2, params, conDest, dataTree);
        }
        closeConnection(conDest);
        return scriptTables;

    }

    public HashMap<String, StringBuilder> getSequenceScript(List<String> globalParams, List<String> generateObjectsFilter, ConnectionData source, ConnectionData target, String schema, GenericData dataTree) {
        HashMap<String, ParameterData> params = getParameters(globalParams);
        HashMap<String, ParameterData> filters = getParameters(generateObjectsFilter);
        HashMap<String, StringBuilder> scriptTables = null;
        //los agrega como parametros globales para procesarlos
        params.putAll(filters);
        Connection conOrig = getConection(source);
        Connection conDest = getConection(target);
        if (conDest != null && conOrig != null) {
            SequenceGenerator sg = new SequenceGenerator(properties);
            HashMap<String, SequenceContainer> seq1 = sg.getSequencesLastValueHash(schema, null, conOrig, source);
            HashMap<String, SequenceContainer> seq2 = sg.getSequencesLastValueHash(schema, null, conDest, target);
            scriptTables = sg.generateSequenceScript(seq1, seq2, params, conDest, dataTree);
        }
        closeConnection(conOrig);
        closeConnection(conDest);
        return scriptTables;

    }

    public Long getLastSequenceValue(String schema, String tableName, String columnName, ConnectionData target) {
        Long ret = null;
        Connection conDest = getConection(target);
        if (conDest != null) {
            SequenceGenerator sg = new SequenceGenerator(properties);
            ret = sg.getLastSequenceValue(schema, tableName, columnName, conDest);
        }
        closeConnection(conDest);
        return ret;
    }

    public HashMap<String, ParameterData> getParameters(List<String> params) {
        HashMap<String, ParameterData> ret = new HashMap<>();
        ParameterData newParam;
        for (String actual : params) {
            newParam = new ParameterData();
            newParam.setValue(Boolean.TRUE);
            ret.put(actual, newParam);
        }
        return ret;
    }

    public ConnectionData setConection(String jdbcUrl, String jdbcUsername, String jdbcPassword) {
        ConnectionData ret = new ConnectionData();
        ret.setConectionUrl(jdbcUrl);
        ret.setConectionUsr(jdbcUsername);
        ret.setConectionPass(jdbcPassword);
        return ret;
    }

    public Connection getConection(ConnectionData conn) {
        try {
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(conn.getConectionUrl());
            ods.setUser(conn.getConectionUsr());
            ods.setPassword(conn.getConectionPass());
            return ods.getConnection();
        } catch (Exception e) {
            return null;
        }
    }

    public void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
            }
        }
    }

    //busca el nodo esquema con los datos de coneccion que se utilizaron para generar los nodos
    public GenericData searchParentObjectDataType(GenericData node, String objectType) {
        GenericData ret = null;
        if (node.getObjectType() != null && node.getObjectType().equals(objectType)) {
            ret = node;
        } else if (node.getParent() != null) {
            ret = searchParentObjectDataType(node.getParent(), objectType);
        }
        return ret;
    }

    public static void main(String args[]) {
        Generador g = new Generador();
        g.v();
    }
}
