package UI;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class DNDWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	protected JPanel panel = new JPanel();
	
	public DNDWindow() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 400);
		setResizable(false);
		setLocationRelativeTo(null);
		panel.setBackground(new Color(0, 43, 60));
		panel.setLayout(null);
		setContentPane(panel);
		setVisible(true);
	}
	
	public DNDWindow(int width, int height) {
		this();
		setSize(width, height);
	}
	
	public DNDWindow(int width, int height, boolean mainWindow) {
		this(width, height);
		if(mainWindow) setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
}