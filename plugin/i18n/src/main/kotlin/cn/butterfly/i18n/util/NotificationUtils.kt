package cn.butterfly.i18n.util

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications

/**
 * 通知工具类
 *
 * @author zjw
 * @date 2024-04-03
 */
object NotificationUtils {

    fun info(msg: String) {
        Notifications.Bus.notify(Notification("i18n", msg, NotificationType.INFORMATION))
    }

}