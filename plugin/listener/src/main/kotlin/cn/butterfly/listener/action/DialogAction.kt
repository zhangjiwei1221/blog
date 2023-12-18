package cn.butterfly.listener.action

import cn.butterfly.listener.ui.DialogDemo
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.Messages

/**
 * 对话框消息通知
 *
 * @author zjw
 * @date 2023-12-10
 */
class DialogAction: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let { DialogDemo(it).show() }
//        Messages.showInfoMessage("Hello, world!", "Title")
    }
    
}