
public class Car {

	private int time;
	
/*
 * Car constructor.
 * @param Time, how long the car will be parked.
 */
	public Car(int time){
		setTime(time);
	}

/*
 * Timer for the cars to be parked in the CarPark.
 */
	public boolean timer(){
		setTime(getTime() - 1);
		
		if(getTime() <= 0){
			return true;
		}
		
		return false;
	}

	
/*
 * Get/set for time.
 */
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
