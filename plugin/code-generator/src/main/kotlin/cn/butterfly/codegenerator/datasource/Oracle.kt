package cn.butterfly.codegenerator.datasource

import java.util.*

/**
 * Oracle
 *
 * @author zjw
 * @date 2025-10-05
 */
class Oracle: Database {
    
    init {
        Class.forName("oracle.jdbc.driver.OracleDriver")
    }
    
    override fun getTableField(dataSource: DataSource): List<Field> {
        val dbName = dataSource.username
        val sql = "SELECT b.COLUMN_NAME AS name, concat(b.DATA_TYPE, concat('(', concat(b.DATA_LENGTH, ')'))) AS TYPE, " +
                " a.COMMENTS AS \"comment\" FROM all_tab_cols b, all_col_comments a WHERE " +
                " a.owner = '${dbName.uppercase(Locale.getDefault())}' " +
                " AND b.TABLE_NAME = '${dataSource.tableName.uppercase(Locale.getDefault())}' AND b.TABLE_NAME = a.TABLE_NAME" +
                " AND b.COLUMN_NAME = a.COLUMN_NAME AND b.owner = a.owner ORDER BY b.COLUMN_ID"
        return getTableInfo(dataSource, sql)
    }
    
}