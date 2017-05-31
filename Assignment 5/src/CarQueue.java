import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CarQueue
implements Runnable{

	private final int MAX_CARS = 15;
	private int count = 0;
	private boolean active = false;
	
	private String name;
	private Car queue[];
	private Random random = new Random();
	private Lock lock = new ReentrantLock();

/*
 * CarQueue constructor.
 * sets name of queue, creates a new array of cars.
 */
	public CarQueue(String name){
		setName(name);
		
		queue = new Car[MAX_CARS];
	}
/*
 * If there is space available in the queue it will add a new car
 * with a random waiting period.
 */
	@Override
	public void run(){
		while(active){
			if (getCount() < MAX_CARS - 1){
				int i = random.nextInt(100);
				
				queue[getCount()] = new Car(i);
				setCount(getCount() + 1);
			}
			
			try {
				System.out.println(getName() + ": " + getCount());
				int i = random.nextInt(1000) + 1000;
				
				Thread.sleep(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
/*
 * Returns car to be parked.
 */
	public synchronized Car parkCar(){
		lock.lock();
		
		try{
			if(getCount() > 0){
				setCount(getCount() - 1);
				
				return queue[0];
			}
			
			return null;			
		}finally{
			lock.unlock();
		}
	}
/*
 * Get/set for name,
 * active boolean,
 * number of cars in queue
 */
	public String getName() {
		return name;
	}
	
	private void setName(String name) {
		this.name = name;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
