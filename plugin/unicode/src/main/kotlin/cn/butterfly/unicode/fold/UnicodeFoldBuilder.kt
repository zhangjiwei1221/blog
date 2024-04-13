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

    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> {
        return (root as? PropertiesFile)?.properties?.filter {
            it.value != null && it.key != null && containsUnicodeEscapeSequence(it.value!!)
        }?.map {
            val startOffset = it.psiElement.startOffset + it.key!!.length + 1
            FoldingDescriptor(it.psiElement.node, TextRange(startOffset, startOffset + it.value!!.length))
        }?.toTypedArray() ?: emptyArray()
    }
    
    private fun containsUnicodeEscapeSequence(str: String) = Regex("\\\\u[0-9a-fA-F]{4}").containsMatchIn(str)
    
    override fun getPlaceholderText(node: ASTNode): String {
        return decodeUnicode(node.text.substring(node.text.indexOf("=") + 1))
    }
    
    private fun decodeUnicode(input: String) = input.replace("\\\\u([0-9a-fA-F]{4})".toRegex()) {
        it.groupValues[1].toInt(16).toChar().toString()
    }

    override fun isCollapsedByDefault(node: ASTNode) = true

}