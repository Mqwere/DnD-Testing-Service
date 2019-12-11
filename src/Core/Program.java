package Core;

import java.util.ArrayList;

import Enums.DamageType;
import Enums.Die;
import Enums.ImmunityType;
import Enums.Race;
import Enums.SaveState;
import Enums.WeaponType;
import UI.CCWindow;
import UI.MainWindow;

public class Program {
	
	public static ArrayList<Entity> entityList = new ArrayList<Entity>();
	
	public static void print(Object message) {
		if(message.getClass() == String.class) {
			String obj = (String)message;
			System.out.print(obj);
		}
		else
		if(message.getClass() == Boolean.class) {
			Boolean obj = (Boolean) message;
			System.out.print(Boolean.toString(obj));
		}
		else {
			System.out.print(message.toString());
		}
	}

	public static void log(Object message) {
		Program.print("\n "+message);
	}
	
	public static String save = new String();
	
	public static void saveToEntityList() {
		String[]   input = save.split("\n");
		SaveState  currSS = null;
		int		   incrementer = 0;
		Entity	   tempE = null;
		Weapon	   tempW = new Weapon();
		int		   tempI = 0;
		Die		   tempD = null;
		DamageType tempDT = null;
		
		for(String line: input) {
			if(line.length()>0) {
			if(line.charAt(0) == SaveState.CCOPEN.startSign) {
				if(tempE!=null) {
					tempE.setWeapon(tempW);
					Program.entityList.add(tempE);
				}
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
			else {
				ImmunityType it;
				switch(currSS) {
				case CCOPEN:
					switch(incrementer++) {
					case  0:	tempE.name 		= line;		break;
					case  1:
						try {
						for (Race r: Race.values()) {
							//Program.log("Line:'"+line+"' Race:'"+r.toString()+"'");
							if(r.toString().equals(line)) {
								tempE.race = r;
							}
						}	
						if(tempE.race == null) {
							Program.log("STELL ERROR: Race null, line: "+line+"\n");
							tempE.race = Race.CUSTOM;
						}
						} catch(Exception e) {
							Program.log("Program.saveToEntityList.CCOPEN: "+e.getMessage());
						}break;
					case  2:	tempE.level 	 = Integer.parseInt(line);	break;
					case  3:	tempE.maxHP 	 = Integer.parseInt(line);  break;
					case  4:	tempE.armorClass = Integer.parseInt(line);  break;
					case  5:	tempE.STR.setValue(Integer.parseInt(line)); if(tempE.STR.value == 0) 		Program.log("STEL ERROR: STR NULL"); break;
					case  6:	tempE.DEX.setValue(Integer.parseInt(line)); if(tempE.DEX.value == 0) 		Program.log("STEL ERROR: DEX NULL"); break;
					case  7:	tempE.CON.setValue(Integer.parseInt(line)); if(tempE.CON.value == 0) 		Program.log("STEL ERROR: CON NULL"); break;
					case  8:	tempE.INT.setValue(Integer.parseInt(line)); if(tempE.INT.value == 0) 		Program.log("STEL ERROR: INT NULL"); break;
					case  9:	tempE.WIS.setValue(Integer.parseInt(line)); if(tempE.WIS.value == 0) 		Program.log("STEL ERROR: WIS NULL"); break;
					case 10:	tempE.CHR.setValue(Integer.parseInt(line)); if(tempE.CHR.value == 0) 		Program.log("STEL ERROR: CHR NULL"); break;
					case 11: 
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;
						tempE.setResistance(DamageType.PIER,it);	break;
					case 12:
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;	
						tempE.setResistance(DamageType.SLAS,it);	break;
					case 13:
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;	
						tempE.setResistance(DamageType.BLUD,it);	break;
					case 14:
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;	
						tempE.setResistance(DamageType.ACID,it);	break;
					case 15:
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;	
						tempE.setResistance(DamageType.COLD,it);	break;
					case 16:
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;	
						tempE.setResistance(DamageType.FIRE,it);	break;
					case 17:
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;	
						tempE.setResistance(DamageType.FORC,it);	break;
					case 18:	
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;
						tempE.setResistance(DamageType.LIGH,it);	break;
					case 19:	
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;
						tempE.setResistance(DamageType.NECR,it);	break;
					case 20:	
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;
						tempE.setResistance(DamageType.POIS,it);	break;
					case 21:	
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;
						tempE.setResistance(DamageType.PSYC,it);	break;
					case 22:	
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;
						tempE.setResistance(DamageType.RADI,it);	break;
					case 23:	
						if(line=="VULNERABLE") it = ImmunityType.VULNERABLE;
						else if(line=="NONE")  it = ImmunityType.NONE;
						else if(line=="RESISTANT") it = ImmunityType.RESISTANT;
						else if(line=="IMMUNE") it = ImmunityType.IMMUNE;
						else  it = ImmunityType.NONE;
						tempE.setResistance(DamageType.THUN,it);	break;
					}
					break;
				case DMGOPEN:
					switch(incrementer++) {
					case 0: tempI = Integer.parseInt(line);	 break;
					case 1:	
						for(Die d: Die.values()) {
							if(d.value == Integer.parseInt(line)) {
								tempD = d;
							}
						}
					break;
					case 2:	 
						for(DamageType dt: DamageType.values()) {
							if(dt.arrValue == Integer.parseInt(line)) {
								tempDT = dt;
								tempW.addDmg(tempI, tempD, tempDT);
							}
						}
					break;
					}
					break;
				case WEPOPEN:
					switch(incrementer++) {
					case 0: 
						tempW = new Weapon(line);
					break;
					case 1:	
						for(DamageType dt: DamageType.values()) {
							if(dt.arrValue == Integer.parseInt(line)) {
								tempW.setDmType(dt);
							}
						}
					break;
					case 2:	 
						tempW.setProfficient(Boolean.parseBoolean(line));
					break;
					case 3:	 
						if(line=="finesse") tempW.setWpType(WeaponType.FINESSE);
						else 				tempW.setWpType(WeaponType.NORMAL);
					break;
					}
					break;
				default:
					break;
				}
			}
		}
		}
	}
	
	public static String getSaveFile() {
		save +="\n=";
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
		Program.save = boop;
	}

	public static void main(String[] args) {
		new MainWindow();
		//new CCWindow();
	}

}
