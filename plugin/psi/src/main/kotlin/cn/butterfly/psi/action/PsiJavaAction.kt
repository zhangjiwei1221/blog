package cn.butterfly.psi.action

import cn.butterfly.psi.util.Utils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil

/**
 * PsiJavaAction
 *
 * @author zjw
 * @date 2024-03-24
 */
class PsiJavaAction: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
//        val psiJavaFile = psiFile as PsiJavaFile
//        Utils.info("当前类所属包：${psiJavaFile.packageName}")
//        
//        psiJavaFile.accept(object: JavaRecursiveElementVisitor() {
//            override fun visitMethod(method: PsiMethod) {
//                Utils.info("查找到方法：${method.name}")
//            }
//        })
        val psiElement = e.getData(PlatformDataKeys.EDITOR)?.caretModel?.let { psiFile?.findElementAt(it.offset) }
//        val psiMethod = PsiTreeUtil.getParentOfType(psiElement, PsiMethod::class.java)
        val psiClass = PsiTreeUtil.getParentOfType(psiElement, PsiClass::class.java)
//        Utils.info("所属方法名：${psiMethod?.name}")
//        Utils.info("所属类名：${psiClass?.name}")
        val psiMethods = PsiTreeUtil.getChildrenOfTypeAsList(psiClass, PsiMethod::class.java)
        Utils.info("包含的方法：${psiMethods.joinToString(",") { it.name }}")
    }

}