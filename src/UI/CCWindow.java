package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EnumMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Core.Entity;
import Core.Program;
import Core.Weapon;
import Enums.Core.DNDClass;
import Enums.Core.Enhancement;
import Enums.Core.ImmunityType;
import Enums.Core.Race;
import Enums.Core.TeamColor;
import Enums.Core.WeaponType;
import Enums.Support.DamageType;
import Enums.Support.Die;
import Enums.Support.PropertyName;
import Support.DClass;
import Support.DamagePackage;
import Support.Editor;
import Support.EntityRegister;
import Support.Skill;

public class CCWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	MainWindow 	parent;
	StatPanel 	statPanel= new StatPanel  (this);
	ResPanel  	resPanel = new ResPanel	  (	);
	WeaponPanel wepPanel = new WeaponPanel("Main weapon");
	JButton		classw	 = new JButton	  ("Class Window");
	JButton		skillw	 = new JButton	  ("Skill Window");
	JButton		affirm	 = new JButton	  ("Save");
	JButton		cancel	 = new JButton	  ("Cancel");
	
	JComboBox<DNDClass>	
				classBox = new JComboBox<>(DNDClass.values());
	JButton		roll	 = new JButton	  (Editor.htmlize("Roll",2));
	
	Boolean		edit 	 = false;
	
	TeamColor	ccolor;
	Integer		cint;
	
	public EnumMap<DNDClass,DClass>
	  classMap 	  = new EnumMap<>(DNDClass.class);
	
	public void close() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	public CCWindow(MainWindow parent, TeamColor ccolor, Integer cint) {this(parent, ccolor, cint, false);}
	
	public CCWindow(MainWindow parent, TeamColor ccolor, Integer cint,boolean edit) {
		super(995, 580, false);
		this.edit = edit;
		this.setTitle("DnD Character Creation");
		this.parent = parent;
		this.ccolor = ccolor;
		this.cint	= cint;
		panel  .setBackground(Program.COLOR_LGHT);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.panel.add(statPanel);
		this.panel.add(resPanel );
		this.panel.add(wepPanel );
		this.panel.add(affirm	);
		this.panel.add(cancel	);

		this.panel.add(classw	);
		this.panel.add(skillw	);
		this.panel.add(classBox	);
		this.panel.add(roll		);
		
		this.classMap= new EnumMap<>(DNDClass.class);
		this.classBox.setSelectedItem(DNDClass.FIGHTER);
		
		classw.addActionListener(this);
		skillw.addActionListener(this);
		roll  .addActionListener(this);
		affirm.addActionListener(this);
		cancel.addActionListener(this);
		
		setContent();
	}
	
	public CCWindow(MainWindow parent, TeamColor ccolor, Integer cint, Entity ent) {
		this(parent,ccolor,cint,true);
		/// ===================
		this.statPanel.acField	.setText(Integer.toString(ent.getLvL()));
		this.statPanel.nameField.setText(ent.getName());
		this.statPanel.hpField	.setText(Integer.toString(ent.getHP()));
		this.statPanel.lvlChoice.setSelectedItem(ent.getLvL());
		this.statPanel.raceBox	.setSelectedItem(ent.getRace());
		this.statPanel.STRBox	.setSelectedItem(ent.getSkillV(PropertyName.STR));
		this.statPanel.DEXBox	.setSelectedItem(ent.getSkillV(PropertyName.DEX));
		this.statPanel.CONBox	.setSelectedItem(ent.getSkillV(PropertyName.CON));
		this.statPanel.INTBox	.setSelectedItem(ent.getSkillV(PropertyName.INT));
		this.statPanel.WISBox	.setSelectedItem(ent.getSkillV(PropertyName.WIS));
		this.statPanel.CHRBox	.setSelectedItem(ent.getSkillV(PropertyName.CHR));
		/// ===================
		this.resPanel.PIERbox.setSelectedItem(ent.getResistance(DamageType.PIER));
		this.resPanel.SLASbox.setSelectedItem(ent.getResistance(DamageType.SLAS));
		this.resPanel.BLUDbox.setSelectedItem(ent.getResistance(DamageType.BLUD));
		this.resPanel.ACIDbox.setSelectedItem(ent.getResistance(DamageType.ACID));
		this.resPanel.COLDbox.setSelectedItem(ent.getResistance(DamageType.COLD));
		this.resPanel.FIREbox.setSelectedItem(ent.getResistance(DamageType.FIRE));
		this.resPanel.FORCbox.setSelectedItem(ent.getResistance(DamageType.FORC));
		this.resPanel.LIGHbox.setSelectedItem(ent.getResistance(DamageType.LIGH));
		this.resPanel.NECRbox.setSelectedItem(ent.getResistance(DamageType.NECR));
		this.resPanel.POISbox.setSelectedItem(ent.getResistance(DamageType.POIS));
		this.resPanel.PSYCbox.setSelectedItem(ent.getResistance(DamageType.PSYC));
		this.resPanel.RADIbox.setSelectedItem(ent.getResistance(DamageType.RADI));
		this.resPanel.THUNbox.setSelectedItem(ent.getResistance(DamageType.THUN));
		/// ===================
		Weapon tmp = ent.getWeapon();
		this.wepPanel.nameField	.setText		(tmp.getName());
		this.wepPanel.profBox	.setSelected	(tmp.getProfficient());
		this.wepPanel.enhancBox	.setSelectedItem(tmp.getEnhancement());
		this.wepPanel.wtBox		.setSelectedItem(tmp.getWpType());
		for(DamagePackage dp: tmp.damage)
			this.wepPanel.updateTheLook(new WeaponRecord(dp.dmgType, dp.dieNo, dp.dieSize));
		/// ===================
		this.classMap = ent.classMap;
	}
	
	private void setContent() {
		statPanel.setBounds( 20, 60,300,440);
		resPanel .setBounds(340, 60,300,440);
		wepPanel .setBounds(660, 60,300,180);
		classBox .setBounds( 20, 15,240, 30);
		roll	 .setBounds(270, 15, 50, 30);
		classw	 .setBounds(340, 15,300, 30);
		skillw	 .setBounds(660, 15,300, 30);
		
		if(this.edit) {
			affirm.setBounds(680,470,240, 30);
			cancel.setBounds(920,470,  0, 30);
		}
		else {
			affirm.setBounds(680,470,110, 30);
			cancel.setBounds(810,470,110, 30);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(affirm == source) {
			Entity output = new Entity(
				(Race)this.statPanel.raceBox.getSelectedItem(),
				this.statPanel.nameField.getText(),
				this.statPanel.lvlChoice.getItemAt(this.statPanel.lvlChoice.getSelectedIndex()),
				Editor.tryParse(this.statPanel.hpField.getText()),
				Editor.tryParse(this.statPanel.acField.getText()),
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
			output.color = this.ccolor;
			
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
			if(putout.getDmType()==null && putout.damage.size()>0) putout.setDmType(putout.damage.get(0).dmgType);
			
			putout.setEnhancement(this.wepPanel.enhancBox.getItemAt(this.wepPanel.enhancBox.getSelectedIndex()));
			putout.setProfficient(this.wepPanel.profBox.isSelected());
			output.classMap = this.classMap;
			output.setWeapon(putout);
	
			EntityRegister.put(this.ccolor, output);
			if(this.ccolor == TeamColor.BLUE) 
				 this.parent.blue_team	.updateTheLook(new CharacterRecord(output));
			else this.parent.red_team	.updateTheLook(new CharacterRecord(output));
			
			this.close();
		}
		else
		if(cancel == source) {
			this.close();
		}
		else
		if(classw == source) {
			new ClassWindow(this,this.statPanel.lvlChoice.getItemAt(this.statPanel.lvlChoice.getSelectedIndex()));
		}
		else
		if(skillw == source) {
			
		}
		else
		if(roll == source) {
			ArrayList<Integer> rolls = new ArrayList<>();
			for(int i=0; i<6; i++) rolls.add(Skill.rollSkill());
			rolls.sort((Integer x, Integer y)-> y.intValue()-x.intValue());
			int i = 0;
			switch((DNDClass)this.classBox.getSelectedItem()) {
			case BARBARIAN:
			case FIGHTER:
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				break;
			case BARD:
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				break;
			case CLERIC:
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				break;
			case DRUID:
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				break;
			case MONK:
			case RANGER:
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				break;
			case PALADIN:
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				break;
			case ROGUE:
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				break;
			case SORCERER:
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				break;
			case WARLOCK:
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				break;
			case WIZARD:
				this.statPanel.INTBox.setSelectedItem(rolls.get(i++));
				this.statPanel.WISBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CHRBox.setSelectedItem(rolls.get(i++));
				this.statPanel.DEXBox.setSelectedItem(rolls.get(i++));
				this.statPanel.CONBox.setSelectedItem(rolls.get(i++));
				this.statPanel.STRBox.setSelectedItem(rolls.get(i++));
				break;
			}
		}
	}
}

class StatPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	CCWindow master;
	
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
			lvlChoice	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Race> 
			raceBox 	= new JComboBox<Race>(Race.values());
	
	JComboBox<Integer> STRBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> DEXBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> CONBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> WISBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> INTBox 	= new JComboBox<Integer>(lvlPoss);
	JComboBox<Integer> CHRBox 	= new JComboBox<Integer>(lvlPoss);
	
	public StatPanel(CCWindow master) {
		this  .setBackground(new Color(65,110, 120));
		this  .master = master;
		
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
		this.add(raceLabel); this.add(raceBox  ); this.raceBox.addActionListener(this);
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
		WISLabel .setBounds(dim.x+ 20,dim.y+318,100, 25);	WISBox 	 .setBounds(dim.x+120,dim.y+318,100, 25);
		INTLabel .setBounds(dim.x+ 20,dim.y+354,100, 25);	INTBox 	 .setBounds(dim.x+120,dim.y+354,100, 25);
		CHRLabel .setBounds(dim.x+ 20,dim.y+390,100, 25);	CHRBox	 .setBounds(dim.x+120,dim.y+390,100, 25);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == raceBox) {
		    this.master.resPanel.PIERbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.SLASbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.BLUDbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.ACIDbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.COLDbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.FIREbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.FORCbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.LIGHbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.NECRbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.POISbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.PSYCbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.RADIbox.setSelectedItem(ImmunityType.NONE);
		    this.master.resPanel.THUNbox.setSelectedItem(ImmunityType.NONE);
		    
			switch((Race)raceBox.getSelectedItem()) {
			case CUSTOM:
				break;
			case DWARF:
				this.master.resPanel.POISbox.setSelectedItem(ImmunityType.RESISTANT);
				break;
			case ELF:
				break;
			case GNOME:
				break;
			case HALFLING:
				break;
			case HALF_ELF:
				break;
			case HALF_ORC:
				break;
			case HUMAN:
				break;
			case KOBOLD:
				break;
			case ORC:
				break;
			case TIEFLING:
				this.master.resPanel.FIREbox.setSelectedItem(ImmunityType.RESISTANT);
				break;
			case BUGBEAR:
				break;
			case DRAGONBORN:
				break;
			case GOBLIN:
				break;
			case WARFORGED:
				this.master.resPanel.POISbox.setSelectedItem(ImmunityType.RESISTANT);
				break;
			default:
				break;
			
			}
		}
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
		this(DamageType.BLUD,1,Die.D4);
	}
	
	public WeaponRecord(DamageType dt, Integer dn, Die dit) {
		this  .setBackground(new Color(100,145,165));
		Rectangle bond = this.getBounds();
		this.add(DICENObox	 );	DICENObox	.setBounds(bond.x+  5,bond.y+ 5, 35, 20);
		this.add(DICETYPEbox );	DICETYPEbox .setBounds(bond.x+ 45,bond.y+ 5, 40, 20);
		this.add(DMGbox		 );	DMGbox		.setBounds(bond.x+100,bond.y+ 5, 60, 20);
		this.add(deleteButton);	deleteButton.setBounds(bond.x+185,bond.y+ 5, 45, 20);
		this.DMGbox		.setSelectedItem(dt);
		this.DICENObox	.setSelectedItem(dn);
		this.DICETYPEbox.setSelectedItem(dit);
	}
}

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
		int height = this.addRecord.getBounds().y+this.addRecord.getBounds().height+75-bond.y;
		this.setBounds(bond.x,bond.y,bond.width,height);
		this.repaint();
	}
	
	public void updateTheLook(WeaponRecord record) {
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
			for(int i=0;i<records.size();i++)
			if(source == records.get(i).deleteButton) {
				this.remove(records.get(i));
				records.remove(i);
				updateTheLook();
			}
		}
	}
}