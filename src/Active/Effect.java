package Active;

import Core.Entity;
import Enums.Core.EffectEffect;
import Enums.Support.DamageType;
import Enums.Support.Die;
import Enums.Support.PropertyName;
import Support.EffectValue;

public class Effect {
	Entity 			performer;
	public 
	EffectValue 	value;
	EffectEffect 	editedAtribute;
	protected Action  parent;
	
	
	public Effect(Entity performer, EffectEffect attrib, 
			int value, Action parent) {
		this.parent = parent;
		this.performer = performer;
		this.editedAtribute = attrib;
		this.value = new EffectValue(value);
	}
	
	public Effect(Entity performer, EffectEffect attrib, 
			Die valDie, int valDieNo, int value, Action parent) {
		this.parent = parent;
		this.performer = performer;
		this.editedAtribute = attrib;
		this.value = new EffectValue(valDieNo,valDie,value);
	}
	
	public void goFull(Entity target) {
		int value = this.value.get();
		this.apply(target, value);
	}
	
	public void goHalf(Entity target) {
		int value = this.value.get()/2;
		this.apply(target, value);
	}
	
	public void apply(Entity target, int value) {
		switch(this.editedAtribute) {
	    case ACID_RES:  target.offsetResistance(value,DamageType.ACID); break;
    	case BLUD_RES:  target.offsetResistance(value,DamageType.BLUD); break;
    	case COLD_RES:  target.offsetResistance(value,DamageType.COLD); break;
    	case FIRE_RES:  target.offsetResistance(value,DamageType.FIRE); break;
    	case FORC_RES:  target.offsetResistance(value,DamageType.FORC); break;
    	case LIGH_RES:  target.offsetResistance(value,DamageType.LIGH); break;
    	case NECR_RES:  target.offsetResistance(value,DamageType.NECR); break;
    	case PIER_RES:  target.offsetResistance(value,DamageType.PIER); break;
    	case POIS_RES:  target.offsetResistance(value,DamageType.POIS); break;
    	case PSYC_RES:  target.offsetResistance(value,DamageType.PSYC); break;
    	case RADI_RES:  target.offsetResistance(value,DamageType.RADI); break;
    	case SLAS_RES:  target.offsetResistance(value,DamageType.SLAS); break;
    	case THUN_RES:  target.offsetResistance(value,DamageType.THUN); break;
    	
	    case ACID_DMG:  target.takeDamage(value, DamageType.ACID);		break;
    	case BLUD_DMG:  target.takeDamage(value, DamageType.BLUD);		break;
    	case COLD_DMG:  target.takeDamage(value, DamageType.COLD);		break;
    	case FIRE_DMG:  target.takeDamage(value, DamageType.FIRE);		break;
    	case FORC_DMG:  target.takeDamage(value, DamageType.FORC);		break;
    	case LIGH_DMG:  target.takeDamage(value, DamageType.LIGH);		break;
    	case NECR_DMG:  target.takeDamage(value, DamageType.NECR);		break;
    	case PIER_DMG:  target.takeDamage(value, DamageType.PIER);		break;
    	case POIS_DMG:  target.takeDamage(value, DamageType.POIS);		break;
    	case PSYC_DMG:  target.takeDamage(value, DamageType.PSYC);		break;
    	case RADI_DMG:  target.takeDamage(value, DamageType.RADI);		break;
    	case SLAS_DMG:  target.takeDamage(value, DamageType.SLAS);		break;
    	case THUN_DMG:  target.takeDamage(value, DamageType.THUN);		break;

	    case STR:   	target.props.get(PropertyName.STR).setValue(target.props.get(PropertyName.STR).value + value); break;
	    case DEX:   	target.props.get(PropertyName.DEX).setValue(target.props.get(PropertyName.DEX).value + value); break;
	    case CON:   	target.props.get(PropertyName.CON).setValue(target.props.get(PropertyName.CON).value + value); break;
	    case INT:   	target.props.get(PropertyName.INT).setValue(target.props.get(PropertyName.INT).value + value); break;
	    case WIS:   	target.props.get(PropertyName.WIS).setValue(target.props.get(PropertyName.WIS).value + value); break;
	    case CHR:   	target.props.get(PropertyName.CHR).setValue(target.props.get(PropertyName.CHR).value + value); break;

	    case 
	    armorClass: 	target.setAC(target.getAC() + value);		  break;
	    case currHP:	target.takeDamage(-value); 					  break;
	    case maxHP: 	target.takeDamage(-value);
	    	        	target.setHP(target.getHP()+value);			  break;
		}
	}
	

	@Override
	public boolean equals(Object obj) {
		//if(obj.getClass()==Effect.class) {
		if(obj.getClass().isAssignableFrom(Effect.class)) {
			Effect eff = (Effect) obj;
			
			if(this.editedAtribute == eff.editedAtribute
			&& this.parent		   == eff.parent 
			&& this.performer	   == eff.performer
			&& this.value		   == eff.value) 
				 return true;
		}return false;
	}

}
