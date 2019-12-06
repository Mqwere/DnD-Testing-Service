package Core;

import java.util.EnumMap;

import Enums.DamageType;
import Enums.Die;
import Enums.ImmunityType;
import Enums.Race;
import Enums.SkillName;
import Support.Roller;

public class Entity{
	public String name;
	
	Race race;

	int level;
	int currHP;
	int maxHP;
	int armorClass;
	
	Skill STR = new Skill(SkillName.STR);
	Skill DEX = new Skill(SkillName.DEX);
	Skill CON = new Skill(SkillName.CON);
	Skill INT = new Skill(SkillName.INT);
	Skill WIS = new Skill(SkillName.WIS);
	Skill CHR = new Skill(SkillName.CHR);
	
	EnumMap<DamageType,ImmunityType> resistanceMap = new 
			EnumMap<DamageType,ImmunityType>(DamageType.class);
	
	public Entity(Race race) {
		this.race = race;
		defaultizeResistances();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSkills(int STR, int DEX, int CON, int INT, int WIS, int CHR) {
		this.STR.setValue(STR);
		this.DEX.setValue(DEX);
		this.CON.setValue(CON);
		this.INT.setValue(INT);
		this.WIS.setValue(WIS);
		this.CHR.setValue(CHR);		
	}
	
	private void defaultizeResistances() {
		for(DamageType dmg: DamageType.values()) {
			resistanceMap.put(dmg, ImmunityType.NONE);
		}
	}
	
	public void applyRacialStats() {
		switch(this.race) {
		case CUSTOM:
			break;
		case DWARF:
			resistanceMap.put(DamageType.POIS, ImmunityType.RESISTANT);
			break;
		case ELF:
			break;
		case GNOME:
			break;
		case HALFLING:
			break;
		case HALF_ELF:
			break;
		case HALF_ORC:
			break;
		case HUMAN:
			break;
		case KOBOLD:
			break;
		case ORC:
			break;
		case TIEFLING:
			resistanceMap.put(DamageType.FIRE, ImmunityType.RESISTANT);
			break;
		case BUGBEAR:
			break;
		case DRAGONBORN:
			break;
		case GOBLIN:
			break;
		case WARFORGED:
			resistanceMap.put(DamageType.POIS, ImmunityType.RESISTANT);
			break;
		default:
			break;
		}
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
}

class Skill{
	public int value;
	public int modifier;
	SkillName  name;
	
	public Skill(SkillName name){
		this.name = name;
	}
	
	public Skill(SkillName name, int value){
		this(name);
		this.value = value;
		this.modifier = value/2 - 5;
	}
		
	public void setValue(int input) {
		this.value = input;
		this.modifier = input/2 - 5;
	}
	
	public boolean check(int DC) {
		int roll = Roller.roll(Die.D20);
		if(roll == 1) 
			return false;
		else 
		if(roll == 20) 
			return true;
		else {
			int result =  roll	+ this.modifier;
			return !(result<DC);
		}
	}
	
	public boolean checkAdv(int DC) {
		int roll = Roller.rollAdv(Die.D20);
		if(roll == 1) 
			return false;
		else 
		if(roll == 20) 
			return true;
		else {
			int result = roll + this.modifier;
			return !(result<DC);
		}
	}
	
	public boolean checkDis(int DC) {
		int roll = Roller.rollDis(Die.D20);
		if(roll == 1) 
			return false;
		else 
		if(roll == 20) 
			return true;
		else {
			int result = roll + this.modifier;
			return !(result<DC);
		}
	}
}
