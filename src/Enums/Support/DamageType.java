package Enums.Support;

public enum DamageType {
	PIER( 0),
	SLAS( 1),
	BLUD( 2),
	
	ACID( 3),
	COLD( 4),
	FIRE( 5),
	FORC( 6),
	LIGH( 7),
	NECR( 8),
	POIS( 9),
	PSYC(10),
	RADI(11),
	THUN(12);
	
	public final int arrValue;
	private DamageType(int value) {
		this.arrValue = value;
	}
}
