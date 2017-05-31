import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
		
	public static void main(String args[]){
		CarQueue queues[];
		
		queues = new CarQueue[2];
		
		queues[0] = new CarQueue("North");
		queues[0].setActive(true);
		queues[1] = new CarQueue("South");
		queues[1].setActive(true);
		
		
		CarPark carPark;
		
		carPark = new CarPark(queues);
		carPark.setActive(true);
		
		
		ExecutorService executor = Executors.newFixedThreadPool(3);
		
		executor.execute(queues[0]);
		executor.execute(queues[1]);
		executor.execute(carPark);
	}
}
