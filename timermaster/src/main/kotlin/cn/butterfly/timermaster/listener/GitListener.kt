package cn.butterfly.timermaster.listener

import cn.butterfly.timermaster.state.TimerMasterState
import cn.butterfly.timermaster.util.Utils
import git4idea.push.GitPushListener
import git4idea.push.GitPushRepoResult
import git4idea.repo.GitRepository

/**
 * git 监听
 *
 * @author zjw
 * @date 2023-12-22
 */
class GitListener: GitPushListener {
    
    private val state = TimerMasterState.getInstance()
    
    override fun onCompleted(repository: GitRepository, pushResult: GitPushRepoResult) {
        val data = Utils.initData()
        ++data.pushCount
        state.statisticsData = Utils.stringify(data)
    }
    
}