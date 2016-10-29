package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;

public class Bishop extends Piece{

	public Bishop(int x , int y, Boolean colour) {
		super(x, y, colour);
	}

	@Override
	public ArrayList<Point> getPossibleMoves(Board board) {

		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		int tempx;
		int tempy;

		for(int i = -1; i <= 1; i += 2){
			for(int j = -1; j <= 1; j += 2){
				try{
					tempx = this.x + i;
					tempy = this.y + j;
					
					while(tempx >=0 || tempx < 8 || tempy >= 0 || tempy < 8){
						if(board.getTile(tempx, tempy).isOccupied() == false){
							possibleMoves.add(new Point(tempx, tempy));
						} else if(board.getTile(tempx, tempy).getPiece().getColour() == this.colour ||
								board.getTile(tempx, tempy).getPiece() instanceof King){
							break;
						} else {
							possibleMoves.add(new Point(tempx, tempy));
							break;
						}
						tempx += i;
						tempy += j;
						
					}
					
				} catch(Exception e){
					
				}
				
			}
		}
		

		return possibleMoves;

	}
	
	
	@Override
	public String toString(){
		if(this.getColour() == true){
			return "black_bishop";
		} else {
			return "white_bishop";
		}
	}

}
