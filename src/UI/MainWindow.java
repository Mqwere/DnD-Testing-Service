package UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
			byte[] input = Program.getSaveFile().getBytes();
			FileControler.saveToFile(this,input);
		}
		else
		if(source == loadButton){
			Program.setCurrentStatus(FileControler.fileToByteArray(this));
			try{Program.saveToEntityList();} catch(Exception e) {Program.log("MainWindow.actionPerformed.loadButton: "+e.getMessage());}
			for(Entity e: Program.entityList) {
				Program.log(Boolean.toString(e.equals(null)));				
			}
		}
		else
		if(source == exitButton){
			System.exit(0);
		}
	}
}
