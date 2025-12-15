package cn.butterfly.codegenerator.datasource

import cn.butterfly.codegenerator.util.TypeUtils
import java.sql.*
import java.util.*

/**
 * 数据库操作
 *
 * @author zjw
 * @date 2025-10-04
 */
interface Database {

    /**
     * 查询表字段信息
     *
     * @param dataSource 数据源信息
     * @param sql sql
     * @return 表字段信息
     */
    fun getTableInfo(dataSource: DataSource, sql: String): List<Field> {
        val results: MutableList<Field> = ArrayList()
        DriverManager.getConnection(dataSource.jdbcUrl.get(), dataSource.username, dataSource.password)
            .use { connection ->
                val statement: Statement = connection.createStatement()
                val resultSet: ResultSet = statement.executeQuery(sql)
                val metaData: ResultSetMetaData = resultSet.metaData
                while (resultSet.next()) {
                    val field = Field("", "", "")
                    for (i in 1..metaData.columnCount) {
                        val columnLabel = metaData.getColumnLabel(i)
                        var columnValue = resultSet.getObject(i) as String?
                        columnValue = columnValue ?: ""
                        when (columnLabel.lowercase(Locale.getDefault())) {
                            "name" -> field.name = columnValue
                            "type" -> field.type = TypeUtils.colDef2DialectType(columnValue)
                            "comment" -> field.comment = columnValue.ifBlank { field.name }
                        }
                    }
                    results.add(field)
                }
            }
        return results
    }
    
    fun getTableField(dataSource: DataSource): List<Field>
    
}