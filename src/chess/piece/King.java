package chess.piece;

import java.awt.Point;
import java.util.ArrayList;

import chess.Board;

public class King extends Piece{
	
	static boolean isChecked;
	static boolean isCheckMate;
	
	public King(int x, int y, Boolean colour) {
		super(x, y, colour);
		
	}
	
	@Override
	public ArrayList<Point> getPossibleMoves(Board board) {
		ArrayList<Point> possibleMoves = new ArrayList<Point>();
		
		int tempx;
		int tempy;
		
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				tempx = this.x + i;
				tempy = this.y + j;
				
				if(!(tempx == 0 && tempy == 0)) {
					if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
						if (board.getTile(tempx, tempy).isOccupied() == false || board.getTile(tempx, tempy).getPiece().getColour() != this.colour) {
								if(this.isSafe(tempx,tempy, board)){
									possibleMoves.add(new Point(tempx, tempy));
								}
						}
					}
				}
			}
		}
		
		return possibleMoves;
	}
	
	public Boolean isCheck(Board board){
		if(this.isSafe(this.x, this.y, board) == false)
			return true;
		return false;
	}
	
	public Boolean isCheckMate(Board board){
		if(this.isCheck(board) && this.getPossibleMoves(board).size() == 0)
			return true;
		return false;
	}
	
	public Boolean isStaleMate(Board board){
		//if king isnt in check and all pieces have no possible moves
		return false;
	}
	
	//return true if safe
	public Boolean isSafe(int x, int y, Board board){
		Piece tempKing = this;
		board.getTile(tempKing.x, tempKing.y).clearPiece();
		
		int tempy;
		int tempx;
		
		//checking left / right and up / down
		for(int horizontal = -1; horizontal <= 1; horizontal += 2 ){
			try{
				tempx = x + horizontal;
				
				while(tempx >=0 || tempx < 8){
					if(board.getTile(tempx, y).isOccupied() == false){
						//continue;
					} else if(board.getTile(tempx, y).getPiece().getColour() == this.colour ||
							board.getTile(tempx, y).getPiece() instanceof King){
						break;
					} else {
						if(board.getTile(tempx, y).getPiece() instanceof Rook || board.getTile(tempx, y).getPiece() instanceof Queen){
							board.getTile(tempKing.x, tempKing.y).setPiece(this);
							return false;
						} else {
							break;
						}
					}
					
					tempx += horizontal;
				
				}
			} catch (Exception e) {
				
			}
		}
		
		for(int vertical = -1; vertical <= 1; vertical += 2 ){
			try{
				tempy = y + vertical;
				
				while(tempy>=0 || tempy < 8){
					if(board.getTile(x, tempy).isOccupied() == false){
						//break;
					} else if(board.getTile(x, tempy).getPiece().getColour() == this.colour ||
							board.getTile(x, tempy).getPiece() instanceof King){
						break;
					} else {
						if(board.getTile(x, tempy).getPiece() instanceof Rook || board.getTile(x, tempy).getPiece() instanceof Queen){
							board.getTile(tempKing.x, tempKing.y).setPiece(this);
							return false;
						} else {
							break;
						}
					}
					tempy += vertical;
				}
			} catch (Exception e) {
				
			}
		}
		
		//check diagonals
		for(int i = -1; i <= 1; i += 2){
			for(int j = -1; j <= 1; j += 2){
				try{
					tempx = x + i;
					tempy = y + j;
					
					while(tempx >=0 || tempx < 8 || tempy >= 0 || tempy < 8){
						if(board.getTile(tempx, tempy).isOccupied() == false){
							//continue;
						} else if(board.getTile(tempx, tempy).getPiece().getColour() == this.colour){
							break;
						} else {
							if(board.getTile(tempx, tempy).getPiece() instanceof Bishop || board.getTile(tempx, tempy).getPiece() instanceof Queen){
								board.getTile(tempKing.x, tempKing.y).setPiece(this);
								return false;
							} else {
								break;
							}
						}
						tempx += i;
						tempy += j;
					}
					
				} catch(Exception e){
					
				}
				
			}
		}
		
		//check knight
		for(int k = -1; k <= 1; k += 2){
			for(int l = -2; l <= 2; l += 4){
				tempx = x + k;
				tempy = y + l;
				
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if(board.getTile(tempx, tempy).isOccupied() == true 
							&& board.getTile(tempx, tempy).getPiece().colour != this.colour
							&& board.getTile(tempx, tempy).getPiece() instanceof Knight){
						board.getTile(tempKing.x, tempKing.y).setPiece(this);
						return false;
					}
				}
			}
		}
		
		for(int s = -1; s <= 1; s += 2){
			for(int t = -2; t <= 2; t += 4){
				tempx = x + t;
				tempy = y + s;
				
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if(board.getTile(tempx, tempy).isOccupied() == true 
							&& board.getTile(tempx, tempy).getPiece().colour != this.colour
							&& board.getTile(tempx, tempy).getPiece() instanceof Knight){
						board.getTile(tempKing.x, tempKing.y).setPiece(this);
						return false;
					}
				}
			}
		}
		
		
		//check pawn, might be the other way round
		//true = black false = white
		if(this.colour == true){
			tempx = x;
			tempy = y + 1;
			
			for(int o = -1; o <= 1; o += 2){
				tempx = x + o;
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if(board.getTile(tempx, tempy).isOccupied() == true 
							&& board.getTile(tempx, tempy).getPiece().colour == false
							&& board.getTile(tempx, tempy).getPiece() instanceof Pawn){
						board.getTile(tempKing.x, tempKing.y).setPiece(this);
						return false;
					}
				}
			}

		} else if (this.colour == false){
			tempx = x;
			//somthing wierd with tempy should be tempy = this.y + 1
			tempy = y - 1;

			
			for(int p = -1; p <= 1; p += 2){
				tempx = x + p;
				if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
					if(board.getTile(tempx, tempy).isOccupied() == true 
							&& board.getTile(tempx, tempy).getPiece().colour == true
							&& board.getTile(tempx, tempy).getPiece() instanceof Pawn){
						board.getTile(tempKing.x, tempKing.y).setPiece(this);
						return false;
					}
				}
			}
		}
				
		//check king
		//not sure this works
		for(int q = -1; q <= 1; q++){
			for(int r = -1; r <= 1; r++){
				tempx = x + q;
				tempy = y + r;
				
				if(!(tempx == 0 && tempy == 0)) {
					if ((tempx >= 0 && tempx < 8 && tempy >= 0 && tempy < 8)) {
						if (board.getTile(tempx, tempy).isOccupied() == true 
								&& board.getTile(tempx, tempy).getPiece().getColour() != this.colour
								&& board.getTile(tempx, tempy).getPiece() instanceof King) {
							board.getTile(tempKing.x, tempKing.y).setPiece(this);
							return false;
						}
					}
				}
			}
		}

		board.getTile(tempKing.x, tempKing.y).setPiece(this);
		return true;
	}
	
	@Override
	public String toString(){
		if(this.getColour() == true){
			return "black_king";
		} else {
			return "white_king";
		}
	}

}
