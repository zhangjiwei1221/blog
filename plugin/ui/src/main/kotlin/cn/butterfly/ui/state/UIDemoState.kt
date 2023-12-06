package cn.butterfly.ui.state

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 数据持久化存储
 *
 * @author zjw
 * @date 2023-12-06
 */
@Service(Service.Level.PROJECT)
@State(name = "UIDemoState", storages = [Storage("ui-demo-state.xml")])
class UIDemoState: PersistentStateComponent<UIDemoState> {
    
    var username = ""
    
    var password = ""
    
    override fun getState(): UIDemoState {
        return this
    }

    override fun loadState(state: UIDemoState) {
        XmlSerializerUtil.copyBean(state, this)
    }

}