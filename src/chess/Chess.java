package chess;

import java.awt.EventQueue;
import java.io.File;

import javax.imageio.ImageIO;

import chess.player.AI;
import chess.player.Alliance;
import chess.player.Player;

	public class Chess {
		public static void main(String[] args) {
			  try { 
	                
	                EventQueue.invokeLater(new Runnable() {
	    				public void run() {
	    					try {
	    						
	    						//http://stackoverflow.com/a/310575/7066174
	    				    	System.setProperty("apple.laf.useScreenMenuBar", "true");
	    				    	System.setProperty("com.apple.mrj.application.apple.menu.about.name", ChessView.TITLE);
	    					
	    						Board b = new Board();
	    						
	    						Player playerWhite = new Player(Alliance.WHITE);
	    						Player playerBlack = new Player(Alliance.BLACK);
	    						
	    						Game game = new Game(playerWhite, playerBlack, b);
	    						game.setUp(b);
	    						
	    						ChessView view = new ChessView(b, playerWhite, playerBlack);
	    						
	    						//ChessController controller = 
	    						new ChessController(game, view);
	    						
	    						//http://icons.iconarchive.com/icons/aha-soft/chess/256/chess-board-icon.png
	    						//view.setIconImage(ImageIO.read(new File("resources/game_icon.png")));
	    						view.setResizable(false);
	    						view.pack();
	    						
	    						view.setVisible(true);
	    						view.setLocationRelativeTo(null);
	    						
	    						playerWhite.startClock();
	    						playerBlack.startClock();
	    						playerBlack.pauseClock();
	    					} catch (Exception e) {
	    						e.printStackTrace();
	    					}
	    				}
	    			}
	    		);
	                
			  } catch(Exception e){}
			 
	}
	
}
