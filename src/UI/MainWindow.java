package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;

import Core.FileControler;
import Core.Program;

public class MainWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
		
	/// UI
	JButton newButton 	= new JButton("New Simulation");
	JButton loadButton 	= new JButton("Load Simulation");
	JButton exitButton	= new JButton("Exit");
	
	public MainWindow() {
		super(540, 640, true);
		this.setTitle("DnD Testing Service");

		newButton	.addActionListener(this);
		loadButton	.addActionListener(this);
		exitButton	.addActionListener(this);
		
		this.panel.add(newButton );
		this.panel.add(loadButton);
		this.panel.add(exitButton);
		setContent();
	}
	
	private void setContent() {
		newButton	.setBounds(20,  20, 500, 160);
		loadButton	.setBounds(20, 220, 500, 160);
		exitButton	.setBounds(20, 420, 500, 160);
	}
	

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == newButton) {
			FileControler controler = new FileControler();
			byte[] input = Program.getSaveFile().getBytes();
			controler.saveToFile(input);
			controler.dispatchEvent(new WindowEvent(controler, WindowEvent.WINDOW_CLOSING));
		}
		else
		if(source == loadButton){
			FileControler controler = new FileControler();
			Program.setCurrentStatus(controler.fileToByteArray());
			controler.dispatchEvent(new WindowEvent(controler, WindowEvent.WINDOW_CLOSING));
		}
		else
		if(source == exitButton){
			System.exit(0);
		}
	}
}
