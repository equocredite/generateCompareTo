package com.equocredite.generateCompareTo.bindings;

import com.equocredite.generateCompareTo.PsiFieldWithComparisonPolicy;
import com.equocredite.generateCompareTo.ui.DoubleButtonCellRendererEditor;
import com.intellij.util.ui.ColumnInfo;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class NullIsLeastColumnInfo extends ColumnInfo<PsiFieldWithComparisonPolicy, Boolean> {
    private final DoubleButtonCellRendererEditor rendererEditor =
            new DoubleButtonCellRendererEditor("min", "max");

    public NullIsLeastColumnInfo(String name) {
        super(name);
    }

    @Override
    public Boolean valueOf(PsiFieldWithComparisonPolicy policy) {
        return policy.isNullLeast();
    }

    @Override
    public void setValue(PsiFieldWithComparisonPolicy policy, Boolean value) {
        policy.setNullLeast(value);
    }

    @Override
    public TableCellRenderer getRenderer(PsiFieldWithComparisonPolicy policy) {
        return rendererEditor;
    }

    @Override
    public boolean isCellEditable(PsiFieldWithComparisonPolicy policy) {
        return (policy.isNullLeast() != null);
    }

    @Override
    public TableCellEditor getEditor(PsiFieldWithComparisonPolicy policy) {
        return rendererEditor;
    }
}
