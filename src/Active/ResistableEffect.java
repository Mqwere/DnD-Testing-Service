package Active;

import Core.Entity;
import Enums.Core.EffectEffect;
import Enums.Support.Die;
import Interfaces.Active.Resistable;

public class ResistableEffect extends Effect implements Resistable{
	private Integer DC;
	
	public ResistableEffect(Entity performer, EffectEffect attrib, 
			int value) {
		super(performer, attrib, value);
	}

	public ResistableEffect(Entity performer, EffectEffect attrib, 
			Die valDie, int valDieNo, int value) {
		super(performer, attrib, valDie, valDieNo, value);
	}
	
	public void	setDC(Integer dc) {this.DC = dc;}

	@Override
	public Integer getDC() {
		return this.DC;
	}

}
