package cn.butterfly.vfs.action

import cn.butterfly.vfs.util.Utils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys

/**
 * VFS 相关
 *
 * @author zjw
 * @date 2024-01-03
 */
class VFSAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE)
        virtualFile?.let {
            it.refresh(true, true) {
                // 文件刷新完成
                Utils.info("""
                    文件路径: ${it.path}<br/>
                    文件类型: ${it.fileType}<br/>
                    文件系统: ${it.fileSystem}<br/>
                    文件后缀: ${it.extension}<br/>
                    文件时间戳: ${it.timeStamp}<br/>
                """.trimIndent())
            }
        }
    }

}