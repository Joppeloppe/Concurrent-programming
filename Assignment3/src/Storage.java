import java.util.concurrent.Semaphore;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public class Storage
extends JPanel{
	
	private int maxItems, maxVolume, maxWeight, currentItems = 0, currentVolume = 0, currentWeight = 0, writePosition, readPosition;
	
	private FoodItem foodItemsList[];
	
	private Semaphore writeSemaphore, readSemaphore;
	
	private JTextArea textArea;
	private JProgressBar progressBar;
/*
 * Storage constructor,
 * sets the max number of items the storage can hold, max weight and max volume for the storage.
 * @param max number of items the storage can hold.
 */
	public Storage(int maxItems, int maxVolume, int maxWeight, JProgressBar progressBar){
		setMaxItems(maxItems);
		setMaxVolume(maxVolume);
		setMaxWeight(maxWeight);
		
		this.progressBar = progressBar;
		
		foodItemsList = new FoodItem[getMaxItems()];
		
		writeSemaphore = new Semaphore(getMaxItems(), true);
		readSemaphore = new Semaphore(0, true);
		
		createTextArea();
	}
/*
 * Adds a FoodItem to the storage.
 * @param FoodItem to be stored.
 */
	public synchronized boolean addFoodItem(FoodItem foodItem){	
		try {	
			System.out.println("\nAvailable WRITER permits: " + writeSemaphore.availablePermits());
		
			if(writeSemaphore.availablePermits() <= 0)
				return false;
		
			writeSemaphore.acquire();
			
			foodItemsList[writePosition] = foodItem;
			writePosition++;
			
			if(writePosition >= getMaxItems()){
				writePosition = 0;
			}
			
			setCurrentItems();
			setCurrentVolume();
			setCurrentWeight();

			updateProgressBar();
			
			return true;
			
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			//Failed semaphore acquire.
		}finally{

			readSemaphore.release();
		}
		return true;
	}
/*
 * Returns the first FoodItem in the storage list.
 */
	public synchronized FoodItem getFoodItem(){
		try {
			System.out.println("\nAvailable READER permits: " + readSemaphore.availablePermits());

			if(readSemaphore.availablePermits() <= 0)
				return null;
			
			readSemaphore.acquire();

			FoodItem firstFood = foodItemsList[readPosition];
			
			foodItemsList[readPosition] = null;
			readPosition++;
			
			if(readPosition >= getMaxItems()){
				readPosition = 0;
			}
			
			setCurrentItems();
			setCurrentVolume();
			setCurrentWeight();
			
			return firstFood;
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			//Failed acquire;
		}finally{
			updateProgressBar();
			
			writeSemaphore.release();
		}
		return null;
	}
/*
 * Creates a JTextArea where messages from the storage can be displayed.
 */
	private void createTextArea(){
		textArea = new JTextArea();
		textArea.setBounds(10, 50, 230, 115);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createTitledBorder("Message from Storage"));
		
		add(textArea);
	}
/*
 * Updates the progress bar with the percentage of current items.
 */
	public synchronized void updateProgressBar(){
		int i = (int)((float)getCurrentItems() / (float)getMaxItems() * 100);
		progressBar.setValue(i);
	}
	
/*
 * Checks if the storage is full.
 */
	private boolean isStorageFull(){
		if(getCurrentItems() > getMaxItems()||
				getCurrentVolume() > getMaxVolume()||
				getCurrentWeight() > getMaxWeight()){
			return true;
		}else
			return false;
	}
	
/*
 * Getters and setters for the max number of items the storage can hold, max volume and max weight the storage can hold, 
 * and the current number of items in the storage, the current volume and weight of the storage.
 */
	public int getMaxItems() {
		return maxItems;
	}
	
	private void setMaxItems(int maxItem) {
		this.maxItems = maxItem;
	}

	public int getMaxVolume() {
		return maxVolume;
	}
	
	public void setMaxVolume(int maxVolume) {
		this.maxVolume = maxVolume;
	}
	
	public int getMaxWeight() {
		return maxWeight;
	}
	
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}
	
	private int getCurrentItems() {
		return currentItems;
	}

	private void setCurrentItems() {
		int i = 0, j = 0;
		
		for(i = 0; i < getMaxItems(); i++){
			if (foodItemsList[i] == null){
				j++;
			}			
		}
		this.currentItems = i - j;
		System.out.println("Current items in storage: " + currentItems);
	}
	
	public int getCurrentVolume() {
		return currentVolume;
	}
	
	public void setCurrentVolume() {
		for(int i = 0; i < getMaxItems(); i++){
			if (foodItemsList[i] != null){
				this.currentVolume += foodItemsList[i].getVolume();
			}			
		}
	}
	
	public int getCurrentWeight() {
		return currentWeight;
	}
	
	public void setCurrentWeight() {
		for(int i = 0; i < getMaxItems(); i++){
			if (foodItemsList[i] != null){
				this.currentWeight += foodItemsList[i].getWeight();
			}			
		}
	}
}