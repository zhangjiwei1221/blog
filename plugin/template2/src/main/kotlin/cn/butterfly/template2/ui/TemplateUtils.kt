package cn.butterfly.template2.ui

import cn.butterfly.template2.module.TemplateSettings
import cn.butterfly.template2.template.TemplateFileFactory
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.io.FileUtil
import com.intellij.ui.dsl.builder.AlignX
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.panel
import java.io.File
import java.util.*

/**
 * 模板工具类
 *
 * @author zjw
 * @date 2024-10-06
 */
object TemplateUtils {
    
    const val PLUGIN_NAME = "TEMPLATE"
    
    fun initUI(settings: TemplateSettings) = panel {
        row("Name: ") {
            textField().align(AlignX.FILL).onChanged {
                settings.name = it.text
            }
        }.topGap(TopGap.MEDIUM)

        row("Description: ") {
            textField().align(AlignX.FILL).onChanged {
                settings.description = it.text
            }
        }.topGap(TopGap.MEDIUM)
    }
    
    fun generateFile(project: Project, basePath: String, settings: TemplateSettings) {
        val template = FileTemplateManager.getInstance(project).getJ2eeTemplate(TemplateFileFactory.PLUGIN_XML)
        val properties = Properties()
        properties.setProperty("name", settings.name)
        properties.setProperty("description", settings.description)
        val renderedText = template.getText(properties)
        FileUtil.writeToFile(File("${basePath}${File.separator}demo.txt"), renderedText)
    }
    
}