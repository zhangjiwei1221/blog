package cn.butterfly.template.template

import cn.butterfly.sqllog.ui.PluginIcons
import com.intellij.ide.fileTemplates.FileTemplateDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory

/**
 * 模板文件配置
 *
 * @author zjw
 * @date 2024-09-14
 */
class TemplateFileFactory: FileTemplateGroupDescriptorFactory {
    
    companion object {
        const val PLUGIN_XML = "template-plugin.xml"
    }
    
    override fun getFileTemplatesDescriptor(): FileTemplateGroupDescriptor {
        val templateGroup = FileTemplateGroupDescriptor("Template", PluginIcons.TEMPLATE_ICON)
        templateGroup.addTemplate(FileTemplateDescriptor(PLUGIN_XML, PluginIcons.TEMPLATE_ICON))
        return templateGroup
    }

}