package cn.butterfly.codegenerator.datasource

import com.intellij.openapi.observable.properties.AtomicProperty

/**
 * 数据源信息
 *
 * @author zjw
 * @date 2025-10-04
 */
data class DataSource(
    var dbType: String = "MySQL",
    var jdbcUrl: AtomicProperty<String> = AtomicProperty("jdbc:mysql://ip:3306/dbName"),
    var tableName: String = "",
    var username: String = "",
    var password: String = "",
)