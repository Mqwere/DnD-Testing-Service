package Core;

import java.util.EnumMap;

import Enums.DamageType;
import Enums.ImmunityType;
import Enums.Race;
import Enums.SkillName;
import Enums.TeamColor;
import Support.Skill;

public class Entity{
	String name;
	
	Race race;
	
	public TeamColor color;
	
	Weapon weapon;

	int level;
	int currHP;
	int maxHP;
	int armorClass;
	
	public Skill STR = new Skill(SkillName.STR);
	public Skill DEX = new Skill(SkillName.DEX);
	public Skill CON = new Skill(SkillName.CON);
	public Skill INT = new Skill(SkillName.INT);
	public Skill WIS = new Skill(SkillName.WIS);
	public Skill CHR = new Skill(SkillName.CHR);
	
	EnumMap<DamageType,ImmunityType> 
	resistanceMap = new EnumMap<>(DamageType.class);
	
	public Entity() {
		
	}
	
	public Entity(Race race) {
		this.race = race;
		defaultizeResistances();
	}
	
	public Entity(Race race, String name, Integer level, Integer maxHP, Integer armorClass,
			Integer STR, Integer DEX, Integer CON, Integer INT, Integer WIS, Integer CHR) {
		this.race = race;
		this.name = name;
		this.level = level;
		this.maxHP = maxHP; this.currHP = maxHP;
		this.armorClass = armorClass;
		this.setSkills(STR, DEX, CON, INT, WIS, CHR);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Race getRace() {
		return this.race;
	}
	
	public Integer getLvL() {
		return this.level;
	}
	
	public Integer getHP() {
		return this.maxHP;
	}
	
	public void setSkills(int STR, int DEX, int CON, int INT, int WIS, int CHR) {
		this.STR.setValue(STR);
		this.DEX.setValue(DEX);
		this.CON.setValue(CON);
		this.INT.setValue(INT);
		this.WIS.setValue(WIS);
		this.CHR.setValue(CHR);		
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public Weapon getWeapon() {
		return this.weapon;
	}
	
	private void defaultizeResistances() {
		for(DamageType dmg: DamageType.values()) {
			resistanceMap.put(dmg, ImmunityType.NONE);
		}
	}
	
	public void setResistance(DamageType dt, ImmunityType it) {
		if(dt == null) Program.log("SET RESISTANCE ERROR: DT NULL");
		if(it == null) Program.log("SET RESISTANCE ERROR: IT NULL");
		
		this.resistanceMap.put(dt, it);
	}
	
	public int getProficiency() {
		return 2 + ((this.level-1)/4);
	}
	
	public ImmunityType getResistance(DamageType dt) {
		return this.resistanceMap.get(dt);
	}

	
	public void takeDamage(int value, DamageType type) {
		switch(resistanceMap.get(type)) {
			case VULNERABLE:
				this.currHP -= value*2;
				break;
		
			case NONE:
				this.currHP -= value;
				break;
				
			case RESISTANT:
				this.currHP -= (value/2);
				break;
				
			case IMMUNE:
				break;
		}
	}
	

	public String toString() {
		String output = new String();
		output += "=\n";
		output += this.name+"\n";
		output += this.race.toString()+"\n";
		output += this.level+"\n";
		output += this.maxHP+"\n";
		output += this.armorClass+"\n";
		output += this.STR.value+"\n";
		output += this.DEX.value+"\n";
		output += this.CON.value+"\n";
		output += this.INT.value+"\n";
		output += this.WIS.value+"\n";
		output += this.CHR.value;
		for(DamageType dmg: DamageType.values()) {
			output +="\n"+resistanceMap.get(dmg);
		}
		output +="\n" + this.color;
		output +="\n" + this.weapon.toString();
		return output;
	}
}
