package chess.player;

import java.util.ArrayList;

import chess.Board;
import chess.Main;
import chess.piece.*;

public class Evaluator {

	public static int evaluate(Board board, Player player){
		
		int counter = 0, material = rateMaterial(player, board);
		counter+=rateMaterial(player, board);
		counter+=rateMovaability(board, player, material);
		counter+=rateAttack(board, player);
		counter+=ratePieces(player, board);
		return counter;
	}
	
	public static int rateAttack(Board board, Player player){
		int counter = 0;
		ArrayList<Move> moves = player.getAllPossibleMoves(player.getColour(), board);
		
		for(Move move : moves){
			if(move.isCapture(board)){
				Piece p = move.getCaptured(board);
				if(p instanceof Pawn){
					counter -= 100;
				} else if (p instanceof Rook){
					counter -= 400;
				} else if (p instanceof Knight){
					counter -= 300;
				} else if (p instanceof Bishop){
					counter -= 350;
				} else if (p instanceof Queen){
					counter -= 900;
				} else if (p instanceof King){
				//	counter += 1000000;
				}
			}
		}
		
		return counter;
	}
	
	public static int rateMaterial(Player player, Board board){
		int counter = 0;
		ArrayList<Move> moves = player.getAllPossibleMoves(player.getColour(), board);
		ArrayList<Move> movesOpp = Main.getOpponent(player).getAllPossibleMoves(player.getColour(), board);
		if(moves.size() > movesOpp.size()){
			for(int i = 0; i < moves.size(); i++){
				counter -= 5 * 4;
			}
		}
		return counter;
	}
	
	public static int ratePieces(Player player, Board board){
		int counter = 0;
		
		ArrayList<Piece> list = new ArrayList<Piece>();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Piece p = board.getTile(i, j).getPiece();
				if(p != null && p.getColour() == player.getColour()){
					list.add(p);
				}
			}
		}
		
		for(int k = 0; k < list.size(); k++){
			Piece p = list.get(k);
			if(p instanceof Pawn){
				counter += 100;
			} else if (p instanceof Rook){
				counter += 400;
			} else if (p instanceof Knight){
				counter += 300;
			} else if (p instanceof Bishop){
				counter += 350;
			} else if (p instanceof Queen){
				counter += 900;
			} else if (p instanceof King){
				//counter += 1000000;
			}
		}
		
		ArrayList<Piece> list1 = new ArrayList<Piece>();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Piece p = board.getTile(i, j).getPiece();
				if(p != null && p.getColour() == !(player.getColour())){
					list1.add(p);
				}
			}
		}
		
		for(int k = 0; k < list1.size(); k++){
			Piece p = list1.get(k);
			if(p instanceof Pawn){
				counter = 100;
			} else if (p instanceof Rook){
				counter -= 400;
			} else if (p instanceof Knight){
				counter -= 300;
			} else if (p instanceof Bishop){
				counter -= 350;
			} else if (p instanceof Queen){
				counter -= 900;
			} else if (p instanceof King){
				//counter -= 1000000;
			}
		}
		
		return counter;
	}
	
	public static int rateMovaability(Board board, Player player, int material){
		int counter = 0;
		ArrayList<Move> moves = player.getAllPossibleMoves(player.getColour(), board);
		counter += moves.size() * material;
		if(board.getKing(Main.getOpponent(player).getColour()).isCheckMate(board)){
			counter -= 20000000;
		} 
		return counter;
	}
	
}
