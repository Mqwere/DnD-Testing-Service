package Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import UI.DNDWindow;

public class FileControler extends DNDWindow{
	private static final long serialVersionUID = 1L;
	
	private File file;
	private JFileChooser 
			fileChooser = new JFileChooser(new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\"));
	public boolean isFine = false;
	
	public FileControler() {
		super(240, 0, false);
		this.setTitle("DND FILE CONTROLER");

		int choice = fileChooser.showOpenDialog(this);
		if(choice == JFileChooser.APPROVE_OPTION)
			this.file  = fileChooser.getSelectedFile();
		else
			this.file  = null;
	}
	
	public ArrayList<Byte> fileToByteArray() {
		if(this.file == null) {
			isFine = false;
			return null;
		}
		else {
			FileInputStream inStream;
			try {
				inStream = new FileInputStream(this.file);
				int content;
				ArrayList<Byte> temp = new  ArrayList<Byte>();
				while((content = inStream.read()) !=-1) {
					temp.add((byte)(content-44));
				}
				inStream.close();
				isFine = true;
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				isFine = false;
				return null;
			}
		}
	}
	
	public boolean saveToFile(byte[] input) {
		if(this.file == null)
			this.file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\DNDFile.bin");
			
		FileOutputStream inStream;
		try {
			inStream = new FileOutputStream(this.file);
			int content;
			for(int i=0; i<input.length;i++) {
				content = (int)input[i];
				inStream.write(content+44);
			}
			inStream.close();
			isFine = true;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			isFine = false;
			return false;
		}
	}
}
