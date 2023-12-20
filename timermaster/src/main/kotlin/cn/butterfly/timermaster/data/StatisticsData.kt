package cn.butterfly.timermaster.data

import cn.butterfly.timermaster.util.Utils

/**
 * 统计数据存储
 * 
 * @author zjw
 * @date 2023-12-20
 */
data class StatisticsData(
    var runTime: Long = 0,
    var createDate: String = Utils.getYmd()
)
