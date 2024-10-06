package cn.butterfly.template2.module

import cn.butterfly.template2.ui.PluginIcons
import cn.butterfly.template2.ui.TemplateUtils
import com.intellij.ide.util.projectWizard.SettingsStep
import com.intellij.ide.util.projectWizard.WebProjectTemplate
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.ProjectGeneratorPeer

/**
 * web 模板生成处理类
 *
 * @author zjw
 * @date 2024-09-21
 */
class WebTemplateGenerator : WebProjectTemplate<TemplateSettings>() {

    private val webSettings = TemplateSettings()

    override fun getDescription() = TemplateUtils.PLUGIN_NAME

    override fun getName() = TemplateUtils.PLUGIN_NAME

    override fun getLogo() = PluginIcons.TEMPLATE_ICON

    override fun createPeer(): ProjectGeneratorPeer<TemplateSettings> = object : ProjectGeneratorPeer<TemplateSettings> {
        override fun getComponent() = TemplateUtils.initUI(webSettings)

        override fun buildUI(step: SettingsStep) {
        }

        override fun getSettings() = webSettings

        override fun validate() = null

        override fun isBackgroundJobRunning() = true

    }

    override fun generateProject(project: Project, baseDir: VirtualFile, settings: TemplateSettings, module: Module) =
        TemplateUtils.generateFile(project, baseDir.path, webSettings)

}