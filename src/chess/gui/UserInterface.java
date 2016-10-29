package chess.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import chess.Board;
import chess.Main;
import chess.Tile;
import chess.piece.King;

public class UserInterface extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private int tileSize = 45;
	
	public void paint(Graphics g){
		super.paintComponent(g);
		
		drawBoard(g);
		
		kingCheckHighlight(Main.board, g);
		
		selectTileHighlight(g);
		
		possibleMovesHighlight(g);
		
		drawPieces(Main.board, g);
	}
	
	public void drawBoard(Graphics g){
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 8; col++){
				if((row + col)%2 == 0){
					g.setColor(new Color(255,200,100));
				} else {
					g.setColor(new Color(150,50,30));
				}
				g.fillRect(row * tileSize, col * tileSize, tileSize, tileSize);
			}
		}
	}
	
	
	public void kingCheckHighlight(Board board, Graphics g){
		
		King kingBlack = board.getKing(true);
		King kingWhite = board.getKing(false);
		
		if(kingBlack.isCheck(Main.board)){
			int itemX = kingBlack.getX();
			int itemY = kingBlack.getY();
			g.setColor(new Color(255,165,30));
			g.fillRect(itemX * tileSize, itemY * tileSize, tileSize, tileSize);
		} else if (kingWhite.isCheck(board)){
			int itemX = kingWhite.getX();
			int itemY = kingWhite.getY();
			g.setColor(new Color(255,165,30));
			g.fillRect(itemX * tileSize, itemY * tileSize, tileSize, tileSize);
		}
	}

	public void selectTileHighlight(Graphics g){
		if(Main.isSelected != null){
			int itemX = Main.isSelected.x;
			int itemY = Main.isSelected.y;
			g.setColor(new Color(30,144,255));
			g.fillRect(itemX * tileSize, itemY * tileSize, tileSize, tileSize);
		}
	}
	
	public void possibleMovesHighlight(Graphics g){
		if(Main.possibleM != null){
			for(Point item: Main.possibleM){
				int itemX = item.x;
				int itemY = item.y;
				g.setColor(new Color(0,153,76));
				g.fillOval(itemX * tileSize + (tileSize / 4), itemY * tileSize + (tileSize / 4), 20, 20);
				if(Main.board.getTile(itemX, itemY).isOccupied()){
					g.setColor(new Color(200,25,25));
					g.fillRect(itemX * tileSize, itemY * tileSize, tileSize, tileSize);
				}
			}
		}
	}
	
	public void drawPieces(Board board, Graphics g){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Tile tile = board.getTile(i, j);
				if(tile.isOccupied()){
					String type = tile.getPiece().toString();
					Image chessPiece = new ImageIcon("resources/" + type + ".png").getImage();
					g.drawImage(chessPiece, i * tileSize, j * tileSize, tileSize, tileSize, this);
				}
			}
		}
	}
	
	public int getTileSize(){
		return this.tileSize;
	}
	
	public void setTileSize(int size){
		this.tileSize = size;
	}
}
