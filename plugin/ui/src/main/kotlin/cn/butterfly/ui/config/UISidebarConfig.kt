package cn.butterfly.ui.config

import cn.butterfly.ui.UIDemo
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.JComponent

/**
 * UI 侧边栏界面配置类
 *
 * @author zjw
 * @date 2023-12-06
 */
class UISidebarConfig: ToolWindowFactory {
    
    private val form = UIDemo()

    private val component: JComponent = form.mainPanel

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        toolWindow.contentManager.addContent(ContentFactory.getInstance().createContent(component, "", false))
    }

}