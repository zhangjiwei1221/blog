package cn.butterfly.timermaster.config

import cn.butterfly.timermaster.state.TimerMasterState
import com.intellij.openapi.options.Configurable
import com.intellij.ui.dsl.builder.bindIntText
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

/**
 * 插件配置
 *
 * @author zjw
 * @date 2023-12-19
 */
class TimerMasterConfig: Configurable {
    
    private val model = Model()
    
    private val state = TimerMasterState.getInstance()
    
    init {
        reset()
    }
    
    override fun createComponent(): JComponent {
        return panel { 
            row("更新间隔(秒): ") {
                intTextField().bindIntText(model::updateInterval)
                comment("<icon src='AllIcons.General.Information'>&nbsp;不设置或者小于 10, 最终都为 10.")
            }
        }
    }

    override fun isModified(): Boolean {
        return state.updateInterval != model.updateInterval
    }

    override fun apply() {
        state.updateInterval = model.updateInterval.coerceAtLeast(10)
    }

    override fun reset() {
        model.updateInterval = state.updateInterval
    }

    override fun getDisplayName() = "TimerMaster"
    
    data class Model(
        var updateInterval: Int = 10
    )

}