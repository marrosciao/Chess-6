package chess;

import java.util.Arrays;
import chess.piece.*;

/**
 * 
 * A board object that holds tiles.
 * 
 * @version 03/10/2016
 *
 */
public class Board {

	private Tile[][] board;
	public static King white_king; /* Keeps track of the white king  */
	public static King black_king; /* Keeps track of the black king */
	
	
	public Board(){
		board = new Tile[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				setTile(i, j, new Tile(null));
			}
		}
		initChessBoard();
	}
	
	public void initChessBoard(){
		
		//set default layout of pieces for black
		getTile(0,0).setPiece(new Rook(0,0,true));
		getTile(1,0).setPiece(new Knight(1,0,true));
		getTile(2,0).setPiece(new Bishop(2,0,true));
		getTile(3,0).setPiece(new Queen(3,0,true));
		getTile(4,0).setPiece(new King(4,0,true));
		getTile(5,0).setPiece(new Bishop(5,0,true));
		getTile(6,0).setPiece(new Knight(6,0,true));
		getTile(7,0).setPiece(new Rook(7,0,true));
		for(int i = 0; i < 8; i++){
			getTile(i,1).setPiece(new Pawn(i, 1, true));
		}
		
		//set default layout of pieces for white
		getTile(0,7).setPiece(new Rook(0,7,false));
		getTile(1,7).setPiece(new Knight(1,7,false));
		getTile(2,7).setPiece(new Bishop(2,7,false));
		getTile(3,7).setPiece(new Queen(3,7, false));
		getTile(4,7).setPiece(new King(4,7,false));
		getTile(5,7).setPiece(new Bishop(5,7,false));
		getTile(6,7).setPiece(new Knight(6,7,false));
		getTile(7,7).setPiece(new Rook(7,7,false));
		for(int i = 0; i < 8; i++){
			getTile(i,6).setPiece(new Pawn(i, 6, false));
		}
		
		white_king = getKing(false);
		black_king = getKing(true);
	}
	
	/**
	 * Using deep cloning to copy the entire object hierarchy
	 * @return
	 */
	//Tile[][] original
	public Tile[][] deepCopy() {
	    if (getTiles() == null) {
	        return null;
	    }

	    final Tile[][] result = new Tile[getTiles().length][];
	    for (int i = 0; i < getTiles().length; i++) {
	        result[i] = Arrays.copyOf(getTiles()[i], getTiles()[i].length);
	    }
	    return result;
	}
	
	
	
	public Tile[][] getTiles(){
		return board;
	}
	
	public void setTiles(Tile[][] tiles){
		this.board = tiles;
	}
	
	public Tile getTile(int x, int y){
		Tile tile = this.board[x][y];
		return tile;
	}
	
	public void setTile(int x, int y, Tile tile){
		this.board[x][y] = tile;
	}
	
	/**
	 * A method to get the king
	 * @param colour
	 * @return King
	 */
	public King getKing(boolean colour){
		Piece p = null;
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(this.getTile(i, j).isOccupied()){
					p = this.getTile(i, j).getPiece();
					
					if(p instanceof King && p.getColour() == colour){
						//System.out.println(p.toString()  + " "+ p.getX() + " " + p .getY());
						return (King) p;
					}
				}
			}
		}
		
		return null;
	}
	
}
