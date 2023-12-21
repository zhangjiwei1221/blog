package cn.butterfly.timermaster.listener

import cn.butterfly.timermaster.state.TimerMasterState
import cn.butterfly.timermaster.util.Utils
import com.intellij.codeInsight.editorActions.CopyPastePreProcessor
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.RawText
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile

/**
 * 复制粘贴监听
 *
 * @author zjw
 * @date 2023-12-21
 */
class CopyPasteListener: CopyPastePreProcessor {
    
    private val state = TimerMasterState.getInstance()
    
    override fun preprocessOnCopy(p0: PsiFile?, p1: IntArray?, p2: IntArray?, p3: String?): String? {
        val data = Utils.initData()
        ++data.copyCount
        state.statisticsData = Utils.stringify(data)
        return null
    }

    override fun preprocessOnPaste(p0: Project?, p1: PsiFile?, p2: Editor?, text: String?, p4: RawText?): String {
        val data = Utils.initData()
        ++data.pasteCount
        state.statisticsData = Utils.stringify(data)
        return text ?: ""
    }

}