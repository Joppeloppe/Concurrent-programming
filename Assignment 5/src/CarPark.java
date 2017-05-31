import java.util.Random;

public class CarPark
implements Runnable{

	private final int MAX_CARS = 50;
	private int count = 0;
	private boolean active = false;
	
	private Car parkedCars[];
	private CarQueue queues[];
	private Random random = new Random();
	
	private Status statusArray[];
	
/*
 * Status for parking slots.
 * Occupied if a car is in slot.
 * Empty if no car is in slot.
 */
	private enum Status{
		Occupied,
		Empty
	}
/*
 * CarPark constructor.
 * Adds the entrances,
 * creates arrays for parked cars and the status of slots.
 */
	public CarPark(CarQueue queues[]){
		this.queues = new CarQueue[queues.length];
		
		for(int i = 0; i < queues.length; i++){
			this.queues[i] = queues[i];
		}
		
		parkedCars = new Car[MAX_CARS];
		statusArray = new Status[MAX_CARS];
		
		for(int i = 0; i < MAX_CARS; i++){
			statusArray[i] = Status.Empty;
		}
	}
/*
 * Parks cars at available slots, and updates parked cars.
 */
	@Override
	public void run() {
		while(active){
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int nextSlot = getAvailableSlot();
			
			if(nextSlot != -1){
				int queueNumb = random.nextInt(queues.length);
				
				Car car = queues[queueNumb].parkCar();
				
				if(car != null){
					statusArray[nextSlot] = Status.Occupied;
					parkedCars[nextSlot] = car;
					setCount(getCount() + 1);
					
					System.out.println("Car parked at: " + nextSlot);
				}
			}
			updateCars();
			
			try {
				System.out.println("CarPark: " + getCount() + "\n");
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
/*
 * Returns integer of first available parking slot.
 * Returns -1 if all slots are occupied.
 */
	private int getAvailableSlot(){
		for(int i = 0; i < MAX_CARS; i++){
			if(statusArray[i] == Status.Empty){
				return i;
			}
		}
		
		return -1;
	}
/*
 * Updates the cars and removes cars that are done parking.
 */
	private void updateCars(){
		for(int i = 0; i < MAX_CARS; i++){
			if(statusArray[i] == Status.Occupied){
				if(parkedCars[i].timer()){
					parkedCars[i] = null;
					statusArray[i] = Status.Empty;
					setCount(getCount() - 1);
					System.out.println("Car LEFT at: " + i);
				}
			}
		}
	}

/*
 * Get/set for active boolean,
 * number of parked cars.
 */
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
