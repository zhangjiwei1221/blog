package cn.butterfly.template.module

import cn.butterfly.template.state.TemplateState
import com.intellij.ide.util.projectWizard.ModuleWizardStep
import com.intellij.ui.dsl.builder.TopGap
import com.intellij.ui.dsl.builder.bindText
import com.intellij.ui.dsl.builder.panel

/**
 * 模板第一个步骤
 *
 * @author zjw
 * @date 2024-09-06
 */
class TemplateFirstStep: ModuleWizardStep() {
    
    private val model = Model()
    
    private val state: TemplateState = TemplateState.getInstance()
    
    private var panel = panel {
        indent {
            row("Name: ") {
                textField().bindText(model::name)
            }.topGap(TopGap.MEDIUM)
            
            row("Location: ") {
                textFieldWithBrowseButton().bindText(model::location)
            }.topGap(TopGap.MEDIUM)
            
            row("Group: ") {
                textField().bindText(model::group)
            }.topGap(TopGap.MEDIUM)
            
            row("Artifact: ") {
                textField().bindText(model::artifact)
            }.topGap(TopGap.MEDIUM)
        }
        
    }
    
    override fun getComponent() = panel

    override fun updateDataModel() {
        state.name = model.name
        state.group = model.group
        state.artifact = model.artifact
        state.location = model.location
        panel.apply()
    }
    
    data class Model(
        var name: String = "",
        var group: String = "",
        var artifact: String = "",
        var location: String = ""
    )

}