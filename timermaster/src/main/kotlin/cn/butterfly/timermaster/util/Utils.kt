package cn.butterfly.timermaster.util

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
    }
    
}