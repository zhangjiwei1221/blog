package cn.butterfly.codegenerator.ui

import cn.butterfly.codegenerator.datasource.Field
import cn.butterfly.codegenerator.factory.TemplateFileFactory
import cn.butterfly.codegenerator.util.Utils
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import com.intellij.ui.dsl.builder.Align
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.swing.JComponent

/**
 * 代码生成弹框
 *
 * @author zjw
 * @date 2025-09-30
 */
class CodeGeneratorDialog(private val project: Project): DialogWrapper(project) {
    
    private val model = TableInfo()
    
    private val table = TableFieldPanel()
    
    private val fields = ArrayList<Field>()
    
    init {
        // 设置标题并初始化
        title = "生成代码"
        init()
    }
    
    // 处理 OK 按钮事件
    override fun doOKAction() {
        super.doOKAction()
        val attributes = HashMap<String, Any>()
        attributes["packageName"] = model.packageName
        attributes["packages"] = model.packages.takeIf { it.isNotBlank() }?.split(",") ?: emptyList<String>()
        attributes["entityName"] = model.entityName
        attributes["author"] = model.author
        attributes["createDate"] = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        attributes["entityClassName"] = model.entityClassName
        attributes["fields"] = table.getTableFields()
        val template = FileTemplateManager.getInstance(project).getJ2eeTemplate(TemplateFileFactory.ENTITY_FILE)
        val renderedText = template.getText(attributes)
        val file = File("${model.path}${File.separator}${model.entityClassName}.java")
        FileUtil.writeToFile(file, renderedText).apply {
            LocalFileSystem.getInstance().refresh(true)
            Utils.info("生成成功")
        }
    }
    
    override fun createCenterPanel(): JComponent {
        return panel {
            row("文件生成路径: ") {
                textFieldWithBrowseButton("选择路径", project, FileChooserDescriptorFactory.createSingleFolderDescriptor())
                    .align(Align.FILL)
                    .bindText(model::path)
            }
            
            row("包名: ") {
                textField().bindText(model::packageName)
                    .align(Align.FILL)
            }
            
            row("额外导入包: ") {
                expandableTextField().bindText(model::packages)
                    .align(Align.FILL)
                    .comment("Tips: 多个包之间用英文逗号隔开")
            }
            
            row("实体名称: ") {
                textField().bindText(model::entityName)
                    .align(Align.FILL)
            }
            
            row("实体类名: ") {
                textField().bindText(model::entityClassName)
                    .align(Align.FILL)
            }
            
            row("作者: ") {
                textField().bindText(model::author)
                    .align(Align.FILL)
            }
            
            row {
                button("获取表字段") {
                    fields.clear()
                    GetTableFieldDialog(project, fields).show().apply { 
                        table.setTableFields(fields)
                    }
                }
            }
            
            row {
                cell(table.create()).align(Align.FILL)
            }
        }.withMinimumWidth(500)
    }
    
    data class TableInfo(
        var path: String = "",
        var packageName: String = "",
        var packages: String = "",
        var entityName: String = "",
        var author: String = "",
        var createDate: String = "",
        var entityClassName: String = "",
        var fields: ArrayList<Field> = arrayListOf(),
    )

}