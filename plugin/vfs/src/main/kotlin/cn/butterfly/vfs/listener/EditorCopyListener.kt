package cn.butterfly.vfs.listener

import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.editor.Caret
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.actionSystem.EditorActionHandler
import com.intellij.openapi.editor.actionSystem.EditorActionManager
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.ui.Messages
import java.awt.datatransfer.DataFlavor

/**
 * 编辑器复制监听
 * 
 * @author zjw
 * @date 2024-01-05
 */
class EditorCopyListener: EditorActionHandler(), ProjectActivity {
    
    override suspend fun execute(project: Project) {
        EditorActionManager.getInstance().setActionHandler(IdeActions.ACTION_EDITOR_COPY, this)
    }

    override fun doExecute(editor: Editor, caret: Caret?, dataContext: DataContext?) {
        editor.selectionModel.copySelectionToClipboard()
        Messages.showInfoMessage("Copy text: ${CopyPasteManager.getInstance()
            .getContents<String>(DataFlavor.stringFlavor)}", "Copy")
    }

}
