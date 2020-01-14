package Core;

import java.util.HashMap;

import javax.swing.JOptionPane;

import Enums.Core.DNDClass;
import Enums.Core.Enhancement;
import Enums.Core.ImmunityType;
import Enums.Core.Race;
import Enums.Core.SaveState;
import Enums.Core.TeamColor;
import Enums.Core.WeaponType;
import Enums.Support.DamageType;
import Enums.Support.Die;
import Enums.Support.PropertyName;
import Support.EntityRegister;
import UI.MainWindow;
import UI.SkillsWindow;

public class Program {
	
	public static MainWindow mainWindow;

	public static String save = new String();

	public static void main(String[] args) {
		//Program.mainWindow = new MainWindow();
		
		//Entity ent = Entity.customChara(); FileControler.persistentSave((ent.toString()+"\n=").getBytes());

		new SkillsWindow(null);
	}
	
	public static void sleep(long milis) {
		try {
			Thread.sleep(milis);
		} catch (InterruptedException e) {
			Program.log(e);
		}
	}
	
	public static void error(Object message) {
		String output = new String();
		if(message.getClass() == String.class) {
			String obj = (String)message;
			output = obj;
		}
		else
		if(message.getClass() == Boolean.class) {
			Boolean obj = (Boolean) message;
			output = Boolean.toString(obj);
		}
		else {
			output = message.toString();
		}
		
		JOptionPane.showMessageDialog(Program.mainWindow, output);
	}
	
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
	public static void saveToEntityList(String save) {
		Program.save = save;
		Program.saveToEntityList();
	}
	
	public static void saveToEntityList() {
		String[]   input = save.split("\n");
		SaveState  currSS = null;
		int		   incrementer = 0, lineNo = 0;
		Entity	   tempE = null;
		Weapon	   tempW = new Weapon();
		int		   tempI = 0;
		Die		   tempD = null;
		
		for(String line: input) {
			lineNo++;
			if(line.length()>0) {
				Program.log(line);
				if(line.length()==1 && line.charAt(0) == SaveState.CCOPEN.startSign) {
					if(tempE!=null) {
						tempE.setWeapon(tempW);
						EntityRegister.put(tempE.color,tempE);
					}
					currSS = SaveState.CCOPEN;
					tempE = new Entity();
					incrementer = 0;
				}
				else
				if(line.length()==1 && line.charAt(0) == SaveState.WEPOPEN.startSign) {
					currSS = SaveState.WEPOPEN;
					tempW = new Weapon();
					incrementer = 0;
				}
				else
				if(line.length()==1 && line.charAt(0) == SaveState.DMGOPEN.startSign) {
					currSS = SaveState.DMGOPEN;
					incrementer = 0;
				}
				else
				if(line.length()==1 && line.charAt(0) == SaveState.CLOPEN.startSign) {
					currSS = SaveState.CLOPEN;
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
								Program.error("Program.saveToEntityList.CCOPEN: Race null, line: "+line+"\n");
								tempE.race = Race.CUSTOM;
							}
							} catch(Exception e) {
								Program.error("Program.saveToEntityList.CCOPEN: "+e.getMessage());
							}break;
						case  2:	tempE.level 	 = Integer.parseInt(line);	break;
						case  3:	tempE.maxHP 	 = Integer.parseInt(line);  break;
						case  4:	tempE.props.get(PropertyName.AC ).setValue(Integer.parseInt(line)); if(tempE.props.get(PropertyName.AC ).value == 0) 		Program.log("STEL ERROR: AC  NULL"); break;
					    case  5:	tempE.props.get(PropertyName.STR).setValue(Integer.parseInt(line)); if(tempE.props.get(PropertyName.STR).value == 0) 		Program.log("STEL ERROR: STR NULL"); break;
						case  6:	tempE.props.get(PropertyName.DEX).setValue(Integer.parseInt(line)); if(tempE.props.get(PropertyName.DEX).value == 0) 		Program.log("STEL ERROR: DEX NULL"); break;
						case  7:	tempE.props.get(PropertyName.CON).setValue(Integer.parseInt(line)); if(tempE.props.get(PropertyName.CON).value == 0) 		Program.log("STEL ERROR: CON NULL"); break;
						case  8:	tempE.props.get(PropertyName.INT).setValue(Integer.parseInt(line)); if(tempE.props.get(PropertyName.INT).value == 0) 		Program.log("STEL ERROR: INT NULL"); break;
						case  9:	tempE.props.get(PropertyName.WIS).setValue(Integer.parseInt(line)); if(tempE.props.get(PropertyName.WIS).value == 0) 		Program.log("STEL ERROR: WIS NULL"); break;
						case 10:	tempE.props.get(PropertyName.CHR).setValue(Integer.parseInt(line)); if(tempE.props.get(PropertyName.CHR).value == 0) 		Program.log("STEL ERROR: CHR NULL"); break;
						case 11: 
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;
							tempE.setResistance(DamageType.PIER,it);	break;
						case 12:
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;	
							tempE.setResistance(DamageType.SLAS,it);	break;
						case 13:
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;	
							tempE.setResistance(DamageType.BLUD,it);	break;
						case 14:
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;	
							tempE.setResistance(DamageType.ACID,it);	break;
						case 15:
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;	
							tempE.setResistance(DamageType.COLD,it);	break;
						case 16:
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;	
							tempE.setResistance(DamageType.FIRE,it);	break;
						case 17:
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;	
							tempE.setResistance(DamageType.FORC,it);	break;
						case 18:	
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;
							tempE.setResistance(DamageType.LIGH,it);	break;
						case 19:	
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;
							tempE.setResistance(DamageType.NECR,it);	break;
						case 20:	
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;
							tempE.setResistance(DamageType.POIS,it);	break;
						case 21:	
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;
							tempE.setResistance(DamageType.PSYC,it);	break;
						case 22:	
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;
							tempE.setResistance(DamageType.RADI,it);	break;
						case 23:	
							if(line.equals("VULNERABLE")) it = ImmunityType.VULNERABLE;
							else if(line.equals("NONE"))  it = ImmunityType.NONE;
							else if(line.equals("RESISTANT")) it = ImmunityType.RESISTANT;
							else if(line.equals("IMMUNE")) it = ImmunityType.IMMUNE;
							else  it = ImmunityType.NONE;
							tempE.setResistance(DamageType.THUN,it);	break;
						case 24:
							if(line.equals("RED")) tempE.color = TeamColor.RED;
							else if (line.equals("BLUE")) tempE.color = TeamColor.BLUE;
							else {
								Program.error("Team is NULL for some reason: '"+line+"'");
								tempE = null;
								return;
							}
							break;							
						default:
							break;
						}
						break;
					case DMGOPEN:
						switch(incrementer++) {
						case 0: tempI = Integer.parseInt(line);	 break;
						case 1:	
							for(Die d: Die.values()) {
								if(d.value == Integer.parseInt(line)) tempD = d;
							}
						break;
						case 2:	 
							for(DamageType dt: DamageType.values()) {
								if(dt.arrValue == Integer.parseInt(line)) tempW.addDmg(tempI, tempD, dt);
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
								if(dt.arrValue == Integer.parseInt(line)) tempW.setDmType(dt);
							}
						break;
						case 2:	 
							tempW.setProfficient(Boolean.parseBoolean(line));
						break;
						case 3:	 
							if(line.equals("finesse")) tempW.setWpType(WeaponType.FINESSE);
							else 				tempW.setWpType(WeaponType.NORMAL);
						break;
						case 4:
								 if(line.charAt(4)=='0') tempW.setEnhancement(Enhancement.plus0);
							else if(line.charAt(4)=='1') tempW.setEnhancement(Enhancement.plus1);
							else if(line.charAt(4)=='2') tempW.setEnhancement(Enhancement.plus2);
							else if(line.charAt(4)=='3') tempW.setEnhancement(Enhancement.plus3);
							else Program.error("Core.Program.saveToEntityList.WEPOPEN.(case 4): Enhancement failed to parse.");
						}
						break;
					case CLOPEN:
						String[] subline = line.split(" ");
						for(DNDClass dc: DNDClass.values()) {
							if (subline[0].equals(dc.toString())) {
								tempE.setClass(dc, Integer.parseInt(subline[1]));
								if(subline.length>2) {
									tempE.classMap.get(dc).isPrimary = true;
								}
							}
						}
						break;
					default:
						Program.error("???");
						break;
					}
				}
			}
			else {
				if(lineNo>20) {
					Program.log("> EMPTY STRING");
					return;
				}
			}
		}
	}
	

	public static String getSaveFile() {
		save ="";
		for(TeamColor tm: TeamColor.values()) {
			HashMap<Integer, Entity> temp = EntityRegister.getMap(tm);
			if(temp!=null)
			for(Integer i: temp.keySet()) {
				save += "\n"+EntityRegister.get(tm, i).toString();
			}
		}
		save +="\n=";
		return save;
	}

}
