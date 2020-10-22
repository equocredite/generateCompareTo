package com.equocredite.GenerateCompareTo.ui;

import com.equocredite.GenerateCompareTo.bindings.NullableColumnInfo;
import com.equocredite.GenerateCompareTo.bindings.SortOrderColumnInfo;
import com.equocredite.GenerateCompareTo.PsiFieldWithSortOrder;
import com.equocredite.GenerateCompareTo.bindings.ClassNameColumnInfo;
import com.equocredite.GenerateCompareTo.PsiUtil;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.*;
import com.intellij.ui.ToolbarDecorator;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.ListTableModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GenerateDialog extends DialogWrapper {
  private final LabeledComponent<JPanel> myComponent;
  private final ListTableModel<PsiFieldWithSortOrder> myFields;

  public GenerateDialog(PsiClass psiClass, String dialogTitle) {
    super(psiClass.getProject());
    setTitle(dialogTitle);

    PsiField[] fields = psiClass.getFields();
    List<PsiFieldWithSortOrder> sortOrders = new ArrayList<>(fields.length);
    for (PsiField field : fields) {
      if (PsiUtil.fieldIsStatic(field)) {
        continue;
      }
      if (PsiUtil.isPrimitiveComparable(field.getType())) {
        sortOrders.add(new PsiFieldWithSortOrder(field, true, null));
      } else if (PsiUtil.psiTypeImplementsComparable(field.getType())) {
        sortOrders.add(new PsiFieldWithSortOrder(field, true, !PsiUtil.fieldAnnotatedNotNull(field)));
      }
    }

    ColumnInfo[] columnInfos = {
            new ClassNameColumnInfo("Class name"),
            new SortOrderColumnInfo("Sort order"),
            new NullableColumnInfo("Nullable")};
    myFields = new ListTableModel<PsiFieldWithSortOrder>(columnInfos, sortOrders, 0);

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

  public List<PsiFieldWithSortOrder> getFields() {
    return myFields.getItems();
  }
}