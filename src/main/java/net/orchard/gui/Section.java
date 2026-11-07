package net.orchard.gui;

/** One clickable icon tile in the left rail. */
public class Section {
    public final String id;
    public final String label;
    public final String iconGlyph;

    public Section(String id, String label, String iconGlyph) {
        this.id = id;
        this.label = label;
        this.iconGlyph = iconGlyph;
    }
}
