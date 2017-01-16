package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;
import chess.Game;
import chess.Move;
import chess.player.Alliance;
import chess.player.Player;

public abstract class Piece{

	protected Alliance allegiance;
	protected int x, y;
	protected final static int MAX_ITERATIONS = 8; 
	
	public Piece(int x, int y, Alliance alliance){
		this.allegiance = alliance;
		this.x = x;
		this.y = y;
	}
	
	public ArrayList<Move> getPossibleMoves(Board board) {
		return null;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public Point getPosition(){
		return new Point(this.x, this.y);
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public Alliance getAllegiance(){
		return allegiance;
	}
	
	protected static Player getPartnership(Alliance alliance){
		return Game.getPlayer(alliance);
	}
	
	protected static Player getOpposition(Alliance alliance){
		return Game.getPlayer(alliance.next());
	}
	
	protected boolean isWithinBounds(int x, int y){
		if(!(x >=0 && x < Board.BOARD_ROW && y >= 0 && y < Board.BOARD_COLUMN))
			return false;
		return true;
	}
	
	protected boolean isEnemy(Board board, int x, int y){
		if(board.isOccupied(x, y) && board.getPiece(x, y).getAllegiance() != this.getAllegiance())
			return true;
		return false;
	}
	
	protected boolean isAlly(Board board, int x, int y){
		if(board.isOccupied(x, y) && board.getPiece(x, y).getAllegiance() == this.getAllegiance())
			return true;
		return false;
	}
	
	protected Move newMove(Board board, Point dest){
		return new Move(board, getPosition(), dest, this);
	}
	
	/**
	 * 	if 		
	 *				there is more than 1 threatening piece then only the king can move
	 *	else if 
	 *				there is only one threatening piece then get all positions the 
	 *				attacking piece can move (except from the last move which 
	 *				would be the king) and add it to the array of threatening 
	 *				points including the position of the attacking piece, if the
	 *				piece is a pawn or knight then just add the current position.
	 *	then (when calculating possible moves)
	 *				check if match all possible moves against the threatening square
	 *				if there is a match that add it to the list of possible moves if not
	 *				then don't.
	 * 
	 * 
	 * @param board
	 * @return
	 */
	
	//all instances of class piece used expect for king and pawn
	protected ArrayList<Move> iterateDirection(Board board, int i, int j, int iterations){
		ArrayList<Move> moves = new ArrayList<Move>();
		Point dest = new Point(x + i, y + j);
		
		int n = 0;
		while(isWithinBounds(dest.x, dest.y) && n < iterations){
			if(!board.isOccupied(dest.x, dest.y)){
				moves.add(newMove(board, new Point(dest.x, dest.y)));
			} else if (isEnemy(board, dest.x, dest.y)){
				moves.add(newMove(board, dest));
				break;
			} else if(isAlly(board, dest.x, dest.y)){
				break;
			}
			
			dest.translate(i, j);
			n++;
		}
		return moves;
	}
	
	//const north = -1, south = +1, east = +1, west = -1
	protected ArrayList<Move> addDiagonalMoves(Board board){
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int i = -1; i <= 1; i += 2){
			for(int j = -1; j <= 1; j += 2){
				moves.addAll(iterateDirection(board, i, j, MAX_ITERATIONS));
			}
		}
		return moves;
	}
	
	protected ArrayList<Move> addVerticalMoves(Board board){
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int vertical = -1; vertical <= 1; vertical += 2){
			moves.addAll(iterateDirection(board, 0, vertical, MAX_ITERATIONS));
		}
		return moves;
	}
	
	protected ArrayList<Move> addHorizontalMoves(Board board){
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int horizontal = -1; horizontal <= 1; horizontal += 2){
			moves.addAll(iterateDirection(board, horizontal, 0, MAX_ITERATIONS));
		}
		return moves;
	}

}
