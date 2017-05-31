import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI
extends JPanel
implements ActionListener{
		
	private int fileLength = 0;
	
	private BoundedBuffer buffer = new BoundedBuffer();
	
	private File file;
		
	private JMenuItem itemOpen, itemExit;
	private JFrame frame;
	private JFileChooser fileChooser;
	private JTextField textFieldFind, textFieldReplace;
	private JButton buttonCopy;
	private JTabbedPane tabbedPane = new JTabbedPane();
	private JTextArea textAreaSource, textAreaDestination;
	private JScrollPane scrollPaneSource, scrollPaneDestination;
/*
 * GUI constructor.
 * Creates the GUI for the program.
 */
	public GUI(){
		initializeGUI();
	}
/*
 * Creates GUI for the program.
 */
	private void initializeGUI() {
		frame = new JFrame();
		
		frame.setLayout(null);
		frame.setBounds(0, 0, 500, 500);
		frame.setTitle("Assignment 4");

		frame.setJMenuBar(setMenuBar());	
		
		textAreaSource = new JTextArea();
		textAreaSource.setEditable(false);
		
		textAreaDestination = new JTextArea();
		textAreaDestination.setEditable(false);
		
		scrollPaneSource = new JScrollPane(textAreaSource);
		scrollPaneSource.setBounds(10, 115, 400, 250);
		
		scrollPaneDestination = new JScrollPane(textAreaDestination);
		scrollPaneDestination.setBounds(10, 115, 400, 250);
		
		tabbedPane.setBounds(10, 115, 470, 325);
		tabbedPane.addTab("Source", scrollPaneSource);
		tabbedPane.addTab("Destination", scrollPaneDestination);
		
		
		frame.getContentPane().add(tabbedPane);
		frame.add(createPanelFindAndReplace());
		
		tabbedPane.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
/*
 * Returns a JMenuBar.
 * Opens a JFileChooser for the user to find the .txt file to use in the program.
 */
	private JMenuBar setMenuBar(){
		JMenu menuFile = new JMenu("File");
		
		itemOpen = new JMenuItem("Open source file");
		itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, ActionEvent.CTRL_MASK));
		itemOpen.addActionListener(this);
		
		itemExit = new JMenuItem("Exit");
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		itemExit.addActionListener(this);
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setFileFilter(new FileNameExtensionFilter(".txt", "txt"));
		
		menuFile.add(itemOpen);
		menuFile.add(itemExit);
		
		JMenuBar menuBar = new JMenuBar();
		
		menuBar.add(menuFile);
		
		return menuBar;
	}
/*
 * Returns a JPanel for the string to be found and replaced with another string.
 * Buttons starts the process of copying.
 */
	private JPanel createPanelFindAndReplace(){
		JPanel panel = new JPanel();
		
		panel.setBorder(BorderFactory.createTitledBorder("Find and Replace"));
		panel.setBounds(10, 10, 450, 100);
		panel.setLayout(null);
		
		textFieldFind = new JTextField();
		textFieldFind.setBounds(10, 20, 300, 25);
		textFieldFind.setLayout(null);
		panel.add(textFieldFind);
		
		textFieldReplace = new JTextField();
		textFieldReplace.setBounds(10, 45, 300, 25);
		textFieldReplace.setLayout(null);
		panel.add(textFieldReplace);
		
		buttonCopy = new JButton("Copy");
		buttonCopy.setBounds(350, 32, 75, 25);
		buttonCopy.addActionListener(this);
		panel.add(buttonCopy);
		
		return panel;
	}
/*
 * Action listener for JFileChooser, and button.
 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource() == itemExit)
			System.exit(0);
		else if(actionEvent.getSource() == itemOpen){
			int returnValue = fileChooser.showOpenDialog(fileChooser);
			
			if(returnValue == JFileChooser.APPROVE_OPTION){
				setFile(fileChooser.getSelectedFile());
				
				new Writer(buffer, getFile());

				try(BufferedReader bufferReader = new BufferedReader(new FileReader(getFile()))){
					String line = bufferReader.readLine();
					
					while(line != null){
						textAreaSource.append(line + "\n");
						fileLength++;
						line = bufferReader.readLine();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else if(actionEvent.getSource() == buttonCopy){
			new Modifier(buffer, textFieldFind.getText(), textFieldReplace.getText());
			new Reader(buffer, fileLength);
		}
	}

	
	
/*
 * Get/set for the .txt file to be processed.
 */
	private File getFile() {
		return file;
	}

	private void setFile(File file) {
		this.file = file;
	}
}