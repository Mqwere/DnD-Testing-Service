package Active;

import Core.Entity;
import Enums.Active.ActionType;

public abstract class Action {
	String 		name;
	Boolean 	isMagical;
	ActionType 	type;
	Entity		performer;
}
