package cn.butterfly.listener.listener

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

/**
 * 项目监听器
 *
 * @author zjw
 * @date 2023-12-18
 */
class MyProjectManagerListener: ProjectManagerListener {
    
    init {
        // 项目启动处理
        val notification = Notification("listener", "Title", "Open", NotificationType.INFORMATION)
        Notifications.Bus.notify(notification, null)
    }

    override fun projectClosing(project: Project) {
        // 项目即将关闭
        println("Closing")
    }
    
}