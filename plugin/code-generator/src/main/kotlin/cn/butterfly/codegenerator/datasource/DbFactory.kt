package cn.butterfly.codegenerator.datasource

/**
 * 数据库工程类
 *
 * @author zjw
 * @date 2025-10-05
 */
object DbFactory {

    private var mysql = MySQL()

    private var oracle = Oracle()

    private var postgres = Postgres()

    private var dm = Dm()

    /**
     * 根据数据库类型获取相应实体
     *
     * @param dbType 数据库类型
     * @return 数据库
     */
    fun create(dbType: String) = when (dbType) {
        "Oracle" -> oracle
        "Postgres" -> postgres
        "Dm" -> dm
        else -> mysql
    }

}