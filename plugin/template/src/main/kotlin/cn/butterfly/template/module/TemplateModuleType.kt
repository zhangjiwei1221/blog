package cn.butterfly.template.module

import cn.butterfly.template.ui.PluginIcons
import com.intellij.openapi.module.ModuleType
import org.jetbrains.annotations.NonNls

/**
 * 模板模块类型
 *
 * @author zjw
 * @date 2024-09-06
 */
class TemplateModuleType(id: @NonNls String = "TEMPLATE_MODULE") : ModuleType<TemplateBuilder>(id) {

    override fun createModuleBuilder() = TemplateBuilder()

    override fun getName() = "Template"

    override fun getDescription() = "TEMPLATE_MODULE"

    override fun getNodeIcon(isOpened: Boolean) = PluginIcons.TEMPLATE_ICON

}