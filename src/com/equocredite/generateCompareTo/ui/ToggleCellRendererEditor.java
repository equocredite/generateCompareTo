package com.equocredite.generateCompareTo.ui;

import com.intellij.util.ui.AbstractTableCellEditor;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ToggleCellRendererEditor extends AbstractTableCellEditor implements TableCellRenderer {
    protected final JToggleButton[] buttons;
    protected final JPanel buttonPanel = new JPanel();

    protected ToggleCellRendererEditor(JToggleButton[] buttons) {
        this.buttons = buttons;
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        for (JToggleButton button : buttons) {
            buttonPanel.add(button);
            button.addActionListener(new StopTableEditingActionListener(buttonPanel));
        }
    }

    protected abstract Component getPanel(Object value);

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean cellHasFocus, int row, int col) {
        return getPanel(value);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean b, int i, int i2) {
        return getPanel(value);
    }

    @Override
    public Object getCellEditorValue() {
        return buttons[0].isSelected();
    }

    protected static class StopTableEditingActionListener implements ActionListener {
        private final JPanel buttonPanel;

        public StopTableEditingActionListener(JPanel buttonPanel) {
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
