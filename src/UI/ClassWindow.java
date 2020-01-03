package UI;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ClassWindow extends DNDWindow {
	private static final long serialVersionUID = 1L;
	
	public int 		clevel;
	public ArrayList<ClassRecord> 
					records = new ArrayList<>();
	
	JScrollPane 	panel 	= new JScrollPane();
	
	public ClassWindow(int clevel) {
		super(400, 640, true);
		this.clevel = clevel;
		
		this.setContentPane(panel);
	}
}

class ClassRecord extends JPanel{
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private ClassWindow master;
	
	public ClassRecord(ClassWindow master) {
		this.setBackground(new Color(180,180,180));
		this.setLayout(null);
		this.master = master;
	}
}
