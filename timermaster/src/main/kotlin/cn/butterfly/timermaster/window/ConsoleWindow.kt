package cn.butterfly.timermaster.window

import cn.butterfly.timermaster.util.Utils
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

/**
 * 控制台窗口
 *
 * @author zjw
 * @date 2023-12-21
 */
class ConsoleWindow: ToolWindowFactory {
    
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        if (Utils.getConsoleViews()[project] == null) {
            Utils.createToolWindow(project, toolWindow)
        }
        Utils.toolWindow = toolWindow
    }

}