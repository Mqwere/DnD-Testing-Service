package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

import Core.Entity;
import Core.FileControler;
import Core.Program;

public class MainWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
		
	/// UI
	JButton newButton 	= new JButton("New Character");
	JButton saveButton	= new JButton("Save Simulation");
	JButton loadButton 	= new JButton("Load Simulation");
	JButton exitButton	= new JButton("Exit");
	
	public MainWindow() {
		super(240, 640, true);
		this.setTitle("DnD Testing Service");

		newButton	.addActionListener(this);
		loadButton	.addActionListener(this);
		saveButton	.addActionListener(this);
		exitButton	.addActionListener(this);
		
		this.panel.add(newButton );
		this.panel.add(saveButton);
		this.panel.add(loadButton);
		this.panel.add(exitButton);
		setContent();
	}
	
	private void setContent() {
		newButton	.setBounds( 10, 10,200,100);
		saveButton	.setBounds( 10,130,200,100);
		loadButton	.setBounds( 10,250,200,100);
		exitButton	.setBounds( 10,370,200,100);
	}
	
	public void saveCCFile(Entity entity) {
		Program.save += entity.toString();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == newButton) {
			new CCWindow(this);
		}
		else
		if(source == saveButton) {
			FileControler controler = new FileControler();
			if(controler.isFine) {
				byte[] input = Program.getSaveFile().getBytes();
				controler.saveToFile(input);
				controler.dispatchEvent(new WindowEvent(controler, WindowEvent.WINDOW_CLOSING));
			} else controler.dispatchEvent(new WindowEvent(controler, WindowEvent.WINDOW_CLOSING));
		}
		else
		if(source == loadButton){
			FileControler controler = new FileControler();
			if(controler.isFine) {
				Program.setCurrentStatus(controler.fileToByteArray());
				controler.dispatchEvent(new WindowEvent(controler, WindowEvent.WINDOW_CLOSING));
			} else controler.dispatchEvent(new WindowEvent(controler, WindowEvent.WINDOW_CLOSING));
		}
		else
		if(source == exitButton){
			System.exit(0);
		}
	}
}
