package cn.butterfly.timermaster.listener

import cn.butterfly.timermaster.data.StatisticsData
import cn.butterfly.timermaster.state.TimerMasterState
import cn.butterfly.timermaster.util.Utils
import com.google.gson.reflect.TypeToken
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.startup.ProjectActivity
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


/**
 * 项目启动监听器
 *
 * @author zjw
 * @date 2023-12-19
 */
class ProjectStartListener: ProjectActivity {
    
    private val state = TimerMasterState.getInstance()

    override suspend fun execute(project: Project) {
        while (true) {
            delay(TimeUnit.SECONDS.toMillis(state.updateInterval.toLong()))
            run {
                // 避免多个项目运行时间统计多次, 增加满足以下规则才进行时间统计: 
                // 当前项目与配置信息一致, 或配置信息为空, 或配置信息内的项目不处于打开状态
                val projectPath = project.basePath.toString()
                val firstOrNull = ProjectManager.getInstance().openProjects.firstOrNull { it.basePath.toString() == state.runProjectPath }
                state.runProjectPath.takeIf { it == projectPath || it.isBlank() || firstOrNull == null }.let { 
                    state.runProjectPath = projectPath
                    val data = Utils.parse(state.statisticsData, TypeToken.get(StatisticsData::class.java))
                    if (data.createDate == Utils.getYmd()) {
                        data.runTime += state.updateInterval
                        state.statisticsData = Utils.stringify(data)
                        return
                    }
                    // 存储的如果不是当日的数据, 则将数据加入到历史数据, 然后再初始化数据
                    val arr = Utils.parse(state.historyData, object : TypeToken<MutableList<String>>() {})
                    arr.add(state.statisticsData)
                    state.historyData = Utils.stringify(arr)
                    data.runTime = state.updateInterval.toLong()
                    data.createDate = Utils.getYmd()
                    state.statisticsData = Utils.stringify(data)
                }
            }
        }
    }

}