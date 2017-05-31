import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
	private int max,
	writePosition = 0,
	readPosition = 0, 
	findPosition;

	private String[] stringArray;
	private State[] stateArray;
	
	private Lock lock = new ReentrantLock();
/*
 * Enum of State, 
 * New - a new String has been written and awaits processing.
 * Checked - a string has been processed and can be copied.
 * Empty - available space for a new string to be written to.
 */
	private enum State{
		New,
		Checked,
		Empty
	}
/*
 * BoundedBuffer constructor.
 * Sets the max number of strings to be in the buffer.
 * Creates two arrays, one for the strings and one for the states of these strings.
 * Sets all states to empty.
 */
	public BoundedBuffer(){
		super();
		
		setMax(10);
		
		stringArray = new String[getMax()];
		stateArray = new State[getMax()];
		
		for(int i = 0; i < getMax(); i++){
			stateArray[i] = State.Empty;
		}
		
		System.out.println("New buffer created with " + getMax() + " as max number of lines.");
	}
/*
 * Adds a new string to the buffer.
 * @param String to be added.
 */
	public synchronized void writeBuffer(String line){
		while(stateArray[getWritePosition()] != State.Empty){
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Failure: Buffer -> writeBuffer.");
			}			
		}
		lock.lock();
		
		stringArray[getWritePosition()] = line;
		System.out.println("Wrote: " + line);
		stateArray[getWritePosition()] = State.New;
		setWritePosition(getWritePosition() + 1);
		
		notify();
		lock.unlock();
	}
/*
 * Modifies the buffer.
 * If the find string is empty the buffer will not replace anything.
 * @param String to be found.
 * @param String to replace found string with.
 */
	public synchronized void modifyBuffer(String find, String replace){
		while(stateArray[getFindPosition()] != State.New){
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println("Failure: Buffer -> modifyBuffer.");
			}
		}
		
		if(find == ""){
			lock.lock();
			
			stateArray[getFindPosition()] = State.Checked;
			setFindPosition(getFindPosition() + 1);
			
			notify();
			lock.unlock();
			
			return;
		}
		
		lock.lock();
		
		String line = stringArray[getFindPosition()];
		stringArray[getFindPosition()] = stringArray[getFindPosition()].replaceAll(find, replace);
		System.out.println("OLD: " + line + "\nNEW: " + stringArray[getFindPosition()]);
		stateArray[getFindPosition()] = State.Checked;
		setFindPosition(getFindPosition() + 1);
		
		notify();
		lock.unlock();
	}
/*
 * Returns the string at read position.	
 */
	public synchronized String readBuffer(){
		try{
			while(stateArray[getReadPosition()] != State.Checked){
				try {
					wait();
				} catch (InterruptedException e) {
					System.out.println("Failure: Buffer -> readBuffer.");
				}				
			}
			
			lock.lock();
				
			return stringArray[getReadPosition()];	
		}finally{
			System.out.println("Read: " + stringArray[getReadPosition()] + "\n");
			stateArray[getReadPosition()] = State.Empty;
			setReadPosition(getReadPosition() + 1);
			
			notify();
			lock.unlock();
		}
	}

	
	
/*
 * Getters and setters for max number of strings in the buffer,
 * writer-, reader-, and find position.	
 */
	public int getMax() {
		return max;
	}

	private void setMax(int max) {
		this.max = max;
	}

	public int getWritePosition() {
		return writePosition;
	}

	private void setWritePosition(int writePosition) {
		this.writePosition = writePosition % getMax();
	}

	public int getReadPosition() {
		return readPosition;
	}

	private void setReadPosition(int readPosition) {
		this.readPosition = readPosition % getMax();
	}

	private int getFindPosition() {
		return findPosition;
	}

	private void setFindPosition(int findPosition) {
		this.findPosition = findPosition % getMax();
	}
}

