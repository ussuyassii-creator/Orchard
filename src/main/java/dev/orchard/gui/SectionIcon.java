package dev.orchard.gui;

/**
 * One icon per section. Drawn procedurally so no external assets are needed
 * and everything scales cleanly (also keeps PojavLauncher happy).
 */
public enum SectionIcon {
    SWORD("Sword"),
    MACE("Mace"),
    CART("Cart"),
    UHC("UHC"),
    SPEAR("Spear"),
    CRYSTAL("Crystal"),
    VISUALS("Visuals"),
    SILENT("Silent"),
    COSMETICS("Cosmetics"),
    SETTINGS("Settings"),
    PLAYER("Player");

    public final String label;

    SectionIcon(String label) { this.label = label; }

    public String key() {
        return name().toLowerCase();
    }
}
