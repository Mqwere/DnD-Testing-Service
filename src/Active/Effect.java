package Active;

import Core.Entity;
import Enums.Core.EffectEffect;
import Enums.Support.DamageType;
import Enums.Support.Die;
import Enums.Support.EffectValue;
import Enums.Support.SkillName;

public class Effect {
	Entity 			performer;
	public 
	EffectValue 	value;
	EffectEffect 	editedAtribute;
	
	
	public Effect(Entity performer, EffectEffect attrib, 
			int value) {
		this.performer = performer;
		this.editedAtribute = attrib;
		this.value = new EffectValue(value);
	}
	
	public Effect(Entity performer, EffectEffect attrib, 
			Die valDie, int valDieNo, int value) {
		this.performer = performer;
		this.editedAtribute = attrib;
		this.value = new EffectValue(valDieNo,valDie,value);
	}
	
	public void apply(Entity target) {
		int value = this.value.get();
		switch(this.editedAtribute) {
	    case ACID_RES:  target.offsetResistance(value,DamageType.ACID); 		  break;
    	case BLUD_RES:  target.offsetResistance(value,DamageType.BLUD); 		  break;
    	case COLD_RES:  target.offsetResistance(value,DamageType.COLD); 		  break;
    	case FIRE_RES:  target.offsetResistance(value,DamageType.FIRE); 		  break;
    	case FORC_RES:  target.offsetResistance(value,DamageType.FORC); 		  break;
    	case LIGH_RES:  target.offsetResistance(value,DamageType.LIGH); 		  break;
    	case NECR_RES:  target.offsetResistance(value,DamageType.NECR); 		  break;
    	case PIER_RES:  target.offsetResistance(value,DamageType.PIER); 		  break;
    	case POIS_RES:  target.offsetResistance(value,DamageType.POIS); 		  break;
    	case PSYC_RES:  target.offsetResistance(value,DamageType.PSYC); 		  break;
    	case RADI_RES:  target.offsetResistance(value,DamageType.RADI); 		  break;
    	case SLAS_RES:  target.offsetResistance(value,DamageType.SLAS); 		  break;
    	case THUN_RES:  target.offsetResistance(value,DamageType.THUN); 		  break;
    	
	    case ACID_DMG:  target.takeDamage(value, DamageType.ACID); 	  break;
    	case BLUD_DMG:  target.takeDamage(value, DamageType.BLUD); 	  break;
    	case COLD_DMG:  target.takeDamage(value, DamageType.COLD); 	  break;
    	case FIRE_DMG:  target.takeDamage(value, DamageType.FIRE); 	  break;
    	case FORC_DMG:  target.takeDamage(value, DamageType.FORC); 	  break;
    	case LIGH_DMG:  target.takeDamage(value, DamageType.LIGH); 	  break;
    	case NECR_DMG:  target.takeDamage(value, DamageType.NECR); 	  break;
    	case PIER_DMG:  target.takeDamage(value, DamageType.PIER); 	  break;
    	case POIS_DMG:  target.takeDamage(value, DamageType.POIS); 	  break;
    	case PSYC_DMG:  target.takeDamage(value, DamageType.PSYC); 	  break;
    	case RADI_DMG:  target.takeDamage(value, DamageType.RADI); 	  break;
    	case SLAS_DMG:  target.takeDamage(value, DamageType.SLAS); 	  break;
    	case THUN_DMG:  target.takeDamage(value, DamageType.THUN); 	  break;

	    case STR:   	target.skills.get(SkillName.STR).setValue(target.skills.get(SkillName.STR).value + value); break;
	    case DEX:   	target.skills.get(SkillName.DEX).setValue(target.skills.get(SkillName.DEX).value + value); break;
	    case CON:   	target.skills.get(SkillName.CON).setValue(target.skills.get(SkillName.CON).value + value); break;
	    case INT:   	target.skills.get(SkillName.INT).setValue(target.skills.get(SkillName.INT).value + value); break;
	    case WIS:   	target.skills.get(SkillName.WIS).setValue(target.skills.get(SkillName.WIS).value + value); break;
	    case CHR:   	target.skills.get(SkillName.CHR).setValue(target.skills.get(SkillName.CHR).value + value); break;

	    case 
	    armorClass: 	target.setAC(target.getAC() + value);		  break;
	    case currHP:	target.takeDamage(-value); 					  break;
	    case maxHP: 	target.takeDamage(-value);
	    	        	target.setHP(target.getHP()+value);			  break;
		}
	}
}
