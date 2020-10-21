package com.equocredite.GenerateCompareTo;

import com.equocredite.GenerateCompareTo.ui.GenerateDialog;
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
    GenerateDialog dlg = new GenerateDialog(psiClass, dialogTitle);
    dlg.show();
    if (dlg.isOK()) {
      generate(psiClass, dlg.getFields());
    }
  }

  public void generate(final PsiClass psiClass, final List<PsiFieldWithSortOrder> fields) {
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

    if (!PsiComparabilityUtil.psiClassImplementsComparable(psiClass)) {
      String comparableText = "Comparable<" + psiClass.getName() + ">";
      PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
      PsiJavaCodeReferenceElement referenceElement = elementFactory.createReferenceFromText(comparableText, psiClass);
      psiClass.getImplementsList().add(referenceElement);
    }
  }

  private static final String[][] COMPARISON_ORDER = {{"<", ">"}, {">", "<"}};

  /**
   * Generates the compare to method for the class
   * @param psiClass
   * @param fields
   */
  private void generateCompareTo(PsiClass psiClass, List<PsiFieldWithSortOrder> fields) {
    StringBuilder builder = new StringBuilder("@Override\n");
    builder.append("public int compareTo(").append(psiClass.getName()).append(" that) { \n");

    for (int i = 0; i < fields.size(); i++) {
      PsiFieldWithSortOrder psiFieldWithSortOrder = fields.get(i);
      PsiField field = psiFieldWithSortOrder.getPsiField();
      boolean ascending = psiFieldWithSortOrder.isAscending();
      int index = ascending ? 0 : 1;
      String[] comparisons = COMPARISON_ORDER[index];
      PsiType type = field.getType();

      if (i != 0) {
        builder.append("\n");
      }

      if (PsiComparabilityUtil.isPrimitiveComparable(type)) {
        builder.append("if (this." + field.getName() + " " + comparisons[0] + " that." + field.getName() + ")" + " {\n");
        builder.append("  return -1;\n");
        builder.append("} else if (this." + field.getName() + " " + comparisons[1] + " that." + field.getName()+ ") {\n");
        builder.append("  return 1;\n");
        builder.append("}\n");
      } else {
        if (psiFieldWithSortOrder.isNullable()) {
          builder.append("if (this." + field.getName() + " == null && that." + field.getName() + " == null) {\n");
          builder.append("  // pass\n");
          builder.append("} else if (this." + field.getName() + " == null && that." + field.getName() + " != null) {\n");
          builder.append("  return " + (ascending ? "-1;\n" : "1;\n"));
          builder.append("} else if (this." + field.getName() + " != null && that." + field.getName() + " == null) {\n");
          builder.append("  return " + (ascending ? "1;\n} else " : "-1;\n} else "));
        }
        builder.append("if (this." + field.getName() + ".compareTo(that." + field.getName() + ") " + comparisons[0] + " 0) {\n");
        builder.append("  return -1;\n");
        builder.append("} else if (this." + field.getName() + ".compareTo(that." + field.getName() + ") " + comparisons[1] + " 0) {\n");
        builder.append("  return 1;\n");
        builder.append("}\n");
      }
    }
    builder.append("return 0;\n");
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