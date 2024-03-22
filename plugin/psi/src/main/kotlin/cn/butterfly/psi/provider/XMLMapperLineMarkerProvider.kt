package cn.butterfly.psi.provider

import cn.butterfly.psi.ui.PluginIcons
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.psi.*
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.xml.XmlAttributeValue
import com.intellij.psi.xml.XmlFile

/**
 * mapper XML 文件行标记配置
 *
 * @author zjw
 * @date 2024-03-07
 */
class XMLMapperLineMarkerProvider: RelatedItemLineMarkerProvider() {

    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        // 查找文件名后缀为 Mapper 内的所有节点
        if (element !is XmlAttributeValue) {
            return
        }
        
        val xmlFile = PsiTreeUtil.getParentOfType(element, XmlFile::class.java) ?: return
        val xmlName = xmlFile.name.substring(0, xmlFile.name.lastIndexOf("."))
        if (!xmlName.endsWith("Mapper")) {
            return
        }

        // 查找同名 Java 文件对应的 PSI 文件对象
        val virtualFile = FileTypeIndex.getFiles(JavaFileType.INSTANCE, GlobalSearchScope.allScope(element.project))
            .first { it.name.startsWith(xmlName) }
        val psiFile = PsiManager.getInstance(element.project).findFile(virtualFile)
        
        // 遍历 Java 文件中方法名等于 XML 节点值的元素, 然后添加可跳转的行标记符
        psiFile?.accept(object : JavaRecursiveElementVisitor() {
            override fun visitMethod(method: PsiMethod) {
                 if (method.name == element.value) {
                     result.add(
                         NavigationGutterIconBuilder.create(PluginIcons.MAPPER_ICON)
                             .setTargets(setOf(method.navigationElement))
                             .setTooltipText("Navigation to target in mapper java").createLineMarkerInfo(element)
                     )
                 }
            }
        })
    }

}