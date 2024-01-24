package cn.butterfly.vfs.listener

import com.intellij.codeInsight.editorActions.TypedHandlerDelegate
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileTypes.FileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiFile

/**
 * 键盘事件监听
 * 
 * @author zjw
 * @date 2024-01-05
 */
class KeyboardListener: TypedHandlerDelegate() {

    override fun charTyped(c: Char, project: Project, editor: Editor, file: PsiFile): Result {
        Messages.showInfoMessage("Input character: $c", "Input")
        return Result.CONTINUE
    }
    
}
