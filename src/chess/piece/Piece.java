package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;

public abstract class Piece{

	protected boolean colour;
	protected int x, y;
	
	public Piece(int x, int y, Boolean colour){
		this.colour = colour;
		this.x = x;
		this.y = y;
	}

	/**
	 * Makes move on the board
	 * 
	 * @param x
	 * @param y
	 * @param board
	 */
	public void move(int x, int y, Board board){
		int oldPosX = this.x;
		int oldPosY = this.y;
		
		ArrayList<Point> possibleMoves = this.getPossibleMoves(board);
		for(Point moves : possibleMoves ){
			int pointX = moves.x;
			int pointY = moves.y;
			if(x == pointX && y == pointY) {
				board.getTile(pointX, pointY).setPiece(this);
				
				board.getTile(pointX, pointY).getPiece().setX(pointX);
				board.getTile(pointX, pointY).getPiece().setY(pointY);
				board.getTile(oldPosX, oldPosY).clearPiece();
			} else {
				//System.out.println("cannot make move");
			}
		}
	}
	
	
	/**
	 * Filter possible moves for pieces if the king is in check.
	 * Pieces move to protect the king when in check.
	 * 
	 * @param piece
	 * @param possibleMoves
	 * @return
	 */
	public ArrayList<Point> getPossibleMovesFiltered(Board board){
		ArrayList<Point> filteredList = new ArrayList<Point>();
		ArrayList<Point> copy = this.getPossibleMoves(board);
		Board checkBoard = board;
		Piece getPiece = null; /* This keeps the piece in this variable, 
		 							so the piece can be replaced on the board*/
		
		for(int i = 0; i < copy.size(); i++){
			int tempx = copy.get(i).x;
			int tempy = copy.get(i).y;
			
			if(checkBoard.getTile(tempx, tempy).isOccupied()){
				getPiece = checkBoard.getTile(tempx, tempy).getPiece();
			}
			
			checkBoard.getTile(tempx, tempy).setPiece(this);
			
			King king = board.getKing(true);
			if(this.getColour() == true){
				king = board.getKing(true);
			} else {
				king = board.getKing(false);
			}
			
			if(!king.isCheck(checkBoard)){
				filteredList.add(copy.get(i));
			}
			
			if(getPiece != null){
				checkBoard.getTile(tempx, tempy).setPiece(getPiece);
				getPiece = null;
			} else {
				checkBoard.getTile(tempx, tempy).clearPiece();
			}
		}
		
		return filteredList;
	}
	
	/**
	 * Filters possible moves if moving this piece puts the king in danger
	 * 
	 * @param board
	 * @return
	 */
	public ArrayList<Point> putsKingInCheck(Piece p, Board board){
		ArrayList<Point> filteredList = new ArrayList<Point>();
		ArrayList<Point> copy = this.getPossibleMoves(board);
		Piece getPiece = null; /* This keeps the piece in this variable, 
									so the piece can be replaced on the board*/
		
		board.getTile(this.getX(), this.getY()).clearPiece();
		
		for(int i = 0; i < copy.size(); i++){
			int tempx = copy.get(i).x;
			int tempy = copy.get(i).y;
			
			if(board.getTile(tempx, tempy).isOccupied()){
				getPiece = board.getTile(tempx, tempy).getPiece();
			}
			
			board.getTile(tempx, tempy).setPiece(this);
			
			King king = board.getKing(true);
			if(this.getColour() == true){
				king = board.getKing(true);
			} else {
				king = board.getKing(false);
			}
			
			if(!king.isCheck(board)){
				filteredList.add(copy.get(i));
			}
			
			if(getPiece != null){
				board.getTile(tempx, tempy).setPiece(getPiece);
				getPiece = null;
			} else {
				board.getTile(tempx, tempy).clearPiece();
			}
		}
		
		board.getTile(this.getX(), this.getY()).setPiece(this);
		
		return filteredList;
	}
	
	
	public ArrayList<Point> getPossibleMoves(Board board) {
		return null;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public Boolean getColour(){
		return colour;
	}
	

	public void setColour(Boolean colour){
		this.colour = colour;
	}
	
	public String toString(){
		if(this.getColour() == true){
			return "black_piece";
		} else {
			return "white_piece";
		}
	}

}
