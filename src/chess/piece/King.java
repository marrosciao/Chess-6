package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;
import chess.Move;
import chess.player.Alliance;
import chess.player.Player;


/**
 * needs testing
 * @author 
 *
 */
public class King extends Piece{
	
	public King(int x, int y, Alliance alliance) {
		super(x, y, alliance);
		
	}
	
	@Override
	public ArrayList<Move> getPossibleMoves(Board board) {
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int i = -1; i <= 1; i ++){
			for(int j = -1; j <= 1; j ++){
				if(!(i == 0 && j == 0) && isSafeMove(board, this.x + i, this.y + j)){
					moves.add(newMove(board, new Point(this.x + i, this.y + j)));
				}
			}
		}
		return moves;
	}
	
	private boolean isSafeMove(Board board, int i, int j){
		if(isWithinBounds(i, j)) {
			if(!board.isOccupied(i, j) || board.getPiece(i, j).getAllegiance() != this.allegiance){
				if(isCheckAtPoint(board ,new Point(i,j)) == false){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isCheckAtPoint(Board board, Point point){
		Board clone = new Board(board.getBoard());
		
		//dangerous and expensive try to redo
		clone.clearTile(this.x, this.y); /*remove king*/
		clone.clearTile(point.x, point.y);	/*remove piece at point*/
		
		Player opponent = getOpposition(this.allegiance);
		ArrayList<Move> tmp =  getAllOpponentAttacks(clone , opponent.getAllReamianingPieceMoves(clone));
		
		if(opponent != null){
			for(Move move : tmp){
				Point dest = move.getMovePosition();
				if(point.equals(dest)){
					return true;
				}
			}
		}
		return false;
	}
	
	private ArrayList<Move> getAllOpponentAttacks(Board board, ArrayList<Move> moves){
		ArrayList<Move> tmp = new ArrayList<Move>();
		
		for(Move move : moves) {
			if(move.getPiece() instanceof Pawn){
				Pawn pawn = (Pawn) move.getPiece();
				tmp.addAll(pawn.getAttackMoves(board));
			} else if(move.getPiece() instanceof King){
				King king = (King) move.getPiece();
				tmp.addAll(king.getLegalAttacks(board));
			}
			else{
				tmp.add(move);
			}
		}
		return tmp;
	}
	
	public ArrayList<Move> getLegalAttacks(Board board){
		ArrayList<Move> attacks = new ArrayList<Move>();
		int destx;
		int desty;
		for(int i = -1; i <= 1; i ++){
			for(int j = -1; j <= 1; j ++){
				if(!(i == 0 && j == 0)){
					destx = this.x + i;
					desty = this.y + j;
					if(isWithinBounds(destx, desty)) {
						if(!board.isOccupied(destx, desty) || board.getPiece(destx, desty).getAllegiance() != this.allegiance){
							attacks.add(newMove(board, new Point(destx,desty)));
						}
					}
				}
			}
		}
			
		return attacks;
	}
	
	@Override
	public String toString(){
		return "" + (allegiance == Alliance.BLACK ? "black_king" : "white_king");
	}
	 
}
