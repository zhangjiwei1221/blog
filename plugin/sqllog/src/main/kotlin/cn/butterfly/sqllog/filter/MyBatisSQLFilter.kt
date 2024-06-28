package cn.butterfly.sqllog.filter

import cn.butterfly.sqllog.constant.Constants
import cn.butterfly.sqllog.render.MybatisSQLRenderer
import com.intellij.execution.filters.ConsoleFilterProvider
import com.intellij.execution.filters.Filter
import com.intellij.execution.impl.InlayProvider
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorCustomElementRenderer
import com.intellij.openapi.project.Project

/**
 * MyBatis SQL 过滤处理
 *
 * @author zjw
 * @date 2024-06-28
 */
class MyBatisSQLFilter: Filter, ConsoleFilterProvider {

    override fun applyFilter(line: String, offset: Int): Filter.Result? {
        if (line.contains(Constants.SQL_PREFIX)) {
            return Filter.Result(listOf(MyBatisSQLResult(offset - line.length),
                Filter.ResultItem(0, 0, null)
            ))
        }
        return null
    }
    
    class MyBatisSQLResult(private val offset: Int) : Filter.Result(offset, offset, null), InlayProvider {
        override fun createInlayRenderer(editor: Editor): EditorCustomElementRenderer {
            return MybatisSQLRenderer(editor, offset)
        }
    }

    override fun getDefaultFilters(p0: Project): Array<Filter> {
        return arrayOf(this)
    }

}