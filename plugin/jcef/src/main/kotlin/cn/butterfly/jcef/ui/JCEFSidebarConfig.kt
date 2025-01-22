package cn.butterfly.jcef.ui

import cn.butterfly.jcef.web.JCEFResourceHandler
import com.intellij.openapi.application.invokeLater
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefScrollbarsHelper
import org.cef.CefApp
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.browser.CefMessageRouter
import org.cef.callback.CefQueryCallback
import org.cef.handler.CefMessageRouterHandlerAdapter

/**
 * JCEF 侧边栏界面配置类
 *
 * @author butterfly
 * @date 2025-01-08
 */
class JCEFSidebarConfig: ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val browser = JBCefBrowser()
//        browser.jbCefClient.addLoadHandler(object : CefLoadHandler {
//            override fun onLoadingStateChange(p0: CefBrowser?, p1: Boolean, p2: Boolean, p3: Boolean) {
//                println("加载状态变更")
//            }
//
//            override fun onLoadStart(p0: CefBrowser?, p1: CefFrame?, p2: CefRequest.TransitionType?) {
//                println("加载开始")
//            }
//
//            override fun onLoadEnd(p0: CefBrowser?, p1: CefFrame?, p2: Int) {
//                println("加载结束")
//            }
//
//            override fun onLoadError(
//                p0: CefBrowser?,
//                p1: CefFrame?,
//                p2: CefLoadHandler.ErrorCode?,
//                p3: String?,
//                p4: String?
//            ) {
//                println("加载出错")
//            }
//
//        }, browser.cefBrowser)
//        browser.jbCefClient.addDownloadHandler(object : CefDownloadHandler {
//            override fun onBeforeDownload(
//                browser: CefBrowser?,
//                downloadItem: CefDownloadItem?,
//                suggestedName: String?,
//                callback: CefBeforeDownloadCallback
//            ) {
//                callback.Continue("", true)
//            }
//
//            override fun onDownloadUpdated(browser: CefBrowser?, downloadItem: CefDownloadItem?, callback: CefDownloadItemCallback?) {
//                println("下载状态更新")
//            }
//
//        }, browser.cefBrowser)
//        browser.jbCefClient.addJSDialogHandler(object : CefJSDialogHandler {
//            override fun onJSDialog(
//                browser: CefBrowser?,
//                originUrl: String?,
//                dialogType: CefJSDialogHandler.JSDialogType?,
//                messageText: String?,
//                defaultPromptText: String?,
//                callback: CefJSDialogCallback,
//                suppressMessage: BoolRef?
//            ): Boolean {
//                invokeLater {
//                    Messages.showInfoMessage(messageText, "")
//                    callback.Continue(true, messageText)
//                }
//                return true
//            }
//
//            override fun onBeforeUnloadDialog(p0: CefBrowser?, p1: String?, p2: Boolean, p3: CefJSDialogCallback?) = true
//
//            override fun onResetDialogState(p0: CefBrowser?) {}
//
//            override fun onDialogClosed(p0: CefBrowser?) {}
//
//        }, browser.cefBrowser)
        val routerConfig = CefMessageRouter.CefMessageRouterConfig("javaQuery", "javaQueryCancel")
        val messageRouter = CefMessageRouter.create(routerConfig, object : CefMessageRouterHandlerAdapter() {

            override fun onQuery(
                browser: CefBrowser,
                frame: CefFrame?,
                queryId: Long,
                request: String?,
                persistent: Boolean,
                callback: CefQueryCallback
            ): Boolean {
                if (request == "butterfly") {
                    invokeLater {
                        println("后端收到请求")
                        browser.executeJavaScript("window.butterfly('Hello, world!')", null, 0)
                    }
                    callback.success("butterfly")
                    return true
                }
                if (request == "init") {
                    callback.success(JBCefScrollbarsHelper.buildScrollbarsStyle())
                    return true
                }
                return false
            }

        })
        browser.jbCefClient.cefClient.addMessageRouter(messageRouter)
        
//        browser.loadHTML("<h1 style=\"color: red;\">Hello World!</h1>")
//        browser.loadHTML("<img src=\"img/test.jpg\"/>", "file:///D:")
//        browser.loadURL("https://www.baidu.com")
        // 注册自定义处理的协议、域名和处理方法, 然后再加载
        CefApp.getInstance()
            .registerSchemeHandlerFactory("http", "butterfly") { _, _, _, _ -> JCEFResourceHandler() }
        browser.loadURL("http://butterfly/index.html")
        toolWindow.contentManager.addContent(ContentFactory.getInstance().createContent(browser.component, "", false))
    }

}
