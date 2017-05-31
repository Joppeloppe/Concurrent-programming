import java.util.Random;

public class Writer
extends Thread {

	private char[] charArray;
	
	private Buffer buffer;
	private Thread thread;
	private String text;
	private Random random = new Random();
	private boolean isSynchronized;
/*
 * Constructor for the writer.
 * @param String to write.
 * @param Buffer to be written to.
 * @param Boolean to tell if program is to run synchronised or not.
 */
	public Writer(String string, Buffer buffer, boolean isSynchronized){
		setText(string);
		this.buffer = buffer;
		setIsSynchronized(isSynchronized);
	}
/*
 * Creates and starts a thread.
 * Casts the string to be written into a char array.
 */
	public void start(){
		thread = new Thread(this);
		
		charArray = text.toCharArray();
		thread.start();
	}
/*
 * Thread writes the next character in line to the buffer.
 * Also writes what character has been written.
 */
	public void run(){
		if (getIsSynchronized()){
				for (int i = 0; i < charArray.length; i++){
					buffer.setCharSync(text.charAt(i));
					
					try {
						long value = random.nextInt(500) + 100;
						Thread.sleep(value);
						
						System.out.println("Wrote: " + text.charAt(i));
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}				
				}		
				
				System.out.println("Done writing.\n"
						+ "Wrote: " + text);
				
				buffer.notifyOther();
		}else{
			for (int i = 0; i < charArray.length; i++){
				buffer.setChar(text.charAt(i));
				
				long value = random.nextInt(500) + 100;
				
				try {
					System.out.println("Wrote: " + text.charAt(i));
					
					Thread.sleep(value);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}			
			}
			
			System.out.println("Done writing.\n"
					+ "Wrote: " + text);
		}
	}

	
	
/*
 * Getters and setters for the text to be displayed, 
 * and the boolean for synchronising. 
 */
	public String getText() {
		return text;
	}

	private void setText(String text) {
		this.text = text;
	}

	public boolean getIsSynchronized() {
		return isSynchronized;
	}

	private void setIsSynchronized(boolean isSyncronized) {
		this.isSynchronized = isSyncronized;
	}
}
