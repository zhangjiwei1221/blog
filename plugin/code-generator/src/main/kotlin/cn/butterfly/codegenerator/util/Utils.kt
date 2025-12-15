package cn.butterfly.codegenerator.util

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications


/**
 * 工具类
 *
 * @author zjw
 * @date 2023-12-20
 */
object Utils {
    
    fun info(msg: String) {
        Notifications.Bus.notify(Notification("code-generator", msg, NotificationType.INFORMATION))
    }
    
}