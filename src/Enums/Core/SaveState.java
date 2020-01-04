package Enums.Core;

public enum SaveState {
	CCOPEN	('='),
	WEPOPEN	('<'),
	DMGOPEN	('>'),
	CLOPEN	('%')
	;
	public final char startSign;
	private SaveState(char sS) {
		this.startSign = sS;
	}
}
