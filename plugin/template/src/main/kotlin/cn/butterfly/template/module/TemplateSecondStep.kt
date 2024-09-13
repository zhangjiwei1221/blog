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
        state.email = model.email
        state.author = model.author
        state.blogUrl = model.blogUrl
        panel.apply()
    }
    
    data class Model(
        var email: String = "1945912314@qq.com",
        var author: String = "butterfly",
        var blogUrl: String = "https://juejin.cn/user/3350967174567352"
    )

}