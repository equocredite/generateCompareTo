package com.equocredite.GenerateCompareTo.bindings;

import com.equocredite.GenerateCompareTo.PsiComparabilityUtil;
import com.equocredite.GenerateCompareTo.PsiFieldWithSortOrder;
import com.equocredite.GenerateCompareTo.ui.NullableColumnInfoCellRendererEditor;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.util.ui.ColumnInfo;
import org.jetbrains.annotations.Nullable;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class NullableColumnInfo extends ColumnInfo<PsiFieldWithSortOrder, Boolean> {
    private final NullableColumnInfoCellRendererEditor rendererEditor =
            new NullableColumnInfoCellRendererEditor();

    public NullableColumnInfo(String name) {
        super(name);
    }

    @Override
    public Boolean valueOf(PsiFieldWithSortOrder psiFieldWithSortOrder) {
        return psiFieldWithSortOrder.isNullable();
    }

    @Override
    public void setValue(PsiFieldWithSortOrder psiFieldWithSortOrder, Boolean value) {
        psiFieldWithSortOrder.setNullable(value);
    }

    @Override
    public TableCellRenderer getRenderer(PsiFieldWithSortOrder psiFieldWithSortOrder) {
        return rendererEditor;
    }

    @Override
    public boolean isCellEditable(PsiFieldWithSortOrder psiFieldWithSortOrder) {
        return !(psiFieldWithSortOrder.getPsiField().getType() instanceof PsiPrimitiveType);
    }

    @Override
    public TableCellEditor getEditor(PsiFieldWithSortOrder psiFieldWithSortOrder) {
        return rendererEditor;
    }

}
