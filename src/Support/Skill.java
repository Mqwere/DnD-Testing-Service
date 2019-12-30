package Support;

import Core.Entity;
import Enums.Support.Die;
import Enums.Support.PropertyName;

public class Skill{
	private Entity master;
	public int value;
	public int modifier;
	public boolean profficiency = false;
	PropertyName  name;
	
	public Skill(Entity master,PropertyName name){
		this.name = name;
	}
	
	public Skill(Entity master,PropertyName name, int value){
		this(master, name);
		this.value = value;
		this.modifier = value/2 - 5;
	}
	
	public Skill(Entity master,PropertyName name, int value, boolean proff){
		this(master, name,value);
		this.profficiency = proff;
	}
		
	public void setValue(int input) {
		this.value = input;
		this.modifier = input/2 - 5;
	}
	
	public void setProfficient() {
		this.profficiency = true;
	}
	
	public void setProfficient(boolean proff) {
		this.profficiency = proff;
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
			if(this.profficiency) result += master.getProficiency();
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
			if(this.profficiency) result += master.getProficiency();
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
			if(this.profficiency) result += master.getProficiency();
			return !(result<DC);
		}
	}
}
