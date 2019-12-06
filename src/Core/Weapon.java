package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Enums.DamageType;
import Enums.Die;
import Enums.WeaponType;
import Support.DamagePackage;

public class Weapon {
	String name = new String();
	ArrayList<DamagePackage> damage = new ArrayList<DamagePackage>();
	DamageType dmType;
	WeaponType wpType;
	
	public Weapon(String name) {
		this.name = name;
	}
	
	public Weapon(String name, WeaponType type) {
		this(name);
		this.wpType = type;
	}
	
	public Weapon(String name, WeaponType type, ArrayList<DamagePackage> dmgPck) {
		this(name,type);
		this.damage = dmgPck;
	}

	public void addDmg(int dieNo, Die dieType, DamageType type) {
		this.damage.add(new DamagePackage(dieNo, dieType, type));
	}
	
	public void clearDmg() {
		this.damage.clear();
	}
	
	public void dealDmg(Entity user, Entity target) {
		for(DamagePackage dmpc: damage) {
			target.takeDamage(dmpc.resolve(), dmpc.dmgType);
		}
		switch(wpType) {
			case NORMAL:
				target.takeDamage(user.STR.modifier, dmType);
			break;
			
			case FINESSE:
				target.takeDamage(user.DEX.modifier, dmType);
			break;
		}
	}

	public String toString() {
		HashMap<Integer,Integer> values = new HashMap<Integer,Integer>();
		int key, value;
		for(DamagePackage dmpk: this.damage) {
			key = 0;
			key += dmpk.dieSize.value;
			key += dmpk.dmgType.arrValue*100;
			if(values.get(key)==null) {
				values.put(key, dmpk.dieNo);
			}
			else {
				value = values.get(key);
				values.put(key, value+dmpk.dieNo);
			}
		}
		String output = new String(this.name);
		switch(this.dmType) {
			case PIER:  output += " (piercing)"; 	break;
			case SLAS:  output += " (slashing)"; 	break;
			case BLUD:  output += " (bludgeoning)"; break;
			case ACID:  output += " (acid)"; 		break;
			case COLD:  output += " (cold)"; 		break;
			case FIRE:  output += " (fire)"; 		break;
			case FORC:  output += " (force)"; 		break;
			case LIGH:  output += " (lightning)"; 	break;
			case NECR:  output += " (necrotic)"; 	break;
			case POIS:  output += " (poison)"; 		break;
			case PSYC:  output += " (psychic)"; 	break;
			case RADI:  output += " (radiant)"; 	break;
			case THUN:  output += " (thunder)"; 	break;
		}
		
		switch(wpType) {
		case FINESSE:
			output += " finesse";
			break;
		default:
			break;			
		}
		output += " weapon.";
		
		for(Map.Entry<Integer, Integer> entry: values.entrySet()) {
			output += "\n "+entry.getValue()+"d"+(entry.getKey()%100)+" ";
			switch(entry.getKey()/100) {
				case  0: output+="piercing"; 	break;
				case  1: output+="slashing"; 	break;
            	case  2: output+="bludgeoning"; break;
            	case  3: output+="acid"; 		break;
            	case  4: output+="cold"; 		break;
            	case  5: output+="fire"; 		break;
            	case  6: output+="force"; 		break;
            	case  7: output+="lightning"; 	break;
            	case  8: output+="necrotic"; 	break;
            	case  9: output+="poison"; 		break;
            	case 10: output+="psychic"; 	break;
            	case 11: output+="radiant"; 	break;
            	case 12: output+="thunder"; 	break;
			}
			output+=" dmg.";
		}
		
		return output;
	}

}
