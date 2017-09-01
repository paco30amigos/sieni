/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unicomer.psdisr.generator.model.utils;

import java.io.Serializable;

/**
 *
 * @author francisco_medina
 */
public class Constants implements Serializable {

    /*
    F_: FILTER
    P_: QUERY_PARAMETER
    Q_: QUERY STRING
     */
    //FILTER USED IN PARAMETER
    //Busca en destino (esquema.tabla.campo), el maximo valor ingresado para asignarle el siguiente a la secuencia a modificar
    public static String F_SEARCH_MAX_DESTINATION_VALUE_TABLE = "f_search_max_destination_value_table";
    //Modifica la secuencia actual para que tengan sus valores iguales en destino, tomando de base el origen
    public static String F_SET_EQUALS_SEQUENCES = "f_set_equals_sequences";
    //Modifica todas las secuencias para que tengan sus valores iguales en destino, tomando de base el origen
    public static String F_SET_ALL_EQUALS_SEQUENCES = "f_set_equals_sequences";
    //Crea todas las secuencias que no existen en destino, tomando de base las de origen
    public static String F_CREATE_ALL_SEQUENCE = "f_create_all_sequence";
    //crea la la secuencia en destino, tomando de base la de origen
    public static String F_CREATE_SEQUENCE = "f_create_new_sequence";
    //crea todas las columnas en que no existen en destino, comparando contra origen
    public static String F_CREATE_ALL_COLUMN = "f_create_all_column";
    //crea una columna en destino, tomando de base la origen
    public static String F_CREATE_COLUMN = "f_create_new_column";
    //crea una columna en destino, tomando de base la origen
    public static String F_CREATE_COLUMN_FOR_TABLE = "f_create_column_for_table";
    //crea una columna si el valor cambia de null hacia not null
    public static String F_ALTER_COLUMN_NULL_TO_NOTNULL = "f_alter_column_null_to_notnull";
    //crea una columna si el valor cambia de notnull hacia null
    public static String F_ALTER_COLUMN_NOTNULL_TO_NULL = "f_alter_column_notnull_to_null";
    //iguala columna de destino, tomando de base la origen
    public static String F_ALTER_COLUMN = "f_alter_column";
    //aplica el alter table a todas las columnas
    public static String F_ALTER_ALL_COLUMN = "f_alter_all_column";
    //elimina una columna existente de destino, que tambien existe en origen
    public static String F_DROP_COLUMN = "f_drop_column";
    //elimina todas las columnas que no existen en origen que están en destino
    public static String F_DROP_ALL_COLUMN_NOT_FOUND_ON_ORIG = "f_drop_all_column_not_found_on_orig";
    //crea una columna en destino, tomando de base la origen
    public static String F_CREATE_ALTER_COLUMN_REMARKS = "f_create_new_column_remarks";
    //crea todos los comentarios en destino, tomando de base la origen
    public static String F_CREATE_ALL_ALTER_COLUMN_REMARKS = "f_create_all_new_column_remarks";
    //crea una columna en destino, tomando de base la origen
    public static String F_DROP_COLUMN_REMARKS = "f_create_new_column_remarks";
    //crea todas las tablas en que no existen en destino, comparando contra origen
    public static String F_CREATE_ALL_TABLE = "f_create_all_table";
    //crea una tabla en destino, tomando de base la origen
    public static String F_CREATE_TABLE = "f_create_new_table";
    //elimina una tabla existente de destino, que tambien existe en origen
    public static String F_DROP_TABLE = "f_drop_table";
    //elimina todas las tablas que no existen en origen que están en destino
    public static String F_DROP_ALL_TABLE_NOT_FOUND_ON_DEST = "f_drop_all_table_not_found_on_dest";
    //crea la llave primaria de la tabla actual en destino
    public static String F_CREATE_PK = "f_create_pk";
    //elimina la llave primaria de la tabla actual en destino
    public static String F_DROP_PK = "f_drop_pk";
    //iguala llave primaria destino, tomando de base la origen
    public static String F_ALTER_PK = "f_constraint_alter_pk";
    //aplica el alter table a todas las llaves primarias
    public static String F_ALTER_ALL_PK = "f_alter_all_pk";
    //elimina todas las llaves primarias en destino
    public static String F_DROP_ALL_PK_NOT_FOUND_ON_ORIG = "f_drop_all_pk_not_found_on_orig";
    //crea las llaves primaria para las tablas seleccionadas que no esten en destino
    public static String F_CREATE_ALL_PK = "f_create_all_pk";
    //crea la llave primaria de la tabla actual en destino
    public static String F_CREATE_FK = "f_create_fk";
    //crea las llaves primaria para las tablas seleccionadas que no esten en destino
    public static String F_CREATE_ALL_FK = "f_create_all_fk";
    //elimina la llave foranea seleccionada que no esten en destino
    public static String F_DROP_FK = "f_drop_fk";
    //iguala llave primaria destino, tomando de base la origen
    public static String F_ALTER_FK = "f_constraint_alter_fk";
    //aplica el alter table a todas las llaves primarias
    public static String F_ALTER_ALL_FK = "f_alter_all_fk";
    //elimina todas las llaves foraneas que no esten en destino
    public static String F_DROP_ALL_FK_NOT_FOUND_ON_ORIG = "f_drop_all_fk_not_found_on_orig";
    //crea la constraint de la tabla actual en destino
    public static String F_CREATE_CONSTRAINT = "f_create_constraint";
    //crea las llaves primaria para las tablas seleccionadas que no esten en destino
    public static String F_CREATE_ALL_CONSTRAINT = "f_create_all_constraint";
    //generar objetos diferentes para tablas
    public static String F_GET_DIFERENT_TABLES = "f_get_diferent_tables";
    //generar objetos diferentes, para secuencias
    public static String F_GET_DIFERENT_SEQUENCES = "f_get_diferent_sequences";
    //generar objetos diferentes, para columnas
    public static String F_GET_DIFERENT_COLUMNS = "f_get_diferent_columns";
    //generar objetos diferentes, para pk
    public static String F_GET_DIFERENT_PK = "f_get_diferent_pk";
    //generar objetos diferentes, para fk
    public static String F_GET_DIFERENT_FK = "f_get_diferent_fk";

    //GENERIC CONSTANTS
    public static String PROPERTIES_RESOURCE_PATH = "configurations.properties";
    public static String QUERY_LINE_END = ";\n";
    public static String QUERY_TABLE_COLUMN_SEPARATOR = ",\n";
    public static String QUERY_TABLE_END_CONFIGURATION = "\n";
    public static String GROUP_DATA = "group_data";
    public static String GROUP_COMMENTS = "group_comments";
    public static String DATA_TYPE_SIZE_PREFIX = "data_type_size_";
    public static String ACTION_CREATE = "create";
    public static String ACTION_UPDATE = "update";
    public static String ACTION_DROP = "drop";
    public static String ACTION_UPDATE_COMMENTS = "update comments";
    public static String ACTION_UPDATE_SEQUENCE_USING_TABLE_COLUMN = "update sequence using table column";
    public static String OBJECT_TYPE_TARGET = "object_type_target";
    public static String OBJECT_TYPE_SCHEMA = "object_type_schema";
    public static String OBJECT_TYPE_TABLE = "object_type_table";
    public static String OBJECT_TYPE_COLUMN = "object_type_column";
    public static String OBJECT_TYPE_SEQUENCE = "object_type_sequence";
    public static String OBJECT_TYPE_PK = "object_type_pk";
    public static String OBJECT_TYPE_FK = "object_type_fk";

    //SEQUENCE CONSTANTS
    public static String Q_SEQUENCE_CREATE_QUERY = "q_sequence_create_query";
    public static String Q_SEQUENCE_ALTER_QUERY = "q_sequence_alter_query";
    public static String Q_SEQUENCE_QUERY_INCREMENT = "q_sequence_query_increment";
    public static String Q_SEQUENCE_LAST_TABLE_VALUE = "q_sequence_last_table_value";

    public static String P_SEQ_ID_COLUMN = "p_seq_id_column";
    public static String P_TABLE_SCHEMA = "p_table_schema";
    public static String P_TABLE_NAME = "p_table_name";
    public static String P_TABLE_COLUMNS = "p_table_columns";
    public static String P_SEQ_VAL = "seq_val";
    public static String Q_SEQUENCE_QUERY = "q_sequence_query";
    public static String P_SPLIT_CHARACTER = "&";

    //SEQUENCE CONSTANTS USED IN QUERIES
    public static String P_SEQUENCE_OWNER = "p_sequence_owner";
    public static String P_SEQUENCE_NAME = "p_sequence_name";
    public static String SEQUENCE_NAME = "sequence_name";
    public static String LAST_NUMBER = "last_number";
    public static String INCREMENT_BY = "increment_by";

    //COLUMN CONSTANTS
    public static String Q_COLUMN_CREATE_QUERY = "q_column_create_query";
    public static String Q_COLUMN_ALTER_QUERY = "q_column_alter_query";
    public static String Q_COLUMN_DROP_QUERY = "q_column_drop_query";
    public static String Q_COLUMN_CREATE_FOR_TABLE_QUERY = "q_column_create_for_table_query";
    public static String Q_COLUMN_CREATE_REMARKS = "q_column_create_remarks";
    public static String Q_COLUMN_DROP_REMARKS = "q_column_drop_remarks";
    public static String Q_COLUMN_COMMENT = "q_column_comment";
    public static String P_COLUMN_NAME = "p_column_name";
    public static String P_COLUMN_DATA_TYPE = "p_column_data_type";
    public static String P_COLUMN_LENGHT = "p_column_lenght";
    public static String P_COLUMN_NULLABLE = "p_column_nullable";
    public static String P_COLUMN_NULLABLE_VALUE = "p_column_nullable_value";
    public static String P_COLUMN_NOT_NULLABLE_VALUE = "p_column_not_nullable_value";
    public static String P_COLUMN_REMARKS = "p_column_remarks";
    public static String P_COMMENTS_VAL = "comments_val";
    public static String P_COL_NAME_VAL = "col_name_val";

    //TABLE CONSTANTS
    public static String Q_TABLE_CREATE_QUERY = "q_table_create_query";
    public static String Q_TABLE_DROP_QUERY = "q_table_drop_query";

    //CONSTRAINTS CONSTANTS
    public static String Q_CONSTRAINT_CREATE_PK_QUERY = "q_constraint_create_pk_query";
    public static String Q_CONSTRAINT_CREATE_FK_QUERY = "q_constraint_create_fk_query";
    public static String Q_CONSTRAINT_DROP_QUERY = "q_constraint_drop_query";
    public static String Q_GRANT_FK_OTHER_SCHEMA = "q_grant_fk_other_schema";
    public static String P_CONSTRAINT_NAME = "p_constraint_name";
    public static String P_CONSTRAINT_COLUMNS = "p_constraint_columns";
    public static String P_PARENT_TABLE_NAME = "p_parent_table_name";
    public static String P_PARENT_TABLE_SCHEMA = "p_parent_table_schema";

    //save generic data value names
    public static String P_GENERIC_DATA_TABLE = "p_generic_data_table";
    public static String P_GENERIC_DATA_COLUMN = "p_generic_data_column";
    public static String P_GENERIC_DATA_COLUMN_VALUE = "p_generic_data_column_value";
}
