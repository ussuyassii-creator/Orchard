package io.orchard.client.cosmetics;

import java.util.EnumMap;
import java.util.Map;

public final class CosmeticProfile {
    private final String name;
    private final Map<CosmeticSlot, String> equipped = new EnumMap<>(CosmeticSlot.class);

    public CosmeticProfile(String name) { this.name = name; }
    public String name() { return name; }
    public Map<CosmeticSlot, String> equipped() { return equipped; }
    public void set(CosmeticSlot slot, String cosmeticId) { equipped.put(slot, cosmeticId); }
    public void clear(CosmeticSlot slot) { equipped.remove(slot); }
}
