package cn.butterfly.timermaster.listener

import cn.butterfly.timermaster.state.TimerMasterState
import cn.butterfly.timermaster.util.Utils
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.event.*

/**
 * 编辑器事件监听
 *
 * @author zjw
 * @date 2023-12-21
 */
class EditorListener: EditorFactoryListener, BulkAwareDocumentListener, CaretListener {
    
    private val state = TimerMasterState.getInstance()
    
    private val editorSet = mutableSetOf<Editor>()

    override fun editorCreated(event: EditorFactoryEvent) {
        val editor = event.editor
        if (editor in editorSet) {
            return
        }
        // 监听编辑操作
        event.editor.document.addDocumentListener(this)
        // 监听光标移动事件
        event.editor.caretModel.addCaretListener(this)
        editorSet.add(editor)
    }
    
    override fun documentChangedNonBulk(event: DocumentEvent) {
        val data = Utils.initData()
        event.takeIf { (it.oldFragment.isNotEmpty() or it.newFragment.isNotEmpty()) }?.let {
            // 只对字符长度为 1 和非空空白符的情况进行统计
            if (it.newFragment.isNotEmpty() && (it.newFragment.length == 1 || it.newFragment.trim().isEmpty())) {
                ++data.keyCount
            }
            // 根据文档代码段变更信息判断是新增还是删除行
            if (it.oldFragment.contains('\n')) {
                data.removeLineCount += it.oldFragment.count { item -> item == '\n' } 
            }
            if (it.newFragment.startsWith('\n')) {
                ++data.addLineCount
            }
        }
        state.statisticsData = Utils.stringify(data)
    }

    override fun caretPositionChanged(event: CaretEvent) {
        state.statisticsData = Utils.stringify(Utils.initData())
    }
    
}