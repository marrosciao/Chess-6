package chess.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import chess.Board;
import chess.Game;
import chess.Move;
import chess.piece.King;
import chess.piece.Piece;
import chess.player.Alliance;

public class BoardView extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private int tileSize = Tile.TILE_SIZE; //px
	public static final String PATH = "resources/";
	private Board board;
	private Tile[][] tiles;
	
	private Piece selected;
	private ArrayList<Move> possibleM;
	
	
	public BoardView(Board board){
		this.setPreferredSize(new Dimension(tileSize * Board.BOARD_ROW, tileSize * Board.BOARD_COLUMN));
		this.tiles = new Tile[Board.BOARD_ROW][Board.BOARD_COLUMN];
		this.board = board;
	}
	
	public void setSelected(Piece piece){
		this.selected = piece;
	}
	
	public Piece getSelected(){
		return selected;
	}
	
	public void setPossibleM(ArrayList<Move> possibleM){
		this.possibleM = possibleM;
	}
	
	public ArrayList getPossibleM(){
		return possibleM;
	}
	
	//https://docs.oracle.com/javase/tutorial/2d/advanced/quality.html
	public void paint(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_ANTIALIASING,
	             RenderingHints.VALUE_ANTIALIAS_ON
	    		);
	    g2.setRenderingHints(rh);
		
		super.paintComponent(g2);
		drawBoard(g2);	
		kingCheckHighlight(board, g2);	
		previousMoveHighlight(g2, Game.getTurn());		
		
		if(selected != null && possibleM != null){
			selectTileHighlight(g2, selected);
			possibleMovesHighlight(g2, possibleM);
			selected = null;
			possibleM = null;
		}
		
		drawPieces(board, g2);
		
		
	}
	
	public void drawBoard(Graphics2D g){
		for(int row = 0; row < Board.BOARD_ROW; row++){
			for(int col = 0; col < Board.BOARD_COLUMN; col++){
				Tile tile;
				if((row + col)%2 == 0)
					tile = new Tile(row, col, new Color(240, 217,181));
				else 
					tile = new Tile(row, col, new Color(181,136,99));
				tile.paintTile(g);
				tiles[row][col] = tile;
			}
		}
	}
	
	public void drawPieces(Board board, Graphics2D g){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				if(board.isOccupied(i,j)){
					String type = board.getPiece(i,j).toString();
					//Image chessPiece = new ImageIcon(PATH + type + ".png").getImage();
					
					BufferedImage img = null;
					try {
					    img = ImageIO.read(new File(PATH + type + ".png"));
					} catch (IOException e) {
					}
					
					g.drawImage(img, i * tileSize, j * tileSize, tileSize, tileSize, this);
				}
			}
		}
	}
	
	public void selectTileHighlight(Graphics2D g, Piece piece){
		if(piece != null){
			Point pos = piece.getPosition();
			tiles[pos.x][pos.y].paintSelected(g);
		}
	}
	
	public void possibleMovesHighlight(Graphics2D g, ArrayList<Move> possibleM){
		if(possibleM != null){
			for(Move item : possibleM){
				Point dest = item.getMovePosition();
				tiles[dest.x][dest.y].paintPossibleMove(board, g);
			}
		}
	}
	
	public void kingCheckHighlight(Board board, Graphics2D g){
		Alliance white = Alliance.WHITE;
		Alliance black = Alliance.BLACK;
		int itemX = 0;
		int itemY = 0;
		boolean isCheck = Game.getPlayer(Game.getTurn()).isCheck(board);
		boolean isCheckMate = Game.getPlayer(Game.getTurn()).isCheckMate(board);
		if(isCheck || isCheckMate){
			if(Game.getTurn() == white && (isCheck || isCheckMate)){
				itemX  = Game.getPlayer(white).getKing(board, white).getX();
				itemY =  Game.getPlayer(white).getKing(board, white).getY();
			} else if(Game.getTurn() == black && (isCheck || isCheckMate)){
				itemX  = Game.getPlayer(black).getKing(board, black).getX();
				itemY =  Game.getPlayer(black).getKing(board, black).getY();
			}
			if(isCheckMate)
				tiles[itemX][itemY].paintKingCheckMate(g);
			else if (isCheck)
				tiles[itemX][itemY].paintKingCheck(g);
		}
	}

	
	public void previousMoveHighlight(Graphics2D g, Alliance turn){
		
		ArrayList<Move> player1Hist = Game.getPlayer(turn.next()).getMoveHistory();
		
		if(player1Hist.size() > 0){
			Move pm = player1Hist.get(player1Hist.size() - 1);
			int itemX = pm.getCurrentPosition().x;
			int itemY = pm.getCurrentPosition().y;
			
			int movX = pm.getMovePosition().x;
			int movY = pm.getMovePosition().y;
			
			//102,205,170
			g.setColor(new Color(102,205,170));
			g.fillRect(itemX * tileSize, itemY * tileSize, tileSize, tileSize);
			
			g.setColor(new Color(102,205,170, 175));
			g.fillRect(movX * tileSize, movY * tileSize, tileSize, tileSize);
		}
		
	}
	
	
	
}
