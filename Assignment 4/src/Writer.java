import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Writer
implements Runnable{
	
	private File file;
	
	private Thread thread;
	private BoundedBuffer buffer;
/*
 * Writer constructor.
 * Sets buffer, and file to be written from.
 * Starts a new thread.
 */
	public Writer(BoundedBuffer buffer, File file){
		this.buffer = buffer;
		this.file = file;
		
		start();
	}
/*
 * Creates and starts a new writer thread.	
 */
	public void start(){
		thread = new Thread(this);
		thread.start();
		
		System.out.println("Writer thread started.");
	}
/*
 * Stops a writer thread.	
 */
	private void stop(){
		if(thread.isAlive() || !thread.equals(null))
			thread.interrupt();
	}	
/*
 * Reads the next line in the file and adds that to the buffer.
 * @param File to be written from.	
 */
	private void fileHandler(File file) throws IOException, InterruptedException{
		Path path = Paths.get(file.toString());
		
		String line = null;
		
		try(BufferedReader reader = Files.newBufferedReader(path)){
			while((line = reader.readLine()) != null){
				buffer.writeBuffer(line);
				
				Thread.sleep(0);
			}	
		}
	}
/*
 * While the thread is not interrupted or
 * is not finished with the file it will write to the buffer.
 */
	@Override
	public void run() {	
		while(!thread.isInterrupted()){
			try {
				fileHandler(file);
				
				stop();
			} catch (IOException e) {
				System.out.println("Failure: Writer -> run.");
			} catch (InterruptedException e) {
				System.out.println("Failure: Writer -> run.");
			}
		}
	}
}