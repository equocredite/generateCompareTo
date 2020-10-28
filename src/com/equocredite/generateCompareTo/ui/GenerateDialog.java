package com.equocredite.generateCompareTo.ui;

import com.equocredite.generateCompareTo.bindings.NullableColumnInfo;
import com.equocredite.generateCompareTo.bindings.SortOrderColumnInfo;
import com.equocredite.generateCompareTo.PsiFieldWithComparisonPolicy;
import com.equocredite.generateCompareTo.bindings.ClassNameColumnInfo;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.*;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

public class GenerateDialog extends DialogWrapper {
  private final LabeledComponent<JPanel> myComponent;
  private final ListTableModel<PsiFieldWithComparisonPolicy> myFields;

  public GenerateDialog(Project project, List<PsiFieldWithComparisonPolicy> policies, String dialogTitle) {
    super(project);
    setTitle(dialogTitle);

    ColumnInfo[] columnInfos = {
            new ClassNameColumnInfo("Class name"),
            new SortOrderColumnInfo("Sort order"),
            new NullableColumnInfo("Nullable")
    };
    myFields = new ListTableModel<PsiFieldWithComparisonPolicy>(columnInfos, policies, 0);

    MyTable jTable = new MyTable(myFields);
    jTable.setRowMargin(2);
    jTable.setRowHeight(20);

    ToolbarDecorator decorator = ToolbarDecorator.createDecorator(jTable);
    decorator.disableAddAction();
    JPanel panel = decorator.createPanel();
    myComponent = LabeledComponent.create(panel, "(Warning: existing method will be replaced)");

    init();
  }

  @Nullable
  @Override
  protected JComponent createCenterPanel() {
    return myComponent;
  }

  public List<PsiFieldWithComparisonPolicy> getFields() {
    return myFields.getItems();
  }
}