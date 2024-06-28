package cn.butterfly.psi.action

import cn.butterfly.psi.util.Utils
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.XmlRecursiveElementVisitor
import com.intellij.psi.xml.XmlAttribute
import com.intellij.psi.xml.XmlFile

/**
 * PsiXMLAction
 *
 * @author zjw
 * @date 2024-03-24
 */
class PsiXMLAction: AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val psiFile = e.getData(CommonDataKeys.PSI_FILE)
        val xmlFile = psiFile as XmlFile
        Utils.info("根标签名称：${xmlFile.rootTag?.name}")
        
        xmlFile.accept(object: XmlRecursiveElementVisitor() {
            override fun visitXmlAttribute(attribute: XmlAttribute) {
                Utils.info("属性名称：${attribute.name}, 属性值：${attribute.value}")
            }
        })
    }

}