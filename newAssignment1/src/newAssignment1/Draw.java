package newAssignment1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

public class Draw
extends JPanel
implements Runnable{

	private double angle = 0;
	
	private Thread thread;
/*
 * Creates and starts a new thread.
 * Sets angle of which the rectangle is drawn to 0.
 */
	public void start(){		
		thread = new Thread(this);
		thread.start();	
		setAngle(0);	
	}
/*
 * Interrupts a thread that is alive.
 */
	public void stop(){
		if(thread.isAlive() || !thread.equals(null))
			thread.interrupt();
	}
/*
 * Creates and draws a rectangle in a JPanel.
 */
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		AffineTransform transform = new AffineTransform();
		Rectangle rectangle = new Rectangle(115, 50, 50, 50);
		
		g2d.setColor(Color.BLUE);
		
		transform.rotate(Math.toRadians(getAngle()), rectangle.getX() + rectangle.getWidth() / 2,
				rectangle.getY() + rectangle.getHeight() / 2);
		
		Shape transformedRectangle = transform.createTransformedShape(rectangle);
		
		g2d.draw(transformedRectangle);
		g2d.fill(transformedRectangle);
		
		repaint();
	}
/*
 * While the thread is not interrupted it will increase the angle of
 * which the rectangle is drawn.
 */
	@Override
	public void run() {
		while(!thread.isInterrupted()){
				setAngle(getAngle() + 0.000001f);			
		}
	}

	
/*
 * Getter and setter for the angle of which the rectangle is drawn.
 */
	public double getAngle() {
		return angle;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
}
