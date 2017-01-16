package chess;

import java.awt.Point;

import chess.piece.Bishop;
import chess.piece.King;
import chess.piece.Knight;
import chess.piece.Pawn;
import chess.piece.Piece;
import chess.piece.Queen;
import chess.piece.Rook;

public class MoveNotationBuilder {

	
	//<move descriptor> :: = <from square><to square>[<promoted to>]
	//to see these Uni-code character you must have a a font that supports it
	//also go to Windows > Preferences > General > Workspace, and change
	//text file encoding to UTF-8. 
	//uni-code symbols retrieved from http://www.unicode.org/charts/PDF/U2600.pdf
	//also see https://en.wikipedia.org/wiki/Chess_symbols_in_Unicode
	public static final String KING = "\u2654"; //K
	public static final String QUEEN = "\u2655"; //Q
	public static final String KNIGHT = "\u2658"; //N or Kn
	public static final String BISHOP = "\u2657"; //B
	public static final String ROOK = "\u2656"; //R
	
	public static final String CHECK = "+";
	public static final String CASTLE_KING_SIDE = "O-O";
	public static final String CAPTURE = "x";
	public static final String CHECK_MATE = "#"; //or ++ (however note that ++ can mean double check)
	public static final String CASTLE_QUEEN_SIDE = "O-O-O";
	
	public String getNotation(Point curr, Point dest, Piece piece, Piece captured){
		String fileName = Board.BOARD_LETTERS.substring((int) curr.getX(), (int) curr.getX()+1);
		String tag, takes, square, status = "";
		String aN = "";
		if(piece instanceof King)
			aN +=  KING;
		if(piece instanceof Queen)
			aN +=  QUEEN;
		if(piece instanceof Rook)
			aN +=  ROOK;
		if(piece instanceof Bishop)
			aN +=  BISHOP;
		if(piece instanceof Knight)
			aN +=  KNIGHT;
		
		if(captured != null){
			if(piece instanceof Pawn)
				aN += fileName;
			aN += "x";
		}
		
		////if(isThisCheck(board))
		//	aN += CHECK;
		//else if (isCheckMate)
		//	aN += CHECK_MATE;
		
		//https://www.cheatography.com/davechild/cheat-sheets/chess-algebraic-notation/
		aN += Board.getSqaureName(dest.x, dest.y);
		
		return aN;
	}
	
}
