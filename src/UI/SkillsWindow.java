package UI;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Core.Program;
import Enums.Active.ActionType;
import Enums.Core.EffectEffect;

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
	ArrayList<EffectRecord> records = new ArrayList<>();
	JComboBox<ActionType> actType = new JComboBox<>();
	JButton	addRecord = new JButton("+");
	JButton save = new JButton("Save");
	
	public SkillWindow(SkillRecord master) {
		super(640,640,false); 
		this.master = master;		setTitle(master!=null? master.name.getText()+" - edit":"SkillWindow");
		Program.sleep(10);
		this.panel.add(actType);
		this.panel.add(addRecord); 	addRecord.addActionListener(this);
		this.panel.add(save);		save	 .addActionListener(this);
		Program.sleep(10);
		this.setComponents();
	}
	
	public void setComponents() {
		Rectangle temp = this.panel.getBounds();
			int tempy;
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
	
	public void setComponents(EffectRecord cr) {
		this.records.add(cr);
		this.panel.add(cr);
		cr.delete		.addActionListener(this);
		cr.switchResist	.addActionListener(this);
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

class EffectRecord extends JPanel{
	private static final long serialVersionUID = 1L;
	JButton switchResist = new JButton();
	JComboBox<EffectEffect> 
			effect = new JComboBox<>();
	JButton delete = new JButton();
	
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.effect      .setBounds((width*1)/10,	height/10,	(width*2)/10,	(height*8)/10);
		this.switchResist.setBounds((width*7)/10,	height/10,	(height*8)/10,	(height*8)/10);
		this.delete		 .setBounds((width*85)/100,	height/10,	(height*8)/10,	(height*8)/10);
	}
}
