package Enums;

public enum Race {

	BUGBEAR("bugbear"),
	CUSTOM("custom"),
	DWARF("dwarf"),
	DRAGONBORN("dragonborn"),
	ELF("elf"),
	GNOME("gnome"),
	GOBLIN("goblin"),
	HALF_ELF("half-elf"),
	HALF_ORC("half-orc"),
	HALFLING("halfling"),
	HUMAN("human"),
	KOBOLD("kobold"),
	ORC("orc"),
	TIEFLING("tiefling"),
	WARFORGED("warforged")
	;
	public String name;
	private Race(String name) {
	}
}
