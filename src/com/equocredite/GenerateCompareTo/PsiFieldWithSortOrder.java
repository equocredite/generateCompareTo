package com.equocredite.GenerateCompareTo;

import com.intellij.psi.PsiField;
import org.jetbrains.annotations.NotNull;

/**
 * Tuple describing the PsiField and whether or not the field should be sorted in ascending or descending order
 */
public class PsiFieldWithSortOrder {
  @NotNull
  private final PsiField psiField;
  private boolean ascending;
  private Boolean nullable;

  public PsiFieldWithSortOrder(@NotNull PsiField psiField, boolean ascending, Boolean nullable) {
    this.psiField = psiField;
    this.ascending = ascending;
    this.nullable = nullable;
  }

  public PsiField getPsiField() {
    return psiField;
  }

  public boolean isAscending() {
    return ascending;
  }

  public Boolean isNullable() {
    return nullable;
  }

  public void setAscending(boolean ascending) {
    this.ascending = ascending;
  }

  public void setNullable(Boolean nullable) {
    this.nullable = nullable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PsiFieldWithSortOrder that = (PsiFieldWithSortOrder) o;

    if (ascending != that.ascending) return false;
    if (nullable != that.nullable) return false;
    return psiField.equals(that.psiField);
  }

  @Override
  public int hashCode() {
    int result = psiField.hashCode();
    result = 31 * result + (ascending ? 1 : 0);
    result = 31 * result + (nullable ? 1 : 0);
    return result;
  }
}

