package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Core.Entity;
import Core.FileControler;
import Core.Program;
import Enums.Race;
import Enums.TeamColor;
import Enums.WindowState;
import Support.EntityRegister;

public class MainWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	WindowState state;
		
	/// INIT UI
	JButton newButton 	= new JButton("New  Simulation");
	JButton saveButton	= new JButton("Save Simulation");
	JButton loadButton 	= new JButton("Load Simulation");
	JButton exitButton	= new JButton("Exit");
	
	/// ENCSET UI
	TeamPanel blue_team = new TeamPanel("Blue team", TeamColor.BLUE);
	TeamPanel red_team  = new TeamPanel("Red  team", TeamColor.RED );
	
	/// ENCINIT UI
	
	public MainWindow() {
		super(240, 540, true);
		this.setTitle("DnD Testing Service");

		newButton	.addActionListener(this);
		loadButton	.addActionListener(this);
		saveButton	.addActionListener(this);
		exitButton	.addActionListener(this);
		
		this.panel.add(newButton );
		this.panel.add(saveButton);
		this.panel.add(loadButton);
		this.panel.add(exitButton);
		this.panel.add(blue_team );
		this.panel.add(red_team  );
		setState(WindowState.INIT);
	}
	
	public	void createCharacter(CharacterRecord record, TeamPanel panel) {
		
	}
	
	private void setState(WindowState state) {
		this.state = state;
		switch(state) {
	    	case ENCINIT:
	    		newButton	.setVisible(false);
	    		saveButton	.setVisible(false);
	    		loadButton	.setVisible(false);
	    		exitButton	.setVisible(false);
	    		blue_team	.setVisible(false);
	    		red_team 	.setVisible(false);
	    			break;
	    		
	    	case ENCSET:
	    		setSize(570, 360);
	    		newButton	.setVisible(false);
	    		saveButton	.setVisible(false);
	    		loadButton	.setVisible(false);
	    		exitButton	.setVisible(false);
	    		blue_team	.setVisible(true );
	    		red_team 	.setVisible(true );
	    		blue_team	.setBounds( 20, 20, 250, 280);
	    		red_team 	.setBounds(290, 20, 250, 280);
	    			break;
	    		
	    	case INIT:
	    		setSize(240, 540);
	    		newButton	.setVisible(true );
	    		saveButton	.setVisible(true );
	    		loadButton	.setVisible(true );
	    		exitButton	.setVisible(true );
	    		blue_team	.setVisible(false);
	    		red_team 	.setVisible(false);
	    		newButton	.setBounds( 20, 20,200,100);
	    		saveButton	.setBounds( 20,140,200,100);
	    		loadButton	.setBounds( 20,260,200,100);
	    		exitButton	.setBounds( 20,380,200,100);
	    			break;
		}
	}
	
	public void saveCCFile(Entity entity) {
		Program.save += entity.toString() + "\n";
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == newButton) {
			//new CCWindow(this);
			this.setState(WindowState.ENCSET);
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

			for(TeamColor tm: TeamColor.values()) {
				HashMap<Integer, Entity> temp = EntityRegister.getMap(tm);
				for(Integer i: temp.keySet()) {
					Program.print(temp.get(i).toString());
				}
			}
		}
		else
		if(source == exitButton){
			System.exit(0);
		}
	}
}

class CharacterRecord extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel	name = new JLabel ("");
	JLabel	race = new JLabel ("");
	JLabel	lvl  = new JLabel ("");
	JButton dlte = new JButton("x");
	JButton edit = new JButton("...");
	
	public CharacterRecord() {
		this("TEST", Race.HUMAN, 1);
	}
	
	public CharacterRecord(String name, Race race, int lvl) {
		this.setBackground(new Color(200,200,200));
		this.name.setText(name);
		this.race.setText(race.toString());
		this.lvl.setText (" lvl "+Integer.toString(lvl));
		this.add(this.name);
		this.add(this.race);
		this.add(this.lvl );
		this.add(this.dlte);
		this.add(this.edit);
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h) {
		Rectangle bond = new Rectangle(x,y,w,h);
		int tempX = bond.width/10, tempY = bond.height/10;
		super.setBounds( x, y, w, h);
		this.name.setBounds(tempX, tempY, (bond.width*70)/100, (bond.height*35)/100);
		tempY *= 2; tempY+= name.getBounds().height;
		this.race.setBounds(tempX, tempY, (bond.width*30)/100, (bond.height*35)/100);
		tempX+= race.getBounds().width;
		this.lvl .setBounds(tempX, tempY, (bond.width*30)/100, (bond.height*35)/100);
		tempY = bond.height/10;
		this.dlte.setBounds(tempX + bond.width/6, tempY, tempY*11 , tempY*9);
		tempX += tempY*11;
		this.edit.setBounds(tempX + bond.width/6, tempY, tempY*11 , tempY*9);
	}
}

class TeamPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	TeamColor color;
	JLabel	title	  = new JLabel();
	List<CharacterRecord> 
			records   = new ArrayList<CharacterRecord>();
	JButton addRecord = new JButton("+");
	
	public TeamPanel(String title, TeamColor color) {
		this.setBackground(new Color(180,180,180));
		this.setLayout(null);
		this.title .setText(title);
		this.add(this.title	   );
		this.add(this.addRecord);
		this.addRecord.addActionListener(this);
		this.color = color;
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h) {
		Rectangle bond = new Rectangle(x,y,w,h);
		int tempX = bond.width/10, tempY = bond.height/10;
		super.setBounds(x, y, w, h);
		this.title		.setBounds(tempX*3, tempY/2, tempX*4, (tempY*3)/2);
		this.addRecord	.setBounds(tempX/2, tempY*2, tempX*9, (tempY*3)/2);
	}
	
	private void updateTheLook() {
		Rectangle bond = this.getBounds();
		int tempX = bond.width/10, tempY = bond.height/10;
		for(int i = 0; i<records.size();i++) 
		{records.get(i).setBounds(tempX/2, tempY*(2+i*2), tempX*9, (tempY*3)/2);}
		
		if(this.records.size()==0) this.addRecord.setBounds(tempX/2, tempY*2, tempX*9, (tempY*3)/2);
		else { 
			Rectangle temp = records.get(records.size()-1).getBounds();
			if(this.records.size()<4) 	this.addRecord.setBounds(tempX/2, temp.y+tempY*2, tempX*9, (tempY*3)/2);
			else 						this.addRecord.setBounds(tempX/2, temp.y+tempY*2, tempX*6, 0);
		}
		this.repaint();
	}
	
	public void updateTheLook(CharacterRecord record) {
		this.records.add(record);
		this.add(record);
		record.dlte.addActionListener(this);
		updateTheLook();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == addRecord) {
			if(this.records.size()<4) new CCWindow(Program.mainWindow,this.color,this.records.size());
		}
		else {
			if(this.records.size()>0) {
				for(int i = 0; i<records.size();i++) {
					if(source == records.get(i).dlte) {
						this.remove(records.get(i));
						EntityRegister.rem(this.color, i);
						records.remove(i);
						updateTheLook();
					}
					else 
					if(source == records.get(i).edit) {
						//new CCWindow(Program.mainWindow, this.color, i);
						Program.log("NOT YET IMPLEMENTED");
					}
				}
			}
		}
	}
}