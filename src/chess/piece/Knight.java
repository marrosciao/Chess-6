package chess.piece;

import java.util.ArrayList;

import chess.Board;
import chess.Move;
import chess.player.Alliance;


public class Knight extends Piece{


	public Knight(int x, int y, Alliance alliance) {
		super(x, y, alliance);
	}
	
	@Override
	public ArrayList<Move> getPossibleMoves(Board board){
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int i = -1; i <= 1; i += 2){
			for(int j = -2; j <= 2; j += 4){
				moves.addAll(iterateDirection(board, i, j, 1));
				moves.addAll(iterateDirection(board, j, i, 1));
			}
		}
		return moves;
	}

	@Override
	public String toString(){
		return "" + (allegiance == Alliance.BLACK ? "black_knight" : "white_knight");
	}


}
