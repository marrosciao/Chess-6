package chess;

import java.awt.EventQueue;

import chess.player.Alliance;
import chess.player.Player;

	public class Chess {
		public static void main(String[] args) {
			http://stackoverflow.com/a/310575/7066174
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", ChessView.TITLE)
            
            EventQueue.invokeLater(new Runnable() {
    			public void run() {
					try {
						Board b = new Board();
						
						Player playerWhite = new Player(Alliance.WHITE);
						Player playerBlack = new Player(Alliance.BLACK);
						
						Game game = new Game(playerWhite, playerBlack, b);
						game.setUp(b);
						
						ChessView view = new ChessView(b);
						
						//ChessController controller = 
						new ChessController(game, view);
						
						view.setVisible(true);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
    			}
            });
	}
	
}
