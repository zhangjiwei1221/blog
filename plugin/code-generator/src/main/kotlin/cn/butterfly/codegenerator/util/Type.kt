package cn.butterfly.codegenerator.util

/**
 * SQL 类型
 * 
 * @author zjw
 * @date 2025-10-04
 */
enum class Type {
    BIGINT,
    BINARY,
    BIT,
    BLOB,
    BOOLEAN,
    CHAR,
    CLOB,
    DATE,
    DECIMAL,
    DOUBLE,
    FLOAT,
    INTEGER,
    JAVA_OBJECT,
    LONGNVARCHAR,
    LONGVARBINARY,
    LONGVARCHAR,
    NCHAR,
    NCLOB,
    NUMERIC,
    NVARCHAR,
    UNKNOW,
    REAL,
    SMALLINT,
    TIME,
    TIMESTAMP,
    TINYINT,
    VARBINARY,
    VARCHAR,
    //mysql
    DATETIME,
    MEDIUMINT,
    INT,
    TINYBLOB,
    TINYTEXT,
    TEXT,
    MEDIUMBLOB,
    MEDIUMTEXT,
    LONGBLOB,
    LONGTEXT,
    YEAR,
    JSON,
    //oracle
    BINARY_FLOAT,
    DOUBLE_PRECISION,
    BINARY_DOUBLE,
    TIMESTAMP_WITH_TIME_ZONE,
    TIMESTAMP_WITH_LOCAL_TIME_ZONE,
    VARCHAR2,
    INTERVAL_YEAR_TO_MONTH,
    INTERVAL_DAY_TO_SECOND;

    companion object {
        fun getByTypeName(typeName: String) = values().find { it.name.equals(typeName, ignoreCase = true) } ?: VARCHAR
    }

}