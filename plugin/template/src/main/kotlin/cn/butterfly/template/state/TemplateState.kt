package cn.butterfly.template.state

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 模板持久化数据
 *
 * @author zjw
 * @date 2024-08-30
 */
@Service
@State(name = "TemplateState", storages = [Storage("template-state.xml")])
class TemplateState(): PersistentStateComponent<TemplateState> {
    
    var name = ""
    
    var group = ""
    
    var artifact = ""
    
    var location = ""
    
    var email = ""
    
    var author = ""
    
    var blogUrl = ""
    
    override fun getState() = this

    override fun loadState(state: TemplateState) {
        XmlSerializerUtil.copyBean(state, this)
    }
    
    companion object {
        fun getInstance(): TemplateState = ApplicationManager.getApplication().getService(TemplateState::class.java)
    }

}