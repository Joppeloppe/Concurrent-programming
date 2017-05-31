import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reader
extends Thread{
	
	private int stringLength;
	private Buffer buffer;
	private Thread thread;
	private List<String> stringList = new ArrayList<String>();
	private Random random = new Random();
	private boolean isSynchronized;
/*
 * Reader constructor.
 * @param Buffer to read from.
 * @param Boolean for synchronisation.
 */
	public Reader(Buffer buffer, boolean isSynchronized){
		this.buffer = buffer;
		setIsSynchronized(isSynchronized);
	}
/*
 * Creates and starts a thread.
 * sets the length of the string that is to be read.
 * @param Length of string.
 */
	public void start(int stringLength){
		thread = new Thread(this);
		this.stringLength = stringLength;
		
		thread.start();
	}
/*
 * While the thread is not finished, i.e. list is not the same length as the writer-string,
 * reads the character in the buffer.
 */
	public void run(){
		while(getIsSynchronized()){
			while(buffer.getChar() == null){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}								
			}
			
				Character character = buffer.getCharSync();
				stringList.add(character.toString());
				
				if (getListLenght() >= stringLength){
					System.out.println("Done reading.");
					getString();
					return;
				}
				
				try {
					System.out.println("Read: " + character);
					
					
					long value = random.nextInt(500) + 100;
					Thread.sleep(value);
					

				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
		}
		while(!getIsSynchronized()){
			if (buffer.getChar() == null)
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			
			stringList.add(buffer.getChar().toString());
			
			long value = random.nextInt(500) + 100;
			
			try {
				System.out.println("Read: " + buffer.getChar());
				
				if (getListLenght() >= stringLength){
					System.out.println("Done reading.");
					getString();
					return;
				}
				
				Thread.sleep(value);					
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	} 
	
	
/*
 *Getters and setters for the list length,
 * string that has been read,
 *  boolean for synchronise. 
 */
	public int getListLenght(){
		return stringList.size();
	}
	
	public void getString(){
		System.out.print("Read: ");
		
		for (int i = 0; i < getListLenght(); i++){
			System.out.print(stringList.get(i));
		}
		
		System.out.println("");
	}

	public boolean getIsSynchronized() {
		return isSynchronized;
	}

	private void setIsSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}
}
