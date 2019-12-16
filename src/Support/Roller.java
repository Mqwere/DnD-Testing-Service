package Support;

import java.util.Random;

import Enums.Support.Die;

public class Roller{
	private static Random ladyLuck = new Random();
	
	public static int roll(Die die) {
		return ladyLuck.nextInt(die.value-1)+1;
	}
	
	public static int rollAdv(Die die) {
		int result1 = ladyLuck.nextInt(die.value-1)+1;
		int result2 = ladyLuck.nextInt(die.value-1)+1;
		return result1>result2 ? result1:result2;
	}
	
	public static int rollDis(Die die) {
		int result1 = ladyLuck.nextInt(die.value-1)+1;
		int result2 = ladyLuck.nextInt(die.value-1)+1;
		return result1>result2 ? result2:result1;
	}
	
	public static int rollSkill() {
		int min = 0;
		int roll1 = roll(Die.D6);
		int roll2 = roll(Die.D6);
		min = roll1>roll2 ? roll2:roll1;
		int roll3 = roll(Die.D6);
		min = min>roll3 ? roll3:min;
		int roll4 = roll(Die.D6);
		min = min>roll4 ? roll4:min;
		
		return roll1 + roll2 + roll3 + roll4 - min;
	}

}
