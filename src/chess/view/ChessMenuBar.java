package chess.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ChessMenuBar extends JMenuBar{
	
	private JMenuItem mn_Undo;
	private JMenu mn_Game;
	private JMenuItem mnitm_NewGame;
	private JMenuItem mnitm_Exit;
	
	public ChessMenuBar(){
		mn_Game = new JMenu("Game");
		mnitm_NewGame = new JMenuItem("New Game");
		mnitm_Exit = new JMenuItem("Exit");
		mn_Undo = new JMenuItem("Undo");
		
		mn_Game.add(mnitm_NewGame);
		mn_Game.add(mn_Undo);
		mn_Game.add(mnitm_Exit);
		
		this.add(mn_Game);
		
		//short-cuts
		mn_Undo.setAccelerator(KeyStroke.getKeyStroke('U', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnitm_NewGame.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
				

		//Close Application
		//mntmExit.addActionListener(new ActionListener() {
		//    public void actionPerformed(ActionEvent e)
		//    {
		//       System.exit(0);
		//    }
		//});
	}
}
