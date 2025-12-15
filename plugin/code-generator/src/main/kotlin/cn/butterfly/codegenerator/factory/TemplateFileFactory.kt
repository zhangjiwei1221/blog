package cn.butterfly.codegenerator.factory

import cn.butterfly.codegenerator.ui.PluginIcons
import com.intellij.ide.fileTemplates.FileTemplateDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptor
import com.intellij.ide.fileTemplates.FileTemplateGroupDescriptorFactory
import com.intellij.openapi.fileTypes.FileTypeRegistry

/**
 * 模板文件配置
 *
 * @author zjw
 * @date 2025-09-30
 */
class TemplateFileFactory : FileTemplateGroupDescriptorFactory {

    companion object {
        const val ENTITY_FILE = "entity.java"
    }

    override fun getFileTemplatesDescriptor(): FileTemplateGroupDescriptor {
        val templateGroup = FileTemplateGroupDescriptor("CodeGenerator", PluginIcons.TEMPLATE_ICON)
        val javaFileIcon = FileTypeRegistry.getInstance().getFileTypeByExtension("java").icon
        templateGroup.addTemplate(FileTemplateDescriptor(ENTITY_FILE, javaFileIcon))
        return templateGroup
    }

}