//package cn.butterfly.template2.module
//
//import cn.butterfly.template2.ui.PluginIcons
//import cn.butterfly.template2.ui.TemplateUtils
//import com.intellij.openapi.module.Module
//import com.intellij.openapi.project.Project
//import com.intellij.openapi.vfs.VirtualFile
//import com.jetbrains.python.newProject.PyNewProjectSettings
//import com.jetbrains.python.newProject.PythonProjectGenerator
//import com.jetbrains.python.remote.PyProjectSynchronizer
//import java.io.File
//import javax.swing.JComponent
//
///**
// * PyCharm 模板生成处理类
// *
// * @author zjw
// * @date 2024-09-21
// */
//class PyTemplateGenerator : PythonProjectGenerator<PyNewProjectSettings>() {
//
//    private val settings = TemplateSettings()
//    
//    private var basePath = ""
//
//    override fun getDescription() = TemplateUtils.PLUGIN_NAME
//
//    override fun getName() = TemplateUtils.PLUGIN_NAME
//
//    override fun getLogo() = PluginIcons.TEMPLATE_ICON
//
//    override fun getSettingsPanel(baseDir: File?): JComponent {
//        basePath = baseDir?.path ?: ""
//        return TemplateUtils.initUI(settings)
//    }
//    
//    override fun configureProject(
//        project: Project,
//        baseDir: VirtualFile,
//        settings: PyNewProjectSettings,
//        module: Module,
//        synchronizer: PyProjectSynchronizer?
//    ) {
//        super.configureProject(project, baseDir, settings, module, synchronizer)
//        basePath = baseDir.path
//    }
//
//    override fun afterProjectGenerated(project: Project) =
//        TemplateUtils.generateFile(project, basePath, settings)
//
//}