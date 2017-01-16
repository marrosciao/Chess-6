package chess.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

public class TableDefaultCellRenderer  extends DefaultTableCellRenderer{

	private static final long serialVersionUID = 1L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JComponent jc = (JComponent)c;

        if(column == 0){
        	formatCell(c, "Tahoma", Font.PLAIN, 13, SwingUtilities.CENTER, new Color(220,220,220));
        	
        } else if (column == 2){
        	formatCell(c, null, Font.BOLD, 15, SwingUtilities.LEFT, new Color(238,238,238));
        	
        } else {
        	formatCell(c, null, Font.BOLD, 15, SwingUtilities.LEFT, new Color(238,238,238));
        	jc.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0)); //added some left padding
        }
        return c;
    }
	   
	
	private void formatCell(Component c, String font, int style, int size, int allignment, Color background){
		c.setForeground(new Color(112,112,112));
    	c.setFont(new Font(null, style, size));
    	c.setBackground(background);
    	setHorizontalAlignment(allignment);
	}
}
