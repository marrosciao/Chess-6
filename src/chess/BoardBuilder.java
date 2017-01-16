package chess;

import chess.piece.*;
import chess.player.Alliance;

public class BoardBuilder {

	private Board board;
	
	public BoardBuilder(Board board){
		this.board = board;
	}
	
	public void build(){
		board.clear();
		initPieces(7, Alliance.WHITE);
		initPieces(0, Alliance.BLACK);
	}
	
	private void initPieces(int column, Alliance alliance){
		setPiece(new Rook(0, column, alliance));
		setPiece(new Knight(1, column, alliance));
		setPiece(new Bishop(2, column, alliance));
		setPiece(new Queen(3, column, alliance));
		setPiece(new Bishop(5, column, alliance));
		setPiece(new Knight(6, column, alliance));
		setPiece(new Rook(7, column, alliance));
		for(int i = 0; i < Board.BOARD_ROW; i++){
			if(alliance == Alliance.WHITE)
				setPiece(new Pawn(i, column - 1, alliance));
			if(alliance == Alliance.BLACK)
				setPiece(new Pawn(i, column + 1, alliance));
		}
		
		setPiece(new King(4, column, alliance));
	}
	
	private void setPiece(Piece piece){
		board.setTile(piece.getX(),piece.getY(), piece);
	}
	
}
