package chess.player;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;
import chess.Game;
import chess.Move;
import chess.piece.*;
import chess.time.Time;

/**
 * @author sHeston
 *
 */

public class Player {
	
	private Alliance allegiance;
	private ArrayList<Move> moveHistory;
	private ArrayList<Piece> graveyard;
	
	private Time time;
	public static int MAX_TIME = 300; //300 secs = 5 mins
	
	private King king;
	
	public Player(Alliance alliance){
		this.allegiance = alliance;
		this.moveHistory = new ArrayList<Move>();
		time = new Time(MAX_TIME);
	}
	
	public void startClock(){
		time.start();
	}
	
	public void pauseClock(){
		time.pause();
	}
	
	public void resumeClock(){
		time.resume();
	}
	
	public Time getTime(){
		return time;
	}
	
	private Player getOpponent(){
		return Game.getPlayer(this.allegiance.next());
	}
	
	public ArrayList<Piece> getGraveyard(){
		return this.graveyard;
	}
	
	public ArrayList<Move> getMoveHistory(){
		return moveHistory;
	}
	
	public void addMoveHistory(Move move){
		this.moveHistory.add(move);
	}
	
	/**
	 * A Check is a condition that occurs when a player's king
	 * is under threat of a capture on their opponents next turn.
	 * A player must get out of check if possible, by either 
	 * interposing a piece between the threatening piece and the king,
	 * capturing the threatening piece or moving a King to a square where
	 * it is no longer in check.
	 * </p>
	 * If there are still remaining legal moves the 
	 * player can make (including other pieces) and if their king 
	 * is in check then return <code> true </code>.
	 * </p>
	 * *note: that <code> tmp = opponent.getAllAvailableMoves(board); </code>
	 * might be null.
	 * 
	 * @return 	<code> true </code> if the players king
	 * 			is in check
	 * @see 	https://en.wikipedia.org/wiki/Check_(chess)
	 */
	public boolean isCheck(Board board){
		if(getCheckingPieces(board).size() >= 1) /*1 or more threatening piece(s)*/
			return true;
		return false;
	}
	
	/**
	 * A Check-mate occurs when the player cannot move out of check. if this
	 * is <code> true </code> then the player loses. Players cannot make any
	 * move that puts their own King in check or Check-mate.
	 * 
	 * @param 	The current <code> board </code>
	 * @return	<code> true </code> if the king is unable to move
	 * 			and the player has no available/legal moves.
	 * @see 	https://en.wikipedia.org/wiki/Check_(chess)
	 */
	public boolean isCheckMate(Board board){
		if((isCheck(board) && getAllAvailableMoves(board).size() == 0))
			return true;
		return false;
	}
	
	//
	/**
	 * A stale-mate occurs when one of the conditions are met resulting
	 * in a draw. These rules are 
	 * <ul>
	 * <li> <code> isMutualAgreement() </code> - when both player agree to draw
	 * <li>	<code> isPerpetualCheck() </code> - when the same position occurs 
	 * 		three times with the same player to move
	 * <li>	<code> isFiftyMoveRule() </code> - when the last fifty successive moves
	 * 		mode by both players contain no capture or pawn move
	 * <li> <code> isInsufficientMaterial() </code> - no sequence of legal moves can 
	 * 		lead to Check-mate
	 * <li> If King is not in check and the player has not available moves
	 * </ul>
	 * 
	 * @param 	The current <code> board </code>
	 * @return	<code> true </code> if one of the stale-mate
	 * 			conditions are met.
	 * @see		https://en.wikipedia.org/wiki/Draw_(chess)
	 */
	public boolean isStaleMate(Board board){
		//if((!isCheck(board) && getAllAvailableMoves(board).size() == 0) 
		//	|| isPerpetualCheck() || isFiftyMoveRule() || isInsufficientMaterial())
		//	return true;
		return false;
	}
	
	public boolean isMutualAgreement(){
		return false; //
	}
	
	private boolean isPerpetualCheck(){
		return false; //
	}
	
	private boolean isFiftyMoveRule(){
		return false; //
	}
	
	private boolean isInsufficientMaterial(){
		return false; //
	}
	
	
	public ArrayList<Move> getDefensiveMoves(Board board, ArrayList<Move> possibleMoves){
		ArrayList<Piece> threateningPieces = getCheckingPieces(board);
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if(threateningPieces.size() == 1 || threateningPieces.size() == 0){
		
			for(Move m : possibleMoves){
				if(!(m.getPiece() instanceof King)){
					movePiece(board, m);
					if(!isCheck(board)){
						undoMove(board, m);
						moves.add(m);
					}
					undoMove(board, m);
				} 
			}
			return moves;
		} else if (threateningPieces.size() > 1){
			return null;
		} else {
			return possibleMoves;
		}
	}
	
	public ArrayList<Piece> getCheckingPieces(Board board){
		ArrayList<Piece> checkingPieces = new ArrayList<Piece>();
		Player opponent = getOpponent();
		ArrayList<Move> tmp = opponent.getAllReamianingPieceMoves(board);
		for(Move move : tmp){
			Piece k = move.getCapturedPiece();
			if(k != null){
				if(k instanceof King && k.getAllegiance() == this.allegiance)
					checkingPieces.add(move.getPiece());
			}
		}
		return checkingPieces;
	}
	
	public ArrayList<Piece> getRemainingPieces(Board board){
		ArrayList<Piece> rp = new ArrayList<Piece>();

		for(int row = 0; row < Board.BOARD_ROW; row++){
			for(int col = 0; col < Board.BOARD_COLUMN; col++){
				Piece tmp = board.getPiece(row, col);
				
				if(board.isOccupied(row, col) && tmp.getAllegiance() == this.allegiance)
					rp.add(tmp);
			}
		}
		
		return rp;
	}
	
	public King getKing(Board board, Alliance alliance){
		for(Piece p : getRemainingPieces(board))
			if(p instanceof King)
				return (King) p;
		return null;
	}
	
	public ArrayList<Move> getAllAvailableMoves(Board board){
		ArrayList<Move> tmp = new ArrayList<Move>();
		
		for(Piece p : getRemainingPieces(board)){
			ArrayList<Move> possibleMoves = p.getPossibleMoves(board);
			if (p instanceof King && possibleMoves != null){
				tmp.addAll(possibleMoves);
			}else if(possibleMoves != null){
				//System.out.println(p.toString());
				tmp.addAll(getDefensiveMoves(board,possibleMoves));
			} 
		}
		return tmp;
	}
	
	public ArrayList<Move> getAllReamianingPieceMoves(Board board){
		ArrayList<Move> tmp = new ArrayList<Move>();
		
		for(Piece p : getRemainingPieces(board)){
			if(p.getPossibleMoves(board) != null){
				tmp.addAll(p.getPossibleMoves(board));
			}
		}
		
		return tmp;
	}
	
	
	public void movePiece(Board board, Move move){
		Point p = move.getMovePosition();
		Point cp = move.getCurrentPosition();
		
		board.setTile(p.x, p.y, move.getPiece());
		board.getPiece(p.x, p.y).setPosition(p.x,p.y);
		board.clearTile(cp.x, cp.y);
	}
	
	//test
	public void undoMove(Board board, Move move){
		
		Point p = move.getMovePosition();
		Point cp = move.getCurrentPosition();
		
		board.setTile(cp.x, cp.y, move.getPiece());
		board.getPiece(cp.x, cp.y).setPosition(cp.x,cp.y);
		board.clearTile(p.x, p.y);
		
		if(move.getCapturedPiece() != null)
			board.setTile(move.getCapturedPiece().getX(), 
						  move.getCapturedPiece().getY(), 
						  move.getCapturedPiece());
		
	}
	
	public Alliance getAllegiance(){
		return this.allegiance;
	}
	
	@Override
	public String toString(){
		return "" + (allegiance == Alliance.BLACK ? "Black Player" : "White Player");
	}
}
