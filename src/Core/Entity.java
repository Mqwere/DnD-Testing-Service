package Core;

import java.util.ArrayList;
import java.util.EnumMap;

import Active.Effect;
import Enums.Core.DNDClass;
import Enums.Core.Enhancement;
import Enums.Core.ImmunityType;
import Enums.Core.Race;
import Enums.Core.TeamColor;
import Enums.Core.WeaponType;
import Enums.Support.DamageType;
import Enums.Support.Die;
import Enums.Support.PropertyName;
import Support.DClass;
import Support.DamagePackage;
import Support.Skill;

public class Entity{
	/////// Fields that will only be used in a running simulation
	Integer	  attackNo;
	
	public ArrayList<ArrayList<Effect>> effectQueue = new ArrayList<ArrayList<Effect>>();
	
	/////// Fields 
	public static int statID = 0;
	public int ID;
	
	/////// Fields that the program needs to save / load
	String	  name;
	
	Race 	  race;
	
	public 
	TeamColor color;
	
	Weapon 	  weapon;

	int 	  level,
			  currHP,
			  maxHP;
	
	public EnumMap<PropertyName,Skill> 
	props = new EnumMap<>(PropertyName.class);
	/*
		
			  STR = new Skill(PropertyName.STR),
			  DEX = new Skill(PropertyName.DEX),
			  CON = new Skill(PropertyName.CON),
			  INT = new Skill(PropertyName.INT),
			  WIS = new Skill(PropertyName.WIS),
			  CHR = new Skill(PropertyName.CHR);
	/**/
	
	EnumMap<DamageType,ImmunityType>
			  resistanceMap = new EnumMap<>(DamageType.class);
	
	EnumMap<DNDClass,DClass>
			  classMap 	  = new EnumMap<>(DNDClass.class);
	
	public Entity() {
		this.ID = statID++;
		props.put(PropertyName.AC ,new Skill(this, PropertyName.AC) );
		props.put(PropertyName.STR,new Skill(this, PropertyName.STR));
		props.put(PropertyName.DEX,new Skill(this, PropertyName.DEX));
		props.put(PropertyName.CON,new Skill(this, PropertyName.CON));
		props.put(PropertyName.INT,new Skill(this, PropertyName.INT));
		props.put(PropertyName.WIS,new Skill(this, PropertyName.WIS));
		props.put(PropertyName.CHR,new Skill(this, PropertyName.CHR));
	}
	
	public Entity(Race race) {
		this();
		this.race = race;
		defaultizeResistances();
	}
	

	
	public static Entity customChara() {
		Entity ent = new Entity(Race.CUSTOM, "Dave", 10, 100, 10, 14, 10, 16, 4, 4 ,4);
		ent.setClass(DNDClass.FIGHTER,new DClass(6,true));
		ent.setClass(DNDClass.BARBARIAN,3);
		ent.setClass(DNDClass.MONK,1);
		ArrayList<DamagePackage> dmgPck = new ArrayList<DamagePackage>();
		dmgPck.add(new DamagePackage(2,Die.D4,DamageType.SLAS));
		dmgPck.add(new DamagePackage(1,Die.D4,DamageType.FIRE));
		dmgPck.add(new DamagePackage(1,Die.D4,DamageType.ACID));
		dmgPck.add(new DamagePackage(1,Die.D4,DamageType.RADI));
		dmgPck.add(new DamagePackage(1,Die.D4,DamageType.PSYC));
		Weapon wep = new Weapon("Sord", WeaponType.NORMAL,dmgPck);
		wep.setEnhancement(Enhancement.plus3);
		wep.setDmType(DamageType.SLAS);
		
		ent.setWeapon(wep);
		ent.realizeTheClasses();
		return ent;
	}
	
	public Entity(Race race, String name, Integer level, Integer maxHP, Integer armorClass,
			Integer STR, Integer DEX, Integer CON, Integer INT, Integer WIS, Integer CHR) {
		this(race);
		this.name = name;
		this.level = level;
		this.maxHP = maxHP; this.currHP = maxHP;
		this.setprops(armorClass, STR, DEX, CON, INT, WIS, CHR);
	}
	
	public void addEffect(int index, Effect effect) {
		this.effectQueue.get(index).add(effect);
	}
	
	public void setClass(DNDClass cl, Integer in) {
		if(in==0) 	this.classMap.remove(cl );
		else 		this.classMap.put(cl, new DClass(in));
	}
	
	public void setClass(DNDClass cl, DClass dclass) {
		this.classMap.put(cl, dclass);
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
		return this.props.get(PropertyName.AC) .value;
	}
	
	public void setAC(Integer AC) {
		this.props.get(PropertyName.AC) .setValue(AC);
	}
	
	public void setprops(int STR, int DEX, int CON, int INT, int WIS, int CHR) {
		this.props.get(PropertyName.STR).setValue(STR);
		this.props.get(PropertyName.DEX).setValue(DEX);
		this.props.get(PropertyName.CON).setValue(CON);
		this.props.get(PropertyName.INT).setValue(INT);
		this.props.get(PropertyName.WIS).setValue(WIS);
		this.props.get(PropertyName.CHR).setValue(CHR);	
	}
	
	public void setprops(int AC, int STR, int DEX, int CON, int INT, int WIS, int CHR) {
		this.props.get(PropertyName.AC) .setValue(AC);
		this.props.get(PropertyName.STR).setValue(STR);
		this.props.get(PropertyName.DEX).setValue(DEX);
		this.props.get(PropertyName.CON).setValue(CON);
		this.props.get(PropertyName.INT).setValue(INT);
		this.props.get(PropertyName.WIS).setValue(WIS);
		this.props.get(PropertyName.CHR).setValue(CHR);	
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
	
	public void setSkillV(PropertyName name, Integer value) {this.props.get(name).setValue(value);}
	
	public Integer getSkillV(PropertyName name) {return this.props.get(name).value;}
	
	public Integer getSkillM(PropertyName name) {return this.props.get(name).modifier;}
	
	public void offsetResistance(int offset, DamageType dt) {
		switch(this.resistanceMap.get(dt)){
	    case VULNERABLE:
            if(offset>2)        this.resistanceMap.put(dt, ImmunityType.IMMUNE);
            else if(offset==2)  this.resistanceMap.put(dt, ImmunityType.RESISTANT);
            else if(offset==1)  this.resistanceMap.put(dt, ImmunityType.NONE);		 break;
            
	    case NONE:
            if(offset>1)        this.resistanceMap.put(dt, ImmunityType.IMMUNE);
            else if(offset==1)  this.resistanceMap.put(dt, ImmunityType.RESISTANT);
            else if(offset<=-1) this.resistanceMap.put(dt, ImmunityType.VULNERABLE); break;
            
	    case RESISTANT:
            if(offset>0)        this.resistanceMap.put(dt, ImmunityType.IMMUNE);
            else if(offset==-1) this.resistanceMap.put(dt, ImmunityType.NONE);
            else if(offset<-1)  this.resistanceMap.put(dt, ImmunityType.VULNERABLE); break;
            
	    case IMMUNE:
            if(offset==-1)      this.resistanceMap.put(dt, ImmunityType.RESISTANT);
            else if(offset==-2) this.resistanceMap.put(dt, ImmunityType.NONE);
            else if(offset<-2)  this.resistanceMap.put(dt, ImmunityType.VULNERABLE); break;             
        }
	}
	
	public void realizeTheClasses() {
		for(DNDClass cl: classMap.keySet()) {
			switch(cl) {
			case BARBARIAN:
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.STR).setProfficient();
					this.props.get(PropertyName.CON).setProfficient();
				}
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
				switch(classMap.get(cl).level) {
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
						if(this.attackNo<=2) this.attackNo = 2;
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
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.DEX).setProfficient();
					this.props.get(PropertyName.CHR).setProfficient();
				}
				//  1: Bardic Inspiration (d6)
				//  2: Jack of all trades
				//  5: Bardic inspiration (d8)
				// 10: Bardic inspiration (d10)
				// 15: Bardic inspiration (d12)
				// 20: Superior inspiration
				break;
			case CLERIC:
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.WIS).setProfficient();
					this.props.get(PropertyName.CHR).setProfficient();
				}
				//  2: Channel Divinity (2)
				//  5: Destroy Undead (1/2CR)
				//  8: Destroy Undead (1  CR)
				// 11: Destroy Undead (2  CR)
				// 14: Destroy Undead (3  CR)
				// 17: Destroy Undead (4  CR)
				// 18: Channel Divinity (3)
				break;
			case DRUID:
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.INT).setProfficient();
					this.props.get(PropertyName.WIS).setProfficient();
				}
				// :)
				break;
			case FIGHTER:
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.STR).setProfficient();
					this.props.get(PropertyName.CON).setProfficient();
				}
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
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.STR).setProfficient();
					this.props.get(PropertyName.DEX).setProfficient();
				}
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
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.WIS).setProfficient();
					this.props.get(PropertyName.CHR).setProfficient();
				}
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
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.STR).setProfficient();
					this.props.get(PropertyName.DEX).setProfficient();
				}
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
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.DEX).setProfficient();
					this.props.get(PropertyName.INT).setProfficient();
				}
				//  1: Sneak Attack *VERY LOUD DYING NOISES*
				//  2: Cunning Action
				//  5: Uncanny Dodge
				//  7: Evasion
				// 15: Slippery mind
				// 18: Elusive
				// 20: Stroke of Luck
				break;
			case SORCERER:
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.CHR).setProfficient();
					this.props.get(PropertyName.CON).setProfficient();
				}
				//  2: Font of Magic
				//  3: Metamagic (2 effects, 1 per spell)
				// 10: Metamagic (3 effects, 1 per spell)
				// 17: Metamagic (4 effects, 1 per spell)
				// 20: Sorcerous Restoration
				break;
			case WARLOCK:
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.WIS).setProfficient();
					this.props.get(PropertyName.CHR).setProfficient();
				}
				//  2: Eldritch Invocations
				//  3: Pact Boon
				// 11: Mystic Arcanum (6th level)
				// 13: Mystic Arcanum (7th level)
				// 15: Mystic Arcanum (8th level)
				// 17: Mystic Arcanum (9th level)
				// 20: Eldritch Master
				break;
			case WIZARD:
				if(classMap.get(cl).isPrimary) {
					this.props.get(PropertyName.WIS).setProfficient();
					this.props.get(PropertyName.CHR).setProfficient();
				}
				//  1: Ritual Casting
				// 18: Spell Mastery
				// 20: Signature Spells
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
		output += this.props.get(PropertyName.AC ).value+"\n";
        output += this.props.get(PropertyName.STR).value+"\n";
        output += this.props.get(PropertyName.DEX).value+"\n";
        output += this.props.get(PropertyName.CON).value+"\n";
        output += this.props.get(PropertyName.INT).value+"\n";
        output += this.props.get(PropertyName.WIS).value+"\n";
        output += this.props.get(PropertyName.CHR).value;
		for(DamageType dmg: DamageType.values()) {output +="\n"+resistanceMap.get(dmg);}
		output +="\n" + this.color;
		output +="\n%";
		for(DNDClass cl: classMap.keySet()) {output +="\n"+cl+" "+classMap.get(cl).toString();}
		output +="\n" + this.weapon.toString();
		return output;
	}
}
