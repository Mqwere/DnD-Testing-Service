package Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class FileControler{
	
	private static JFileChooser 
			fileChooser = new JFileChooser(".");
	
	public static ArrayList<Byte> fileToByteArray(JFrame parent) {
		int choice = fileChooser.showOpenDialog(parent);
		File file;
		if(choice == JFileChooser.APPROVE_OPTION) {
			file  = fileChooser.getSelectedFile();
			FileInputStream inStream;
			try {
				inStream = new FileInputStream(file);
				int content;
				ArrayList<Byte> temp = new  ArrayList<Byte>();
				while((content = inStream.read()) !=-1) {
					temp.add((byte)(content-44));
				}
				inStream.close();
				return temp;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.print("ERROR: "+e.getMessage());
				return null;
			}
		}
		else {
			System.out.print("\nOI, YE FECKIN NULL CUNT");
			return null;
		}
	}
	
	public static boolean saveToFile(JFrame parent,byte[] input) {
		int choice = fileChooser.showSaveDialog(parent);
		File file;
		if(choice == JFileChooser.APPROVE_OPTION) {
			file  = fileChooser.getSelectedFile();
		}
		else {
			file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\DNDFile.bin");
		}
		FileOutputStream inStream;
		try {
			inStream = new FileOutputStream(file);
			int content;
			for(int i=0; i<input.length;i++) {
				content = (int)input[i];
				inStream.write(content+44);
			}
			inStream.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.print("ERROR: "+e.getMessage());
			return false;
		}
	}
}
