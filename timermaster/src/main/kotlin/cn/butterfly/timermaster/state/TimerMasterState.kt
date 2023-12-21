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
    
    // 运行项目路径
    var runProjectPath = ""
    
    // 更新间隔
    var updateInterval: Int = 10
    
    // 当日统计数据
    var statisticsData: String = "{}"
    
    // 历史数据
    var historyData: String = "[]"
    
    // 活跃时间
    var activeTime: Long = Long.MAX_VALUE
    
    // 活跃间隔
    var activeInterval: Int = 30
    
    override fun getState() = this

    override fun loadState(state: TimerMasterState) {
        XmlSerializerUtil.copyBean(state, this)
    }
    
    companion object {
        fun getInstance(): TimerMasterState = ApplicationManager.getApplication().getService(TimerMasterState::class.java)
    }

}