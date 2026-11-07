package dev.orchard.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A module belongs to exactly one section. Sections with content register
 * modules; placeholder sections stay empty for now.
 */
public class Module {
    private final String name;
    private final String description;
    private boolean enabled;

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isEnabled() { return enabled; }
    public void toggle() { enabled = !enabled; }

    public static List<Module> forSection(String section) {
        List<Module> modules = new ArrayList<>();
        switch (section) {
            case "visuals" -> {
                modules.add(new Module("Fullbright", "Brightens the world"));
                modules.add(new Module("Custom Sky", "Swap sky visuals"));
                modules.add(new Module("Block Outlines", "Clean block edges"));
                modules.add(new Module("Item Physics", "Dropped items tumble"));
                modules.add(new Module("Particles", "Rich particle effects"));
            }
            case "silent" -> {
                modules.add(new Module("Silent Mode", "Muted actions, hidden traces"));
            }
            case "cosmetics" -> {
                modules.add(new Module("Capes", "Browse and equip capes"));
                modules.add(new Module("Hats", "Cosmetic headwear"));
                modules.add(new Module("Aura Effects", "Glowing player auras"));
            }
            case "settings" -> {
                modules.add(new Module("Accent Color", "Pick your violet"));
                modules.add(new Module("Font Scale", "UI text sizing"));
                modules.add(new Module("GUI Blur", "Background blur toggle"));
            }
            default -> {
                // sword / mace / cart / uhc / spear / crystal / player: intentionally empty
            }
        }
        return Collections.unmodifiableList(modules);
    }
}
