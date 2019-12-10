package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Core.Entity;
import Core.Weapon;
import Enums.DamageType;
import Enums.Die;
import Enums.Enhancement;
import Enums.ImmunityType;
import Enums.Race;
import Enums.WeaponType;

public class CCWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	MainWindow parent;
	StatPanel statPanel = new StatPanel	 ( );
	ResPanel  resPanel  = new ResPanel	 ( );
	WeaponPanel wepPanel= new WeaponPanel("Main weapon");
	JButton	affirm		= new JButton	 ("Save");
	JButton	cancel		= new JButton	 ("Cancel");
	
	public CCWindow(MainWindow parent) {
		super(995, 540, false);
		this.setTitle("DnD Character Creation");
		this.parent = parent;
		panel  .setBackground(new Color(50, 93, 110));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.panel.add(statPanel);
		this.panel.add(resPanel );
		this.panel.add(wepPanel );
		this.panel.add(affirm	);
		this.panel.add(cancel	);		
		affirm.addActionListener(this);
		cancel.addActionListener(this);
		
		setContent();
	}
	
	private void setContent() {
		statPanel.setBounds( 20, 20,300,440);
		resPanel .setBounds(340, 20,300,440);
		wepPanel .setBounds(660, 20,300,180);
		affirm	 .setBounds(680,430,110, 30);
		cancel	 .setBounds(810,430,110, 30);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(affirm == source) {
			Entity output = new Entity(
					(Race)this.statPanel.raceBox.getSelectedItem(),
					this.statPanel.nameField.getText(),
					this.statPanel.lvlChoice.getItemAt(this.statPanel.lvlChoice.getSelectedIndex()),
					Integer.parseInt(this.statPanel.hpField.getText()),
					Integer.parseInt(this.statPanel.acField.getText()),
					this.statPanel.STRBox.getItemAt(this.statPanel.STRBox.getSelectedIndex()),
					this.statPanel.DEXBox.getItemAt(this.statPanel.DEXBox.getSelectedIndex()),
					this.statPanel.CONBox.getItemAt(this.statPanel.CONBox.getSelectedIndex()),
					this.statPanel.WISBox.getItemAt(this.statPanel.WISBox.getSelectedIndex()),
					this.statPanel.INTBox.getItemAt(this.statPanel.INTBox.getSelectedIndex()),
					this.statPanel.CHRBox.getItemAt(this.statPanel.CHRBox.getSelectedIndex())
					);
			output.setResistance(DamageType.PIER,this.resPanel.PIERbox.getItemAt(this.resPanel.PIERbox.getSelectedIndex()));
			output.setResistance(DamageType.SLAS,this.resPanel.SLASbox.getItemAt(this.resPanel.SLASbox.getSelectedIndex()));
			output.setResistance(DamageType.BLUD,this.resPanel.BLUDbox.getItemAt(this.resPanel.BLUDbox.getSelectedIndex()));
			output.setResistance(DamageType.ACID,this.resPanel.ACIDbox.getItemAt(this.resPanel.ACIDbox.getSelectedIndex()));
			output.setResistance(DamageType.COLD,this.resPanel.COLDbox.getItemAt(this.resPanel.COLDbox.getSelectedIndex()));
			output.setResistance(DamageType.FIRE,this.resPanel.FIREbox.getItemAt(this.resPanel.FIREbox.getSelectedIndex()));
			output.setResistance(DamageType.FORC,this.resPanel.FORCbox.getItemAt(this.resPanel.FORCbox.getSelectedIndex()));
			output.setResistance(DamageType.LIGH,this.resPanel.LIGHbox.getItemAt(this.resPanel.LIGHbox.getSelectedIndex()));
			output.setResistance(DamageType.NECR,this.resPanel.NECRbox.getItemAt(this.resPanel.NECRbox.getSelectedIndex()));
			output.setResistance(DamageType.POIS,this.resPanel.POISbox.getItemAt(this.resPanel.POISbox.getSelectedIndex()));
			output.setResistance(DamageType.PSYC,this.resPanel.PSYCbox.getItemAt(this.resPanel.PSYCbox.getSelectedIndex()));
			output.setResistance(DamageType.RADI,this.resPanel.RADIbox.getItemAt(this.resPanel.RADIbox.getSelectedIndex()));
			output.setResistance(DamageType.THUN,this.resPanel.THUNbox.getItemAt(this.resPanel.THUNbox.getSelectedIndex()));
			
			Weapon putout = new Weapon(this.wepPanel.nameField.getText(),this.wepPanel.wtBox.getItemAt(this.wepPanel.wtBox.getSelectedIndex()));
			DamageType temp;
			for(WeaponRecord wr: this.wepPanel.records) {
				temp = wr.DMGbox.getItemAt(wr.DMGbox.getSelectedIndex());
				putout.addDmg(wr.DICENObox.getItemAt(wr.DICENObox.getSelectedIndex()), 
							  wr.DICETYPEbox.getItemAt(wr.DICETYPEbox.getSelectedIndex()), 
							  temp);
				if(temp == DamageType.BLUD || temp == DamageType.PIER ||temp == DamageType.SLAS) {
					if(putout.getDmType()==null) {
						putout.setDmType(temp);
					}
				}
			}
			if(putout.getDmType()==null) {
				putout.setDmType(putout.damage.get(0).dmgType);
			}
			putout.setEnhancement(this.wepPanel.enhancBox.getItemAt(this.wepPanel.enhancBox.getSelectedIndex()));
			putout.setProfficient(this.wepPanel.profBox.isSelected());
			output.setWeapon(putout);
			
			parent.saveCCFile(output);
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else
		if(cancel == source) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}

}

class StatPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel titleLabel	= new JLabel("Character Statistics");
	
	JLabel nameLabel	= new JLabel("Name:");
	JLabel lvlLabel 	= new JLabel("Level:");
	JLabel raceLabel 	= new JLabel("Race:");
	JLabel hpLabel		= new JLabel("HP:");
	JLabel acLabel		= new JLabel("AC:");
	
	JLabel STRLabel 	= new JLabel("STR:");
	JLabel DEXLabel 	= new JLabel("DEX:");
	JLabel CONLabel 	= new JLabel("CON:");
	JLabel INTLabel 	= new JLabel("INT:");
	JLabel WISLabel 	= new JLabel("WIS:");
	JLabel CHRLabel 	= new JLabel("CHR:");
	
	JTextField nameField= new JTextField("NAME");
	JTextField hpField	= new JTextField("6");
	JTextField acField	= new JTextField("10");
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

	    this.add(titleLabel);
		this.add(nameLabel); this.add(nameField);
		this.add(lvlLabel ); this.add(lvlChoice);
		this.add(raceLabel); this.add(raceBox  );
		this.add(hpLabel  ); this.add(hpField  );
		this.add(acLabel  ); this.add(acField  );
		this.add(STRLabel ); this.add(STRBox   );
		this.add(DEXLabel ); this.add(DEXBox   );
		this.add(CONLabel ); this.add(CONBox   );
		this.add(INTLabel ); this.add(INTBox   );
		this.add(WISLabel ); this.add(WISBox   );
		this.add(CHRLabel ); this.add(CHRBox   );
		
		this.setLayout(null);
		setContent();
	}
	
	private void setContent() {
		Rectangle dim = this.getBounds();

		titleLabel.setBounds(dim.x+60,dim.y,    240, 25);
		nameLabel.setBounds(dim.x+ 20,dim.y+ 28,100, 25);	nameField.setBounds(dim.x+120,dim.y+ 28,100, 25);
		hpLabel	 .setBounds(dim.x+ 20,dim.y+ 56,100, 25); 	hpField	 .setBounds(dim.x+120,dim.y+ 56,100, 25);
		acLabel  .setBounds(dim.x+ 20,dim.y+ 84,100, 25); 	acField  .setBounds(dim.x+120,dim.y+ 84,100, 25);
		raceLabel.setBounds(dim.x+ 20,dim.y+113,100, 25);	raceBox	 .setBounds(dim.x+120,dim.y+113,100, 25);
		lvlLabel .setBounds(dim.x+ 20,dim.y+149,100, 25);	lvlChoice.setBounds(dim.x+120,dim.y+149,100, 25);
		STRLabel .setBounds(dim.x+ 20,dim.y+210,100, 25);	STRBox 	 .setBounds(dim.x+120,dim.y+210,100, 25);
		DEXLabel .setBounds(dim.x+ 20,dim.y+246,100, 25);	DEXBox 	 .setBounds(dim.x+120,dim.y+246,100, 25);
		CONLabel .setBounds(dim.x+ 20,dim.y+282,100, 25);	CONBox 	 .setBounds(dim.x+120,dim.y+282,100, 25);
		INTLabel .setBounds(dim.x+ 20,dim.y+318,100, 25);	INTBox 	 .setBounds(dim.x+120,dim.y+318,100, 25);
		WISLabel .setBounds(dim.x+ 20,dim.y+354,100, 25);	WISBox 	 .setBounds(dim.x+120,dim.y+354,100, 25);
		CHRLabel .setBounds(dim.x+ 20,dim.y+390,100, 25);	CHRBox	 .setBounds(dim.x+120,dim.y+390,100, 25);
	}
}

class ResPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel titleLabel = new JLabel("Damage Resistances");
	
	JLabel	PIERlabel = new JLabel("Piercing"   ); JComboBox<ImmunityType> PIERbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	SLASlabel = new JLabel("Slashing"   ); JComboBox<ImmunityType> SLASbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	BLUDlabel = new JLabel("Bludgeoning"); JComboBox<ImmunityType> BLUDbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	ACIDlabel = new JLabel("Acid"       ); JComboBox<ImmunityType> ACIDbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	COLDlabel = new JLabel("Cold"       ); JComboBox<ImmunityType> COLDbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	FIRElabel = new JLabel("Fire"       ); JComboBox<ImmunityType> FIREbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	FORClabel = new JLabel("Force"      ); JComboBox<ImmunityType> FORCbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	LIGHlabel = new JLabel("Lightning"  ); JComboBox<ImmunityType> LIGHbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	NECRlabel = new JLabel("Necrotic"   ); JComboBox<ImmunityType> NECRbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	POISlabel = new JLabel("Poison"     ); JComboBox<ImmunityType> POISbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	PSYClabel = new JLabel("Psychic"    ); JComboBox<ImmunityType> PSYCbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	RADIlabel = new JLabel("Radiant"    ); JComboBox<ImmunityType> RADIbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	JLabel	THUNlabel = new JLabel("Thunder"    ); JComboBox<ImmunityType> THUNbox	= new JComboBox<ImmunityType>(ImmunityType.values());
	
	public ResPanel() {
		this  .setBackground(new Color(65,110, 120));
		
	    PIERbox.setSelectedItem(ImmunityType.NONE);
	    SLASbox.setSelectedItem(ImmunityType.NONE);
	    BLUDbox.setSelectedItem(ImmunityType.NONE);
	    ACIDbox.setSelectedItem(ImmunityType.NONE);
	    COLDbox.setSelectedItem(ImmunityType.NONE);
	    FIREbox.setSelectedItem(ImmunityType.NONE);
	    FORCbox.setSelectedItem(ImmunityType.NONE);
	    LIGHbox.setSelectedItem(ImmunityType.NONE);
	    NECRbox.setSelectedItem(ImmunityType.NONE);
	    POISbox.setSelectedItem(ImmunityType.NONE);
	    PSYCbox.setSelectedItem(ImmunityType.NONE);
	    RADIbox.setSelectedItem(ImmunityType.NONE);
	    THUNbox.setSelectedItem(ImmunityType.NONE);
		
	    this.add(titleLabel);
		this.add(PIERlabel); this.add(PIERbox);
		this.add(SLASlabel); this.add(SLASbox);
		this.add(BLUDlabel); this.add(BLUDbox);
		this.add(ACIDlabel); this.add(ACIDbox);
		this.add(COLDlabel); this.add(COLDbox);
		this.add(FIRElabel); this.add(FIREbox);
		this.add(FORClabel); this.add(FORCbox);
		this.add(LIGHlabel); this.add(LIGHbox);
		this.add(NECRlabel); this.add(NECRbox);
		this.add(POISlabel); this.add(POISbox);
		this.add(PSYClabel); this.add(PSYCbox);
		this.add(RADIlabel); this.add(RADIbox);
		this.add(THUNlabel); this.add(THUNbox);

		this.setLayout(null);
		setContent();
	}
	
	private void setContent() {
		Rectangle dim = this.getBounds();
		
		titleLabel.setBounds(dim.x+60,dim.y,    240, 30);
		PIERlabel.setBounds(dim.x+ 20,dim.y+ 30,100, 25);   PIERbox.setBounds(dim.x+120,dim.y+ 30,100, 25);
		SLASlabel.setBounds(dim.x+ 20,dim.y+ 60,100, 25);   SLASbox.setBounds(dim.x+120,dim.y+ 60,100, 25);
		BLUDlabel.setBounds(dim.x+ 20,dim.y+ 90,100, 25);   BLUDbox.setBounds(dim.x+120,dim.y+ 90,100, 25);
		ACIDlabel.setBounds(dim.x+ 20,dim.y+120,100, 25);   ACIDbox.setBounds(dim.x+120,dim.y+120,100, 25);
		COLDlabel.setBounds(dim.x+ 20,dim.y+150,100, 25);   COLDbox.setBounds(dim.x+120,dim.y+150,100, 25);
		FIRElabel.setBounds(dim.x+ 20,dim.y+180,100, 25);   FIREbox.setBounds(dim.x+120,dim.y+180,100, 25);
		FORClabel.setBounds(dim.x+ 20,dim.y+210,100, 25);   FORCbox.setBounds(dim.x+120,dim.y+210,100, 25);
		LIGHlabel.setBounds(dim.x+ 20,dim.y+240,100, 25);   LIGHbox.setBounds(dim.x+120,dim.y+240,100, 25);
		NECRlabel.setBounds(dim.x+ 20,dim.y+270,100, 25);   NECRbox.setBounds(dim.x+120,dim.y+270,100, 25);
		POISlabel.setBounds(dim.x+ 20,dim.y+300,100, 25);   POISbox.setBounds(dim.x+120,dim.y+300,100, 25);
		PSYClabel.setBounds(dim.x+ 20,dim.y+330,100, 25);   PSYCbox.setBounds(dim.x+120,dim.y+330,100, 25);
		RADIlabel.setBounds(dim.x+ 20,dim.y+360,100, 25);   RADIbox.setBounds(dim.x+120,dim.y+360,100, 25);
		THUNlabel.setBounds(dim.x+ 20,dim.y+390,100, 25);   THUNbox.setBounds(dim.x+120,dim.y+390,100, 25);
	}
}

class WeaponRecord extends JPanel{
	private static final long serialVersionUID = 1L;

	Integer[] temp 	= {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20}; 
	public JComboBox<DamageType> DMGbox	= new JComboBox<DamageType>(DamageType.values());
	public JComboBox<Integer> DICENObox	= new JComboBox<Integer>(temp);
	public JComboBox<Die> DICETYPEbox	= new JComboBox<Die>(Die.values());
	public JButton deleteButton 		= new JButton("X");
	
	public WeaponRecord() {
		this  .setBackground(new Color(100,145,165));
		Rectangle bond = this.getBounds();
		this.add(DICENObox	 );	DICENObox	.setBounds(bond.x+  5,bond.y+ 5, 35, 20);
		this.add(DICETYPEbox );	DICETYPEbox .setBounds(bond.x+ 45,bond.y+ 5, 40, 20);
		this.add(DMGbox		 );	DMGbox		.setBounds(bond.x+100,bond.y+ 5, 60, 20);
		this.add(deleteButton);	deleteButton.setBounds(bond.x+185,bond.y+ 5, 45, 20);
	}
}
/*
WeaponRecord wrc = new WeaponRecord();
this.panel.add(wrc);
wrc.setBounds(340,500,280, 30);
//*/

class WeaponPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	JLabel title = new JLabel("");
	JComboBox<Enhancement> enhancBox = new JComboBox<Enhancement>(Enhancement.values());
	JLabel name  = new JLabel("Name:");
	JLabel wtLabel = new JLabel("Type:");
	JComboBox<WeaponType> wtBox = new JComboBox<WeaponType>(WeaponType.values());
	JCheckBox profBox = new JCheckBox("Profficient");
	public JTextField nameField = new JTextField("");
	public ArrayList<WeaponRecord> 
			records = new ArrayList<WeaponRecord>();
	private JButton 
			addRecord = new JButton("+");
	
	public WeaponPanel(String tit) {
		Rectangle bond = this.getBounds();
		this.setBackground(new Color(65,110, 120));
		this.add(title); 	title.setBounds(bond.x+100,bond.y+5,120, 30); title.setText(tit);
		this.add(enhancBox);enhancBox.setBounds(bond.x+200,bond.y+5, 60, 30);
		this.add(name); 	name.setBounds(bond.x+20,bond.y+40,80, 25);
		this.add(nameField);nameField.setBounds(bond.x+70,bond.y+40, 190, 25);
		this.add(wtLabel);	wtLabel.setBounds(bond.x+20, bond.y+70, 80, 25);
		this.add(wtBox);	wtBox.setBounds(bond.x+70, bond.y+70, 90, 25); this.add(profBox); profBox.setBounds(bond.x+170, bond.y+70, 90, 25);
		this.add(addRecord);addRecord.addActionListener(this);
		this.updateTheLook();
		this.setLayout(null);
	}
	
	private void updateTheLook() {
		Rectangle bond = this.getBounds();
		for(int i = 0; i<records.size();i++) {
			records.get(i).setBounds(20, 110+33*i,240,30);
		}
		Rectangle temp = new Rectangle(0,75,0,0);
		if(records.size()>0) temp = records.get(records.size()-1).getBounds();
		if(this.records.size()<8) this.addRecord.setBounds(20, temp.y+33, 240, 30);
		else this.addRecord.setBounds(20, temp.y+30, 240, 0);
		this.setBounds(bond.x,bond.y,bond.width,this.addRecord.getBounds().y+this.addRecord.getBounds().height+30-bond.y);
	}
	
	private void updateTheLook(WeaponRecord record) {
		this.records.add(record);
		this.add(record);
		record.deleteButton.addActionListener(this);
		updateTheLook();
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == addRecord) {
			if(this.records.size()<8) updateTheLook(new WeaponRecord());
		}
		else {
			if(this.records.size()>1) {
				for(int i = 0; i<records.size();i++) {
					if(source == records.get(i).deleteButton) {
						this.remove(records.get(i));
						records.remove(i);
						updateTheLook();
					}
				}
			}
		}
	}
	}


