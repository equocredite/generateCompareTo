package com.equocredite.GenerateCompareTo.bindings;

import com.equocredite.GenerateCompareTo.PsiFieldWithSortOrder;
import com.equocredite.GenerateCompareTo.ui.SortOrderColumnInfoCellRendererEditor;
import com.intellij.util.ui.ColumnInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Column info describing the sort order of the PsiField
 */
public class SortOrderColumnInfo extends ColumnInfo<PsiFieldWithSortOrder, Boolean> {
    private final SortOrderColumnInfoCellRendererEditor rendererEditor = new SortOrderColumnInfoCellRendererEditor();

    public SortOrderColumnInfo(String name) {
      super(name);
    }

    @Override
    public Boolean valueOf(PsiFieldWithSortOrder psiFieldWithSortOrder) {
      return psiFieldWithSortOrder.isAscending();
    }

    @Override
    public void setValue(PsiFieldWithSortOrder psiFieldWithSortOrder, Boolean value) {
      psiFieldWithSortOrder.setAscending(value);
    }

    @Override
    public TableCellRenderer getRenderer(PsiFieldWithSortOrder psiFieldWithSortOrder) {
      return rendererEditor;
    }

    @Override
    public boolean isCellEditable(PsiFieldWithSortOrder psiFieldWithSortOrder) {
      return true;
    }

    @Override
    public TableCellEditor getEditor(PsiFieldWithSortOrder psiFieldWithSortOrder) {
      return rendererEditor;
    }
  }
