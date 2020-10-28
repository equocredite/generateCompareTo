package com.equocredite.generateCompareTo.ui;

import javax.swing.*;
import java.awt.*;

public class SortOrderColumnInfoCellRendererEditor extends ToggleCellRendererEditor {
    public SortOrderColumnInfoCellRendererEditor() {
      super(new JRadioButton[]{new JRadioButton("asc"), new JRadioButton("desc")});
      buttons[0].setSelected(true);
      buttons[1].setSelected(false);
      ButtonGroup buttonGroup = new ButtonGroup();
      buttonGroup.add(buttons[0]);
      buttonGroup.add(buttons[1]);
    }

    @Override
    protected Component getPanel(Object value) {
        if (!getCellEditorValue().equals(value)) {
            buttons[0].setSelected(Boolean.TRUE.equals(value));
            buttons[1].setSelected(Boolean.FALSE.equals(value));
        }
        return buttonPanel;
    }
  }
