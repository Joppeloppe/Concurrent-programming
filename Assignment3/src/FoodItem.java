
public class FoodItem {
	
	private String name;
	private float weight, volume;
/*
 * FoodItem constructor.
 * @param Name of the FoodItem.
 * @param Weight of the FoodItem.
 * @param Volume of the FoodItem.
 */
	public FoodItem(String name, float weight, float volume ){
		setName(name);
		setWeight(weight);
		setVolume(volume);
	}

	
/*
 * Getters and setters for name, weight and volume of the FoodItem.
 */
	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public float getWeight() {
		return weight;
	}

	private void setWeight(float weight) {
		this.weight = weight;
	}

	public float getVolume() {
		return volume;
	}

	private void setVolume(float volume) {
		this.volume = volume;
	}

}
