package cn.butterfly.listener.action

import com.intellij.notification.Notification
import com.intellij.notification.NotificationAction
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 侧边栏消息通知
 *
 * @author zjw
 * @date 2023-12-10
 */
class NoticeAction: AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        val notification = Notification("listener", "Title", "Hello, world!", NotificationType.INFORMATION)
        notification.addAction(object : NotificationAction("ShowSettings") {
            override fun actionPerformed(e: AnActionEvent, notification: Notification) {
                // 用于打开设置界面, 这里的 ShowSettings 是 IDEA 定义的常量值
                ActionManager.getInstance().getAction("ShowSettings").actionPerformed(e)
            }
        })
        Notifications.Bus.notify(notification, e.project)
    }

}