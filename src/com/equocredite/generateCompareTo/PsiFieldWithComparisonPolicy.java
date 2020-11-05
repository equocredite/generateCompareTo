package com.equocredite.generateCompareTo;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PsiFieldWithComparisonPolicy {
  @NotNull
  private final PsiField psiField;
  private boolean ascending;
  private Boolean nullable;
  private Boolean nullIsLeast;

  private final PsiMethod getter;
  private Boolean useGetterOption;

  public PsiFieldWithComparisonPolicy(@NotNull PsiField psiField, boolean ascending, Boolean nullable, Boolean nullIsLeast,
                                      PsiMethod getter, Boolean useGetterOption) {
    this.psiField = psiField;
    this.ascending = ascending;
    this.nullable = nullable;
    this.nullIsLeast = nullIsLeast;
    this.getter = getter;
    this.useGetterOption = useGetterOption;
  }

  public static List<PsiFieldWithComparisonPolicy> constructDefaultPoliciesForFields(@NotNull PsiClass psiClass) {
    Map<PsiField, PsiMethod> getters = PsiUtil.getGetters(psiClass);
    PsiField[] psiFields = psiClass.getFields();
    List<PsiFieldWithComparisonPolicy> policies = new ArrayList<>(psiFields.length);
    for (PsiField psiField : psiFields) {
      if (PsiUtil.fieldIsStatic(psiField)) {
        continue;
      }
      PsiMethod getter = getters.get(psiField);
      Boolean useGetter = (getter == null ? null : true);
      if (PsiUtil.isPrimitiveComparable(psiField.getType())) {
        policies.add(new PsiFieldWithComparisonPolicy(psiField, true, null, null, getter, useGetter));
      } else if (PsiUtil.psiTypeImplementsComparable(psiField.getType())) {
        policies.add(new PsiFieldWithComparisonPolicy(psiField, true,
                !PsiUtil.fieldAnnotatedNotNull(psiField), true, getter, useGetter));
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

  public Boolean isNullLeast() {
    return nullIsLeast;
  }

  public PsiMethod getGetter() {
    return getter;
  }

  public Boolean getUseGetterOption() {
    return useGetterOption;
  }

  public void setAscending(boolean ascending) {
    this.ascending = ascending;
  }

  public void setNullable(Boolean nullable) {
    this.nullable = nullable;
  }

  public void setNullLeast(Boolean nullIsLeast) {
    this.nullIsLeast = nullIsLeast;
  }

  public void setUseGetterOption(Boolean useGetterOption) {
    this.useGetterOption = useGetterOption;
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

