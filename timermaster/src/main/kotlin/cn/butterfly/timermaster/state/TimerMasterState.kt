package cn.butterfly.timermaster.state

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

/**
 * 持久化配置
 * 
 * @author zjw
 * @date 2023-12-19
 */
@Service
@State(name = "TimerMaster", storages = [Storage("timer-master-state.xml")])
class TimerMasterState: PersistentStateComponent<TimerMasterState> {
    
    var runProjectPath = ""
    
    var updateInterval: Int = 10
    
    var statisticsData: String = "{}"
    
    var historyData: String = "[]"
    
    override fun getState() = this

    override fun loadState(state: TimerMasterState) {
        XmlSerializerUtil.copyBean(state, this)
    }
    
    companion object {
        fun getInstance(): TimerMasterState = ApplicationManager.getApplication().getService(TimerMasterState::class.java)
    }

}