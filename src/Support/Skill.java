package Support;

import Enums.Support.Die;
import Enums.Support.SkillName;

public class Skill{
	public int value;
	public int modifier;
	SkillName  name;
	
	public Skill(SkillName name){
		this.name = name;
	}
	
	public Skill(SkillName name, int value){
		this(name);
		this.value = value;
		this.modifier = value/2 - 5;
	}
		
	public void setValue(int input) {
		this.value = input;
		this.modifier = input/2 - 5;
	}
	
	public boolean check(int DC) {
		int roll = Roller.roll(Die.D20);
		if(roll == 1) 
			return false;
		else 
		if(roll == 20) 
			return true;
		else {
			int result =  roll	+ this.modifier;
			return !(result<DC);
		}
	}
	
	public boolean checkAdv(int DC) {
		int roll = Roller.rollAdv(Die.D20);
		if(roll == 1) 
			return false;
		else 
		if(roll == 20) 
			return true;
		else {
			int result = roll + this.modifier;
			return !(result<DC);
		}
	}
	
	public boolean checkDis(int DC) {
		int roll = Roller.rollDis(Die.D20);
		if(roll == 1) 
			return false;
		else 
		if(roll == 20) 
			return true;
		else {
			int result = roll + this.modifier;
			return !(result<DC);
		}
	}
}
