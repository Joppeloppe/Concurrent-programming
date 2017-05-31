package newAssignment1;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Text 
extends JPanel
implements Runnable{

	private int x, y;
	private String text;
	
	private Random random = new Random();
	private Thread thread;
/*
 * Constructor which sets the text to be displayed, 
 * this text is hardcoded in the GUI.
 */
	public Text(String textToDisplay){
		setText(textToDisplay);
	}
/*
 * Creates and starts a new Thread.
 */
	public void start(){	
		thread = new Thread(this);
		thread.start();							
	}
/*
 * Interrupts a thread that is alive.
 */
	public void stop(){
		if(thread.isAlive() || !thread.equals(null))
			thread.interrupt();
	}
/*
 * Draws the text in the JPanel.
 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.drawString(getText(), x, y);
	
		repaint();
 	}
/*
 * While the thread is not interrupted it will randomize
 * the x- and y- values of which the text is displayed at
 * in the JPanel.
 */
	@Override
	public void run() {
		while(!thread.isInterrupted()){
			x = random.nextInt(400);
			y = random.nextInt(200);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				return;
			}
		}	
	}

	
/*
 * Getter and setter for the text that is to be displayed.
 */
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
