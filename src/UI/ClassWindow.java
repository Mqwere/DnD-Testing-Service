package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.EnumMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import Core.Program;
import Enums.Core.DNDClass;
import Support.DClass;

public class ClassWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private int 		maxclevel;
	private CCWindow	master;
	public  int			clevel;
	public ArrayList<ClassRecord> 
					records   = new ArrayList<>();
	public ArrayList<DNDClass> 
					clarr 	  = new ArrayList<>();
	JPanel			panel	  = new JPanel();
	JButton			addRecord = new JButton("+");
	JButton			save 	  = new JButton("SAVE");
	
	public ClassWindow(CCWindow master, int clevel) {
		super(400, 540, false);
		this.master = master;
		if(master!=null) this.setTitle((this.master!=null ? master.statPanel.nameField.getText()+" - Class":"ClassWindow"));
		this.panel.setBackground(Program.COLOR_DARK);
		this.maxclevel 	= clevel;
		this.clevel 	= clevel;
		
		panel.setLayout(null);
		this.panel.add(addRecord); 	addRecord.addActionListener(this);
		this.panel.add(save); 		save	 .addActionListener(this); save.setEnabled(false);
		initializeArr();
		this.setContentPane(panel);
		evaluateMaster();
		this.setVisible(true);
		this.setComponents();
	}
	//this.setComponents(new ClassRecord(this));
	//ClassWindow master, int remLvL, DNDClass dcl, Integer lvl, ArrayList<DNDClass> arr, boolean primary
	
	public void evaluateMaster() {
		if(this.master.classMap.size()>0) {
			for(DNDClass dc: this.master.classMap.keySet()) {
				this.setComponents(new ClassRecord(this,this.clevel,dc,this.master.classMap.get(dc).level,this.clarr,this.master.classMap.get(dc).isPrimary));
			}
			this.refreshRecords();
		}
	}
	
	public void initializeArr() {for(DNDClass cl: DNDClass.values())	clarr.add(cl);}
	
	public void setComponents() {
		Rectangle temp = this.panel.getBounds();
			int tempy = temp.height*1/20;
		for(int i=0; i<records.size(); i++) {
			tempy = temp.height*1/20 + ((i*3*temp.height)/16);
			records.get(i).setBounds(temp.width/10,tempy,temp.width*8/10,temp.height*9/64);
		}
		tempy = temp.height*1/20 + ((records.size()*3*temp.height)/16);
		if(records.size()>=4 || this.clevel<1) 
			addRecord.setBounds(temp.width/10,tempy,temp.width*8/10,0);
		else
			addRecord.setBounds(temp.width/10,tempy,temp.width*8/10,temp.height*9/64);
		
		save.setBounds(temp.width/10,temp.height/20 + (3*temp.height)/4,temp.width*8/10,temp.height/8);
	}
	
	public void setComponents(ClassRecord cr) {
		this.records.add(cr);
		this.panel.add(cr);
		cr.classBox	.addActionListener(this);
		cr.lvlBox	.addActionListener(this);
		cr.delete	.addActionListener(this);
		cr.primBox	.addActionListener(this);
		this.setComponents();
	}
	
	public DNDClass giveUnusedClass() {
		ArrayList<DNDClass> 	dclist = new ArrayList<>(this.clarr);
		
		for(ClassRecord cr: this.records) {
			dclist.remove((DNDClass)cr.classBox.getSelectedItem());
		}
		
		return dclist.get(0);
	}
	
	public void setPrimary(ClassRecord classRecord) {
		for(ClassRecord cr: this.records) {
			if(cr == classRecord) {
				cr.setPrimary(true);
			}
			else {
				cr.setPrimary(false);
			}
		}
	}
	
	public void refreshRecords() {
		this.clevel = this.maxclevel;
		ArrayList<ClassRecord> 	crlist = new ArrayList<>();
		ArrayList<DNDClass> 	dclist = new ArrayList<>(this.clarr);
		ArrayList<DNDClass> 	dctemp = new ArrayList<>();
		
		for(ClassRecord cr: this.records) {
			this.clevel -= (Integer)cr.lvlBox.getSelectedItem();
			dclist.remove((DNDClass)cr.classBox.getSelectedItem());
		}
		
		for(ClassRecord cr: this.records) {
			dctemp = new ArrayList<>(dclist);
			dctemp.add(0,(DNDClass)cr.classBox.getSelectedItem());
			crlist.add(
					new ClassRecord(
							this,
							(Integer)cr.lvlBox.getSelectedItem()+this.clevel,
							(DNDClass)cr.classBox.getSelectedItem(),
							(Integer)cr.lvlBox.getSelectedItem(),
							dctemp,
							cr.primary
					)
			);
			this.remove(cr);
		}
		this.records.clear();
		if(crlist.size()>0)
			for(ClassRecord cr: crlist)
				this.setComponents(cr);
		
		else this.setComponents();
		
		if(this.clevel==0) 	this.save.setEnabled(true);
		else				this.save.setEnabled(false);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source==addRecord) {
			this.setComponents(new ClassRecord(this));
			if(this.records.size()==1) this.setPrimary(this.records.get(0));
			refreshRecords();
		}
		else
		if(source==save) {
			EnumMap<DNDClass,DClass> tempClass = new EnumMap<DNDClass,DClass>(DNDClass.class);
			
			for(ClassRecord cr : this.records)
				tempClass.put((DNDClass)cr.classBox.getSelectedItem(), new DClass((Integer)cr.lvlBox.getSelectedItem(),cr.primary));
			
			if(master!=null) this.master.classMap = tempClass;
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
		else {
			for(ClassRecord cr: records) {
				if(cr.classBox	== source || cr.lvlBox	== source) {
					refreshRecords();
					break;
				}
				else
				if(cr.primBox == source){
					this.setPrimary(cr); cr.setPrimary(true);
				}
				else
				if(cr.delete	== source) {
					this.panel	.remove(cr);
					this.records.remove(cr);
					if(cr.primary) this.setPrimary(this.records.get(0));
					refreshRecords();
					break;
				}
			}
		}
	}
}

class ClassRecord extends JPanel{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private ClassWindow master;
	public	boolean		primary;
	
	public JComboBox<DNDClass> classBox;
	public JComboBox<Integer>  lvlBox  ;
	public JButton			   delete  = new JButton ("X");
	public JButton			   primBox = new JButton("Set primary");
	
	public ClassRecord(ClassWindow master) {
		this(master, 1, master.giveUnusedClass(), 1, master.clarr, false);
	}
	
	public ClassRecord(ClassWindow master, int remLvL, DNDClass dcl, Integer lvl, ArrayList<DNDClass> arr) {
		this(master, remLvL, dcl, lvl, arr, false);
	}
	
	public ClassRecord(ClassWindow master, int remLvL, DNDClass dcl, Integer lvl, ArrayList<DNDClass> arr, boolean primary) {
		setPrimary(primary);
		this.setLayout(null);
		this.classBox = new JComboBox<DNDClass>(ALtoArr(arr));
		this.setBoxSize(remLvL);
		this.add(classBox); this.classBox.setSelectedItem(dcl);
		this.add(lvlBox)  ;	this.lvlBox  .setSelectedItem(lvl);
		this.add(primBox) ;
		this.add(delete)  ;
		this.master = master;
	}
	
	public void setPrimary(boolean prim) {
		this.primary = prim;
		if(prim) {
			this.setBackground(new Color(240,240,0));
			this.primBox.setBackground(new Color(240,240,180));
		}
		else {
			this.setBackground(new Color(100,180,180));
			this.primBox.setBackground(new Color(180,180,180));
		}
	}
	
	public DNDClass[] ALtoArr(ArrayList<DNDClass> arr) {
		Object[] temp = arr.toArray();
		DNDClass[] output = new DNDClass[arr.size()];
		for(int i=0;i<arr.size();i++) {
			DNDClass dc = (DNDClass) temp[i];
			output[i]	= dc;
		}
		return output;
	}
	
	public void setBoxSize(int size) {
		Integer arr[] = new Integer[size];
		for(int i=1; i<=size;i++) arr[i-1] = i;
		this.lvlBox = new JComboBox<Integer>(arr);		
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.classBox	.setBounds(	 width/20,		height*3/10,(width*4)/10,	(height*6)/10);
		this.lvlBox		.setBounds(	(width*5)/10,	height/10,	(width*2)/10,	(height*8)/10);
		this.delete		.setBounds( (width*75)/100,	height/10,	(height*8)/10,	(height*8)/10);
		this.primBox	.setBounds(	 width/20,		height/20,	(width*4)/10,	height/5);
	}
}
