package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;

public class Pawn extends Piece{

	public Pawn(int x, int y, Boolean colour) {
		super(x, y,colour);
	}
	
	@Override
	public ArrayList<Point> getPossibleMoves(Board board) {
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		
		
		int tempx;
		int tempy;
		
		if(this.colour == false){
			tempx = this.x;
			tempy = this.y - 1;
			
			if((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)){
				if (board.getTile(tempx, tempy).isOccupied() == false) {
					possibleMoves.add(new Point(tempx, tempy));
					if(this.y == 6 && board.getTile(tempx, this.y - 2).isOccupied() == false ) {
						possibleMoves.add(new Point(tempx, this.y  - 2));
					} 
				}
			}
			
			for(int i = -1; i <= 1; i += 2){
				tempx = this.x + i;
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if (board.getTile(tempx, tempy).isOccupied() && board.getTile(tempx, tempy).getPiece().getColour() == true) {
							possibleMoves.add(new Point(tempx, tempy));
					}
				}
			}

		} else if (this.colour == true){
			tempx = this.x;
			tempy = this.y + 1;
			
			if((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)){
				if (board.getTile(tempx, tempy).isOccupied() == false) {
					possibleMoves.add(new Point(tempx, tempy));
					if(this.y == 1 && board.getTile(tempx, this.y + 2).isOccupied() == false) {
						possibleMoves.add(new Point(tempx, this.y  + 2));
					}
				}
			}
			
			for(int j = -1; j <= 1; j += 2){
				tempx = this.x + j;
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if (board.getTile(tempx, tempy).isOccupied() && board.getTile(tempx, tempy).getPiece().getColour() == false) {
							possibleMoves.add(new Point(tempx, tempy));
					} 
				}
			}
		}
		
		return possibleMoves;
	}

	@Override
	public String toString(){
		if(this.getColour() == true){
			return "black_pawn";
		} else {
			return "white_pawn";
		}
	}

}
