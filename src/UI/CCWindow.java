package UI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Enums.ImmunityType;
import Enums.Race;

public class CCWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	StatPanel statPanel = new StatPanel();
	
	public CCWindow() {
		super(640, 510, false);
		this.setTitle("DnD Character Creation");
		panel  .setBackground(new Color(50, 93, 110));
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.panel.add(statPanel);
		
		
		setContent();
	}
	
	private void setContent() {
		statPanel.setBounds(20,20,300,440);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}

class StatPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel nameLabel	= new JLabel("Name:");
	JLabel lvlLabel 	= new JLabel("Level:");
	JLabel raceLabel 	= new JLabel("Race:");
	JLabel hpLabel		= new JLabel("HP:");
	
	JLabel STRLabel 	= new JLabel("STR:");
	JLabel DEXLabel 	= new JLabel("DEX:");
	JLabel CONLabel 	= new JLabel("CON:");
	JLabel INTLabel 	= new JLabel("INT:");
	JLabel WISLabel 	= new JLabel("WIS:");
	JLabel CHRLabel 	= new JLabel("CHR:");
	
	JTextField nameField= new JTextField("NAME");
	JTextField hpField	= new JTextField("6");
	Integer[] lvlPoss 	= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}; 
	JComboBox<Integer> 
		lvlChoice		= new JComboBox<Integer>(lvlPoss);
	JComboBox<Race> 
		raceBox 		= new JComboBox<Race>(Race.values());
	
	JComboBox<Integer> STRBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> DEXBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> CONBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> INTBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> WISBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> CHRBox 	= new JComboBox<Integer>(lvlPoss);
	
	public StatPanel() {
		this  .setBackground(new Color(65,110, 120));
		
		raceBox.setSelectedItem(Race.CUSTOM);
		STRBox .setSelectedItem(10);
		DEXBox .setSelectedItem(10);
		CONBox .setSelectedItem(10);
		INTBox .setSelectedItem(10);
		WISBox .setSelectedItem(10);
		CHRBox .setSelectedItem(10);
		
		this.add(nameLabel); this.add(nameField);
		this.add(lvlLabel ); this.add(lvlChoice);
		this.add(raceLabel); this.add(raceBox  );
		this.add(hpLabel  ); this.add(hpField  );
		this.add(STRLabel ); this.add(STRBox   );
		this.add(DEXLabel ); this.add(DEXBox   );
		this.add(CONLabel ); this.add(CONBox   );
		this.add(INTLabel ); this.add(INTBox   );
		this.add(WISLabel ); this.add(WISBox   );
		this.add(CHRLabel ); this.add(CHRBox   );

		setContent();
	}
	
	private void setContent() {
		nameLabel.setBounds( 40, 40,100, 30);	nameField.setBounds(140, 40,100, 25);
		hpLabel	 .setBounds( 40, 85,100, 30); 	hpField	 .setBounds(140, 85,100, 25);
		raceLabel.setBounds( 40,120,100, 30);	raceBox	 .setBounds(140,120,100, 25);
		lvlLabel .setBounds( 40,155,100, 30);	lvlChoice.setBounds(140,155,100, 25);
		STRLabel .setBounds( 40,210,100, 30);	STRBox 	 .setBounds(140,210,100, 25);
		DEXLabel .setBounds( 40,245,100, 30);	DEXBox 	 .setBounds(140,245,100, 25);
		CONLabel .setBounds( 40,280,100, 30);	CONBox 	 .setBounds(140,280,100, 25);
		INTLabel .setBounds( 40,315,100, 30);	INTBox 	 .setBounds(140,315,100, 25);
		WISLabel .setBounds( 40,350,100, 30);	WISBox 	 .setBounds(140,350,100, 25);
		CHRLabel .setBounds( 40,385,100, 30);	CHRBox	 .setBounds(140,385,100, 25);
	}
}

class ResPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel	PIERlabel = new JLabel("");
	JLabel	SLASlabel = new JLabel("");
	JLabel	BLUDlabel = new JLabel("");
	JLabel	ACIDlabel = new JLabel("");
	JLabel	COLDlabel = new JLabel("");
	JLabel	FIRElabel = new JLabel("");
	JLabel	FORClabel = new JLabel("");
	JLabel	LIGHlabel = new JLabel("");
	JLabel	NECRlabel = new JLabel("");
	JLabel	POISlabel = new JLabel("");
	JLabel	PSYClabel = new JLabel("");
	JLabel	RADIlabel = new JLabel("");
	JLabel	THUNlabel = new JLabel("");

	JComboBox<ImmunityType> PIERbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> SLASbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> BLUDbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> ACIDbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> COLDbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> FIREbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> FORCbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> LIGHbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> NECRbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> POISbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> PSYCbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> RADIbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JComboBox<ImmunityType> THUNbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	
}

