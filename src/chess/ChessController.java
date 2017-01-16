package chess;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import chess.piece.King;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.player.Alliance;
import chess.player.Player;
import chess.view.BoardView;
import chess.view.Tile;
public class ChessController {
	
	private Game game;
	private ChessView view;
	
	private Piece selected;
	private ArrayList<Move> possibleMoves;
	
	//refactor
	public ChessController(Game game, ChessView view){
		this.view = view;
		this.game = game;
		
		this.view.addMouseListener(new BoardDisplayListener());
	}
	
	public void newGame(){
		Board b = new Board();
		Game g = new Game(null, null, b);
		System.out.println("New game");
		possibleMoves = null;
		selected = null;
		view.updateView();
	}
	
	public class BoardDisplayListener implements MouseListener, MouseMotionListener{
	
		private void selectPiece(Board board, int x, int y){
			selected = board.getPiece(x, y);
			if(selected instanceof King)
				possibleMoves = selected.getPossibleMoves(board);
			else 
				possibleMoves = Game.getPlayer(Game.getTurn()).getDefensiveMoves(board, selected.getPossibleMoves(board));
			view.paintSelectedPiece(selected);
			view.paintPossibleMoves(possibleMoves);
			
		}
		
		private void movePieceToSquare(Board board, Alliance turn, int x, int y){
			Player playerWhite = Game.getPlayer(Alliance.WHITE);
			Player playerBlack = Game.getPlayer(Alliance.BLACK);
			
			if(selected != null && possibleMoves != null){
				for(Move move : possibleMoves){
					if(move.getMovePosition().equals(new Point(x,y))){
						if(turn == Alliance.WHITE){
							playerWhite.movePiece(board, move);
							playerWhite.addMoveHistory(move);
						} else {
							playerBlack.movePiece(board, move);
							playerBlack.addMoveHistory(move);
						}
						
						if(selected instanceof Pawn)
							((Pawn) selected).promote(board);
							
						selected = null;
						possibleMoves = null;
						game.nexTurn();
						view.displayTurnHighlight();
						view.updateMoveHistTable();
					}
				}
			}
		}
		
		private void handleMouseClick(Board board, int x, int y, Alliance turn){
			if(board.isOccupied(x, y) && board.getPiece(x, y).getAllegiance() == turn){
				selectPiece(board, x, y);
			} else {
				movePieceToSquare(board, turn, x, y);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			int tileSize = Tile.TILE_SIZE;
			if(e.getX() < Board.BOARD_ROW * tileSize && e.getY() < Board.BOARD_COLUMN * tileSize){
				
				if(e.getButton() == MouseEvent.BUTTON1){
					
					int x = (int)Math.floor(e.getX() / tileSize);
					int y = (int)Math.floor(e.getY() / tileSize);
					
					Board board = game.getBoard();
					Alliance turn = Game.getTurn();
					
					handleMouseClick(board, x, y, turn);
					
					view.updateView();
				}
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
		}
		@Override
		public void mouseMoved(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	
}