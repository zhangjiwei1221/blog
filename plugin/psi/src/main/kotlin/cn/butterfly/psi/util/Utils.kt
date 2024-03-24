package cn.butterfly.vfs.util

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications


/**
 * 工具类
 *
 * @author zjw
 * @date 2023-12-20
 */
class Utils {
    
    companion object {
        
        fun info(msg: String) {
            Notifications.Bus.notify(Notification("psi", msg, NotificationType.INFORMATION))
        }
        
    }
    
}