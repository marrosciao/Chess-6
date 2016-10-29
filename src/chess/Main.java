package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chess.gui.UserInterface;
import chess.piece.King;
import chess.piece.Piece;
import chess.player.AI;
import chess.player.Person;
import chess.player.Player;
import chess.player.Move;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

@SuppressWarnings("serial")
public class Main extends JFrame implements MouseListener, MouseMotionListener {

	private JPanel contentPane;
	public static boolean turn;
	public static Player player1;
	public static Player player2;
	public static Board board = new Board();
	public UserInterface ui;
	int mouseX, mouseY;
	public static ArrayList<Point> possibleM = new ArrayList<Point>();
	public static Point isSelected;

	public JLabel lblTurn;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Chess");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 388, 509);
		//setResizable(false);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu mnGame = new JMenu("Game");
		JMenuItem mntmNewGame = new JMenuItem("New Game");
		JMenuItem mntmExit = new JMenuItem("Exit");
		
		//add to frame
		setJMenuBar(menuBar);
		menuBar.add(mnGame);
		mnGame.add(mntmNewGame);
		mnGame.add(mntmExit);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel Meta = new JPanel();
		contentPane.add(Meta, BorderLayout.NORTH);
		Meta.setLayout(new BoxLayout(Meta, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		Meta.add(panel_3);
		
		JLabel lblNewLabel_3 = new JLabel(" ");
		panel_3.add(lblNewLabel_3);
		
		JPanel panel_1 = new JPanel();
		Meta.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel_1.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("    ");
		panel_1.add(lblNewLabel_1);
		
		JButton btnUndo = new JButton("Undo");
		panel_1.add(btnUndo);
		
		JLabel lblNewLabel = new JLabel("      ");
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		Meta.add(panel_2);
		
		JLabel lblNewLabel_2 = new JLabel(" ");
		panel_2.add(lblNewLabel_2);
		
		ui = new UserInterface();
		newGame(); /* Create new game */
		
		lblTurn = new JLabel(getPlayerTurn().toString() + "'s turn");
		panel.add(lblTurn);
		
		ui.setBackground(Color.yellow);
		ui.addMouseListener(this);
		ui.addMouseMotionListener(this);
		
		contentPane.add(ui, BorderLayout.CENTER);
		
		//New Game
		mntmNewGame.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		       newGame();
		    }
		});
		
		//Undo move
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				undoPieceMove();
				//undoPieceMove();
				ui.repaint();
			}
		});
		
		//Close Application
		mntmExit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e)
		    {
		       System.exit(0);
		    }
		});
	}
	
	public void newGame(){
		player1 = new Person(false);
		player2 = new AI(true);
		board = new Board();
		turn = false;
		System.out.println("New game");
		possibleM = null;
		isSelected = null;
		ui.repaint();
	}
	
	public Player getPlayerTurn(){
		if(turn){
			return player1;
		} else
			return player2;
	}
	
	public void undoPieceMove(){
		if(turn){
			if(player1.getMoveHistory().size() > 0){
				nextTurn();
			}
			player1.undoMove(board);
		} else {
			if(player2.getMoveHistory().size() > 0){
				nextTurn();
			}
			player2.undoMove(board);
		}
	}
	
	public void nextTurn(){
		turn = !turn;
		lblTurn.setText(getPlayerTurn().toString() + "'s turn");
		System.out.println("\n");
		if(turn == true){
			if(board.getKing(true).isCheckMate(board) && player2.getAllPossibleMoves(true, board).size() == 0){
				System.out.println("White Wins");
				String winner = "White wins!";
				JOptionPane.showMessageDialog(this,
						winner,
						"Congratulations",
					    JOptionPane.PLAIN_MESSAGE);
			} else {
				Move move = ((AI) player2).execute(board, 1, player2);
				//System.out.println(move);
				move.movePiece(board);
				player2.playerMoveHistory.add(move);
				nextTurn();
			}
		} else if (turn == false){
			
			if(board.getKing(false).isCheckMate(board) && player1.getAllPossibleMoves(false, board).size() == 0){
				System.out.println("Black Wins");
				String winner = "Black wins!";
				JOptionPane.showMessageDialog(this,
						winner,
						"Congratulations",
					    JOptionPane.PLAIN_MESSAGE);
			}
		}
		
		
	}
	
	public boolean getTurn(){
		return turn;
	}
	
	
	public static Player getOpponent(Player player){
		if(player == player2){
			return player1;
		} else {
			return player2;
		}
	}
	
	public static Player getCurrentPlayer(){
		if(turn == false){
			return player1;
		} else {
			return player2;
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int tileSize = ui.getTileSize();
		if(e.getX() < 8 * tileSize && e.getY() < 8 * tileSize){
			
			mouseX = e.getX();
			mouseY = e.getY();
			
			//button one left click
			if(e.getButton() == MouseEvent.BUTTON1){
				
				int x = (int)Math.floor(mouseX / tileSize);
				int y = (int)Math.floor(mouseY / tileSize);
				
				//only black selected if turn is true
				//get turn 
				boolean turnColour = getTurn();
				
				if(board.getTile(x, y).getPiece() != null && board.getTile(x, y).getPiece().getColour() == turnColour){
					isSelected = new Point(x, y);
					Piece piece = board.getTile(x, y).getPiece();
					if(turnColour == false && !(board.getTile(x, y).getPiece() instanceof King) && board.getKing(false).isCheck(board)){
						
						possibleM = piece.getPossibleMovesFiltered(board);
					} else if(turnColour == true && !(board.getTile(x, y).getPiece() instanceof King) && board.getKing(true).isCheck(board) ){
						
						possibleM = piece.getPossibleMovesFiltered(board);
					}  else {
						if(!(board.getTile(x, y).getPiece() instanceof King)){
							possibleM = piece.putsKingInCheck(piece, board);
						} else {
							
							possibleM = piece.getPossibleMoves(board);
							
						}
					}
					
				} else {
					if(isSelected != null && possibleM != null){
						for(Point p : possibleM){
							if(p.x == x &&  p.y == y){
								Piece piece = board.getTile(isSelected.x, isSelected.y).getPiece();
								if(turnColour == false){
									player1.movePiece(x, y, board, piece);
								} else {
									player2.movePiece(x, y, board, piece);
								}
								//piece.move(x, y, board);
								isSelected = null;
								possibleM = null;
								ui.repaint();
								nextTurn();
							}
						}
					}
					isSelected = null;
					possibleM = null;
				}
				
				ui.repaint();
			}
			
		} 
	}
	
	//Because this class implements an interface it must have these methods implemented
	@Override
	public void mouseDragged(MouseEvent arg0) {
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
