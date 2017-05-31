package newAssignment1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;

public class GUI
extends JPanel
implements ActionListener{
	
	private JFrame frame;
	private JButton btnOpen, btnPlay, btnStop,
	btnRecPlay, btnRecStop,
	btnTextPlay, btnTextStop;
	private JFileChooser fileChooser;
	
	private File file;
	
	private Draw draw = new Draw();
	private Text text = new Text("Hello there!");
	private Music music;
		
	public GUI(){
		initializeGUI();
	}
/*
 * Creates a GUI for the program.
 * A single frame with three panels,
 * (music, draw, and text)
 */

	private void initializeGUI() {
		frame = new JFrame();
		frame.setLayout(null);
		frame.setBounds(0, 0, 800, 600);
		frame.setTitle("Assignment 1");
		
		fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setFileFilter(new FileNameExtensionFilter(".wav", "wav"));
		fileChooser.setFileFilter(new FileNameExtensionFilter(".mp3", "mp3"));
		
		frame.add(getPanelMusic());
		frame.add(getPanelRect());
		frame.add(getPanelText());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
/*
 * Action performed for buttons.
 * Whenever a play-button is pressed it is disabled,
 * and the stop-button is enabled. Reversed for stop-buttons. 
 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {			
		if(actionEvent.getSource() == btnOpen){
			int returnValue = fileChooser.showOpenDialog(fileChooser);
			
			if(returnValue == JFileChooser.APPROVE_OPTION){
				setFile(fileChooser.getSelectedFile());
				
				Media media = new Media(getFile().toURI().toString());
				music = new Music(media);
				
				music.start();
				
				btnPlay.setEnabled(true);
			}
		}else if(actionEvent.getSource() == btnPlay){
			music.play();
			
			btnPlay.setEnabled(false);
			btnStop.setEnabled(true);
		}else if(actionEvent.getSource() == btnStop){
			music.stop();
			
			btnPlay.setEnabled(true);
			btnStop.setEnabled(false);
		}
		
		if(actionEvent.getSource() == btnRecPlay){
			draw.start();
			
			btnRecPlay.setEnabled(false);
			btnRecStop.setEnabled(true);
		}else if(actionEvent.getSource() == btnRecStop){
			draw.stop();
			
			btnRecPlay.setEnabled(true);
			btnRecStop.setEnabled(false);
		}
		
		if(actionEvent.getSource() == btnTextPlay){
			text.start();
			
			btnTextPlay.setEnabled(false);
			btnTextStop.setEnabled(true);
		}else if (actionEvent.getSource() == btnTextStop){
			text.stop();
			
			btnTextPlay.setEnabled(true);
			btnTextStop.setEnabled(false);
		}
	}
/*
 * Returns a JFXPanel for the music panel.
 */
	public JFXPanel getPanelMusic(){
		JFXPanel pnlMusic = new JFXPanel();
		pnlMusic.setBorder(BorderFactory.createTitledBorder("Music player"));
		pnlMusic.setBounds(10,10,250,150);
		pnlMusic.setLayout(null);
		
		btnOpen = new JButton("Open file...");
		btnOpen.setBounds(10, 20, 75, 25);
		btnOpen.addActionListener(this);
		pnlMusic.add(btnOpen);
		
		btnPlay = new JButton("Play");
		btnPlay.setBounds(10, 50, 75, 25);
		btnPlay.addActionListener(this);
		btnPlay.setEnabled(false);
		pnlMusic.add(btnPlay);
		
		btnStop = new JButton("Stop");
		btnStop.setBounds(10, 80, 75, 25);
		btnStop.addActionListener(this);
		btnStop.setEnabled(false);
		pnlMusic.add(btnStop);
		
		return pnlMusic;
	}
/*
 * Returns a JPanel for drawing a rectangle.
 */
	public JPanel getPanelRect(){
		JPanel pnlRect = draw;
		pnlRect.setBorder(BorderFactory.createTitledBorder("Rectangle"));
		pnlRect.setBounds(10, 175, 200, 150);
		pnlRect.setLayout(null);
		
		btnRecPlay = new JButton("Play");
		btnRecPlay.setBounds(10, 20, 75, 25);
		btnRecPlay.addActionListener(this);
		pnlRect.add(btnRecPlay);
		
		btnRecStop = new JButton("Stop");
		btnRecStop.setBounds(10, 50, 75, 25);
		btnRecStop.addActionListener(this);
		btnRecStop.setEnabled(false);
		pnlRect.add(btnRecStop);
		
		return pnlRect;
	}
/*
 * Returns a panel for text display.
 */
	public JPanel getPanelText(){
		JPanel pnlText = text;
		pnlText.setBorder(BorderFactory.createTitledBorder("Moving Text"));
		pnlText.setBounds(300, 10, 400, 250);
		pnlText.setLayout(null);
		
		btnTextPlay = new JButton("Play");
		btnTextPlay.setBounds(10, 20, 75, 25);
		btnTextPlay.addActionListener(this);
		pnlText.add(btnTextPlay);
		
		btnTextStop = new JButton("Stop");
		btnTextStop.setBounds(10, 50, 75, 25);
		btnTextStop.addActionListener(this);
		btnTextStop.setEnabled(false);
		pnlText.add(btnTextStop);
		
		return pnlText;
	}
	
	
/*
 * Getter and setter for the music file to be played.
 */
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
