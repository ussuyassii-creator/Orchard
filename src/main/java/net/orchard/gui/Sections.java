package net.orchard.gui;

import java.util.List;

/** All primary sections, in rail order. */
public final class Sections {
    public static final List<Section> ALL = List.of(
        new Section("sword",     "Sword",      "\u2694"),   // crossed swords
        new Section("mace",      "Mace",       "\u1F528"),  // hammer
        new Section("cart",      "Cart",       "\u1F68B"),  // minecart/train
        new Section("uhc",       "UHC",        "\u2764"),   // heart
        new Section("spear",     "Spear",      "\u1F5F1"),  // trident
        new Section("crystal",   "Crystal",    "\u1F48E"),  // gem
        new Section("visuals",   "Visuals",    "\u1F441"),  // eye
        new Section("silent",    "Silent",     "\u1F47B"),  // ghost
        new Section("cosmetics", "Cosmetics",  "\u2B50"),   // star
        new Section("settings",  "Settings",   "\u2699"),   // gear
        new Section("player",    "Player Model","\u1F9CD")  // person
    );

    private Sections() {}
}
