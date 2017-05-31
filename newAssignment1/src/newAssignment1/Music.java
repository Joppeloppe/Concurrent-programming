package newAssignment1;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music
extends JFXPanel
implements Runnable{

	private Thread thread;
	
	private MediaPlayer mediaPlayer;
	private Media media;
/*
 * Constructs a new Media player with the selected file,
 * from the GUI.
 */
	public Music(Media media){
		new JFXPanel();
		
		setMedia(media);
		mediaPlayer = new MediaPlayer(media);
	}
/*
 * Creates and starts a new Thread.
 */
	public void start(){
		thread = new Thread(this);
		thread.start();
	}
/*
 * Starts playing the file in the media player.
 */
	public void play(){
		mediaPlayer.play();
	}
/*
 * Stops playing the file in the media player.
 */
	public void stop(){
		mediaPlayer.stop();
		thread.interrupt();
	}
/*
 * Thread runs as long as it is not interrupted.
 */
	@Override
	public void run() {
		while(!thread.isInterrupted())
			;
	}
	
/*
 * Getter and setter for the media for the media player.
 */
	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

}
