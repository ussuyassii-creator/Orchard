package io.orchard.client.cosmetics;

import java.util.List;

public final class CosmeticRegistry {
    private CosmeticRegistry() { }

    public static List<CosmeticDefinition> all() {
        return List.of(
                new CosmeticDefinition("dragon", "Dragon", "Companion", "Orbiting dragon preview", "DRAGON", CosmeticSlot.COMPANION),
                new CosmeticDefinition("pets", "Pets", "Companion", "Equip multiple pets", "PETS", CosmeticSlot.COMPANION),
                new CosmeticDefinition("orbits", "Orbits", "Effects", "Rings, crystals, and stars", "ORBIT", CosmeticSlot.ORBIT),
                new CosmeticDefinition("angel-horns", "Angel Horns", "Head", "Halo and celestial horns", "ANGEL", CosmeticSlot.HEAD),
                new CosmeticDefinition("devil-horns", "Devil Horns", "Head", "Horns, aura, and embers", "DEVIL", CosmeticSlot.HEAD),
                new CosmeticDefinition("capes", "Capes", "Back", "Animated cape preview", "CAPE", CosmeticSlot.BACK),
                new CosmeticDefinition("first-person", "First-Person Animations", "Hands", "Hand and swing animations", "HANDS", CosmeticSlot.FIRST_PERSON),
                new CosmeticDefinition("anime-models", "Anime Models", "Player Model", "Custom model preview slot", "MODEL", CosmeticSlot.MODEL),
                new CosmeticDefinition("auras", "Auras", "Effects", "Glow, trails, and energy", "AURA", CosmeticSlot.AURA),
                new CosmeticDefinition("wings", "Wings", "Back", "Animated wings and trails", "WINGS", CosmeticSlot.BACK),
                new CosmeticDefinition("halos", "Halos", "Head", "Floating halo variants", "HALO", CosmeticSlot.HEAD),
                new CosmeticDefinition("particles", "Particle Effects", "Effects", "Ambient particle layers", "FX", CosmeticSlot.AURA),
                new CosmeticDefinition("presets", "Presets", "Profiles", "Save complete looks", "SAVE", CosmeticSlot.MODEL)
        );
    }

    public record CosmeticDefinition(String id, String name, String category, String description, String badge, CosmeticSlot slot) { }
}
