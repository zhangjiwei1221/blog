package cn.butterfly.timermaster.listener

import cn.butterfly.timermaster.state.TimerMasterState
import cn.butterfly.timermaster.util.Utils
import com.intellij.openapi.editor.event.*

/**
 * 编辑器事件监听
 *
 * @author zjw
 * @date 2023-12-21
 */
class EditorListener: EditorFactoryListener, BulkAwareDocumentListener, CaretListener {
    
    private val state = TimerMasterState.getInstance()

    override fun editorCreated(event: EditorFactoryEvent) {
        // 监听编辑操作
        event.editor.document.addDocumentListener(this)
        // 监听光标移动事件
        event.editor.caretModel.addCaretListener(this)
    }
    
    override fun documentChangedNonBulk(event: DocumentEvent) {
        val data = Utils.initData()
        event.takeIf { it.oldFragment.isNotEmpty() or it.newFragment.isNotEmpty() }?.let {
            ++data.keyCount
            // 根据文档代码段变更信息判断是新增还是删除行
            if (it.oldFragment.startsWith('\n')) {
                ++data.removeLineCount
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