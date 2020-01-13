package Support;
/*public static EnumMap<TeamColor,HashMap<Integer,Entity>> entityMap = 
			  new EnumMap<TeamColor,HashMap<Integer,Entity>>(TeamColor.class);*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;

import Core.Entity;
import Core.Program;
import Enums.Core.TeamColor;

public class EntityRegister{
	
	private static EnumMap<TeamColor,HashMap<Integer,Entity>> 
			entityMap = new EnumMap<>(TeamColor.class);
	
	private static void sort(TeamColor color) {
		HashMap<Integer,Entity> map 	= getMap(color);
		ArrayList<String> 		tempa 	= new ArrayList<>();
		HashMap<String,Entity> 	tempm 	= new HashMap<>();
		for(Integer i: map.keySet()) {tempa.add(map.get(i).getName());tempm.put(map.get(i).getName(),map.get(i));}
		
		String[] temp = new String[tempa.size()];
		
		for(int i=0; i<tempa.size();i++)
			temp[i] = tempa.get(i);
		
		
		Arrays.sort(temp);
		EntityRegister.entityMap.get(color).clear();
		for(int i=0;i<temp.length;i++) {
			getMap(color).put(i, tempm.get(temp[i]));
		}
	}
	
	public static void put(TeamColor color, Entity ent) {
		if(EntityRegister.entityMap.get(color) == null)
			EntityRegister.entityMap.put(color, new HashMap<Integer,Entity>());
		put(color,
			EntityRegister.entityMap.get(color).size(),
			ent);
		sort(color);
	}
	
	public static void put(TeamColor color, Integer index, Entity ent) {
		HashMap<Integer,Entity> temp = EntityRegister.entityMap.get(color);
		temp.put(index,ent);
		EntityRegister.entityMap.put(color,temp);
		sort(color);
	}
	
	public static void rem(TeamColor color, Integer index) {
		HashMap<Integer,Entity> temp = EntityRegister.entityMap.get(color);
		if(temp.size()>1 && index<temp.size()-1)
		for(int i=index+1; i<temp.size();i++) {
			temp.put(i-1,temp.get(i));}
		temp.remove(temp.size()-1);
		sort(color);
	}
	
	public static Entity get(TeamColor color, Integer index) {
		HashMap<Integer,Entity> temp = EntityRegister.entityMap.get(color);
		return temp.get(index);
	}
	
	public static HashMap<Integer,Entity> getMap(TeamColor color) {
		return EntityRegister.entityMap.get(color);
	}
}
