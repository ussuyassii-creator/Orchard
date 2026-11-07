package io.orchard.client.cosmetics;

import java.util.EnumMap;
import java.util.Map;

public final class CosmeticState {
    private static final Map<CosmeticSlot, String> equipped = new EnumMap<>(CosmeticSlot.class);
    private static String previewing;
    private CosmeticState() { }
    public static void preview(String id) { previewing = id; }
    public static String previewing() { return previewing; }
    public static boolean isEquipped(String id) { return equipped.containsValue(id); }
    public static void equip(String id) { previewing = id; }
    public static void unequip(String id) { equipped.values().removeIf(id::equals); if (id.equals(previewing)) previewing = null; }
    public static void equip(CosmeticRegistry.CosmeticDefinition definition) { equipped.put(definition.slot(), definition.id()); previewing = definition.id(); }
    public static void unequip(CosmeticRegistry.CosmeticDefinition definition) { if (definition.id().equals(equipped.get(definition.slot()))) equipped.remove(definition.slot()); if (definition.id().equals(previewing)) previewing = null; }
    public static Map<CosmeticSlot, String> equipped() { return Map.copyOf(equipped); }
}
