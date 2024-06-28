package cn.butterfly.sqllog.util

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications


/**
 * 工具类
 *
 * @author zjw
 * @date 2024-06-28
 */
class Utils {
    
    companion object {
        
        fun info(msg: String) {
            Notifications.Bus.notify(Notification("sqllog", msg, NotificationType.INFORMATION))
        }
        
    }
    
}