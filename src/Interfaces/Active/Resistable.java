package Interfaces.Active;

import Core.Entity;
import Enums.Active.ResultOfResist;
import Enums.Support.PropertyName;

public interface Resistable {
	//public Integer 			getDC();
	//public ResultOfResist 	getIfResist();
	//public PropertyName 	getResistingProperty();
	public void getResisted(Entity target);
}
