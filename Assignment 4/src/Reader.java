import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Reader
implements Runnable{

	private int readPosition = 0;
	private String[] stringArray;
	
	private BoundedBuffer buffer;
	
	private Thread thread;
/*
 * Reader constructor.
 * Sets the buffer.
 * Creates a new string array.	
 * Starts a new thread.
 */
	public Reader(BoundedBuffer buffer, int lenght){
		this.buffer = buffer;
		stringArray = new String[lenght];
		
		start();
	}
/*
 * Creates and starts a new thread.	
 */
	public void start(){
		thread = new Thread(this);
		thread.start();
		
		System.out.println("Reader thread started.");
	}
/*
 * Stops a thread.	
 */
	private void stop(){
		if(thread.isAlive() || !thread.equals(null))
			thread.interrupt();
	}	
/*
 * While the thread is not interrupted the thread will try and read from the buffer.
 */
	@Override
	public void run() {
		while(!thread.isInterrupted()){
			String newString =  buffer.readBuffer();
			
			stringArray[readPosition] = newString;
			readPosition++;
			
			if(stringArray.length >= buffer.getMax()){
				
				try {
					File file = new File("copy.txt");
					
					if(!file.exists()){
						file.createNewFile();
					}
					
					FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					
					for(int i = 0; i < readPosition; i++){
						bufferedWriter.append(stringArray[i]);
						bufferedWriter.newLine();
					}
					
					bufferedWriter.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				stop();
			}
			
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				System.out.print("");
			}
		}
	}
}