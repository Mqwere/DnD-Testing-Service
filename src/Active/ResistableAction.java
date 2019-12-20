package Active;

import Interfaces.Active.Resistable;

public class ResistableAction extends Action implements Resistable{
	private Integer DC;
	
	public void	setDC(Integer dc) {this.DC = dc;}
	@Override
	public Integer getDC() {return this.DC;}
}
