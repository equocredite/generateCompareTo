package com.equocredite.GenerateCompareTo;

import com.intellij.psi.*;
import com.intellij.openapi.util.text.StringUtil;

public class PsiUtil {
    public static boolean fieldIsStatic(PsiField field) {
        return field.getModifierList().hasModifierProperty(PsiModifier.STATIC);
    }

    private static final String[] NOTNULL_ANNOTATIONS = {"notnull", "nonnull"};

    public static boolean fieldAnnotatedNotNull(PsiField field) {
        PsiModifierList modifierList = field.getModifierList();
        if (modifierList != null) {
            for (PsiAnnotation psiAnnotation : modifierList.getAnnotations()) {
                String shortName = StringUtil.getShortName(psiAnnotation.getQualifiedName()).toLowerCase();
                for (String annotation : NOTNULL_ANNOTATIONS) {
                    if (annotation.equals(shortName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

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
