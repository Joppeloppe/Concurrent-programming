import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Truck
extends JPanel
implements Runnable{

	private int currentItem;
	private final int MAX_ITEM = 50;
	private float currentVolume, currentWeight;
	private final float MAX_VOLUME = 50, MAX_WEIGHT = 25;
	private boolean run;
	
	private List<FoodItem> foodItemsList = new ArrayList<FoodItem>();
	
	private Thread thread;
	
	private Storage storage;
	
	private JTextArea textArea;
/*
 * Truck constructor.
 * Sets storage and semaphore,
 * as well as creates a JTextArea for messaging from the truck and starts a new Thread.
 * @param Storage from which to fetch items.
 * @param Semaphore to use.
 */
	public Truck(Storage storage){
		this.storage = storage;
		setRun(false);

		createTextArea();
		
		start();
	}
/*
 * Starts a new Thread.
 */
	public void start(){
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}
/*
 * While the thread is alive it will try and fetch a FoodItem from the storage.
 * Displays which item has been fetched.
 * Leaves when it has fetched 5 items.
 * Waits if the storage is empty.
 */
	@Override
	public void run() {	
		while (thread.isAlive()) {
			while (getRun()) {
				try {
					
					textArea.setBackground(Color.GREEN);
					textArea.setText("Fetching item...");

					FoodItem foodItem = storage.getFoodItem();

					if(foodItem != null){
						textArea.setText("Loading: " + foodItem.getName() + "\n");
						foodItemsList.add(foodItem);
						
						setCurrentItem();
						setCurrentWeight();
						setCurrentVolume();						
						
						Thread.sleep(1500);					
						
					}else{
						textArea.setBackground(Color.YELLOW);
						textArea.setText("Storage empty...");
						
						Thread.sleep(2000);
					}
					
					if(isFull()){
						textArea.setBackground(Color.ORANGE);
						
						if(getCurrentItem() >= MAX_ITEM){
							textArea.setText("Item limit reached, leaving...");
						}else if(getCurrentWeight() >= MAX_WEIGHT){
							textArea.setText("Weight limit reached, leaving...");
						}else if(getCurrentVolume() >= MAX_VOLUME){
							textArea.setText("Volume limit reached, leaving...");
						}
						
						try {
							Thread.sleep(5000);
							foodItemsList.clear();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			while (!getRun()) {
				textArea.setBackground(Color.RED);

				if(!getRun())
					textArea.setText("Waiting...");
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} 
		}
	}
/*
 * Creates a JTextArea for messaging from the truck.
 */
	private void createTextArea(){
		textArea = new JTextArea();
		textArea.setBounds(95, 20, 145, 100);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createTitledBorder("Message from truck"));
		
		add(textArea);
	}

	private boolean isFull(){
		return (getCurrentItem() >= MAX_ITEM ||
				getCurrentWeight() >= MAX_WEIGHT ||
				getCurrentVolume() >= MAX_VOLUME);
	}
	
/*
 * Get/set for the run boolean,
 * current total number of items in the truck, 
 * total weight, and volume of the items in the truck.
 */
	private boolean getRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
	
	private int getCurrentItem(){
		return currentItem;
	}
	
	private void setCurrentItem(){
		currentItem = foodItemsList.size();
	}

	private float getCurrentWeight() {
		return currentWeight;
	}

	private void setCurrentWeight() {
		currentWeight = 0;
		
		for(int i = 0; i < foodItemsList.size(); i++){
			currentWeight += foodItemsList.get(i).getWeight();
		}
	}

	private float getCurrentVolume() {
		return currentVolume;
	}

	private void setCurrentVolume() {
		currentVolume = 0;
		
		for(int i = 0; i < foodItemsList.size(); i++){
			currentVolume += foodItemsList.get(i).getVolume();
		}
	}
}
