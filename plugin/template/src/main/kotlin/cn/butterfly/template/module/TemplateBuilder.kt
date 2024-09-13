package cn.butterfly.template.module

import com.intellij.ide.projectWizard.ProjectSettingsStep
import com.intellij.ide.util.projectWizard.ModuleBuilder
import com.intellij.ide.util.projectWizard.WizardContext
import com.intellij.openapi.Disposable
import com.intellij.openapi.roots.ModifiableRootModel
import com.intellij.openapi.roots.ui.configuration.ModulesProvider

/**
 * 模板构建
 *
 * @author zjw
 * @date 2024-09-06
 */
class TemplateBuilder: ModuleBuilder() {

    override fun getModuleType() = TemplateModuleType()

    override fun setupRootModel(rootModel: ModifiableRootModel) {
        rootModel.inheritSdk()
    }

    override fun createWizardSteps(wizardContext: WizardContext, modulesProvider: ModulesProvider) =
        arrayOf(TemplateSecondStep())

    override fun getCustomOptionsStep(context: WizardContext?, parentDisposable: Disposable?) = TemplateFirstStep()

    override fun getIgnoredSteps(): MutableList<Class<ProjectSettingsStep>> = mutableListOf(ProjectSettingsStep::class.java)

}