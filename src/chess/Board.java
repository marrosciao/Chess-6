package chess;

import chess.piece.*;
import chess.player.Alliance;

/**
 * 
 * A board object that holds tiles.
 * 
 * @version 03/10/2016
 *
 */
public class Board{

	private Piece[][] board;
	
	public static final int BOARD_ROW = 8;
	public static final int BOARD_COLUMN = 8;
	public static final int BOARD_SIZE = BOARD_ROW * BOARD_COLUMN;
	public static final String BOARD_LETTERS = "abcdefgh";
	public static final String BOARD_NUMBERS = "87654321";
			//"87654321";
	
	public Board(){
		this.board = new Piece[BOARD_ROW][BOARD_COLUMN];
	}
	
	public Board(Piece[][] board){
		this.board = new Piece[BOARD_ROW][BOARD_COLUMN];
		clone2D(board);
	}
	
	public void clone2D(Piece[][] board){
		for(int i = 0; i < BOARD_SIZE; i++)
			this.board[i%BOARD_ROW][i/BOARD_COLUMN] = clonePiece(board[i%BOARD_ROW][i/BOARD_COLUMN], this);
	}
	
	private Piece clonePiece(Piece src, Board board){
		if(src == null)
			return null;
		return newPieceCopy(src, board, src.getAllegiance(), src.getX(), src.getY());
	}
	
	private Piece newPieceCopy(Piece src, Board board, Alliance alliance, int x, int y){
		Piece copy = null;
		if(src instanceof Pawn)
			copy = new Pawn(x,y, alliance);
		else if(src instanceof Queen)
			copy = new Queen(x,y, alliance);
		else if(src instanceof Knight)
			copy = new Knight(x,y, alliance);
		else if(src instanceof Bishop)
			copy = new Bishop(x,y, alliance);
		else if(src instanceof Rook)
			copy = new Rook(x,y, alliance);
		else if(src instanceof King)
			copy = new King(x,y, alliance);
		else
			copy = null;
		return copy;
	}
	
	public static String getSqaureName(int x, int y){
		String subStrX = BOARD_LETTERS.substring(x, x+1);
		String subStrY = BOARD_NUMBERS.substring(y, y+1);
		return subStrX + subStrY;
	}
	
	public Piece[][] getBoard(){
		return board;
	}
	
	public Piece getPiece(int x, int y){
		return this.board[x][y];
	}
	
	public void setTile(int x, int y, Piece piece){
		this.board[x][y] = piece;
	}
	
	public void clearTile(int x, int y){
		this.board[x][y] = null;
	}
	
	public Boolean isOccupied(int x, int y){
		if(getPiece(x,y) == null)
			return false;
		return true;
	}
	
	public void clear(){
		for(int i = 0; i < BOARD_SIZE; i++)
			 board[i%BOARD_ROW][i/BOARD_COLUMN] = null;
	}
	
	@Override
	public String toString(){
		return "Board";
	}

}
