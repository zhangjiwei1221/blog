package cn.butterfly.template.module

import cn.butterfly.template.state.TemplateState
import cn.butterfly.template.template.TemplateFileFactory
import com.intellij.ide.fileTemplates.FileTemplateManager
import com.intellij.ide.projectWizard.ProjectSettingsStep
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ide.util.projectWizard.SettingsStep
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider
import com.intellij.openapi.util.io.FileUtil
import com.intellij.openapi.vfs.LocalFileSystem
import java.io.File
import java.util.*

/**
 * 模板构建
 *
 * @author zjw
 * @date 2024-09-06
 */
class TemplateBuilder: ModuleBuilder() {
    
    private val state = TemplateState.getInstance()

    override fun getModuleType() = TemplateModuleType()

    override fun setupRootModel(rootModel: ModifiableRootModel) {
        // 创建 resources 文件夹
        val resFile = File("${state.location}${File.separator}src${File.separator}resources")
        resFile.mkdirs()
        // 设置项目路径
        val virtualFile = LocalFileSystem.getInstance().refreshAndFindFileByPath(state.location)
        rootModel.addContentEntry(virtualFile!!)
        // 读取模板并设置变量
        val template = FileTemplateManager.getInstance(rootModel.project).getJ2eeTemplate(TemplateFileFactory.PLUGIN_XML)
        val properties = Properties()
        properties.setProperty("name", state.name)
        properties.setProperty("group", state.group)
        properties.setProperty("artifact", state.artifact)
        properties.setProperty("location", state.location)
        properties.setProperty("description", state.description)
        properties.setProperty("author", state.author)
        properties.setProperty("email", state.email)
        properties.setProperty("blogUrl", state.blogUrl)
        val renderedText = template.getText(properties)
        // 写入 XML 文件
        val file = File(resFile.absolutePath + File.separator + TemplateFileFactory.PLUGIN_XML)
        FileUtil.writeToFile(file, renderedText)
        // 设置 source 文件夹
        rootModel.contentEntries.forEach {
            val src = LocalFileSystem.getInstance().refreshAndFindFileByPath("${state.location}${File.separator}src")!!
            it.addSourceFolder(src, false)
        }
    }

    override fun modifyProjectTypeStep(settingsStep: SettingsStep): ModuleWizardStep? {
        settingsStep.context.projectName = state.name
        return super.modifyProjectTypeStep(settingsStep)
    }

    override fun createProject(name: String?, path: String?) = super.createProject(state.name, state.location)

    override fun createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider) =
        arrayOf(TemplateSecondStep())

    override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?) = TemplateFirstStep()

    override fun getIgnoredSteps(): MutableList<Class<out ModuleWizardStep>> = mutableListOf(ProjectSettingsStep::class.java)

}