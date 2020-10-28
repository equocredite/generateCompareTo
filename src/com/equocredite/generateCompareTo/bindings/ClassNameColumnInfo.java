package com.equocredite.generateCompareTo.bindings;

import com.equocredite.generateCompareTo.PsiFieldWithComparisonPolicy;
import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.util.ui.ColumnInfo;
import org.jetbrains.annotations.Nullable;

/**
 * ColumnInfo for rendering the class name
 */
public class ClassNameColumnInfo extends ColumnInfo<PsiFieldWithComparisonPolicy, String> {
  private final DefaultPsiElementCellRenderer psiFieldWithSortOrderCellRenderer
      = new DefaultPsiElementCellRenderer();

  public ClassNameColumnInfo(String name) {
    super(name);
  }

  @Nullable
  @Override
  public String valueOf(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
    return psiFieldWithComparisonPolicy.getPsiField().getName();
  }
}
