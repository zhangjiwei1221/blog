package cn.butterfly.ui.config

import cn.butterfly.ui.UIDemo
import cn.butterfly.ui.state.UIDemoState
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import javax.swing.JComponent

/**
 * UI 配置界面配置类
 *
 * @author zjw
 * @date 2023-12-06
 */
class UISettingsConfig(project: Project): Configurable {
    
    private val form = UIDemo()

    private val component: JComponent
    
    private val state = project.getService(UIDemoState::class.java)
    
    init {
        component = form.mainPanel
        reset()
    }
    
    override fun createComponent() = component
    
    override fun isModified(): Boolean {
        return state.username != form.username.text || state.password != form.password.text
                
    }

    override fun apply() {
        state.username = form.username.text
        state.password = form.password.text
    }
    
    override fun reset() {
        form.username.text = state.username
        form.password.text = state.password
    }

    override fun getDisplayName() = "UISettingsConfig"

}