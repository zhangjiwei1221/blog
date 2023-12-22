package cn.butterfly.timermaster.util

import cn.butterfly.timermaster.data.StatisticsData
import cn.butterfly.timermaster.state.TimerMasterState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import java.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * 工具类
 *
 * @author zjw
 * @date 2023-12-20
 */
class Utils {
    
    companion object {
        private val gson = Gson()
        
        private var consoleViews: MutableMap<Project, ConsoleView> = mutableMapOf()
        
        var toolWindow: ToolWindow? = null
        
        fun info(msg: String) {
            Notifications.Bus.notify(Notification("timermaster", msg, NotificationType.INFORMATION))
        }
        
        fun stringify(obj: Any): String = gson.toJson(obj)
        
        fun <T> parse(str: String, type: TypeToken<T>): T = gson.fromJson(str, type)
        
        fun getTodayYmd(): String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        
        fun getYmd(date: LocalDate): String = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        
        fun formatTime(seconds: Long): String {
            if (seconds < 60) {
                return "${seconds}秒"
            }
            if (seconds < 3600) {
                return "${DecimalFormat("#.0").format(seconds / 60.0)}分钟"
            }
            return "${DecimalFormat("#.0").format(seconds / 3600.0)}小时"
        }
        
        private fun getTime(): Long = System.currentTimeMillis()
        
        fun initData(): StatisticsData {
            val state = TimerMasterState.getInstance()
            // 存储的如果不是当日的数据, 则将数据加入到历史数据, 然后再初始化数据
            var data = parse(state.statisticsData, TypeToken.get(StatisticsData::class.java))
            if (data.createDate != getTodayYmd()) {
                val arr = parse(state.historyData, object : TypeToken<MutableList<String>>() {})
                arr.add(state.statisticsData)
                state.historyData = stringify(arr)
                data = StatisticsData()
            }
            state.activeTime = getTime()
            return data
        }
        
        fun getConsoleViews() = consoleViews
        
        fun consoleInfo(project: Project, msg: String) {
            if (consoleViews[project] == null) {
                ToolWindowManager.getInstance(project).getToolWindow("TimerMaster Console")
                    ?.let { createToolWindow(project, it) }
            }
            consoleViews[project]?.clear()
            consoleViews[project]?.print(msg, ConsoleViewContentType.NORMAL_OUTPUT)
            toolWindow?.activate(null, false)
        }
        
        fun createToolWindow(project: Project, toolWindow: ToolWindow) {
            val consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(project).console
            consoleViews[project] = consoleView
            val content = toolWindow.contentManager.factory.createContent(consoleView.component, "TimerMaster Output", false)
            content.component.isVisible = true
            content.isCloseable = true
            toolWindow.contentManager.addContent(content)
        }
    }
    
}