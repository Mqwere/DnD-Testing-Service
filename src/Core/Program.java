package Core;

import java.util.ArrayList;

import UI.CCWindow;
import UI.MainWindow;

public class Program {
	
	public static String save = new String();
	
	public static String getSaveFile() {
		return save;
	}
	
	public static void setCurrentStatus(ArrayList<Byte> array) {
		String boop = new String();
		for(Byte b: array) {
			boop += (char)Byte.toUnsignedInt(b);
		}
		System.out.print(boop);
	}

	public static void main(String[] args) {
		new MainWindow();
		//new CCWindow();
	}

}
