package cn.butterfly.sqllog.filter

import cn.butterfly.sqllog.filter.MyBatisSQLFilter.MyBatisSQLResult
import cn.butterfly.sqllog.render.MybatisSQLRenderer
import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.JvmExceptionOccurrenceFilter
import com.intellij.execution.impl.InlayProvider
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.psi.PsiClass

/**
 * ExceptionFilter
 *
 * @author zjw
 * @date 2024-06-29
 */
class ExceptionFilter: JvmExceptionOccurrenceFilter {
    
    override fun applyFilter(exceptionClassName: String, classes: MutableList<PsiClass>, offset: Int): Filter.ResultItem {
        return object : Filter.Result(offset, offset + exceptionClassName.length, null), InlayProvider { 
            override fun createInlayRenderer(editor: Editor): EditorCustomElementRenderer {
                return MybatisSQLRenderer(editor, offset)
            }}
    }

}