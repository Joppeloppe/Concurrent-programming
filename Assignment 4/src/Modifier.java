
public class Modifier
implements Runnable{

	private String find,
		replace;
	
	private BoundedBuffer buffer;
	
	private Thread thread;
/*
 * Modifier constructor.
 * Sets buffer, string to be found, and string to be replaced.
 * Starts a new thread.
 */
	public Modifier(BoundedBuffer buffer, String find, String replace){
		this.buffer = buffer;
		this.find = find;
		this.replace = replace;
		
		start();
	}
/*
 * Creates and starts a new modifier thread.	
 */
	private void start(){
		thread = new Thread(this);
		thread.start();
		
		System.out.println("Modifier thread started.");
	}
/*
 * Stops a modifier thread.	
 */
	private void stop(){
		if(thread.isAlive() || !thread.equals(null))
			thread.interrupt();
	}
/*
 * While the modifier thread is alive it will find and replace string in the buffer.
 */
	@Override
	public void run() {
		while(!thread.isInterrupted()){
			buffer.modifyBuffer(find, replace);
		}
	}
}
