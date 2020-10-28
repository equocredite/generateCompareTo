package com.equocredite.generateCompareTo;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Tuple describing the PsiField and whether or not the field should be sorted in ascending or descending order
 */
public class PsiFieldWithComparisonPolicy {
  @NotNull
  private final PsiField psiField;
  private boolean ascending;
  private Boolean nullable;

  public PsiFieldWithComparisonPolicy(@NotNull PsiField psiField, boolean ascending, Boolean nullable) {
    this.psiField = psiField;
    this.ascending = ascending;
    this.nullable = nullable;
  }

  public static PsiFieldWithComparisonPolicy constructDefaultPolicy(@NotNull PsiField psiField) {
    if (!PsiUtil.fieldIsStatic(psiField)) {
      if (PsiUtil.isPrimitiveComparable(psiField.getType())) {
        return new PsiFieldWithComparisonPolicy(psiField, true, null);
      } else if (PsiUtil.psiTypeImplementsComparable(psiField.getType())) {
        return new PsiFieldWithComparisonPolicy(psiField, true, !PsiUtil.fieldAnnotatedNotNull(psiField));
      }
    }
    return null;
  }

  public static List<PsiFieldWithComparisonPolicy> constructDefaultPoliciesForFields(@NotNull PsiClass psiClass) {
    PsiField[] psiFields = psiClass.getFields();
    List<PsiFieldWithComparisonPolicy> policies = new ArrayList<>(psiFields.length);
    for (PsiField psiField : psiFields) {
      PsiFieldWithComparisonPolicy policy = constructDefaultPolicy(psiField);
      if (policy != null) {
        policies.add(policy);
      }
    }
    return policies;
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

    PsiFieldWithComparisonPolicy that = (PsiFieldWithComparisonPolicy) o;

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

