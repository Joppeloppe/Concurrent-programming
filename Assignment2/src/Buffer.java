import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer{

	private Character character;
	
	private Lock lock = new ReentrantLock();
/*
 * Returns the character.
 */
	public Character getChar() {
		return character;
	}
/*
 * Sets the character.
 * @param Character to be written.
 */
	public void setChar(Character character) {
		this.character = character;
	}
/*
 * Returns the character in a synchronised manner.
 */
	public synchronized Character getCharSync(){	
		
		lock.lock();
		
		try{
			return character;			
		}finally{
			notify();
			
			lock.unlock();
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
/*
 * Sets the character in a synchronised manner.
 * @param Character to be written.
 */
	public synchronized void setCharSync(Character character){
		
		lock.lock();
		
		try{
			this.character = character;			
		}finally{
			notify();
			
			lock.unlock();
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
/*
 * Notifies waiting thread.
 */
	public synchronized void notifyOther(){
		notify();
	}
}