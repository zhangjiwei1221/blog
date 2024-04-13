package cn.butterfly.unicode.action

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * UnicodeAction
 *
 * @author zjw
 * @date 2024-04-13
 */
class UnicodeAction: AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        ActionManager.getInstance().getAction("CollapseRegion").actionPerformed(e)
    }

}