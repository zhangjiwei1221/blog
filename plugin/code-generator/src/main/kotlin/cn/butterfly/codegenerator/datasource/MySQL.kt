package cn.butterfly.codegenerator.datasource

/**
 * MySQL
 *
 * @author zjw
 * @date 2025-10-05
 */
class MySQL: Database {
    
    init {
        Class.forName("com.mysql.cj.jdbc.Driver")
    }
    
    override fun getTableField(dataSource: DataSource): List<Field> {
        val jdbcUrl = dataSource.jdbcUrl.get()
        val endIndex = if (jdbcUrl.contains("?")) jdbcUrl.indexOf("?") else jdbcUrl.length
        val dbName = jdbcUrl.substring(jdbcUrl.lastIndexOf("/") + 1, endIndex)
        val sql = "select column_name as name, column_type as type, column_comment as comment from " +
                " information_schema.columns where table_schema = '$dbName' AND table_name = '${dataSource.tableName}'" +
                " order by ORDINAL_POSITION"
        return getTableInfo(dataSource, sql)
    }

}