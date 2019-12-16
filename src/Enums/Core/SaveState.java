package Enums.Core;

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
