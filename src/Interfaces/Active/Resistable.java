package Interfaces.Active;

import Core.Entity;

public interface Resistable {
	//public Integer 			getDC();
	//public ResultOfResist 	getIfResist();
	//public PropertyName 	getResistingProperty();
	public void getResisted(Entity target);
}
