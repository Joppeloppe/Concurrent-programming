
public class Main {
	
	public static void main(String[] args) {
		System.out.println("Starting program...");
		
		MainMenu mainMenu = new MainMenu();
		Buffer buffer = new Buffer();
		Writer writer = new Writer(mainMenu.getInput(), buffer, mainMenu.getSynchronization());
		Reader reader = new Reader(buffer, mainMenu.getSynchronization());
				
		writer.start();
		reader.start(mainMenu.getInput().length());
	}
}
