package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;
import chess.Move;
import chess.player.Alliance;

public class Pawn extends Piece{

	public Pawn(int x, int y, Alliance alliance) {
		super(x, y, alliance);
	}
	
	@Override
	public ArrayList<Move> getPossibleMoves(Board board){
		ArrayList<Move> moves = new ArrayList<Move>();
		
		if(this.allegiance == Alliance.WHITE) {
			moves.addAll(getLegalMoves(board, this.x, this.y + -1, this.allegiance));
			for(int i = -1; i <= 1; i += 2)
				moves.addAll(getLegalAttacks(board, x + i, y + -1));
	 	} else {
	 		moves.addAll(getLegalMoves(board, this.x, this.y + 1, this.allegiance));
			for(int i = -1; i <= 1; i += 2)
				moves.addAll(getLegalAttacks(board, x + i, y + 1));
		}
		
		return moves;
	}
	
	private ArrayList<Move> getLegalMoves(Board board, int i, int j, Alliance allegiance){
		ArrayList<Move> moves = new ArrayList<Move>();
		if(isWithinBounds(i, j)) {
			if(!board.isOccupied(i, j)){
				moves.add(newMove(board, new Point(i,j)));
			
				if(this.getAllegiance() == Alliance.WHITE && this.y == 6){
					if(!board.isOccupied(i, j - 1))
						moves.add(newMove(board, new Point(i,j - 1)));
				
				} else if(this.getAllegiance() == Alliance.BLACK && this.y == 1){
					if(!board.isOccupied(i, j + 1))
						moves.add(newMove(board, new Point(i,j + 1)));
				}
			}
		}	
		
		return moves;
	}
	
	private ArrayList<Move> getLegalAttacks(Board board, int i, int j){
		ArrayList<Move> attacks = new ArrayList<Move>();
		if(isWithinBounds(i, j))
			if(board.isOccupied(i, j) && board.getPiece(i, j).getAllegiance() != this.allegiance)
				attacks.add(newMove(board, new Point(i,j)));
		return attacks;
	}
	
	public ArrayList<Move> getAttackMoves(Board board){
		ArrayList<Move> tmp = new ArrayList<Move>();

		for(int i = -1; i <= 1; i+= 2){
			if(this.allegiance == Alliance.WHITE){
				if(isWithinBounds(this.x + i, this.y - 1))
					tmp.add(new Move(board, this.getPosition(), new Point(this.x + i, this.y - 1), this));
				
			} else if(this.allegiance == Alliance.BLACK){
				if(isWithinBounds(this.x + i, this.y + 1))
					tmp.add(new Move(board, this.getPosition(), new Point(this.x + i, this.y + 1), this));
				
			}
		}
				
		return tmp;
	}
	
	public void promote(Board board){
		if(this.getAllegiance() == Alliance.WHITE && this.y == 0){
			board.clearTile(x, y);
			promoteToQueen(board, x, y, Alliance.WHITE);
		} else if(this.getAllegiance() == Alliance.BLACK && this.y == 7) {
			board.clearTile(x, y);
			promoteToQueen(board, x, y, Alliance.BLACK);
		}
	}
	
	private void promoteToQueen(Board board, int x, int y, Alliance allegiance){
		board.setTile(x,y, new Queen(x, y, allegiance));
	}
	
	@Override
	public String toString(){
		return "" + (allegiance == Alliance.BLACK ? "black_pawn" : "white_pawn");
	}
	 
}
