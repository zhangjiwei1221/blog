package cn.butterfly.timermaster.util

import cn.butterfly.timermaster.data.StatisticsData
import cn.butterfly.timermaster.state.TimerMasterState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.format.DateTimeFormatter


/**
 * 工具类
 *
 * @author zjw
 * @date 2023-12-20
 */
class Utils {
    
    companion object {
        private val gson = Gson()
        
        fun stringify(obj: Any): String = gson.toJson(obj)
        
        fun <T> parse(str: String, type: TypeToken<T>): T = gson.fromJson(str, type)
        
        fun getYmd(): String = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        
        fun getTime(): Long = System.currentTimeMillis()
        
        fun initData(): StatisticsData {
            val state = TimerMasterState.getInstance()
            // 存储的如果不是当日的数据, 则将数据加入到历史数据, 然后再初始化数据
            var data = parse(state.statisticsData, TypeToken.get(StatisticsData::class.java))
            if (data.createDate != getYmd()) {
                val arr = parse(state.historyData, object : TypeToken<MutableList<String>>() {})
                arr.add(state.statisticsData)
                state.historyData = stringify(arr)
                data = StatisticsData()
            }
            state.activeTime = getTime()
            return data
        }
    }
    
}