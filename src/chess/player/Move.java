package chess.player;


import java.awt.Point;

import chess.Board;
import chess.Tile;
import chess.piece.Piece;

public class Move {

	private Point currentPos;
	private Point movePos;
	private Piece piece;
	private Piece captured;
	
	public Move(Point currentPos, Point movePos, Piece piece){
		this.currentPos = currentPos;
		this.movePos = movePos;
		this.piece = piece;
	}
	
	public boolean isCapture(Board board){
		Tile tile = board.getTile(movePos.x, movePos.y);
		if(tile.isOccupied() && (tile.getPiece().getColour() != piece.getColour()))
			return true;
		return false;
	}
	
	public Piece getCaptured(Board board){
		Tile tile = board.getTile(movePos.x, movePos.y);
		if(tile.isOccupied() && (tile.getPiece().getColour() != piece.getColour()))
			return tile.getPiece();
		return tile.getPiece();
	}
	
	public Piece getCapturedPiece(){
		return this.captured;
	}
	
	public void movePiece(Board board){
		int pointX = movePos.x;
		int pointY = movePos.y;
		
		if(this.isCapture(board) == true){
			captured = getCaptured(board);
		}
		
		board.getTile(pointX, pointY).setPiece(this.piece);
		board.getTile(pointX, pointY).getPiece().setX(pointX);
		board.getTile(pointX, pointY).getPiece().setY(pointY);
		board.getTile(currentPos.x, currentPos.y).clearPiece();
	}
	
	public Point getCurrentPosition(){
		return this.currentPos;
	}
	
	public Point getMovePosition(){
		return this.movePos;
	}
	
	public Piece getMovingPiece(){
		return piece;
	}
	
	@Override
	public String toString(){
		String result = this.piece.toString() + " moved from:  " + this.currentPos + "  to  " + this.movePos;
		if(captured != null){
			result += "  Captured Piece : " + captured.toString();
		}
		return result;
	}
	
}
