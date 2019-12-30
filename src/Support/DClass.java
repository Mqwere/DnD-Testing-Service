package Support;

public class DClass {
	public boolean 	isPrimary;
	public int		level;
	
	public DClass(int level) {
		this.level = level;
		this.isPrimary = false;
	}
	
	public DClass(int level, boolean isPrimary) {
		this.level = level;
		this.isPrimary = isPrimary;
	}
	
	public String toString() {
		String output = Integer.toString(this.level) + (this.isPrimary? " primary":"");
		return output;
	}
}
