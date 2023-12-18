package cn.butterfly.listener.listener

import com.intellij.openapi.ui.Messages

/**
 * MyListenerA
 *
 * @author zjw
 * @date 2023-12-18
 */
class MyListenerA: MyListener {

    override fun afterHelpBtnClicked(msg: String) {
        Messages.showInfoMessage(msg, "Title")
    }
    
}