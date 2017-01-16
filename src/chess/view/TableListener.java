package chess.view;

import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
/**
 * //http://stackoverflow.com/a/14547911/7066174
 * @author
 *
 */
public class TableListener implements TableModelListener{
	
	private JTable tbl_table;
	
	public TableListener(final JTable tbl_table){
		this.tbl_table = tbl_table;
	}
	
	@Override
    public void tableChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.INSERT) {
            invokeScroll();
        }
    }

    protected void invokeScroll() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                int last = tbl_table.getModel().getRowCount() - 1;
                Rectangle r = tbl_table.getCellRect(last, 0, true);
                tbl_table.scrollRectToVisible(r);
            }
        });
    }
}
