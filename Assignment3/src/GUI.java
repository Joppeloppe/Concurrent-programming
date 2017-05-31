import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class GUI 
extends JPanel
implements ActionListener{

	private static final int MAX_ITEM = 10, MAX_VOLUME = 25, MAX_WEIGHT = 50;
	
	private JFrame frame;
	private JButton btnAStart, btnAStop, btnBStart, btnBStop, btnTruckStart, btnTruckStop;
	private JProgressBar progressBar;
	
	private Storage storage;
	private Factory factoryA;
	private Factory factoryB;
	private Truck truck;
/*
 * GUI constructor,
 * creates the frame and panels for the program.
 */
	public GUI(){
		initializeGUI();
	}
/*
 * Creates the frame and panels for the program.
 */
	private void initializeGUI() {
		frame = new JFrame();
		frame.setLayout(null);
		frame.setBounds(0, 0, 550, 400);
		frame.setTitle("Assignment 3");
		
		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(10, 20, 230, 25);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		
		storage  = new Storage(MAX_ITEM, MAX_VOLUME, MAX_WEIGHT, progressBar);
		factoryA = new Factory(storage);
		factoryB = new Factory(storage);
		truck = new Truck(storage);

		
		frame.add(getPnlFactoryA());
		frame.add(getPnlFactoryB());
		frame.add(getPnlStorage());
		frame.add(getPnlTruck());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}

/*
 * Returns a JPanel for factory A.
 */
	private JPanel getPnlFactoryA() {
		JPanel pnlFactoryA = factoryA;
		
		pnlFactoryA.setBorder(BorderFactory.createTitledBorder("Factory A"));
		pnlFactoryA.setBounds(10, 10, 250, 150);
		pnlFactoryA.setLayout(null);
		
		btnAStart = new JButton("Start");
		btnAStart.setBounds(10, 20, 75, 25);
		btnAStart.addActionListener(this);
		btnAStart.setEnabled(true);
		pnlFactoryA.add(btnAStart);
		
		btnAStop = new JButton("Stop");
		btnAStop.setBounds(10, 50, 75, 25);
		btnAStop.addActionListener(this);
		btnAStop.setEnabled(false);
		pnlFactoryA.add(btnAStop);
		
		return pnlFactoryA;
	}
/*
 * Returns a JPanel for factory B.	
 */
	private JPanel getPnlFactoryB() {
		JPanel pnlFactoryB = factoryB;
		
		pnlFactoryB.setBorder(BorderFactory.createTitledBorder("Factory B"));
		pnlFactoryB.setBounds(275, 10, 250, 150);
		pnlFactoryB.setLayout(null);
		
		btnBStart = new JButton("Start");
		btnBStart.setBounds(10, 20, 75, 25);
		btnBStart.addActionListener(this);
		btnBStart.setEnabled(true);
		pnlFactoryB.add(btnBStart);
		
		btnBStop = new JButton("Stop");
		btnBStop.setBounds(10, 50, 75, 25);
		btnBStop.addActionListener(this);
		btnBStop.setEnabled(false);
		pnlFactoryB.add(btnBStop);
		
		return pnlFactoryB;
	}
/*
 * Returns a JPanel for the truck.
 */
	private JPanel getPnlTruck(){
		JPanel pnlTruck = truck;
		
		pnlTruck.setBorder(BorderFactory.createTitledBorder("Truck"));
		pnlTruck.setBounds(10, 175, 250, 175);
		pnlTruck.setLayout(null);
		
		btnTruckStart = new JButton("Start");
		btnTruckStart.setBounds(10, 20, 75, 25);
		btnTruckStart.addActionListener(this);
		btnTruckStart.setEnabled(true);
		pnlTruck.add(btnTruckStart);
		
		btnTruckStop = new JButton("Stop");
		btnTruckStop.setBounds(10, 50, 75, 25);
		btnTruckStop.addActionListener(this);
		btnTruckStop.setEnabled(false);
		pnlTruck.add(btnTruckStop);
		
		return pnlTruck;
	}
/*
 * Returns a JPanel for the storage.
 */
	private JPanel getPnlStorage(){
		JPanel pnlStorage = storage;
		
		pnlStorage.setBorder(BorderFactory.createTitledBorder("Storage"));
		pnlStorage.setBounds(275, 175, 250, 175);
		pnlStorage.setLayout(null);
		
		pnlStorage.add(progressBar);
		
		return pnlStorage;
	}
/*
 * Action listener for the start- and stop buttons.
 */
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		if(actionEvent.getSource() == btnAStart){
			factoryA.setRun(true);
			
			btnAStart.setEnabled(false);
			btnAStop.setEnabled(true);
		}else if(actionEvent.getSource() == btnAStop){
			factoryA.setRun(false);
			
			btnAStart.setEnabled(true);
			btnAStop.setEnabled(false);
		}
		
		if(actionEvent.getSource() == btnBStart){
			factoryB.setRun(true);
			
			btnBStart.setEnabled(false);
			btnBStop.setEnabled(true);
		}else if(actionEvent.getSource() == btnBStop){
			factoryB.setRun(false);
			
			btnBStart.setEnabled(true);
			btnBStop.setEnabled(false);
		}
		
		if(actionEvent.getSource() == btnTruckStart){
			truck.setRun(true);
			
			btnTruckStart.setEnabled(false);
			btnTruckStop.setEnabled(true);
		}else if(actionEvent.getSource() == btnTruckStop){
			truck.setRun(false);
			
			btnTruckStart.setEnabled(true);
			btnTruckStop.setEnabled(false);
		}
	}
}
