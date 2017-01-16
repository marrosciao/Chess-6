package chess.player;
/**
 * Basic AI using minimax algorithm
 */
import java.util.ArrayList;

import chess.Board;
import chess.Game;
import chess.Move;

public class AI extends Player{
	
	public Move bestMove = null;
	private int MaxDepth = 1;
	public static int boardEval = 0;
	public AI(Alliance alliance){
		//this.allegiance = alliance;
		super(alliance);
	}
	
	public Move search(final Board board, final int depth) {

		Move bestMove = null;
		int maxVal = Integer.MIN_VALUE;
		int minVal = Integer.MAX_VALUE;
		int currentValue = 0;
		//White minimising, Black maximising player

		for (final Move move : getAllAvailableMoves(board)) {
			Board clone = new Board(board.getBoard());
			movePiece(clone, move);
			
			if(getAllegiance() == Alliance.WHITE){
				currentValue = min(clone, depth + 1, maxVal, minVal);
			} else {
				currentValue = max(clone, depth + 1, maxVal, minVal);
			}
	
			if (getAllegiance() == Alliance.BLACK && currentValue > maxVal) {
				maxVal = currentValue;
				bestMove = move;
			}
			else if (getAllegiance() == Alliance.WHITE && currentValue < minVal) {
				minVal = currentValue;
				bestMove = move;
			}
		}
		System.out.println(bestMove.toString() + " " + (Game.getTurn() == Alliance.WHITE ? minVal : maxVal));
		return bestMove;
	}
	
	private int max(final Board b, int depth, int alpha, int beta){
		if(isEndGame(b) || depth > MaxDepth){
			return Evaluator.evaluate(b);
		}
		int max = Integer.MIN_VALUE;
		Player currentPlayer = Game.getPlayer(Alliance.BLACK);
		for(Move m : this.getAllAvailableMoves(b)){
			Board c = new Board(b.getBoard());
			boardEval++;
			this.movePiece(c, m);
			int x = min(c, depth+1, alpha, beta);
			//this.undoMove(c, m);
			if(x>max) max = x;
			if(x>alpha) alpha = x;
			if(alpha>=beta) return alpha;
		}
		
		return max;
	}
	
	private int min(final Board b, int depth, int alpha, int beta) {
		if(isEndGame(b) || depth > MaxDepth){
			return Evaluator.evaluate(b);
		}
		int min = Integer.MAX_VALUE;
		Player currentPlayer = Game.getPlayer(Alliance.WHITE);
		for(Move m : currentPlayer.getAllAvailableMoves(b)){
			Board c = new Board(b.getBoard());
			boardEval++;
			currentPlayer.movePiece(c, m);
			int x = max(c, depth+1, alpha, beta);
			//currentPlayer.undoMove(c, m);
			if(x<min) min = x;
			if(x<beta) beta = x;
			if(alpha>=beta) return beta;
		}
		
		return min;
	}

	private boolean isEndGame(Board board){
		if(Game.getPlayer(Game.getTurn()).isCheckMate(board) || this.isCheckMate(board))
			return true;
		return false;
	}
	
}

