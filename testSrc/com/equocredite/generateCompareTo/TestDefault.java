package com.equocredite.generateCompareTo;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;


import java.io.File;
import java.util.List;

public class TestDefault extends TestWithMockJdk {
    private void compareFiles(String testName) {
        myFixture.configureByFile(testName + "/before.java");
        PsiElement elementAtCaret = myFixture.getFile().findElementAt(myFixture.getCaretOffset());
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAtCaret, PsiClass.class);
        List<PsiFieldWithComparisonPolicy> fields = PsiFieldWithComparisonPolicy.constructDefaultPoliciesForFields(psiClass);
        new GenerateCompareToAction().generate(psiClass, fields);
        myFixture.checkResultByFile(testName + "/after.java");
    }

    public void testNullableString() {
        compareFiles(getTestName(false));
    }

    public void testNotNullString() {
        compareFiles(getTestName(false));
    }

    public void testCheckComparability() {
        compareFiles(getTestName(false));
    }
}
