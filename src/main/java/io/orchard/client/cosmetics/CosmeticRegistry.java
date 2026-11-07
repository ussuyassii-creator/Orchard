package io.orchard.client.cosmetics;

import java.util.List;

public final class CosmeticRegistry {
    private CosmeticRegistry() { }

    public static List<CosmeticDefinition> all() {
        return List.of(
                new CosmeticDefinition("dragon", "Dragon", "Companion", "Orbiting dragon preview", "DRAGON"),
                new CosmeticDefinition("pets", "Pets", "Companion", "Equip multiple pets", "PETS"),
                new CosmeticDefinition("orbits", "Orbits", "Effects", "Rings, crystals, and stars", "ORBIT"),
                new CosmeticDefinition("angel-horns", "Angel Horns", "Head", "Halo and celestial horns", "ANGEL"),
                new CosmeticDefinition("devil-horns", "Devil Horns", "Head", "Horns, aura, and embers", "DEVIL"),
                new CosmeticDefinition("capes", "Capes", "Back", "Animated cape preview", "CAPE"),
                new CosmeticDefinition("first-person", "First-Person Animations", "Hands", "Hand and swing animations", "HANDS"),
                new CosmeticDefinition("anime-models", "Anime Models", "Player Model", "Custom model preview slot", "MODEL"),
                new CosmeticDefinition("auras", "Auras", "Effects", "Glow, trails, and energy", "AURA"),
                new CosmeticDefinition("wings", "Wings", "Back", "Animated wings and trails", "WINGS"),
                new CosmeticDefinition("halos", "Halos", "Head", "Floating halo variants", "HALO"),
                new CosmeticDefinition("particles", "Particle Effects", "Effects", "Ambient particle layers", "FX"),
                new CosmeticDefinition("presets", "Presets", "Profiles", "Save complete looks", "SAVE")
        );
    }

    public record CosmeticDefinition(String id, String name, String category, String description, String badge) { }
}
