package Support;

import Enums.DamageType;
import Enums.Die;

public class DamagePackage {
	public int 			dieNo;
	public Die 			dieSize;
	public DamageType 	dmgType;
	
	public DamagePackage(int dieNo, Die dieSize, DamageType dmgType) {
		this.dieNo	 = dieNo;
		this.dieSize = dieSize;
		this.dmgType = dmgType;
	}
	
	public int resolve() {
		int output = 0;
		for(int i=0; i<dieNo;i++) output += Roller.roll(this.dieSize);
		return output;
	}
}
