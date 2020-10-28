package com.equocredite.generateCompareTo.bindings;

import com.equocredite.generateCompareTo.PsiFieldWithComparisonPolicy;
import com.equocredite.generateCompareTo.ui.SortOrderColumnInfoCellRendererEditor;
import com.intellij.util.ui.ColumnInfo;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * Column info describing the sort order of the PsiField
 */
public class SortOrderColumnInfo extends ColumnInfo<PsiFieldWithComparisonPolicy, Boolean> {
    private final SortOrderColumnInfoCellRendererEditor rendererEditor = new SortOrderColumnInfoCellRendererEditor();

    public SortOrderColumnInfo(String name) {
      super(name);
    }

    @Override
    public Boolean valueOf(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
      return psiFieldWithComparisonPolicy.isAscending();
    }

    @Override
    public void setValue(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy, Boolean value) {
      psiFieldWithComparisonPolicy.setAscending(value);
    }

    @Override
    public TableCellRenderer getRenderer(PsiFieldWithComparisonPolicy psiFieldWithSortOrder) {
      return rendererEditor;
    }

    @Override
    public boolean isCellEditable(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
      return true;
    }

    @Override
    public TableCellEditor getEditor(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
      return rendererEditor;
    }
  }
