package Enums.Support;

import Support.Roller;

public class EffectValue{
	Die valueDie = null;
	int valueDieNo = 0;
	int valueModif = 0;
	
	public EffectValue(int VDN, Die VD, int VM) {
		this.valueDieNo = VDN;
		this.valueDie 	= VD;
		this.valueModif = VM;
	}
	
	public EffectValue(int VM) {
		this.valueModif = VM;
	}
	
	public int get() {
		int output = this.valueModif;
		if(this.valueDieNo<0) output *= -1;
		int times = this.valueDieNo<0? this.valueDieNo*=-1:this.valueDieNo;
		for(int i=0; i<times; i++)
			if(this.valueDieNo>0) output += Roller.roll(this.valueDie);
			else 				  output -= Roller.roll(this.valueDie);
		
		return output;
	}
}
