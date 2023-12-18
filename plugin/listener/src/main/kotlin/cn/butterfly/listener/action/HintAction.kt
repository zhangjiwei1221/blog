package cn.butterfly.listener.action

import com.intellij.codeInsight.hint.HintManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys

/**
 * 编辑器提示处理
 *
 * @author zjw
 * @date 2023-12-12
 */
class HintAction: AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
//         e.getData(PlatformDataKeys.EDITOR)?.let { HintManager.getInstance().showInformationHint(it, "Information") }
//         e.getData(PlatformDataKeys.EDITOR)?.let { HintManager.getInstance().showErrorHint(it, "Error") }
         e.getData(PlatformDataKeys.EDITOR)?.let {
             HintManager.getInstance().showQuestionHint(it, "Question", it.caretModel.offset, it.caretModel.offset + 6) {
                 true
             }
         }
    }

}