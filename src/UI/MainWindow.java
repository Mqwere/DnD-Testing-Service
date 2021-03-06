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
import Enums.Core.Race;
import Enums.Core.TeamColor;
import Enums.UI.WindowState;
import Support.Editor;
import Support.EntityRegister;

public class MainWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	WindowState state;
		
	/// INIT UI
	JButton newButton 	 = new JButton("New  Simulation");
	JButton loadButton 	 = new JButton("Load Simulation");
	JButton exitButton	 = new JButton("Exit");
	
	/// ENCSET UI
	TeamPanel blue_team  = new TeamPanel(Editor.htmlize("Blue team",5), TeamColor.BLUE);
	TeamPanel red_team   = new TeamPanel(Editor.htmlize("Red  team",5), TeamColor.RED );
	JButton   startButton= new JButton	(Editor.htmlize("Start Simulation",4));
	JButton   saveButton = new JButton	(Editor.htmlize("Save\nSimulation",true, 4));
	JButton	  backButton = new JButton	(Editor.htmlize("Back",4));
	
	/// ENCINIT UI
	
	public MainWindow() {
		super(240, 420, true);
		this.setTitle("DnD Testing Service");

		newButton	.addActionListener(this);
		startButton .addActionListener(this);
		loadButton	.addActionListener(this);
		saveButton	.addActionListener(this);
		exitButton	.addActionListener(this);
		backButton	.addActionListener(this);  
		
		this.panel.add(newButton );
		this.panel.add(startButton);
		this.panel.add(saveButton);
		this.panel.add(loadButton);
		this.panel.add(exitButton);
		this.panel.add(blue_team );
		this.panel.add(red_team  );
		this.panel.add(backButton);
		setState(WindowState.INIT);
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
	    		backButton 	.setVisible(false);
	    			break;
	    		
	    	case ENCSET:
	    		setSize(570, 680);
	    		blue_team	.clear();
	    		red_team	.clear(); 
	    		newButton	.setVisible(false);
	    		loadButton	.setVisible(false);
	    		exitButton	.setVisible(false);
	    		saveButton	.setVisible(true);
	    		blue_team	.setVisible(true );
	    		red_team 	.setVisible(true );
	    		backButton 	.setVisible(true );
	    		blue_team	.setBounds( 20, 20,250,500);
	    		red_team 	.setBounds(290, 20,250,500);
	    		backButton	.setBounds( 20,540,180, 80);
	    		saveButton	.setBounds(220,540,120, 80);
	    		startButton .setBounds(360,540,180, 80);
	    			break;
	    		
	    	case INIT:
	    		setSize(240, 420);
	    		newButton	.setVisible(true );
	    		saveButton	.setVisible(false );
	    		loadButton	.setVisible(true );
	    		exitButton	.setVisible(true );
	    		blue_team	.setVisible(false);
	    		red_team 	.setVisible(false);
	    		backButton 	.setVisible(false);
	    		newButton	.setBounds( 20, 20,200,100);
	    		loadButton	.setBounds( 20,140,200,100);
	    		exitButton	.setBounds( 20,260,200,100);
	    			break;
		}
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
			String input = FileControler.fileToByteArray(this);
			if(input!=null) {
				try{Program.saveToEntityList(input);} catch(Exception e) {Program.log("MainWindow.actionPerformed.loadButton: "+e.getMessage());}
				this.setState(WindowState.ENCSET);
				for(TeamColor tm: TeamColor.values()) {
					HashMap<Integer, Entity> temp = EntityRegister.getMap(tm);
					if(temp!=null) {
						for(Integer i: temp.keySet()) {
							//Program.log(i);
							if(tm == TeamColor.BLUE)
								this.blue_team.updateTheLook(i, temp.get(i));
							else 
								this.red_team .updateTheLook(i, temp.get(i));
						}
					}
				}
			/**/
			}
		}
		else
		if(source == backButton) {
			this.setState(WindowState.INIT);
		}
		else
		if(source == exitButton){
			System.exit(0);
		}
	}
}

class CharacterRecord extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public int ID;
	
	JLabel	name = new JLabel ("");
	JLabel	race = new JLabel ("");
	JLabel	lvl  = new JLabel ("");
	JButton dlte = new JButton("x");
	JButton edit = new JButton("...");
	
	public CharacterRecord() {
		this("TEST", Race.HUMAN, 1);
	}
	
	public CharacterRecord(Entity ent) {
		this(ent.getName(),ent.getRace(), ent.getLvL());
		this.ID = ent.ID;
	}
	
	public CharacterRecord(String name, Race race, int lvl) {
		this.ID = Entity.statID;
		this.setBackground(new Color(200,200,200));
		this.name.setText(name);
		this.race.setText(race.toString());
		this.lvl.setText (" lvl "+Integer.toString(lvl));
		this.add(this.name);
		this.add(this.race);
		this.add(this.lvl );
		this.add(this.dlte);
		this.add(this.edit);
		/// id
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
		this.dlte.setBounds(tempX + bond.width/6, tempY, (int)(bond.width/(5.3)) , (int)(tempY*8.8));
		tempX += tempY*12;
		this.edit.setBounds(tempX + bond.width/6, tempY, (int)(bond.width/(5.3)) , (int)(tempY*8.8));
	}
}

class TeamPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	TeamColor color;
	JLabel	title	  = new JLabel();
	List<CharacterRecord> 
			records   = new ArrayList<>();
	JButton addRecord = new JButton(Editor.htmlize("+",6));
	
	public TeamPanel(String title, TeamColor color) {
		this.setBackground(new Color(180,180,180));
		this.setLayout(null);
		this.title .setText(title);
		this.add(this.title	   );
		this.add(this.addRecord);
		this.addRecord.addActionListener(this);
		this.color = color;
	}
	
	public void clear() {
		for(CharacterRecord cr: records) {
			this.remove(cr);
		}
		this.records.clear();
	}
	
	private void sortRecords() {
		List<CharacterRecord> temp   = new ArrayList<>();
		for(Integer i: EntityRegister.getMap(this.color).keySet())
			for(int j=0; j<records.size();j++) {
				if(EntityRegister.getMap(this.color).get(i).ID == records.get(j).ID) temp.add(records.get(j));
			}
		this.records = new ArrayList<CharacterRecord>(temp);
	}
	
	@Override
	public void setBounds(int x, int y, int w, int h) {
		Rectangle bond = new Rectangle(x,y,w,h);
		int tempX = bond.width/10, tempY = bond.height/18;
		super.setBounds(x, y, w, h);
		this.title		.setBounds(tempX*3, tempY/2, tempX*4, (tempY*3)/2);
		this.addRecord	.setBounds(tempX/2, tempY*2, tempX*9, (tempY*3)/2);
	}
	
	public void updateTheLook() {
		Rectangle bond = this.getBounds();
		int tempX = bond.width/10, tempY = bond.height/18;
		this.sortRecords();
		for(int i = 0; i<records.size();i++){
			records.get(i).setLayout(null);
			records.get(i).setBounds(tempX/2, tempY*(2+i*2), tempX*9, (tempY*3)/2);
		}
		
		if(this.records.size()==0) this.addRecord.setBounds(tempX/2, tempY*2, tempX*9, (tempY*3)/2);
		else { 
			Rectangle temp = records.get(records.size()-1).getBounds();
			if(this.records.size()<8) 	this.addRecord.setBounds(tempX/2, temp.y+tempY*2, tempX*9, (tempY*3)/2);
			else 						this.addRecord.setBounds(tempX/2, temp.y+tempY*2, tempX*6, 0);
		}
		this.repaint();
	}
	
	public void updateTheLook(int index, Entity ent) {
		CharacterRecord record = new CharacterRecord(ent);
		CharacterRecord temp   = this.records.size()<=index? null:/**/this.records.get(index);
		if(temp!=null)	this.remove(temp);
		this.records.add(record);
		this.add(record );
		record.dlte.addActionListener(this);
		record.edit.addActionListener(this);
		updateTheLook();
	}
	
	public void updateTheLook(CharacterRecord record) {
		this.records.add(record);
		this.add(record);
		record.dlte.addActionListener(this);
		record.edit.addActionListener(this);
		updateTheLook();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == addRecord) {
			if(this.records.size()<8) new CCWindow(Program.mainWindow,this.color,this.records.size());
		}
		else {
			if(this.records.size()>0) {
				for(int i = 0; i<records.size();i++) {
					if(source == records.get(i).dlte) {
						this.remove(records.get(i));
						EntityRegister.rem(this.color, i);
						this.records.remove(i);
						updateTheLook();
					}
					else 
					if(source == records.get(i).edit) {
						new CCWindow(Program.mainWindow, this.color, i, EntityRegister.get(this.color, i));
						this.remove(records.get(i));
						EntityRegister.rem(this.color, i);
						this.records.remove(i);
						//Program.log("NOT YET IMPLEMENTED");
					}
				}
			}
		}
	}
}