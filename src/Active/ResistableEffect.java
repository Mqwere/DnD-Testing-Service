package Active;

import Core.Entity;
import Enums.Active.ResultOfResist;
import Enums.Core.EffectEffect;
import Enums.Support.Die;
import Enums.Support.PropertyName;
import Interfaces.Active.Resistable;
import Support.Roller;

public class ResistableEffect extends Effect implements Resistable{
	private Integer        DC;
	private ResultOfResist resultOfResist;
	private PropertyName   resistingProperty;
	
	public ResistableEffect(Entity performer, EffectEffect attrib, 
			int value, int DC, Action parent) {
		super(performer, attrib, value, parent);
		this.setDC(DC);
	}

	public ResistableEffect(Entity performer, EffectEffect attrib, 
			Die valDie, int valDieNo, int value, int DC, Action parent) {
		super(performer, attrib, valDie, valDieNo, value, parent);
		this.setDC(DC);
	}
	
	public void	setDC				(Integer dc) {this.DC = dc;}
	public void setResultOfResist	(ResultOfResist ror) {this.resultOfResist = ror;}
	public void setResistingProperty(PropertyName pn) {this.resistingProperty = pn;}

	@Override
	public void getResisted(Entity target) {
		int value = target.props.get(this.resistingProperty).modifier + Roller.roll(Die.D20);
		if(value >= DC) {
			switch(this.resultOfResist) {
				case DeleteEffect:
					break;
				case HalfEffect:
					this.goHalf(target);
					break;
				case NoEffect:
					break;
			}
		}
		else this.goFull(target);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj.getClass().isAssignableFrom(ResistableEffect.class)) {
			ResistableEffect eff = (ResistableEffect) obj;
			if(super.equals(obj)) {
				if(eff.DC == this.DC
				&& eff.resistingProperty == this.resistingProperty
				&& eff.resultOfResist == this.resultOfResist)
					return true;
			}
		}
		return false;
	}
}
