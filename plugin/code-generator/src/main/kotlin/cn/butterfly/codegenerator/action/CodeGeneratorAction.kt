package cn.butterfly.codegenerator.action

import cn.butterfly.codegenerator.ui.CodeGeneratorDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
 * 代码生成操作
 *
 * @author zjw
 * @date 2025-09-30
 */
class CodeGeneratorAction: AnAction() {
    
    override fun actionPerformed(e: AnActionEvent) {
        e.project?.let {
            CodeGeneratorDialog(it).show()
        }
    }

}