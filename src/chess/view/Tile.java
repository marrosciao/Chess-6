package chess.view;

/**
 * 
 * @author sHeston
 *
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import chess.Board;

public class Tile{
	
	public static int TILE_SIZE = 45; //px
	
	private int x, y;
	private Color colour;
	
	private static Color COLOUR_SELECT = new Color(30,144,255, 175),
						 COLOUR_POSSIBLE_TILE = new Color(0,163,76, 220),
						 COLOUR_POSSIBLE_ATK = new Color(220,25,25, 175),
						 
						 COLOUR_KING_CHK = new Color(255,105,80, 175),
						 COLOUR_KING_CHKM = new Color(255,220,220, 175);
	
	public Tile(int x, int y, Color colour){
		this.colour = colour;
		this.x = x;
		this.y = y;
	}
	
	public void paintTile(Graphics2D g){
		g.setColor(this.colour);
		g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	public void paintPossibleMove(Board board, Graphics2D g){
		int ovalSize = 20;
		int halfTile = (int) (TILE_SIZE / 2) - (ovalSize / 2);
		int x2 = (x*TILE_SIZE) + halfTile;
		int y2 = (y*TILE_SIZE) + halfTile;
		
		if(board.isOccupied(x, y)){
			g.setColor(COLOUR_POSSIBLE_ATK);
			g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		} else {
			g.setColor(COLOUR_POSSIBLE_TILE);
			g.fillOval(x2, y2, ovalSize, ovalSize);
		}
	}
	
	public void paintSelected(Graphics2D g){
		g.setColor(COLOUR_SELECT);
		g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	public void paintKingCheck(Graphics2D g){
		g.setColor(COLOUR_KING_CHK);
		g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
	public void paintKingCheckMate(Graphics2D g){
		g.setColor(COLOUR_KING_CHKM);
		g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
	}
	
}
