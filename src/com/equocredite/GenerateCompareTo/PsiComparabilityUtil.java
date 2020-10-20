package com.equocredite.GenerateCompareTo;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;

public class PsiComparabilityUtil {
    private static final PsiPrimitiveType[] primitiveComparableTypes = {
            PsiPrimitiveType.CHAR,
            PsiPrimitiveType.BYTE,
            PsiPrimitiveType.SHORT,
            PsiPrimitiveType.INT,
            PsiPrimitiveType.LONG,
            PsiPrimitiveType.FLOAT,
            PsiPrimitiveType.DOUBLE
    };

    public static boolean isPrimitiveComparable(PsiType type) {
        for (PsiPrimitiveType primitiveType : primitiveComparableTypes) {
            if (primitiveType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static boolean implementsComparable(PsiClass psiClass) {
        for (PsiClassType implementsListType : psiClass.getImplementsListTypes()) {
            PsiClass resolvedClass = implementsListType.resolve();
            if (resolvedClass != null && "java.lang.Comparable".equals(resolvedClass.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isComparable(PsiType type) {
        if (isPrimitiveComparable(type)) {
            return true;
        }
        if (type instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType)type;
            return implementsComparable(psiClassType.resolve());
        }
        return false;
    }
}
