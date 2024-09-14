package cn.butterfly.template.module

import cn.butterfly.template.state.TemplateState
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel

/**
 * 模板第二个步骤
 *
 * @author zjw
 * @date 2024-09-06
 */
class TemplateSecondStep: ModuleWizardStep() {
    
    private val model = Model()
    
    private val state: TemplateState = TemplateState.getInstance()
    
    init {
        model.email = state.email
        model.author = state.author
        model.blogUrl = state.blogUrl
    }
    
    private var panel = panel {
        indent {
            row("Email: ") {
                textField().bindText(model::email)
            }.topGap(TopGap.MEDIUM)
            
            row("Author: ") {
                textField().bindText(model::author)
            }.topGap(TopGap.MEDIUM)
            
            row("BlogUrl: ") {
                textField().bindText(model::blogUrl)
            }.topGap(TopGap.MEDIUM)
        }
    }
    
    override fun getComponent() = panel

    override fun updateDataModel() {
        panel.apply()
        state.email = model.email
        state.author = model.author
        state.blogUrl = model.blogUrl
    }
    
    data class Model(
        var email: String = "",
        var author: String = "",
        var blogUrl: String = ""
    )

}