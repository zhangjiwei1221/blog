package cn.butterfly.listener.action

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.ui.components.JBList
import javax.swing.Icon


/**
 * PopupAction
 *
 * @author zjw
 * @date 2023-12-18
 */
class PopupAction:AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        JBPopupFactory.getInstance()
//            .createMessage("Hello, world!")
            .createConfirmation("Title", {}, 0)
//            .createListPopup(MyListPopupStep("Title", arrayOf("Option 1", "Option 2", "Option 3")))
            .showInFocusCenter()
    }
    
}

class MyListPopupStep constructor(title: String?, values: Array<String?>) : BaseListPopupStep<String>(title, *values) {
    
    override fun onChosen(selectedValue: String, finalChoice: Boolean): PopupStep<*> {
        // 选中事件
        return FINAL_CHOICE
    }

    override fun getIconFor(value: String): Icon? {
        // 设置图标
        return null
    }

    override fun isMnemonicsNavigationEnabled(): Boolean = false
    
}