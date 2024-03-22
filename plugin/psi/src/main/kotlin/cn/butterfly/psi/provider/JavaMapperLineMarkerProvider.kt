package cn.butterfly.psi.provider

import cn.butterfly.psi.ui.PluginIcons
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.ide.highlighter.XmlFileType
import com.intellij.psi.*
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttributeValue


/**
 * mapper 类行标记配置
 *
 * @author zjw
 * @date 2024-03-07
 */
class JavaMapperLineMarkerProvider: RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        // 查找类名后缀为 Mapper 内的所有方法
        if (element !is PsiMethod) {
            return
        }
        val psiClass = PsiTreeUtil.getParentOfType(element, PsiClass::class.java) ?: return
        val className = psiClass.name ?: return
        if (!className.endsWith("Mapper")) {
            return
        }

        // 查找同名 XML 文件对应的 PSI 文件对象
        val virtualFile = FileTypeIndex.getFiles(XmlFileType.INSTANCE, GlobalSearchScope.allScope(element.project))
            .first { it.name.startsWith(className) }
        val psiFile = PsiManager.getInstance(element.project).findFile(virtualFile)
        
        // 遍历 XML 文件中节点值等于 Java 方法名的元素, 然后添加可跳转的行标记符
        psiFile?.accept(object : XmlRecursiveElementVisitor() {
            override fun visitXmlAttributeValue(xmlAttributeValue: XmlAttributeValue) {
                if (xmlAttributeValue.value == element.name) {
                    result.add(
                        NavigationGutterIconBuilder.create(PluginIcons.MAPPER_ICON)
                            .setTargets(setOf(xmlAttributeValue.navigationElement))
                            .setTooltipText("Navigation to target in mapper xml").createLineMarkerInfo(element)
                    )
                }
            }
        })
    }

}