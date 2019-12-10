package Enums;

public enum Enhancement {
	plus0(0),
	plus1(1),
	plus2(2),
	plus3(3)
	;
	
	public int value;
	private Enhancement(int v) {
		this.value = v;
	}
}
