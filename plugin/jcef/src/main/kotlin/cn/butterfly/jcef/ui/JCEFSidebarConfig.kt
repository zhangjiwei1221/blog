package cn.butterfly.jcef.ui

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.jcef.JBCefBrowser

/**
 * JCEF 侧边栏界面配置类
 *
 * @author butterfly
 * @date 2025-01-08
 */
class JCEFSidebarConfig: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val browser = JBCefBrowser()
        
        browser.loadHTML("<h1>Hello World</h1>")
        toolWindow.contentManager.addContent(
            ContentFactory.getInstance().createContent(browser.component, "", false))
    }

}
