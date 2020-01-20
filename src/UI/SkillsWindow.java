package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import Core.Program;
import Enums.Active.ActionType;
import Enums.Active.ResultOfResist;
import Enums.Core.EffectEffect;
import Enums.Support.Die;
import Enums.Support.PropertyName;

public class SkillsWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	CCWindow master;
	ArrayList<SkillRecord> 
			records = new ArrayList<>();
	
	JButton	addRecord = new JButton("+");
	JButton	save	  = new JButton("Save");

	public SkillsWindow(CCWindow master) {
		super(400,720,true/*false/**/);
		this.master = master; this.setTitle((this.master!=null ? master.statPanel.nameField.getText()+" - Skills":"SkillWindow"));
		this.add(addRecord); addRecord.addActionListener(this);
		this.add(save);		 save	  .addActionListener(this);
		Program.sleep(10);
		this.setComponents();
	}
	
	public void sort() {
		this.records.sort(
			(SkillRecord X, SkillRecord Y) -> {
				int i = 0;
				while(X.name.getText().charAt(i)==Y.name.getText().charAt(i)) {
					if(i<X.name.getText().length()-1 && i<Y.name.getText().length()-1) i++;
					else return 0;
				}
				return (Integer)(X.name.getText().charAt(i) - Y.name.getText().charAt(i));
			}
		);
	}
	
	public void setComponents() {
		Rectangle temp = this.panel.getBounds();
			int tempy;
		this.sort();
		for(int i=0; i<records.size(); i++) {
			tempy = temp.height*1/40 + ((i*5*temp.height)/48);
			records.get(i).setBounds(temp.width/16,tempy,temp.width*7/8,temp.height*1/12);
		}
		tempy = temp.height*1/40 + ((records.size()*5*temp.height)/48);
		if(records.size()>=8) 
			addRecord.setBounds(temp.width/16,tempy,temp.width*7/8,0);
		else
			addRecord.setBounds(temp.width/16,tempy,temp.width*7/8,temp.height*1/12);
		
		save.setBounds(temp.width/16,temp.height/40 + (40*temp.height)/48,temp.width*7/8,temp.height/12);
		this.repaint();
	}
	
	public void setComponents(SkillRecord cr) {
		this.records.add(cr);
		this.panel.add(cr);
		cr.delete	.addActionListener(this);
		cr.edit		.addActionListener(this);
		this.setComponents();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == addRecord) {
			SkillRecord rec = new SkillRecord(this);
			setComponents(rec);
			new SkillWindow(rec);
		}
		else {
			if(this.records.size()>0) {
				for(int i = 0; i<records.size();i++) {
					if(source == records.get(i).delete) {
						this.remove(records.get(i));;
						this.records.remove(i);
						setComponents();
					}
					else 
					if(source == records.get(i).edit) {
						//this.remove(records.get(i));
						//this.records.remove(i);
						new SkillWindow(records.get(i));
					}
				}
			}
		}
	}
}

class SkillRecord extends JPanel{
	private static final long serialVersionUID = 1L;
	SkillsWindow master;
	public JLabel	name	= new JLabel ("Skill");
	public JButton	delete  = new JButton("X");
	public JButton	edit	= new JButton("...");
	
	public SkillRecord(SkillsWindow master) {
		this.master = master;
		this.add(name); name.setText("Skill"+(master.records.size()+1));
		this.add(delete);
		this.add(edit);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.name       .setBounds(	(width*1)/10,	height/10,	(width*2)/10,	(height*8)/10);
		this.edit		.setBounds(	(width*7)/10,	height/10,	(height*8)/10,	(height*8)/10);
		this.delete		.setBounds( (width*85)/100,	height/10,	(height*8)/10,	(height*8)/10);
	}
}

class SkillWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	SkillRecord master;
	JLabel nameL = new JLabel("Name:");
	JTextField nameP = new JTextField();
	ArrayList<EffectRecord> records = new ArrayList<>();
	JComboBox<ActionType> actType = new JComboBox<>();
	JButton	addRecord = new JButton("+");
	JButton save = new JButton("Save");
	
	public SkillWindow(SkillRecord master) {
		super(640,640,false); 
		this.master = master;		setTitle(master!=null? master.name.getText()+" - edit":"SkillWindow");
		this.panel.setBackground(new Color( 60,103,120));
		nameP = new JTextField(master!=null? master.name.getText():"skill name");
		this.panel.add(nameL);
		this.panel.add(nameP);
		
		this.panel.add(actType);
		this.panel.add(addRecord); 	addRecord.addActionListener(this);
		this.panel.add(save);		save	 .addActionListener(this);
		this.setComponents(new EffectRecord());
		Program.sleep(100);
		this.remove(records.get(0));;
		this.records.remove(0);
		setComponents();
	}
	
	public void setComponents() {
		Rectangle temp = this.panel.getBounds();
			int tempy;
		nameL.setBounds(temp.width/16,temp.height*1/40,temp.width*1/8,temp.height*1/14); 
		nameP.setBounds(temp.width*3/16,temp.height*1/40,temp.width*6/8,temp.height*1/14);
		
		for(int i=0; i<records.size(); i++) {
			tempy = temp.height*3/80 + temp.height*1/12 + ((i*5*temp.height)/48);
			records.get(i).setBounds(temp.width/16,tempy,temp.width*7/8,temp.height*1/12);
		}
		tempy = temp.height*3/80 + temp.height*1/12 + ((records.size()*5*temp.height)/48);
		if(records.size()>=7) 
			addRecord.setBounds(temp.width/16,tempy,temp.width*7/8,0);
		else
			addRecord.setBounds(temp.width/16,tempy,temp.width*7/8,temp.height*1/12);
		
		save.setBounds(temp.width/16,temp.height/40 + (40*temp.height)/48,temp.width*7/8,temp.height/12);
		this.repaint();
	}
	
	public void setComponents(EffectRecord cr) {
		this.records.add(cr);
		this.panel.add(cr);
		cr.delete.addActionListener(this);
		cr.res	 .addActionListener(this);
		this.setComponents();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == addRecord) setComponents(new EffectRecord());
		else {
			if(this.records.size()>0) {
				for(int i = 0; i<records.size();i++) {
					if(source == records.get(i).delete) {
						this.remove(records.get(i));;
						this.records.remove(i);
						setComponents();
					}
				}
			}
		}
	}
}

class EffectRecord extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	boolean resistable = false;
	JButton res = new JButton("resist");
	JComboBox<EffectEffect> 
			effect = new JComboBox<>(EffectEffect.values());
	JButton delete = new JButton("X");
	
	String[] stab = {"+","-"};
	Integer[] tab = { 0, 1, 2, 3, 4, 
					  5, 6, 7, 8, 9,
					 10,11,12,13,14,
					 15,16,17,18,19,20};
	
	JComboBox<String>	howTo = new JComboBox<>(stab);
	JComboBox<Integer>	dieNo = new JComboBox<>(tab);
	JComboBox<Die> 		dieType = new JComboBox<>(Die.values());
	JLabel				plusLabel = new JLabel("+");
	JComboBox<Integer> 	addValue = new JComboBox<>(tab);
	
	
	
	public EffectRecord() {
		this.add(res);		res.addActionListener(this);
		this.add(effect);	effect.setSelectedItem(EffectEffect.FIRE_DMG);
		this.add(delete);
		
		this.add(howTo);
		this.add(dieNo);	dieNo	.setSelectedItem(0);
		this.add(dieType); 	dieType	.setSelectedItem(Die.D4);
		this.add(plusLabel);
		this.add(addValue); addValue.setSelectedItem(1);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.effect   .setBounds(	height/10,	 height/10,	(width*15)/100,	(height*8)/10);
		this.res.setBounds((width*155)/200,height/10,	(height*14)/10,	(height*8)/10);
		this.delete	  .setBounds((width*91)/100, height/10,	(height*9)/10,	(height*8)/10);
		
		this.howTo	  .setBounds((width*20)/100, height/10,	(width*3)/100,	(height*8)/10);
		this.dieNo	  .setBounds((width*23)/100, height/10,	(width*7)/100,	(height*8)/10);
		this.dieType  .setBounds((width*30)/100, height/10,	(width*7)/100,	(height*8)/10);
		this.plusLabel.setBounds((width*37)/100, height/10,	(width*7)/100,	(height*8)/10);
		this.addValue .setBounds((width*39)/100, height/10,	(width*7)/100,	(height*8)/10);
		
		//if(this.resistable) {
			
		//}
		//else {
			
		//}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == res) {
			new ResWindow();
		}
		
	}
}

class ResWindow extends DNDWindow{
	private static final long serialVersionUID = 1L;
	
	JButton save = new JButton("Save");
	JButton delt = new JButton("X");
	
	JComboBox<ResultOfResist> 
		ror = new JComboBox<>(ResultOfResist.values());
	JComboBox<PropertyName>
		dcProp = new JComboBox<>(PropertyName.values());
	
	public ResWindow() {
		super(480,360,false);
		Rectangle temp = this.panel.getBounds();
		
		this.panel.add(save);	save	.setBounds(temp.width/3, temp.height*5/6, temp.width/3, temp.height/8);
		this.panel.add(delt);	delt	.setBounds(temp.width/3, temp.height*4/6, temp.width/3, temp.height/8);
		this.panel.add(ror);	ror 	.setBounds(temp.width/3, temp.height*3/6, temp.width/3, temp.height/8);
		this.panel.add(dcProp);	dcProp	.setBounds(temp.width/3, temp.height*2/6, temp.width/3, temp.height/8);
	}
}
