package cn.butterfly.listener.ui

import cn.butterfly.listener.listener.MyListener
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.GotItTooltip
import com.intellij.ui.components.JBCheckBox
import com.intellij.ui.dsl.builder.Cell
import com.intellij.ui.dsl.builder.panel
import com.intellij.ui.dsl.builder.selected
import javax.swing.JButton
import javax.swing.JComponent


/**
 * 对话框 demo
 *
 * @author zjw
 * @date 2023-12-11
 */
class DialogDemo(private val project: Project): DialogWrapper(project) {
    
    private lateinit var checkBox: Cell<JBCheckBox>
    
    private lateinit var helpButton: JButton
    
    init {
        // 设置标题并初始化
        title = "Title"
        init()
    }

    // 创建 Don't show again 选择框
    override fun createDoNotAskCheckbox(): JComponent {
        return panel { 
            row { 
                checkBox = checkBox("Do not show again")
            }
        }
    }

    // 设置帮助按钮 id
    override fun getHelpId(): String {
        return "ListenerHelp"
    }

    // 设置帮助按钮的 Tooltip
    override fun setHelpTooltip(helpButton: JButton) {
        helpButton.toolTipText = "Tip"
        this.helpButton = helpButton
    }

    // 处理帮助按钮事件
    override fun doHelpAction() {
        showInfo("Help")
        project.messageBus.syncPublisher(MyListener.MY_TOPIC).afterHelpBtnClicked("点击了帮助按钮")
        GotItTooltip("listener.tip.id", "GotItTooltip")
            .show(helpButton, GotItTooltip.BOTTOM_MIDDLE)
    }

    // 处理 OK 按钮事件
    override fun doOKAction() {
        super.doOKAction()
        showInfo("OK, value: ${checkBox.selected()}")
    }

    // 处理取消按钮事件
    override fun doCancelAction() {
        super.doCancelAction()
        showInfo("Cancel")
    }

    // 展示消息
    private fun showInfo(msg: String) {
        Notifications.Bus.notify(Notification("listener", msg, NotificationType.INFORMATION), project)
    }

    override fun isOKActionEnabled(): Boolean = false

    // 创建布局面板
    override fun createCenterPanel(): JComponent {
        return panel {
            row {
                label("Hello, world!")
            }
        }
    }

}