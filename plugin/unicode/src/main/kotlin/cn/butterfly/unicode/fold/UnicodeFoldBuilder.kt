package cn.butterfly.unicode.fold

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.lang.properties.psi.PropertiesFile
import com.intellij.openapi.editor.Document
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.refactoring.suggested.startOffset

/**
 * UnicodeFoldBuilder
 *
 * @author zjw
 * @date 2024-04-13
 */
class UnicodeFoldBuilder: FoldingBuilderEx() {

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean) =
        // 对 properties 文件中的键值对进行遍历
        (root as? PropertiesFile)?.properties?.filter {
            // 只保留键值非空并且包含 Unicode 字符的键值对
            it.value != null && it.key != null && containsUnicodeEscapeSequence(it.value!!)
        }?.map {
            // 获取折叠区域，这里只截取键值对中的值部分
            val startOffset = it.psiElement.startOffset + it.key!!.length + 1
            val endOffset = startOffset + it.value!!.length
            FoldingDescriptor(it.psiElement.node, TextRange(startOffset, endOffset))
        }?.toTypedArray() ?: emptyArray()

    /**
     * 用于判断字符串中是否包含 Unicode 编码过的字符
     */
    private fun containsUnicodeEscapeSequence(str: String) =
        Regex("\\\\u[0-9a-fA-F]{4}").containsMatchIn(str)

    /**
     * 将 Unicode 编码的字符进行反编码
     */
    override fun getPlaceholderText(node: ASTNode): String {
        return decodeUnicode(node.text.substring(node.text.indexOf("=") + 1))
    }

    /**
     * 用于将字符串转为 Unicode 编码
     */
    private fun decodeUnicode(input: String) = input.replace("\\\\u([0-9a-fA-F]{4})".toRegex())
    {
        it.groupValues[1].toInt(16).toChar().toString()
    }

    /**
     * 默认将符合条件的文本进行折叠
     */
    override fun isCollapsedByDefault(node: ASTNode) = true

}