import java.util.Scanner;

public class MainMenu {

	private boolean synchronization;
	private String input;
/*
 * Constructor for the main menu which lets the user set
 * if the program is to be run synchronized or not, 
 * as well as input the text to be read/write.
 */
	public MainMenu(){
		System.out.println("Run the program with synchronization? (Y/N)");

		Scanner scanner = new Scanner(System.in);
		input = scanner.nextLine();
		
		if (input.equals("Y") || input.equals("y")){
			System.out.println("synchronization ON");
			setSynchronization(true);
		}else if (input.equals("N") || input.equals("n")){
			System.out.println("synchronization OFF");
			setSynchronization(false);
		}
		
		System.out.println("Text to read/write: ");
		setInput(scanner.nextLine());
		
		scanner.close();
	}

	
/*
 * Getters and setters for the synchronized boolean,
 * and the text to be read/write.
 */
	public boolean getSynchronization() {
		return synchronization;
	}

	private void setSynchronization(boolean synchronization) {
		this.synchronization = synchronization;
	}
	
	public String getInput() {
		return input;
	}

	private void setInput(String input) {
		this.input = input;
	}

}
