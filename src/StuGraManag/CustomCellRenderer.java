package StuGraManag;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        Component cellComponent = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, column);


        if (row % 2 == 0) {
            cellComponent.setBackground(Color.WHITE);
        } else {
            cellComponent.setBackground(Color.LIGHT_GRAY);
        }
        cellComponent.setForeground(Color.BLACK);

        return cellComponent;
    }
}
