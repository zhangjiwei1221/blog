package cn.butterfly.listener.listener

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

/**
 * 项目监听
 *
 * @author zjw
 * @date 2023-12-18
 */
class MyProjectActivity: ProjectActivity {
    
    override suspend fun execute(project: Project) {
        val notification = Notification("listener", "Title", "Open", NotificationType.INFORMATION)
        Notifications.Bus.notify(notification, null)
    }
    
}