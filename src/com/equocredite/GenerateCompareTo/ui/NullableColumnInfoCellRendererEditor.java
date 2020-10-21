package com.equocredite.GenerateCompareTo.ui;

import com.intellij.util.ui.AbstractTableCellEditor;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NullableColumnInfoCellRendererEditor extends AbstractTableCellEditor implements TableCellRenderer {
    private final JCheckBox nullableButton = new JCheckBox();
    private final JPanel buttonPanel = new JPanel();

    public NullableColumnInfoCellRendererEditor() {
        nullableButton.setSelected(true);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(nullableButton);
        nullableButton.addActionListener(new StopTableEditingActionListener(buttonPanel));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean cellHasFocus, int row, int col) {
        if (!getCellEditorValue().equals(value)) {
            nullableButton.setSelected(Boolean.TRUE.equals(value));
        }
        return buttonPanel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean b, int i, int i2) {
        if (!getCellEditorValue().equals(value)) {
            nullableButton.setSelected(Boolean.TRUE.equals(value));
        }
        return buttonPanel;
    }

    @Override
    public Object getCellEditorValue() {
        return nullableButton.isSelected();
    }

    private static class StopTableEditingActionListener implements ActionListener {
        private final JPanel buttonPanel;

        private StopTableEditingActionListener(JPanel buttonPanel) {
            this.buttonPanel = buttonPanel;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            MyTable parentTable = (MyTable) buttonPanel.getParent();
            TableCellEditor editor = parentTable.getCellEditor();
            if (editor != null) {
                editor.stopCellEditing();
            }
        }
    }
}
