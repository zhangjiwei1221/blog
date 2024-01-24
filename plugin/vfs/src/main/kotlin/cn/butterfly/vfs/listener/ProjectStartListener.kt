package cn.butterfly.vfs.listener

import cn.butterfly.vfs.util.Utils
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent

/**
 * 项目启动监听
 * 
 * @author zjw
 * @date 2024-01-05
 */
class ProjectStartListener: ProjectActivity {
    
    override suspend fun execute(project: Project) {
        project.messageBus.connect().subscribe(VirtualFileManager.VFS_CHANGES, object : BulkFileListener {
            override fun after(events: MutableList<out VFileEvent>) {
                for (event in events) {
                    Utils.info("变更的文件: ${event.file?.path}")
                }
            }
        })
    }
    
}
