package cn.butterfly.vfs.render

import com.intellij.ide.ui.AntialiasingType
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.editor.Inlay
import com.intellij.openapi.editor.impl.FontInfo
import com.intellij.openapi.editor.markup.TextAttributes
import com.intellij.ui.JBColor
import com.intellij.util.ui.GraphicsUtil
import java.awt.*
import java.awt.font.FontRenderContext
import kotlin.math.ceil

/**
 * 字体渲染 demo
 *
 * @author zjw
 * @date 2024-01-23
 */
class DemoRender<T>(
    private val editor: Editor,
    private val renderText: T,
    private var wordCount: Float = 0f
): EditorCustomElementRenderer {
    
    // 设置字体
    private val font = Font("Microsoft YaHei", Font.ITALIC, editor.colorsScheme.editorFontSize)
    
    override fun calcWidthInPixels(p0: Inlay<*>): Int {
        // 获取渲染内容的宽度, 如果是多行文本则取最长文本行的宽度
        return when (renderText) {
            is String -> calcTextWidth(renderText)
            is MutableList<*> -> renderText.maxOfOrNull { calcTextWidth(it.toString()) } ?: 0
            else -> 0
        }
    }

    override fun calcHeightInPixels(inlay: Inlay<*>): Int {
        // 获取渲染内容的高度, 如果是多行文本则需要将行高乘以行数
        return when (renderText) {
            is MutableList<*> -> super.calcHeightInPixels(inlay) * renderText.size
            else -> super.calcHeightInPixels(inlay)
        }
    }

    override fun paint(inlay: Inlay<*>, g: Graphics, targetRegion: Rectangle, textAttributes: TextAttributes) {
        val g2 = g.create() as Graphics2D
        GraphicsUtil.setupAAPainting(g2)
        textAttributes.foregroundColor = JBColor.GRAY
        val lineHeight = editor.lineHeight.toDouble()
        val fontBaseline = ceil(font.createGlyphVector(getFontMetrics().fontRenderContext, "中文").visualBounds.height)
        val linePadding = (lineHeight - fontBaseline) / 2.0
        val offsetX = targetRegion.x
        val offsetY = targetRegion.y + fontBaseline + linePadding
        g2.font = font
        g2.color = JBColor.GRAY
        when (renderText) {
            is String -> {
                g2.drawString(renderText, offsetX.toFloat(), offsetY.toFloat())
            }
            is MutableList<*> -> {
                // 多行文本渲染的时候设置缩进
                val tabSize = editor.settings.getTabSize(editor.project)
                val startOffset = calcTextWidth("Z") * (wordCount + tabSize)
                renderText.forEach {
                    g2.drawString(it.toString(), startOffset, offsetY.toFloat())
                    g2.translate(0.0, lineHeight)
                }
            }
            else -> return
        }
        g2.dispose()
    }
    
     private fun calcTextWidth(text: String): Int {
        return getFontMetrics().stringWidth(text) 
     }
    
    private fun getFontMetrics(): FontMetrics {
        val editorContext = FontInfo.getFontRenderContext(editor.contentComponent)
        val context = FontRenderContext(editorContext.transform, AntialiasingType.getKeyForCurrentScope(false), 
            editorContext.fractionalMetricsHint)
        return FontInfo.getFontMetrics(font, context)
    }

}