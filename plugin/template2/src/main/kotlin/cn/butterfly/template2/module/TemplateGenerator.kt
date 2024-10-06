package cn.butterfly.template2.module

import cn.butterfly.template2.ui.PluginIcons
import cn.butterfly.template2.ui.TemplateUtils
import com.intellij.ide.util.projectWizard.WebProjectTemplate
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile


/**
 * 模板生成处理类
 *
 * @author zjw
 * @date 2024-09-21
 */
class TemplateGenerator : WebProjectTemplate<TemplateSettings>() {
    
    override fun getDescription() = "TEMPLATE"

    override fun getName() = "TEMPLATE"

    override fun getLogo() = PluginIcons.TEMPLATE_ICON

    override fun createPeer() = TemplateGeneratorPeer()

    override fun generateProject(project: Project, baseDir: VirtualFile, settings: TemplateSettings, module: Module) =
        TemplateUtils.generateFile(project, baseDir, settings)

}