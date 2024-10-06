//package cn.butterfly.template2.module
//
//import cn.butterfly.template2.ui.PluginIcons
//import cn.butterfly.template2.ui.TemplateUtils
//import com.intellij.openapi.module.Module
//import com.intellij.openapi.project.Project
//import com.intellij.openapi.vfs.VirtualFile
//import com.jetbrains.cidr.cpp.cmake.projectWizard.generators.CLionProjectGenerator
//
///**
// * CLION 模板生成处理类
// *
// * @author zjw
// * @date 2024-09-21
// */
//class CTemplateGenerator : CLionProjectGenerator<Any>() {
//
//    private val settings = TemplateSettings()
//
//    override fun getDescription() = TemplateUtils.PLUGIN_NAME
//
//    override fun getName() = TemplateUtils.PLUGIN_NAME
//
//    override fun getLogo() = PluginIcons.TEMPLATE_ICON
//
//    override fun generateProject(project: Project, baseDir: VirtualFile, obj: Any, module: Module) =
//        TemplateUtils.generateFile(project, baseDir.path, settings)
//
//    override fun getSettingsPanel() = TemplateUtils.initUI(settings)
//
//}