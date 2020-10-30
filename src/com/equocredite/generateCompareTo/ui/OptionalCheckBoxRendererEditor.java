package com.equocredite.generateCompareTo.ui;

import javax.swing.*;
import java.awt.*;

public class OptionalCheckBoxRendererEditor extends ToggleCellRendererEditor {
    private final JPanel emptyPanel = new JPanel();

    public OptionalCheckBoxRendererEditor() {
        super(new JCheckBox[]{new JCheckBox()});
        buttons[0].setSelected(true);
    }

    @Override
    protected Component getPanel(Object value) {
        if (value == null) {
            buttons[0].setSelected(Boolean.FALSE);
            return emptyPanel;
        }
        if (!getCellEditorValue().equals(value)) {
            buttons[0].setSelected(Boolean.TRUE.equals(value));
        }
        return buttonPanel;
    }
}
