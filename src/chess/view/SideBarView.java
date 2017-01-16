package chess.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import chess.Game;
import chess.player.Alliance;
import chess.player.Player;

import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;



public class SideBarView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	private JPanel pnl_playerOneMeta;
	private JPanel pnl_movehistory;
	private JPanel pnl_playerTwoMeta;
	private Player playerWhite;
	private Player playerBlack;
	private JTable tbl_table;
	private JScrollPane src_scrollPane;
	private TableDefaultCellRenderer tableRenderer;
	private MoveHistoryModel moveHistoryModel;
	private TableListener tableListener;
	private JTableHeader header;
	private String[] headerNames = {"#",
            						"White",
            						"Black"};
	
	
	public SideBarView(Player playerWhite, Player playerBlack){
		this.playerWhite = playerWhite;
		this.playerBlack = playerBlack;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(150, 300));
		this.setBorder(BorderFactory.createEmptyBorder(0,4,4,4));
		
		pnl_playerOneMeta = new MetaView(playerWhite, playerWhite.getTime());
		pnl_playerTwoMeta = new MetaView(playerBlack, playerBlack.getTime());
		pnl_movehistory = new JPanel();
		tbl_table = new JTable();
		tableListener = new TableListener(tbl_table);
		moveHistoryModel = new MoveHistoryModel();
		tableRenderer = new TableDefaultCellRenderer();
		
		init();
		
		this.add(pnl_playerTwoMeta);
		this.add(src_scrollPane);
		this.add(pnl_playerOneMeta);
		
	}
	
	private void init(){
		pnl_movehistory.add(tbl_table.getTableHeader(), BorderLayout.PAGE_START);
		pnl_movehistory.setPreferredSize(new Dimension(155, 150));
		pnl_movehistory.setBorder(BorderFactory.createEmptyBorder()); 
		pnl_movehistory.add(tbl_table, BorderLayout.CENTER);
		
		moveHistoryModel.addTableModelListener(tableListener);
		moveHistoryModel.setColumnIdentifiers(headerNames);
		
		for(int i = 0; i < 12; i++){
			moveHistoryModel.addRow(new Object[] { "", "", ""});
		}
		
		tbl_table.setModel(moveHistoryModel);
		tbl_table.setDefaultRenderer(Object.class, tableRenderer);
		tbl_table.setBorder(BorderFactory.createEmptyBorder());
		tbl_table.setIntercellSpacing(new Dimension(0, 0));
		tbl_table.setShowGrid(false);
		
		header = tbl_table.getTableHeader();
		header.setDefaultRenderer(tableRenderer);
		
		TableColumn column = null;
		for (int i = 0; i < 3; i++) {
		    column = tbl_table.getColumnModel().getColumn(i);
		    if(i == 0){
		    	column.setPreferredWidth(10);
		    } else {
		    	column.setPreferredWidth(50);
		    }
		}
		
		src_scrollPane = new JScrollPane(tbl_table, 
							JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
							JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		src_scrollPane.setViewportBorder(BorderFactory.createLineBorder(new Color(207,207,207),1));
		src_scrollPane.setBorder(BorderFactory.createEmptyBorder());
		src_scrollPane.setPreferredSize(new Dimension(0, 154));
		
	}
	
	public void updateTable(){
		int count1 = playerWhite.getMoveHistory().size();
		int count2 = playerBlack.getMoveHistory().size();
		Alliance turn = Game.getTurn();
		
		if(turn == Alliance.BLACK && count1 != 0){
			if(tbl_table.getRowCount() < count1){
				moveHistoryModel.addRow(new Object[] { count1, playerWhite.getMoveHistory().get(count1 - 1).toString(), ""});
			}else {
				moveHistoryModel.setValueAt(playerWhite.getMoveHistory().get(count1 - 1).toString(), count1 - 1, 1);
				moveHistoryModel.setValueAt(count1, count1 - 1, 0);
			}
			
		} else if (turn == Alliance.WHITE && count2 != 0){
			moveHistoryModel.setValueAt(playerBlack.getMoveHistory().get(count2 - 1), count1 - 1, 2);
		}
		
		this.repaint();
	}
	
	public void updateHighlight(){
		this.pnl_playerOneMeta.repaint();
		this.pnl_playerTwoMeta.repaint();
	}
}
