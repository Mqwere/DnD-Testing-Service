package Active;

import Core.Entity;
import Enums.Core.EffectEffect;
import Enums.Support.Die;
import Interfaces.Active.SavingThrowable;

public class HarmfulEffect extends Effect implements SavingThrowable{
	private Integer DC;
	
	public HarmfulEffect(Entity performer, EffectEffect attrib, 
			int value) {
		super(performer, attrib, value);
	}

	public HarmfulEffect(Entity performer, EffectEffect attrib, 
			Die valDie, int valDieNo, int value) {
		super(performer, attrib, valDie, valDieNo, value);
	}
	
	public void	setDC(Integer dc) {this.DC = dc;}

	@Override
	public Integer getDC() {
		return this.DC;
	}

}
