package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Enums.DamageType;
import Enums.Die;
import Enums.Enhancement;
import Enums.WeaponType;
import Support.DamagePackage;
import Support.Roller;

public class Weapon {
	public ArrayList<DamagePackage> 
				damage = new ArrayList<DamagePackage>(); //done
	private String 		name = new String(); //done
	private Boolean 	profficient;//done
	private Enhancement enhancement;//done
	private DamageType 	dmType;		//de-facto to ma zadawać dmg z STR lub DEX //done
	private WeaponType 	wpType; 	//done
	
	public Weapon() {
		
	}
	
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
		int theRoll = Roller.roll(Die.D20) + (this.profficient ? user.getProficiency():0) + 
		(this.wpType==WeaponType.NORMAL?user.STR.modifier:user.DEX.modifier) + this.enhancement.value;
		if(theRoll>=target.armorClass) {
			for(DamagePackage dmpc: damage) {target.takeDamage(dmpc.resolve(), dmpc.dmgType);}
			switch(wpType) {
				case NORMAL:
					target.takeDamage(user.STR.modifier, dmType);
					break;
			
				case FINESSE:
					target.takeDamage(user.DEX.modifier, dmType);
					break;
			}
		}
	}
	

	public Boolean getProfficient() {
		return profficient;
	}

	public Enhancement getEnhancement() {
		return enhancement;
	}

	public DamageType getDmType() {
		return dmType;
	}

	public WeaponType getWpType() {
		return wpType;
	}

	public void setProfficient(Boolean profficient) {
		this.profficient = profficient;
	}

	public void setEnhancement(Enhancement enhancement) {
		this.enhancement = enhancement;
	}

	public void setDmType(DamageType dmType) {
		this.dmType = dmType;
	}

	public void setWpType(WeaponType wpType) {
		this.wpType = wpType;
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
		String output = new String("<\n");

		output += this.name;
		output +="\n"+this.dmType.arrValue;
		output +="\n"+this.profficient;
		
		switch(wpType) {
			case FINESSE: output += "\nfinesse"; break;
			default: 	  output += "\nnormal";  break;			
		}
		
		for(Map.Entry<Integer, Integer> entry: values.entrySet()) {
			output +="\n>";
			output +="\n"+entry.getValue()  /// ile kości
			+"\n"+(entry.getKey()%100)		/// jakich kości
			+"\n"+(entry.getKey()/100);		/// i jaki dmg reprezentuja
		}
		
		return output;
	}

}
