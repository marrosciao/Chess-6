package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;

public class Rook extends Piece{
	
	public Rook(int x, int y, Boolean colour) {
		super(x, y, colour);
	}

	@Override
	public ArrayList<Point> getPossibleMoves(Board board){
		
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		int tempx;
		int tempy;
		
		for(int horizontal = -1; horizontal <= 1; horizontal += 2 ){
			try{
				tempx = this.x + horizontal;
			
				while(tempx >=0 || tempx < 8){
					if(board.getTile(tempx, this.y).isOccupied() == false){
						possibleMoves.add(new Point(tempx, this.y));
					} else if(board.getTile(tempx, this.y).getPiece().getColour() == this.colour || 
							board.getTile(tempx, this.y).getPiece() instanceof King){
						break;
					} else {
						possibleMoves.add(new Point(tempx, this.y));
						break;
					}
					
					tempx += horizontal;
				
				}
			} catch (Exception e){}
			
		}
		
		for(int vertical = -1; vertical <= 1; vertical += 2 ){
			try{
				tempy = this.y + vertical;
				
				while(tempy>=0 || tempy < 8){
					if(board.getTile(this.x, tempy).isOccupied() == false){
						possibleMoves.add(new Point(this.x, tempy));
					} else if(board.getTile(this.x, tempy).getPiece().getColour() == this.colour  || 
							board.getTile(this.x, tempy).getPiece() instanceof King){
						break;
					} else {
						possibleMoves.add(new Point(this.x, tempy));
						break;
					}
					tempy += vertical;
				}
			} catch (Exception e) {
				
			}
		}
	
		return possibleMoves;
}
	
	@Override
	public String toString(){
		if(this.getColour() == true){
			return "black_rook";
		} else {
			return "white_rook";
		}
	}

}
