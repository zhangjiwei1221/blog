package cn.butterfly.timermaster.listener

import cn.butterfly.timermaster.state.TimerMasterState
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener

/**
 * 项目关闭监听
 *
 * @author zjw
 * @date 2023-12-19
 */
class ProjectCloseListener: ProjectManagerListener {
    
    private val state = TimerMasterState.getInstance()

    override fun projectClosing(project: Project) {
        // 关闭前清空运行项目路径配置
        state.runProjectPath.takeIf { it == project.basePath.toString() }.let { state.runProjectPath = "" }
    }
    
}