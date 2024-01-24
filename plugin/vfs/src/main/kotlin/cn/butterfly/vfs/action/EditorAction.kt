package cn.butterfly.vfs.action

import cn.butterfly.vfs.render.DemoRender
import cn.butterfly.vfs.util.Utils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.InlayProperties
import com.intellij.openapi.editor.ScrollType
import com.intellij.openapi.editor.VisualPosition
import com.intellij.openapi.editor.markup.HighlighterLayer
import com.intellij.openapi.editor.markup.HighlighterTargetArea
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.util.Disposer
import com.intellij.ui.JBColor
import org.jetbrains.plugins.notebooks.visualization.cellSelectionModel

/**
 * 编辑器相关
 *
 * @author zjw
 * @date 2024-01-03
 */
class EditorAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        // var editor = e.project?.let { FileEditorManager.getInstance(it).selectedEditor }
        val editor = e.getData(PlatformDataKeys.EDITOR) ?: return
        val document = editor.document
        val caretModel = editor.caretModel
        val inlayModel = editor.inlayModel
        val softWrapModel = editor.softWrapModel
        val markupModel = editor.markupModel
        val selectionModel = editor.selectionModel
        val foldingModel = editor.foldingModel
        val indentsModel = editor.indentsModel
        val scrollingModel = editor.scrollingModel
//        Utils.info("""
//            逻辑位置:<br/>
//            ${"-".repeat(20)}<br/>
//            行号: ${caretModel.logicalPosition.line + 1}<br/>
//            列号: ${caretModel.logicalPosition.column + 1}<br/><br/>
//            视觉位置:<br/>
//            ${"-".repeat(20)}<br/>
//            行号: ${caretModel.visualPosition.line + 1}<br/>
//            列号: ${caretModel.visualPosition.column + 1}<br/>
//        """.trimIndent())
//        Utils.info("""
//            偏移量:<br/>
//            ${"-".repeat(20)}<br/>
//            偏移量: ${caretModel.offset}<br/>
//        """.trimIndent())
//        caretModel.allCarets.forEach { 
//            Utils.info("""
//                偏移量:<br/>
//                ${"-".repeat(20)}<br/>
//                偏移量: ${it.offset}<br/>
//            """.trimIndent())
//        }
//        WriteCommandAction.runWriteCommandAction(e.project) {
//            // 插入字符串并移动光标位置到结尾
//            val msg = "庄周de蝴蝶"
//            document.insertString(caretModel.offset, msg)
//            caretModel.moveToOffset(caretModel.offset + msg.length)
//        }
        
//        inlayModel.getInlineElementsInRange(0, editor.document.textLength).forEach { Disposer.dispose(it) }
//        inlayModel.getBlockElementsInRange(0, editor.document.textLength).forEach { Disposer.dispose(it) }
//        val offset = caretModel.offset
//        val column = caretModel.visualPosition.column
//        inlayModel.addInlineElement(offset, DemoRender(editor, "庄周de蝴蝶"))
//        inlayModel.addBlockElement(offset, InlayProperties(),
//            DemoRender(editor, mutableListOf("first line", "second line", "third line"), column.toFloat()))
//        caretModel.moveToVisualPosition(VisualPosition(caretModel.visualPosition.line, column))
        
//        Utils.info("""
//            是否开启自动断行: ${softWrapModel.isSoftWrappingEnabled}<br/>
//            当前行是否存在自动断行: ${softWrapModel.getSoftWrapsForLine(caretModel.offset).isNotEmpty()}<br/>
//        """.trimIndent())
        
//        markupModel.removeAllHighlighters()
//        val lineAttr = TextAttributes()
//        lineAttr.backgroundColor = JBColor.RED
//        val line = caretModel.logicalPosition.line
//        markupModel.addLineHighlighter(line, HighlighterLayer.ERROR, lineAttr)
//        val rangeAttr = TextAttributes()
//        rangeAttr.backgroundColor = JBColor.BLUE
//        markupModel.addRangeHighlighter(0, 6, HighlighterLayer.SELECTION, rangeAttr, HighlighterTargetArea.EXACT_RANGE)
        
//        selectionModel.removeSelection(true)
//        selectionModel.setSelection(0, 6)
//        Utils.info("""
//            选中的文本内容: ${selectionModel.selectedText}<br/>
//            选中文本的开始和结束位置: (${selectionModel.selectionStart}, ${selectionModel.selectionEnd})<br/>
//        """.trimIndent())
        
//        foldingModel.runBatchFoldingOperation {
//            foldingModel.allFoldRegions.forEach { foldingModel.removeFoldRegion(it) }
//            foldingModel.addFoldRegion(selectionModel.selectionStart, selectionModel.selectionEnd, "庄周de蝴蝶")
//        }
        
//        val guide = indentsModel.caretIndentGuide ?: return
//        Utils.info("""
//            缩进级别: ${guide.indentLevel}<br/>
//            开始行: ${guide.startLine + 1}<br/>
//            结束行: ${guide.endLine + 1}<br/>
//        """.trimIndent())
        
//        scrollingModel.scrollToCaret(ScrollType.CENTER)
//        scrollingModel.runActionOnScrollingFinished {
//            scrollingModel.visibleArea.let {
//                Utils.info("""
//                    可视区域左上角坐标: (${it.x}, ${it.y})<br/>
//                    可视区域宽度: ${it.width}<br/>
//                    可视区域高度: ${it.height}<br/>
//                """.trimIndent())
//            }
//        }
    }

}