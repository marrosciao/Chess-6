package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;


public class Knight extends Piece{


	public Knight(int x, int y,Boolean colour) {
		super(x, y, colour);
	}
	
	@Override
	public ArrayList<Point> getPossibleMoves(Board board) {
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		
		int	tempx;
		int tempy;
		
		for(int i = -1; i <= 1; i += 2){
			for(int j = -2; j <= 2; j += 4){
				tempx = this.x + i;
				tempy = this.y + j;
				
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if (board.getTile(tempx, tempy).isOccupied() == false || board.getTile(tempx, tempy).getPiece().getColour() != this.colour) {
							possibleMoves.add(new Point(tempx, tempy));
					} else if ( board.getTile(tempx, tempy).isOccupied() == true &&
							board.getTile(tempx, tempy).getPiece() instanceof King){
						break;
					}
				}
			}
		}
		
		for(int k = -1; k <= 1; k += 2){
			for(int j = -2; j <= 2; j += 4){
				tempx = this.x + j;
				tempy = this.y + k;
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if (board.getTile(tempx, tempy).isOccupied() == false || board.getTile(tempx, tempy).getPiece().getColour() != this.colour) {
							possibleMoves.add(new Point(tempx, tempy));
					} else if ( board.getTile(tempx, tempy).isOccupied() == true &&
							board.getTile(tempx, tempy).getPiece() instanceof King){
						break;
					}
				}
			}
		}
		
		return possibleMoves;
	}
	
	@Override
	public String toString(){
		if(this.getColour() == true){
			return "black_knight";
		} else {
			return "white_knight";
		}
	}


}
