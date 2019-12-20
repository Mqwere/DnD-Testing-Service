package Core;

import java.util.EnumMap;

import Enums.Core.DNDClass;
import Enums.Core.ImmunityType;
import Enums.Core.Race;
import Enums.Core.TeamColor;
import Enums.Support.DamageType;
import Enums.Support.SkillName;
import Support.Skill;

public class Entity{
	/////// Fields that will only be used in a running simulation
	Integer	  attackNo;
	
	///////
	
	/////// Fields that the program needs to save / load
	String	  name;
	
	Race 	  race;
	
	public 
	TeamColor color;
	
	Weapon 	  weapon;

	int 	  level,
			  currHP,
			  maxHP,
			  armorClass;
	
	public EnumMap<SkillName,Skill> 
	skills = new EnumMap<>(SkillName.class);
	/*
		
			  STR = new Skill(SkillName.STR),
			  DEX = new Skill(SkillName.DEX),
			  CON = new Skill(SkillName.CON),
			  INT = new Skill(SkillName.INT),
			  WIS = new Skill(SkillName.WIS),
			  CHR = new Skill(SkillName.CHR);
	/**/
	
	EnumMap<DamageType,ImmunityType>
			  resistanceMap = new EnumMap<>(DamageType.class);
	
	EnumMap<DNDClass,Integer>
			  classMap 	  = new EnumMap<>(DNDClass.class);
	//////
	
	public Entity() {
		skills.put(SkillName.STR,new Skill(SkillName.STR));
		skills.put(SkillName.DEX,new Skill(SkillName.DEX));
		skills.put(SkillName.CON,new Skill(SkillName.CON));
		skills.put(SkillName.INT,new Skill(SkillName.INT));
		skills.put(SkillName.WIS,new Skill(SkillName.WIS));
		skills.put(SkillName.CHR,new Skill(SkillName.CHR));
	}
	
	public Entity(Race race) {
		this();
		this.race = race;
		defaultizeResistances();
	}
	
	public Entity(Race race, String name, Integer level, Integer maxHP, Integer armorClass,
			Integer STR, Integer DEX, Integer CON, Integer INT, Integer WIS, Integer CHR) {
		this(race);
		this.name = name;
		this.level = level;
		this.maxHP = maxHP; this.currHP = maxHP;
		this.armorClass = armorClass;
		this.setSkills(STR, DEX, CON, INT, WIS, CHR);
	}
	
	public void setClass(DNDClass cl, Integer in) {
		if(in==0) 	this.classMap.remove(cl );
		else 		this.classMap.put(cl, in);
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
	
	public void setHP(int hp) {
		this.maxHP = hp;
	}
	
	public Integer getAC() {
		return this.armorClass;
	}
	
	public void setAC(Integer AC) {
		this.armorClass = AC;
	}
	
	public void setSkills(int STR, int DEX, int CON, int INT, int WIS, int CHR) {
		this.skills.get(SkillName.STR).setValue(STR);
		this.skills.get(SkillName.DEX).setValue(DEX);
		this.skills.get(SkillName.CON).setValue(CON);
		this.skills.get(SkillName.INT).setValue(INT);
		this.skills.get(SkillName.WIS).setValue(WIS);
		this.skills.get(SkillName.CHR).setValue(CHR);	
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
	
	public void setSkillV(SkillName name, Integer value) {this.skills.get(name).setValue(value);}
	
	public Integer getSkillV(SkillName name) {return this.skills.get(name).value;}
	
	public Integer getSkillM(SkillName name) {return this.skills.get(name).modifier;}
	
	public void offsetResistance(int offset, DamageType dt) {
		switch(this.resistanceMap.get(dt)){
	    case VULNERABLE:
            if(offset>2)        this.resistanceMap.put(dt, ImmunityType.IMMUNE);
            else if(offset==2)  this.resistanceMap.put(dt, ImmunityType.RESISTANT);
            else if(offset==1)  this.resistanceMap.put(dt, ImmunityType.NONE);  
	    	break; 
	    case NONE:
            if(offset>1)        this.resistanceMap.put(dt, ImmunityType.IMMUNE);
            else if(offset==1)  this.resistanceMap.put(dt, ImmunityType.RESISTANT);
            else if(offset<=-1) this.resistanceMap.put(dt, ImmunityType.VULNERABLE);  
	    	break;
	    case RESISTANT:
            if(offset>0)        this.resistanceMap.put(dt, ImmunityType.IMMUNE);
            else if(offset==-1) this.resistanceMap.put(dt, ImmunityType.NONE);
            else if(offset<-1)  this.resistanceMap.put(dt, ImmunityType.VULNERABLE);  
	    	break;
	    case IMMUNE:
            if(offset==-1)      this.resistanceMap.put(dt, ImmunityType.RESISTANT);
            else if(offset==-2) this.resistanceMap.put(dt, ImmunityType.NONE);
            else if(offset<-2)  this.resistanceMap.put(dt, ImmunityType.VULNERABLE);  
	    	break;             
        }
	}
	
	public void realizeTheClasses() {
		for(DNDClass cl: classMap.keySet()) {
			switch(cl) {
			case BARBARIAN:
				//  1: Rage Skill
				//  2: Reckless Attack
				//  5: 2x Attacks
				//  7: Feral Instinct...?
				//  9: Brutal Critical (1 die)
				// 11: Relentless Rage
				// 13: Brutal Critical (2 dice)
				// 15: Persistent Rage
				// 17: Brutal Critical (3 dice)
				// 18: Indominable Might
				switch(classMap.get(cl)) {
					case 20:
					case 19:
					case 18:
						//
					case 17:
						//
					case 16:
					case 15:
						//
					case 14:
					case 13:
						//
					case 12:
					case 11:
						//
					case 10:
					case  9:
						//
					case  8:
					case  7:
						//
					case  6:
					case  5:
						//
					case  4:
					case  3:
					case  2:
						//
					case  1:
						//
						break;
						
				}
				break;
			case BARD:
				//  1: Bardic Inspiration (d6)
				//  2: Jack of all trades
				//  5: Bardic inspiration (d8)
				// 10: Bardic inspiration (d10)
				// 15: Bardic inspiration (d12)
				// 20: Superior inspiration
				break;
			case CLERIC:
				//  2: Channel Divinity (2)
				//  5: Destroy Undead (1/2CR)
				//  8: Destroy Undead (1  CR)
				// 11: Destroy Undead (2  CR)
				// 14: Destroy Undead (3  CR)
				// 17: Destroy Undead (4  CR)
				// 18: Channel Divinity (3)
				break;
			case DRUID:
				// :)
				break;
			case FIGHTER:
				// Fighting Style ???
				//  1: Second Wind
				//  2: Action Surge (x1)
				//  5: 2x Attack
				//  9: Indominable 	(x1)
				// 11: 3x Attack
				// 13: Indominable 	(x2)
				// 17: Action Surge (x2)
				// 17: Indominable 	(x3)
				// 20: 4x Attack
				break;
			case MONK:
				//  1: Martial Arts :))))))))
				//  2: Ki (should be okay)
				//  3: Deflect Missiles
				//  5: 2x Attack
				//  5: Stunning Strike
				//  6: Ki-Empowered Strikes
				//  7: Evasion
				// 10: Purity of Body
				// 14: Diamond Soul
				// 18: Empty Body
				// 20: Perfect Soul
				break;
			case PALADIN:
				//  1: Lay on Hands
				//  2: Divine Smite
				//  2: Fighting Style?
				//  3: Divine Health
				//  3: Channel Divinity
				//  5: 2x Attack
				//  6: Aura of Protection
				// 10: Aura of Courage
				// 11: Improved Divine Smite
				// 14: Cleansing Touch
				break;
			case RANGER:
				//  1: Favoured Enemy...?
				//  1: Natural Explorer...? (Adv on Attack on ent that have not yet acted)
				//  2: Fighting Style...?
				//  6: Greater Favoured Enemy............?
				//  8: Fleet of Foot
				// 14: Vanish
				// 18: Feral Senses
				// 20: Foe Slayer
				break;
			case ROGUE:
				//  1: Sneak Attack *VERY LOUD DYING NOISES*
				//  2: Cunning Action
				//  5: Uncanny Dodge
				//  7: Evasion
				// 15: Slippery mind
				// 18: Elusive
				// 20: Stroke of Luck
				break;
			case SORCERER:
				//  2: Font of Magic
				//  3: Metamagic (2 effects, 1 per spell)
				// 10: Metamagic (3 effects, 1 per spell)
				// 17: Metamagic (4 effects, 1 per spell)
				// 20: Sorcerous Restoration
				break;
			case WARLOCK:
				//  2: Eldritch Invocations
				//  3: Pact Boon
				// 11: Mystic Arcanum (6th level)
				// 13: Mystic Arcanum (7th level)
				// 15: Mystic Arcanum (8th level)
				// 17: Mystic Arcanum (9th level)
				// 20: Eldritch Master
				break;			
			}
		}
	}
	
	public void takeDamage(int value) {this.currHP -= value;}
	
	public void takeDamage(int value, DamageType type) {
		switch(resistanceMap.get(type)) {
			case VULNERABLE:
				this.takeDamage(value*2);
				break;
		
			case NONE:
				this.takeDamage(value);
				break;
				
			case RESISTANT:
				this.takeDamage(value/2);
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
        output += this.skills.get(SkillName.STR).value+"\n";
        output += this.skills.get(SkillName.DEX).value+"\n";
        output += this.skills.get(SkillName.CON).value+"\n";
        output += this.skills.get(SkillName.INT).value+"\n";
        output += this.skills.get(SkillName.WIS).value+"\n";
        output += this.skills.get(SkillName.CHR).value;
		for(DamageType dmg: DamageType.values()) {output +="\n"+resistanceMap.get(dmg);}
		output +="\n" + this.color;
		for(DNDClass cl: classMap.keySet()) {output +="\n"+cl+" "+classMap.get(cl);}
		output +="\n" + this.weapon.toString();
		return output;
	}
}
