package chess.player;

import java.util.ArrayList;

import chess.Board;
import chess.Main;

public class AI extends Player{

	public Move bestMove = null;
	public AI(boolean colour){
		super(colour);

	}

	public Move execute(final Board board,
			final int depth, Player player) {

		Move bestMove = null;
		int highestSeenValue = Integer.MIN_VALUE;
		int lowestSeenValue = Integer.MAX_VALUE;
		int currentValue = 0;

		ArrayList<Move> numMoves = player.getAllPossibleMoves(player.getColour(), board);
		for (final Move move : numMoves) {

			move.movePiece(board);
			player.playerMoveHistory.add(move);
			
			if(player.getColour() == false){
				currentValue = min(board, depth - 1, highestSeenValue, lowestSeenValue, player);
			} else {
				currentValue = max(board, depth - 1, highestSeenValue, lowestSeenValue, player);
			}
	
			if (player.getColour() == true && currentValue > highestSeenValue) {
				highestSeenValue = currentValue;
				bestMove = move;
			}
			else if (player.getColour() == false && currentValue < lowestSeenValue) {
				lowestSeenValue = currentValue;
				bestMove = move;
			}
			player.undoMove(board);
			
		}
		return bestMove;
	}

	private int max(Board board, int depth, int alpha, int beta, Player player) {
		ArrayList<Move> numMoves = player.getAllPossibleMoves(player.getColour(), board);
		if(depth == 0 || numMoves.size() == 0 
				|| board.getKing(player.getColour()).isCheckMate(board) == true){
			 return -(Evaluator.evaluate(board, player));
		}
		int currentHighest = alpha;
		for(Move move : numMoves){
			move.movePiece(board);
			currentHighest = min(board, depth - 1, alpha, beta, Main.getOpponent(player));
			player.undoMove(board);
			if(currentHighest > beta){
				return beta;
			}
		}
		
		return currentHighest;
	}

	private int min(Board board, int depth, int alpha, int beta, Player player) {
		ArrayList<Move> numMoves = player.getAllPossibleMoves(player.getColour(), board);
		if(depth == 0 || numMoves.size() == 0 
				|| board.getKing(player.getColour()).isCheckMate(board) == true){
			 return +(Evaluator.evaluate(board, player));
		}
		int currentLowest = beta;
		for(Move move : numMoves){
			move.movePiece(board);
			currentLowest = max(board, depth - 1,alpha, beta, Main.getOpponent(player));
			player.undoMove(board);
			if(currentLowest < alpha){
				return alpha;
			}
		}
		
		return currentLowest;
	}
}

