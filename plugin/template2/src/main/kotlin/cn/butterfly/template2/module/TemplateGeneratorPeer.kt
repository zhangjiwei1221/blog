package cn.butterfly.template2.module

import cn.butterfly.template2.ui.TemplateUtils
import com.intellij.ide.util.projectWizard.SettingsStep
import com.intellij.platform.ProjectGeneratorPeer

/**
 * TemplateGeneratorPeer
 *
 * @author zjw
 * @date 2024-10-06
 */
class TemplateGeneratorPeer : ProjectGeneratorPeer<TemplateSettings> {

    private val settings = TemplateSettings()

    override fun getComponent() = TemplateUtils.initUI(settings)

    override fun buildUI(step: SettingsStep) {
    }

    override fun getSettings() = settings

    override fun validate() = null

    override fun isBackgroundJobRunning() = true

}