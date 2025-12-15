package cn.butterfly.codegenerator.util

import java.math.BigDecimal

/**
 * SQL 类型处理工具类
 *
 * @author zjw
 * @date 2025-10-04
 */
abstract class TypeUtils {

    companion object {
        private val TYPE_TO_JAVA_MAP = mutableMapOf<Type, Class<*>>()

        init {
            TYPE_TO_JAVA_MAP[Type.NUMERIC] = Integer::class.java
            TYPE_TO_JAVA_MAP[Type.BIGINT] = Long::class.java
            TYPE_TO_JAVA_MAP[Type.BOOLEAN] = Boolean::class.java
            TYPE_TO_JAVA_MAP[Type.TINYINT] = Byte::class.java
            TYPE_TO_JAVA_MAP[Type.SMALLINT] = Short::class.java
            TYPE_TO_JAVA_MAP[Type.VARCHAR] = String::class.java
            TYPE_TO_JAVA_MAP[Type.BINARY] = java.sql.Blob::class.java
            TYPE_TO_JAVA_MAP[Type.BIT] = Boolean::class.java
            TYPE_TO_JAVA_MAP[Type.BLOB] = java.sql.Blob::class.java
            TYPE_TO_JAVA_MAP[Type.CHAR] = String::class.java
            TYPE_TO_JAVA_MAP[Type.CLOB] = java.sql.Clob::class.java
            TYPE_TO_JAVA_MAP[Type.DECIMAL] = BigDecimal::class.java
            TYPE_TO_JAVA_MAP[Type.DOUBLE] = Double::class.java
            TYPE_TO_JAVA_MAP[Type.FLOAT] = Float::class.java
            TYPE_TO_JAVA_MAP[Type.INTEGER] = Integer::class.java
            TYPE_TO_JAVA_MAP[Type.INT] = Integer::class.java
            TYPE_TO_JAVA_MAP[Type.JAVA_OBJECT] = Any::class.java
            TYPE_TO_JAVA_MAP[Type.LONGNVARCHAR] = String::class.java
            TYPE_TO_JAVA_MAP[Type.LONGVARBINARY] = String::class.java
            TYPE_TO_JAVA_MAP[Type.LONGVARCHAR] = String::class.java
            TYPE_TO_JAVA_MAP[Type.TEXT] = String::class.java
            TYPE_TO_JAVA_MAP[Type.NCHAR] = String::class.java
            TYPE_TO_JAVA_MAP[Type.NCLOB] = java.sql.Clob::class.java
            TYPE_TO_JAVA_MAP[Type.NVARCHAR] = String::class.java
            TYPE_TO_JAVA_MAP[Type.UNKNOW] = Any::class.java
            TYPE_TO_JAVA_MAP[Type.REAL] = Float::class.java
            TYPE_TO_JAVA_MAP[Type.VARBINARY] = java.sql.Blob::class.java
            TYPE_TO_JAVA_MAP[Type.DATE] = java.util.Date::class.java
            TYPE_TO_JAVA_MAP[Type.TIME] = java.sql.Time::class.java
            TYPE_TO_JAVA_MAP[Type.TIMESTAMP] = java.time.LocalDateTime::class.java
            TYPE_TO_JAVA_MAP[Type.DATETIME] = java.time.LocalDateTime::class.java
        }
        
        fun colDef2DialectType(column: String): String {
            val endIndex = if (column.contains("(")) column.indexOf("(") else column.length
            val columnDef = column.substring(0, endIndex)
            var simpleName = TYPE_TO_JAVA_MAP[Type.getByTypeName(columnDef)]?.simpleName
            simpleName?.let {
                simpleName = it.substring(0, 1).uppercase() + it.substring(1)
            }
            return simpleName ?: ""
        }
    }

}