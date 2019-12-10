package Core;

import java.util.ArrayList;

import Enums.DamageType;
import Enums.Die;
import Enums.SaveState;
import UI.CCWindow;
import UI.MainWindow;

public class Program {
	
	public static ArrayList<Entity> entityList = new ArrayList<Entity>();
	
	public static String save = new String();
	
	public static void saveToEntityList() {
		String[]   input = save.split("\n");
		SaveState  currSS = null;
		int		   incrementer = 0;
		Entity	   tempE = new Entity();
		Weapon	   tempW = new Weapon();
		int		   tempI = 0;
		Die		   tempD = null;
		DamageType tempDT = null;
		
		for(String line: input) {
			if(line.charAt(0) == SaveState.CCOPEN.startSign) {
				currSS = SaveState.CCOPEN;
				tempE = new Entity();
				incrementer = 0;
			}
			else
			if(line.charAt(0) == SaveState.WEPOPEN.startSign) {
				currSS = SaveState.WEPOPEN;
				tempW = new Weapon();
				incrementer = 0;
			}
			else
			if(line.charAt(0) == SaveState.DMGOPEN.startSign) {
				currSS = SaveState.DMGOPEN;
				incrementer = 0;
			}
			//output += this.name+"\n";
			//output += this.race+"\n";
			//output += this.level+"\n";
			//output += this.maxHP+"\n";
			//output += this.armorClass+"\n";
			//output += this.STR.value+"\n";
			//output += this.STR.value+"\n";
			//output += this.DEX.value+"\n";
			//output += this.CON.value+"\n";
			//output += this.INT.value+"\n";
			//output += this.WIS.value+"\n";
			//output += this.CHR.value+"\n";
			//PIER( 0),
			//SLAS( 1),
			//BLUD( 2),
			//ACID( 3),
			//COLD( 4),
			//FIRE( 5),
			//FORC( 6),
			//LIGH( 7),
			//NECR( 8),
			//POIS( 9),
			//PSYC(10),
			//RADI(11),
			//THUN(12);
			else {
				switch(currSS) {
				case CCOPEN:
					switch(incrementer++) {
					case  0:	break;
					case  1:	break;
					case  2:	break;
					case  3:	break;
					case  4:	break;
					case  5:	break;
					case  6:	break;
					case  7:	break;
					case  8:	break;
					case  9:	break;
					case 10:	break;
					case 11:	break;
					case 12:	break;
					case 13:	break;
					case 14:	break;
					case 15:	break;
					case 16:	break;
					case 17:	break;
					case 18:	break;
					case 19:	break;
					case 20:	break;
					case 21:	break;
					case 22:	break;
					case 23:	break;
					case 24:	break;
					}
					break;
				case DMGOPEN:
					break;
				case WEPOPEN:
					break;
				default:
					break;
				}
			}
		}
	}
	
	public static String getSaveFile() {
		System.out.print(save);
		return save;
	}
	
	public static int addEntity(Entity ent) {
		Program.entityList.add(ent);
		return Program.entityList.size()-1;
	}
	
	public static Entity getEntity(int index) {
		return Program.entityList.get(index);
	}
	
	public static void setCurrentStatus(ArrayList<Byte> array) {
		String boop = new String();
		for(Byte b: array) {
			boop += (char)Byte.toUnsignedInt(b);
		}
		System.out.print("K W I\n\n"+boop);
		Program.save = boop;
	}

	public static void main(String[] args) {
		new MainWindow();
		//new CCWindow();
	}

}
