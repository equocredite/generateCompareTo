package com.equocredite.GenerateCompareTo;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;

public class PsiComparabilityUtil {
    private static final PsiPrimitiveType[] PRIMITIVE_COMPARABLE_TYPES = {
            PsiPrimitiveType.CHAR,
            PsiPrimitiveType.BYTE,
            PsiPrimitiveType.SHORT,
            PsiPrimitiveType.INT,
            PsiPrimitiveType.LONG,
            PsiPrimitiveType.FLOAT,
            PsiPrimitiveType.DOUBLE
    };

    public static boolean isPrimitiveComparable(PsiType type) {
        for (PsiPrimitiveType primitiveType : PRIMITIVE_COMPARABLE_TYPES) {
            if (primitiveType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean psiClassImplementsComparable(PsiClass psiClass) {
        for (PsiClassType implementsListType : psiClass.getImplementsListTypes()) {
            PsiClass resolvedClass = implementsListType.resolve();
            if (resolvedClass != null && "java.lang.Comparable".equals(resolvedClass.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean psiTypeImplementsComparable(PsiType type) {
        if (type instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType)type;
            return psiClassImplementsComparable(psiClassType.resolve());
        }
        return false;
    }
}
