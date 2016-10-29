package chess;

import chess.piece.Piece;

/**
 * 
 * A class to hold a piece.
 * 
 * @version 03/08/2016
 *
 */

public class Tile {

	private Piece piece;
	
	/**
	 * A constructor that takes a piece.
	 * @param piece - takes piece
	 */
	public Tile(Piece piece){
		this.piece = piece;
	}
	
	/**
	 * This method checks to see if there is a piece on this tile.
	 * return true if there is no piece on this tile else if null return false.
	 * @return boolean
	 */
	public Boolean isOccupied(){
		if(this.piece != null){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * A setter method to set a piece onto the tile.
	 * @param piece
	 */
	public void setPiece(Piece piece){
		this.piece = piece;
	}
	
	/**
	 * This method empties the tile and sets it to null
	 */
	public void clearPiece(){
		this.piece = null;
	}
	
	/**
	 * Accessor methods that get the piece on this tile
	 * @return piece
	 */
	public Piece getPiece(){
		return this.piece;
	}
	
	/**
	 * This method will return the piece type that is on this tile
	 * example: white_pawn
	 * @return String
	 */
	@Override
	public String toString(){
		if(piece != null)
			return piece.toString();
		return " ";
	}
	
}
