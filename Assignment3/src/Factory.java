import java.awt.Color;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Factory
extends JPanel
implements Runnable{

	private boolean run;
	
	private Thread thread;

	private FoodItem[] foodItems;
	private Storage storage;
	
	private Random random = new Random();
	
	private JTextArea textArea;
/*
 * Factory constructor.
 * Sets storage, semaphore, and run boolean.
 * Creates a JTextArea for messaging.
 * Creates an array of FoodItems that can be produced.
 * Starts a new Thread.
 * @param Storage to deliver FoodItems to.
 * @param Semaphore to use.
 */
	public Factory(Storage storage){
		this.storage = storage;
		setRun(false);

		createTextArea();
		initFoodItems();
		
		start();
	}
/*
 * Fills an array of FoodItems with FoodItems that can be produced.
 */
	private void initFoodItems() {
		foodItems = new FoodItem[10];
		
		foodItems[0] = new FoodItem("Apple", 0.2f, 0.4f);
		foodItems[1] = new FoodItem("Banana", 0.4f, 0.4f); 
		foodItems[2] = new FoodItem("Bacon", 10f, 10f); 
		foodItems[3] = new FoodItem("Tomato", 0.3f, 0.2f); 
		foodItems[4] = new FoodItem("Milk", 1.4f, 1.5f); 
		foodItems[5] = new FoodItem("Bread", 2f, 1.5f); 
		foodItems[6] = new FoodItem("Beer", 1f, 0.75f);
		foodItems[7] = new FoodItem("Chicken", 2.4f, 1f); 
		foodItems[8] = new FoodItem("Sausage", 0.5f, 0.3f); 
		foodItems[9] = new FoodItem("Candy", 3.75f, 5f); 
	}
/*
 * Creates and starts a new Thread.
 */
	public void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
/*
 * Returns a random FoodItem from the array.
 */
	public FoodItem produceFoodItem(){
		int i = random.nextInt(9);
		
		return foodItems[i];
	}
/*
 * While the thread is alive it will produce FoodItems for the storage.
 * Waits while the storage is full.
 */
	@Override
	public void run() {	
		while (thread.isAlive()){	
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			while (getRun()) {
				try {					
					FoodItem newItem = produceFoodItem();
					long value = random.nextInt(1000) + 1000;

					if(this.storage.addFoodItem(newItem)){
						textArea.setBackground(Color.GREEN);
						textArea.setText("Producing...");
						
						Thread.sleep(value);
						
						textArea.setBackground(Color.YELLOW);
						textArea.setText("Stored: " + newItem.getName() + "\n");	
						
						value = random.nextInt(500) + 500;

						Thread.sleep(value);
					}else{
						textArea.setBackground(Color.ORANGE);
						textArea.setText("Storage full...");
						
						value = random.nextInt(2500);

						Thread.sleep(value);
					}
					
					value = random.nextInt(400) + 100;

					Thread.sleep(value);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			while (!getRun()) {
				textArea.setBackground(Color.RED);
				
				if(!getRun())
					textArea.setText("Production stopped...");

				try {				
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		}
	}
/*
 * Creates a JTextArea for messaging.
 */
	private void createTextArea(){
		textArea = new JTextArea();
		textArea.setBounds(95, 20, 145, 100);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createTitledBorder("Message from factory"));
		textArea.setBackground(Color.RED);
		textArea.setText("Waiting...");
		
		add(textArea);
	}

	
/*
 * Getters and setters for boolean if the factory can produce FoodItems for the storage,
 * and run boolean.
 */
	public boolean getRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
}
