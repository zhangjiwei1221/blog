package cn.butterfly.codegenerator.datasource

/**
 * Postgres
 *
 * @author zjw
 * @date 2025-10-05
 */
class Postgres: Database {
    
    init {
        Class.forName("org.postgresql.Driver")
    }
    
    override fun getTableField(dataSource: DataSource): List<Field> {
        val sql = "SELECT col.column_name AS name,  " +
                "    CASE  " +
                "        WHEN udt_name = 'varchar' THEN concat('varchar(', COALESCE(character_maximum_length, 255), ')') " +
                "        WHEN data_type = 'integer' THEN concat('numeric(', numeric_precision, ',', numeric_scale, ')') " +
                "        ELSE col.udt_name " +
                "    END AS type,  " +
                "    des.description AS \"comment\" " +
                "FROM " +
                "    information_schema.columns AS col " +
                "LEFT JOIN  " +
                "    pg_description AS des ON col.ordinal_position = des.objsubid AND des.objoid =" +
                " (SELECT oid FROM pg_class WHERE relname = '${dataSource.tableName}') " +
                "WHERE  " +
                "    col.table_schema = 'public' " +
                "    AND col.table_name = '${dataSource.tableName}' order by ordinal_position"
        return getTableInfo(dataSource, sql)
    }
    
}