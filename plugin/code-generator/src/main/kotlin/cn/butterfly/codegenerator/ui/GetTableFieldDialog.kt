package cn.butterfly.codegenerator.ui

import cn.butterfly.codegenerator.datasource.DataSource
import cn.butterfly.codegenerator.datasource.DbFactory
import cn.butterfly.codegenerator.datasource.Field
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

/**
 * 获取表字段对话框
 *
 * @author zjw
 * @date 2025-10-03
 */
class GetTableFieldDialog(project: Project, private var fields: ArrayList<Field>) : DialogWrapper(project) {

    private val model = DataSource()

    init {
        // 设置标题并初始化
        title = "获取表字段"
        init()
    }

    // 处理 OK 按钮事件
    override fun doOKAction() {
        super.doOKAction()
        DbFactory.create(model.dbType).getTableField(model).forEach {
            it.name = underlineToCamel(it.name.lowercase())
            fields.add(it)
        }
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            row("数据源类型：") {
                comboBox(listOf("MySQL", "Oracle", "Postgres", "Dm")).align(Align.FILL)
                    .onChanged {
                        model.dbType = it.selectedItem as String
                        when (model.dbType) {
                            "MySQL" -> model.jdbcUrl.set("jdbc:mysql://ip:3306/dbName")
                            "Oracle" -> model.jdbcUrl.set("jdbc:oracle:thin:@ip:1521:dbName")
                            "Postgres" -> model.jdbcUrl.set("jdbc:postgresql://ip:5432/dbName")
                            "Dm" -> model.jdbcUrl.set("jdbc:dm://ip:5236/dbName")
                        }
                    }
            }

            row("数据源连接字符串：") {
                textField().bindText(model.jdbcUrl)
                    .align(Align.FILL)
            }

            row("表名：") {
                textField().bindText(model::tableName)
                    .align(Align.FILL)
            }

            row("用户名：") {
                textField().bindText(model::username)
                    .align(Align.FILL)
            }

            row("密码：") {
                passwordField().bindText(model::password)
                    .align(Align.FILL)
            }
        }.withMinimumWidth(500)
    }
    
    private fun underlineToCamel(underscore: String): String {
        if (underscore.isBlank()) {
            return underscore
        }
        val result = StringBuilder()
        var toUpper = false
        for (i in underscore.indices) {
            val c = underscore[i]
            if (c == '_') {
                toUpper = true
            } else {
                if (toUpper) {
                    result.append(c.uppercaseChar())
                    toUpper = false
                } else {
                    result.append(c)
                }
            }
        }
        return result.toString()
    }

}