package chess;

import chess.ChessController.BoardDisplayListener;
import chess.piece.Piece;
import chess.player.Alliance;
import chess.player.Player;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import chess.view.*;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;

public class ChessView extends JFrame{
	
	private static final long serialVersionUID = 1L;

	private JPanel boardDisplay;
	private JPanel clock;
	private JPanel northPanelView;
	private JMenuBar menu;
	
	public static final String TITLE = "Chess";
	
	public ChessView(Board board, Player playerWhite, Player playerBlack){
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 377, 510);
		setResizable(true);

		menu = new ChessMenuBar();
		boardDisplay = new BoardView(board);
		northPanelView = new SideBarView(playerWhite, playerBlack);
		
		this.setJMenuBar(menu);
		this.add(boardDisplay);
		this.add(northPanelView, BorderLayout.EAST);
	}

	public int getTime(){
		return 0; //TODO: return parsed time
	}
	
	public void addMouseListener(BoardDisplayListener listener){
		boardDisplay.addMouseListener(listener);
	}
	
	public void paintSelectedPiece(Piece piece){
		if(piece != null)
			((BoardView) boardDisplay).setSelected(piece);
		updateView();
	}
	
	public void paintPossibleMoves(ArrayList<Move> moves){
		//if(piece != null)
		((BoardView) boardDisplay).setPossibleM(moves);
		updateView();
	}
	
	public void updateView(){
		boardDisplay.repaint();
	}
	
	public void updateMoveHistTable(){
		((SideBarView) northPanelView).updateTable();
	}
	
	public void displayTurnHighlight(){
		((SideBarView) northPanelView).updateHighlight();
	}
	
	public void displayErrorMessage(String errorMessage){
		//TODO: error message
	}
	
	public void displayGameOverMessage(Alliance winner, String message){
		System.out.println(winner.toString() + "Wins");
		String winMessage = winner.toString() + "Wins";
		JOptionPane.showMessageDialog(this,message, winMessage , JOptionPane.PLAIN_MESSAGE);
	}
}
