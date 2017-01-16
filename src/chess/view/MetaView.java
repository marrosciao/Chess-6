package chess.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import chess.player.Alliance;
import chess.player.Player;
import chess.time.Time;


public class MetaView extends JPanel{

	private static final long serialVersionUID = 1L;
	private Time time;
	private Player player;
	private Timer timer;
	
	public MetaView(Player player, Time time){
		this.player = player;
		this.time = time;
		this.timer = new Timer();
		startClock();
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
	    RenderingHints rh = new RenderingHints(
	             RenderingHints.KEY_TEXT_ANTIALIASING,
	             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	    g2.setRenderingHints(rh);
		
	    drawMeta(g2);
	}
	
	//we cannot continuously call the repaint method because
    //it can be expensive, so I decided to use a timer that
    //updates every second
	private void startClock(){
		timer.scheduleAtFixedRate(new TimerTask(){
			public void run(){
				update();
			}
		}, 1000, 1000);
	}
	
	public void drawMeta(Graphics2D g2){
	    g2.setFont(new Font("Tahoma", Font.PLAIN, 30)); 
		g2.setColor(new Color(112,112,112));
		 
	    String t = time.getTime();
	    
	    if(player.getAllegiance() == Alliance.WHITE){
			g2.drawString(t, 10, 40);
			g2.setFont(new Font("Tahoma", Font.PLAIN, 15)); 
			g2.drawString(player.toString(), 11, 60);
	    } else if(player.getAllegiance() == Alliance.BLACK){ 
			g2.drawString(t, 9, 55);
			g2.setFont(new Font("Tahoma", Font.PLAIN, 15)); 
			g2.drawString(player.toString(), 11, 25);
	    }
	}
	
	public void update(){
		this.repaint();
	}
	
}
