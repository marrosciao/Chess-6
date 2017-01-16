package chess;

import chess.player.AI;
import chess.player.Alliance;
import chess.player.Player;
import chess.time.Time;

import java.awt.Point;

import chess.piece.*;

/**
 *
 */


public class Game {
	
	private static Player player1;
	private static Player player2;
	public static boolean isCheck;
	private Board board;
	
	private static Alliance turn;
	
	//local multi-player
	public Game(Player playerOne, Player playertwo, Board board){
		player1 = playerOne;
		player2 = playertwo;
		this.isCheck = false;
		this.board = board;
		this.turn = Alliance.WHITE;
	}
	
	//single player / multi-player
	public Game(Player player1, Board board){
		
	}
		
	public void setUp(Board board){
		new BoardBuilder(board).build();
	}
	
	public static Board flipBoard(Board src){
		Piece[][] cpy = new Piece[Board.BOARD_ROW][Board.BOARD_COLUMN];
		
		for(int i = 0; i < Board.BOARD_SIZE; i++){
			Piece tmp = (Piece) src.getBoard()[(Board.BOARD_ROW - 1) - (i/Board.BOARD_ROW)][(Board.BOARD_COLUMN - 1) - (i%Board.BOARD_COLUMN)];
			if(tmp != null)
				cpy[i/Board.BOARD_ROW][i%Board.BOARD_COLUMN] = tmp;
		}
		
		return new Board(cpy);
	}
	
	//turn = !turn;
	public void nexTurn(){
		getPlayer(turn).pauseClock();
		turn = turn.next();
		Player player = getPlayer(turn);
		
		//if(turn == Alliance.BLACK){
		//	try{
		//		player2.movePiece(board, ((AI) player2).search(board, 0));
		//	} catch (Exception e){}
		//	System.out.println(AI.boardEval);
		////	
		//	//player.resumeClock();
		//	nexTurn();
		//}
		
		player.resumeClock();
		
	}
	
	public static Player getPlayer(Alliance alliance){
		if(player1.getAllegiance() == alliance)
			return player1;
		else 
			return player2;
	}
	
	public static Alliance getTurn(){
		return turn;
	}
	
	public String turnToString(){
		return turn == Alliance.WHITE ? "White Players turn" : "Black Players turn"; 
	}
	
	public Board getBoard(){
		return this.board;
	}
	
}
