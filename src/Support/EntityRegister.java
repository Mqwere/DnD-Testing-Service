package Support;
/*public static EnumMap<TeamColor,HashMap<Integer,Entity>> entityMap = 
			  new EnumMap<TeamColor,HashMap<Integer,Entity>>(TeamColor.class);*/

import java.util.EnumMap;
import java.util.HashMap;

import Core.Entity;
import Enums.TeamColor;

public class EntityRegister{
	
	@SuppressWarnings("unused")
	private static EnumMap<TeamColor,HashMap<Integer,Entity>> entityMap = 
			  new EnumMap<TeamColor,HashMap<Integer,Entity>>(TeamColor.class);
	
	public static void put(TeamColor color, Entity ent) {
		if(EntityRegister.entityMap.get(color) == null) {
			EntityRegister.entityMap.put(color, new HashMap<Integer,Entity>());
		}
		put(color,
			EntityRegister.entityMap.get(color).size(),
			ent);
	}
	
	public static void put(TeamColor color, Integer index, Entity ent) {
		HashMap<Integer,Entity> temp = EntityRegister.entityMap.get(color);
		temp.put(index,ent);
		EntityRegister.entityMap.put(color,temp);
	}
	
	public static void rem(TeamColor color, Integer index) {
		HashMap<Integer,Entity> temp = EntityRegister.entityMap.get(color);
		if(temp.size()>1 && index<temp.size()-1)
		for(int i=index+1; i<temp.size();i++) {
			temp.put(i-1,temp.get(i));}
		temp.remove(temp.size()-1);
	}
	
	public static Entity get(TeamColor color, Integer index) {
		HashMap<Integer,Entity> temp = EntityRegister.entityMap.get(color);
		return temp.get(index);
	}
	
	public static HashMap<Integer,Entity> getMap(TeamColor color) {
		return EntityRegister.entityMap.get(color);
	}
	

}
