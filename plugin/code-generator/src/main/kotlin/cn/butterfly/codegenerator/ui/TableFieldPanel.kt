package cn.butterfly.codegenerator.ui

import cn.butterfly.codegenerator.datasource.Field
import com.intellij.ui.JBColor
import com.intellij.ui.TableUtil
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.EditableModel
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.table.AbstractTableModel

/**
 * 表字段面板
 *
 * @author zjw
 * @date 2025-10-01
 */
class TableFieldPanel {

    private var tableModel = PropertiesTableModel()

    fun getTableFields(): List<Field> {
        return tableModel.options
    }
    
    fun setTableFields(fields: List<Field>) {
        tableModel.clear()
        tableModel.addAll(fields)
    }

    fun create(): JPanel {
        val table = JBTable(tableModel)
        table.emptyText.setText("暂无字段数据")
        val toolbarDecorator = ToolbarDecorator.createDecorator(table)
            .disableUpDownActions()
            .setAddAction { _ ->
                val cellEditor = table.cellEditor
                cellEditor?.stopCellEditing()
                val model = table.model
                (model as EditableModel).addRow()
                TableUtil.editCellAt(table, model.rowCount - 1, 0)
            }
        val tablePanel = toolbarDecorator.createPanel()
        val border = BorderFactory.createTitledBorder("字段")
        tablePanel.border = border
        border.border = BorderFactory.createLineBorder(JBColor.GRAY)
        tablePanel.border = border
        return tablePanel
    }

    class PropertiesTableModel : AbstractTableModel(), EditableModel {
        private val myRows: MutableList<Field> = ArrayList()

        private val columns = arrayOf("名称", "类型" , "注释")

        override fun getColumnName(column: Int) = columns[column]

        override fun getColumnClass(columnIndex: Int): Class<*> {
            when (columnIndex) {
                0 -> return String::class.java
                1 -> return String::class.java
                2 -> return String::class.java
            }
            return String::class.java
        }

        override fun getRowCount() = myRows.size

        override fun getColumnCount() = 3

        override fun isCellEditable(rowIndex: Int, columnIndex: Int) = columnIndex < columnCount

        override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
            when (columnIndex) {
                0 -> return myRows[rowIndex].name
                1 -> return myRows[rowIndex].type
                2 -> return myRows[rowIndex].comment
            }
            return ""
        }

        override fun setValueAt(aValue: Any?, rowIndex: Int, columnIndex: Int) {
            val cellVal = aValue.toString()
            when (columnIndex) {
                0 -> myRows[rowIndex].name = cellVal
                1 -> myRows[rowIndex].type = cellVal
                2 -> myRows[rowIndex].comment = cellVal
            }
        }

        override fun removeRow(idx: Int) {
            myRows.removeAt(idx)
            fireTableRowsDeleted(idx, idx)
        }

        override fun exchangeRows(oldIndex: Int, newIndex: Int) {
            // Unsupported
        }

        override fun canExchangeRows(oldIndex: Int, newIndex: Int): Boolean {
            return false
        }

        override fun addRow() {
            myRows.add(Field("", "String", ""))
            val index = myRows.size - 1
            fireTableRowsInserted(index, index)
        }

        val options: List<Field> get() = myRows
        
        fun clear() {
            val size = myRows.size
            if (size == 0) {
                return
            }
            myRows.clear()
            fireTableRowsDeleted(0, size - 1)
        }
        
        fun addAll(fields: List<Field>) {
            fields.forEach { 
                myRows.add(it)
                val index = myRows.size - 1
                fireTableRowsInserted(index, index)
            }
        }
    }
    
}