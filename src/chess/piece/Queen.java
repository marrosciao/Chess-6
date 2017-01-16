package chess.piece;

import java.util.ArrayList;

import chess.Board;
import chess.Move;
import chess.player.Alliance;

public class Queen extends Piece{

	public Queen(int x, int y, Alliance alliance) {
		super(x, y, alliance);
	}
	
	@Override
	public ArrayList<Move> getPossibleMoves(Board board){
		ArrayList<Move> moves = new ArrayList<Move>();
		moves.addAll(addVerticalMoves(board));
		moves.addAll(addHorizontalMoves(board));
		moves.addAll(addDiagonalMoves(board));
		return moves;
	}
	
	@Override
	public String toString(){
		return "" + (allegiance == Alliance.BLACK ? "black_queen" : "white_queen");
	}


}
