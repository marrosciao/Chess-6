package chess;


import java.awt.Point;

import chess.piece.*;

public class Move {

	//private Board board;
	private Point curr;
	private Point dest;
	private Piece piece;
	private Piece captured;
	private boolean isCheck;
	private boolean isCheckMate;

	public Move(Board board, Point currentPos, Point movePos, Piece piece){
		//this.board = board;
		this.curr = currentPos;
		this.dest = movePos;
		this.piece = piece;
		setCaptured(board);
		
		//if this move will check
		//if this move will check mate
	}
	
	public void setCaptured(Board board){
		if(board.isOccupied(dest.x, dest.y) && 
				(board.getPiece(dest.x, dest.y).getAllegiance() != piece.getAllegiance())){
			this.captured = board.getPiece(dest.x, dest.y);
			//System.out.println(this.captured.toString());
		} else {
			this.captured = null;
		}
			
		
	}
	
	public Piece getCapturedPiece(){
		return this.captured;
	}
	
	public boolean isThisCheck(Board board){
		if(board.isOccupied(dest.x, dest.y) && (board.getPiece(dest.x, dest.y) instanceof King))
			return true;
		return false;
	}
	
	public Point getCurrentPosition(){
		return this.curr;
	}
	
	public Point getMovePosition(){
		return this.dest;
	}
	
	public Piece getPiece(){
		return piece;
	}
	
	@Override
	public String toString(){
		String result = this.piece.toString() + " moved from:  " + this.curr + "  to  " + this.dest;
		if(captured != null){
			result += "  Captured Piece : " + captured.toString();
		}
		MoveNotationBuilder b = new MoveNotationBuilder();
		String s = b.getNotation(this.curr, this.dest, this.piece, this.captured);
		return s;
	}
	
}
