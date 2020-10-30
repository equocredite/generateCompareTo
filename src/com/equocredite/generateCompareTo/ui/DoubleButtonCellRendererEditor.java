package com.equocredite.generateCompareTo.ui;

import javax.swing.*;
import java.awt.*;

public class DoubleButtonCellRendererEditor extends ToggleCellRendererEditor {
    private final JPanel emptyPanel = new JPanel();

    public DoubleButtonCellRendererEditor(String nameTrue, String nameFalse) {
      super(new JRadioButton[]{new JRadioButton(nameTrue), new JRadioButton(nameFalse)});
      buttons[0].setSelected(true);
      buttons[1].setSelected(false);
      ButtonGroup buttonGroup = new ButtonGroup();
      buttonGroup.add(buttons[0]);
      buttonGroup.add(buttons[1]);
    }

    @Override
    protected Component getPanel(Object value) {
        if (value == null) {
            buttons[0].setSelected(Boolean.TRUE);
            return emptyPanel;
        }
        if (!getCellEditorValue().equals(value)) {
            buttons[0].setSelected(Boolean.TRUE.equals(value));
            buttons[1].setSelected(Boolean.FALSE.equals(value));
        }
        return buttonPanel;
    }
  }
