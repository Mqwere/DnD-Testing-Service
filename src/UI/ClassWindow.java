package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Enums.Core.DNDClass;

public class ClassWindow extends DNDWindow implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	public int 		clevel;
	public ArrayList<ClassRecord> 
					records   = new ArrayList<>();
	public ArrayList<DNDClass> 
					clarr 	  = new ArrayList<>();
	JScrollPane 	pane;
	JPanel			panel	  = new JPanel();
	JButton			addRecord = new JButton("+");
	JButton			save 	  = new JButton("SAVE");
	
	public ClassWindow(int clevel) {
		super(400, 540, true);
		this.panel.setBackground(new Color(0, 43, 60));
		this.clevel = clevel;
		
		panel.setLayout(null);
		this.panel.add(addRecord); 	addRecord.addActionListener(this);
		this.panel.add(save); 		save	 .addActionListener(this);
		initializeArr();
		pane = new JScrollPane(panel);
		this.setContentPane(pane);
		this.setVisible(true);
		this.setComponents();
	}
	
	public void initializeArr() {for(DNDClass cl: DNDClass.values())	clarr.add(cl);}
	
	public void setComponents() {
		Rectangle temp = this.getBounds();
			int tempy = temp.height*1/20;
		for(int i=0; i<records.size(); i++) {
			tempy = temp.height*1/20 + ((i*5*temp.height)/32);
			records.get(i).setBounds(temp.width/15,tempy,temp.width*8/10,temp.height/8);
		}
		tempy = temp.height*1/20 + ((records.size()*5*temp.height)/32);
		if(records.size()<4) 
			addRecord.setBounds(temp.width/15,tempy,temp.width*8/10,temp.height/8);
		else
			addRecord.setBounds(temp.width/15,tempy,temp.width*8/10,0);
		
		save.setBounds(temp.width/15,temp.height*1/20 + (20*temp.height)/32,temp.width*8/10,temp.height/8);
	}
	
	public void setComponents(ClassRecord cr) {
		this.records.add(cr);
		this.panel.add(cr);
		this.setComponents();
	}
	
	public void refreshClarr() {
		ArrayList<DNDClass> temp = new ArrayList<DNDClass>();
	}
	
	public void refreshClvl() {
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source==addRecord) {
			this.setComponents(new ClassRecord(this,clevel));
		}
		else {
			for(ClassRecord cr: records) {
				if(cr.classBox	== source) {
					
				}
				else
				if(cr.lvlBox	== source) {
					
				}
			}
		}
	}
}

class ClassRecord extends JPanel{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private ClassWindow master;
	
	public JComboBox<DNDClass> classBox;
	public JComboBox<Integer>  lvlBox  ;
	public JButton			   delete  = new JButton("X");
	
	public ClassRecord(ClassWindow master, int remLvL) {
		this.setBackground(new Color(100,180,180));
		this.setLayout(null);
		this.classBox = new JComboBox<DNDClass>(ALtoArr(master.clarr));
		this.setBoxSize(remLvL);
		this.add(classBox);
		this.add(lvlBox);
		this.add(delete);
		this.master = master;
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
		lvlBox = new JComboBox<Integer>(arr);		
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		this.classBox	.setBounds(	 width/20,		height/10,(width*4)/10,	(height*8)/10);
		this.lvlBox		.setBounds(	(width*5)/10,	height/10,(width*2)/10,	(height*8)/10);
		this.delete		.setBounds( (width*75)/100,	height/10,(height*8)/10,(height*8)/10);
	}
}
