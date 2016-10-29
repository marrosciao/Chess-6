package chess.player;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import chess.Board;
import chess.piece.*;

public abstract class Player {

	private boolean colour;
	public ArrayList<Move> playerMoveHistory;
	
	
	public Player(boolean colour){
		this.colour = colour;
		playerMoveHistory = new  ArrayList<Move>();
	}

	
	public ArrayList<Move> getAllPossibleMoves(boolean colour, Board board){
		ArrayList<Move> listOfRemainingMoves  = new ArrayList<Move>();
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Piece p = board.getTile(i, j).getPiece();
				if(board.getTile(i, j).isOccupied() && p.getColour() == this.getColour()){
					ArrayList<Point> tempfiltered = new ArrayList<Point>();
					if(!(p instanceof King) && board.getKing(this.getColour()).isCheck(board)){
						tempfiltered = p.getPossibleMovesFiltered(board);
					} else{
						if(!(p instanceof King)){
							tempfiltered =  p.putsKingInCheck(p, board);
						} else {
							tempfiltered = p.getPossibleMoves(board);
						}
					}
					for(Point dest : tempfiltered){
						Point curr = new Point(i, j);
						Move move = new Move(curr, dest, p);
						listOfRemainingMoves.add(move);
					}
				}
			}
		}
		listOfRemainingMoves.removeAll(Arrays.asList("", null));
		
		return listOfRemainingMoves;
		
	}
	
	/**
	 * Makes move on the board
	 * change to make move
	 * 
	 * @param x
	 * @param y
	 * @param board
	 */
	public void movePiece(int x, int y, Board board, Piece piece){
		
		Point curr = new Point(piece.getX(), piece.getY());
		Point mov = new Point(x, y);
		
		Move move = new Move(curr, mov, piece);
		move.movePiece(board);
		playerMoveHistory.add(move);
		System.out.println(move.toString());
		
	}
	
	public void undoMove(Board board){
		if(playerMoveHistory.size() > 0){
			
			Move prevMove = playerMoveHistory.get(playerMoveHistory.size() - 1);
			
			Point previous = prevMove.getMovePosition();
			Point dest = prevMove.getCurrentPosition();
			
			Move move = new Move(previous, dest, prevMove.getMovingPiece());
			move.movePiece(board);
			
			if(prevMove.getCapturedPiece() != null){
				Piece p = prevMove.getCapturedPiece();
				board.getTile(p.getX(), p.getY()).setPiece(p);
			}
			
			playerMoveHistory.remove(playerMoveHistory.size() - 1);
			
		} else {
			//System.out.println("Cannot undo move");
		}
	}
	
	public boolean getColour(){
		return this.colour;
	}
	
	public void setColour(boolean colour){
		this.colour = colour;
	}
	
	public ArrayList<Move> getMoveHistory(){
		return playerMoveHistory;
	}
	
	public void setMoveHistory(ArrayList<Move> moveHist){
		this.playerMoveHistory = moveHist;
	}
	
	@Override
	public String toString(){
		return "" + (colour ? "White Player" : "Black Player");
	}
}
