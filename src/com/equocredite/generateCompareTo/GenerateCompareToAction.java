package com.equocredite.generateCompareTo;

import com.equocredite.generateCompareTo.ui.GenerateDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.List;

/**
 * The class for generating a compareTo dialog and implementing the sources
 * This class was helpfully built using http://blogs.jetbrains.com/idea/2012/12/webinar-recording-live-coding-a-plugin-from-scratch/ by Dmitry Jemerov
 * and a similar example at https://github.com/seanlandsman/guavagenerators.git by Sean Landsman
 *
 * Kudos to these gentlemen!
 */
public class GenerateCompareToAction extends AnAction {
  private final String dialogTitle;

  public GenerateCompareToAction() {
    this("compareTo()", "Generate compareTo()");
  }

  public GenerateCompareToAction(String text, String dialogTitle) {
    super(text);
    this.dialogTitle = dialogTitle;
  }

  public void actionPerformed(AnActionEvent e) {
    PsiClass psiClass = getPsiClassFromContext(e);
    GenerateDialog dlg = new GenerateDialog(psiClass.getProject(),
            PsiFieldWithComparisonPolicy.constructDefaultPoliciesForFields(psiClass), dialogTitle);
    dlg.show();
    if (dlg.isOK()) {
      generate(psiClass, dlg.getFields());
    }
  }

  public void generate(final PsiClass psiClass, final List<PsiFieldWithComparisonPolicy> fields) {
    new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()) {
      @Override
      protected void run() throws Throwable {
        generateCompareTo(psiClass, fields);
        generateImplementsComparable(psiClass);
      }
    }.execute();
  }

  /**
   * Modifies the class to add an implements Comparable<T> definition only if it is necessary
   * @param psiClass
   */
  private void generateImplementsComparable(PsiClass psiClass) {

    if (!PsiUtil.psiClassImplementsComparable(psiClass)) {
      String comparableText = "Comparable<" + psiClass.getName() + ">";
      PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
      PsiJavaCodeReferenceElement referenceElement = elementFactory.createReferenceFromText(comparableText, psiClass);
      psiClass.getImplementsList().add(referenceElement);
    }
  }

  private static final String[] COMPARISON_ORDER = {"<", ">"};

  /**
   * Generates the compare to method for the class
   * @param psiClass
   * @param fields
   */
  private void generateCompareTo(PsiClass psiClass, List<PsiFieldWithComparisonPolicy> fields) {
    StringBuilder builder = new StringBuilder("@Override\n");
    builder.append("public int compareTo(").append(psiClass.getName()).append(" that) { \n");

    for (int i = 0; i < fields.size(); i++) {
      PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy = fields.get(i);
      PsiField field = psiFieldWithComparisonPolicy.getPsiField();
      boolean ascending = psiFieldWithComparisonPolicy.isAscending();
      int index = ascending ? 0 : 1;
      PsiType type = field.getType();

      if (i != 0) {
        builder.append("\n");
      }

      if (PsiUtil.isPrimitiveComparable(type)) {
        builder.append("if (this." + field.getName() + " != " + "that." + field.getName() + ") {\n");
        // possible overflow if subtract
        builder.append("\treturn (this." + field.getName() + " " + COMPARISON_ORDER[index] + " that." + field.getName() + " ? -1 : 1);\n");
        builder.append("}\n");
      } else {
        if (psiFieldWithComparisonPolicy.isNullable()) {
          builder.append("if (this." + field.getName() + " == null && that." + field.getName() + " == null) {\n");
          builder.append("  // pass\n");
          builder.append("} else if (this." + field.getName() + " == null && that." + field.getName() + " != null) {\n");
          builder.append("  return " + (ascending ? "-1;\n" : "1;\n"));
          builder.append("} else if (this." + field.getName() + " != null && that." + field.getName() + " == null) {\n");
          builder.append("  return " + (ascending ? "1;\n} else " : "-1;\n} else "));
        }
        builder.append("if (this." + field.getName() + ".compareTo(that." + field.getName() + ") != 0) {\n");
        builder.append("\treturn (this." + field.getName() + ".compareTo(that." + field.getName() + ") " + COMPARISON_ORDER[index] + " 0 ? -1 : 1);\n");
        builder.append("}\n");
      }
    }
    builder.append("\nreturn 0;\n");
    builder.append("}\n");

    setNewMethod(psiClass, builder.toString(), "compareTo");
  }

  protected void setNewMethod(PsiClass psiClass, String newMethodBody, String methodName) {
    PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
    PsiMethod newMethod = elementFactory.createMethodFromText(newMethodBody, psiClass);
    PsiElement method = addOrReplaceMethod(psiClass, newMethod, methodName);
    JavaCodeStyleManager.getInstance(psiClass.getProject()).shortenClassReferences(method);
  }

  protected PsiElement addOrReplaceMethod(PsiClass psiClass, PsiMethod newMethod, String methodName) {
    PsiMethod existingMethod = findMethod(psiClass, methodName);

    PsiElement method;
    if (existingMethod != null) {
      method = existingMethod.replace(newMethod);
    } else {
      method = psiClass.add(newMethod);
    }
    return method;
  }

  protected PsiMethod findMethod(PsiClass psiClass, String methodName) {
    PsiMethod[] allMethods = psiClass.getAllMethods();
    for (PsiMethod method : allMethods) {
      if (psiClass.getName().equals(method.getContainingClass().getName()) && methodName.equals(method.getName())) {
        return method;
      }
    }
    return null;
  }

  @Override
  public void update(AnActionEvent e) {
    PsiClass psiClass = getPsiClassFromContext(e);
    e.getPresentation().setEnabled(psiClass != null);
  }

  private PsiClass getPsiClassFromContext(AnActionEvent e) {
    PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);
    Editor editor = e.getData(PlatformDataKeys.EDITOR);
    if (psiFile == null || editor == null) {
      return null;
    }
    int offset = editor.getCaretModel().getOffset();
    PsiElement elementAt = psiFile.findElementAt(offset);
    return PsiTreeUtil.getParentOfType(elementAt, PsiClass.class);
  }
}
