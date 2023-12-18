package cn.butterfly.listener.listener

import com.intellij.util.messages.Topic


/**
 * 自定义监听器
 *
 * @author zjw
 * @date 2023-12-18
 */
interface MyListener {
    
    companion object {
        var MY_TOPIC: Topic<MyListener> = Topic.create("listener", MyListener::class.java)
    }
    
    fun afterHelpBtnClicked(msg: String)

}
