package cn.butterfly.i18n.action

import cn.butterfly.i18n.util.MessageUtils
import cn.butterfly.i18n.util.NotificationUtils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import java.util.*

/**
 * TestAction
 *
 * @author zjw
 * @date 2024-04-03
 */
class TestAction: AnAction() {
    
    override fun actionPerformed(event: AnActionEvent) {
        Locale.setDefault(Locale.JAPANESE)
        NotificationUtils.info(MessageUtils.message("demo.greet-msg", "butterfly"))
    }
    
}