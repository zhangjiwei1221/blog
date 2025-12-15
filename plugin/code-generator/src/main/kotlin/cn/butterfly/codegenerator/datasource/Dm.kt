package cn.butterfly.codegenerator.datasource

/**
 * Dm
 *
 * @author zjw
 * @date 2025-10-05
 */
class Dm: Database {
    
    init {
        Class.forName("dm.jdbc.driver.DmDriver")
    }
    
    override fun getTableField(dataSource: DataSource): List<Field> {
        val sql = "SELECT b.COLUMN_NAME AS name, concat(b.DATA_TYPE, concat('(', concat(b.DATA_LENGTH, ')'))) AS TYPE, " +
                " a.COMMENTS AS \"comment\" FROM all_tab_cols b, all_col_comments a WHERE " +
                " a.owner = '${dataSource.username.uppercase()}' " +
                " AND b.TABLE_NAME = '${dataSource.tableName.uppercase()}' AND b.TABLE_NAME = a.TABLE_NAME" +
                " AND b.COLUMN_NAME = a.COLUMN_NAME AND b.owner = a.owner ORDER BY b.COLUMN_ID"
        return getTableInfo(dataSource, sql)
    }
    
}