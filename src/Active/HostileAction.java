package Active;

import Interfaces.Active.SavingThrowable;

public class HostileAction extends Action implements SavingThrowable{
	private Integer DC;
	
	public void	setDC(Integer dc) {this.DC = dc;}
	@Override
	public Integer getDC() {return this.DC;}
	
	
}
