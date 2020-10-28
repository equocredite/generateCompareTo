package com.equocredite.generateCompareTo;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCustom extends TestWithMockJdk {
    private void compareFiles(String testName, List<String> fieldNames, List<Boolean> ascendingOptions, List<Boolean> nullableOptions) {
        myFixture.configureByFile(testName + "/before.java");
        PsiElement elementAtCaret = myFixture.getFile().findElementAt(myFixture.getCaretOffset());
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAtCaret, PsiClass.class);
        List<PsiFieldWithComparisonPolicy> policies = new ArrayList<>();
        for (int i = 0; i < fieldNames.size(); ++i) {
            String fieldName = fieldNames.get(i);
            Boolean ascending = ascendingOptions.get(i);
            Boolean nullable = nullableOptions.get(i);
            policies.add(new PsiFieldWithComparisonPolicy(psiClass.findFieldByName(fieldName, false), ascending, nullable));
        }
        new GenerateCompareToAction().generate(psiClass, policies);
        myFixture.checkResultByFile(testName + "/after.java");
    }

    public void testPrimitiveDescending() {
        compareFiles(
                getTestName(false),
                new ArrayList<String>(Arrays.asList("d", "x")),
                new ArrayList<Boolean>(Arrays.asList(false, false)),
                new ArrayList<Boolean>(Arrays.asList(null, null))
        );
    }
}
