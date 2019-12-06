package Enums;

public enum Die {
	D4 ( 4),
	D6 ( 6),
	D8 ( 8),
	D10(10),
	D12(12),
	D20(20);
	
	public final int value;
	private Die(int value) {
		this.value = value;
	}
	
}
