package cn.butterfly.listener.ui

import com.intellij.openapi.fileEditor.FileEditor
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.ui.EditorNotificationPanel
import com.intellij.ui.EditorNotificationProvider
import java.util.function.Function
import javax.swing.JComponent


/**
 * 编辑器横幅
 *
 * @author zjw
 * @date 2023-12-18
 */
class EditorBanner: EditorNotificationProvider {
    
    override fun collectNotificationData(project: Project, virtualFile: VirtualFile): Function<in FileEditor, out JComponent?> {
        return Function {
            val banner = EditorNotificationPanel()
            banner.text = "EditorBanner"
            banner.toolTipText = "ShowSettings"
            banner.createActionLabel("ShowSettings") {
                ShowSettingsUtil.getInstance().showSettingsDialog(project, "Editor")
            }
            banner
        }
        
    }

}