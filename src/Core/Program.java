package Core;

import java.util.ArrayList;

import Enums.DamageType;
import Enums.Die;
import Enums.WeaponType;
import UI.CCWindow;

public class Program {
	
	public static String getSaveFile() {
		Weapon OPSword = new Weapon("Flaming Poisoning Raging Sword Of Doom", WeaponType.NORMAL);
		OPSword.dmType = DamageType.SLAS;
		OPSword.addDmg(2, Die.D6, DamageType.SLAS);
		OPSword.addDmg(1, Die.D8, DamageType.FIRE);
		OPSword.addDmg(1, Die.D4, DamageType.FIRE);
		OPSword.addDmg(2, Die.D6, DamageType.SLAS);
		OPSword.addDmg(1, Die.D8, DamageType.FIRE);
		OPSword.addDmg(1, Die.D4, DamageType.FIRE);
		OPSword.addDmg(1, Die.D6, DamageType.POIS);
		OPSword.addDmg(1, Die.D8, DamageType.FIRE);
		OPSword.addDmg(1, Die.D6, DamageType.POIS);
		OPSword.addDmg(1, Die.D8, DamageType.FIRE);
		OPSword.addDmg(1, Die.D8, DamageType.RADI);
		
		return OPSword.toString();
	}
	
	public static void setCurrentStatus(ArrayList<Byte> array) {
		String boop = new String();
		for(Byte b: array) {
			boop += (char)Byte.toUnsignedInt(b);
		}
		System.out.print(boop);
	}

	public static void main(String[] args) {
		//new MainWindow();
		new CCWindow();
	}

}
