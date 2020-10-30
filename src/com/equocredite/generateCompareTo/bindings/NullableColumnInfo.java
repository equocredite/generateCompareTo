package com.equocredite.generateCompareTo.bindings;

import com.equocredite.generateCompareTo.PsiFieldWithComparisonPolicy;
import com.equocredite.generateCompareTo.ui.OptionalCheckBoxRendererEditor;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.util.ui.ColumnInfo;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class NullableColumnInfo extends ColumnInfo<PsiFieldWithComparisonPolicy, Boolean> {
    private final OptionalCheckBoxRendererEditor rendererEditor = new OptionalCheckBoxRendererEditor();

    public NullableColumnInfo(String name) {
        super(name);
    }

    @Override
    public Boolean valueOf(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
        return psiFieldWithComparisonPolicy.isNullable();
    }

    @Override
    public void setValue(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy, Boolean value) {
        psiFieldWithComparisonPolicy.setNullable(value);
    }

    @Override
    public TableCellRenderer getRenderer(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
        return rendererEditor;
    }

    @Override
    public boolean isCellEditable(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
        return !(psiFieldWithComparisonPolicy.getPsiField().getType() instanceof PsiPrimitiveType);
    }

    @Override
    public TableCellEditor getEditor(PsiFieldWithComparisonPolicy psiFieldWithComparisonPolicy) {
        return rendererEditor;
    }

}
