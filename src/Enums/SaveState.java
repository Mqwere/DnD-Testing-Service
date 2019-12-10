package Enums;

public enum SaveState {
	CCOPEN('='),
	WEPOPEN('<'),
	DMGOPEN('>')
	;
	public final char startSign;
	private SaveState(char sS) {
		this.startSign = sS;
	}
}
