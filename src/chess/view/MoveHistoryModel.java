package chess.view;

import javax.swing.table.DefaultTableModel;

/**
 * http://stackoverflow.com/a/3875829/7066174
 * @author
 *
 */
public class MoveHistoryModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;

    @Override
    public int getColumnCount() {
        return 3;
    }
    
   
    
}

