package Active;

import Core.Entity;
import Enums.Active.ResultOfResist;
import Enums.Support.PropertyName;
import Interfaces.Active.Resistable;

public class ResistableAction extends Action implements Resistable{
	private Integer 		DC;
	private ResultOfResist 	resultOfResist;
	private PropertyName 	resistingProperty;
	
	public void	setDC(Integer dc) {this.DC = dc;}
	public void setResultOfResist(ResultOfResist ror) {this.resultOfResist = ror;}
	public void setResistingProperty(PropertyName pn) {this.resistingProperty = pn;}

	@Override
	public void getResisted(Entity target) {
		
	}
}
