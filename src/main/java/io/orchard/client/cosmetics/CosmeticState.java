package io.orchard.client.cosmetics;

import java.util.HashSet;
import java.util.Set;

public final class CosmeticState {
    private static final Set<String> equipped = new HashSet<>();
    private static String previewing;

    private CosmeticState() { }

    public static void preview(String id) { previewing = id; }
    public static String previewing() { return previewing; }
    public static boolean isEquipped(String id) { return equipped.contains(id); }
    public static void equip(String id) { equipped.add(id); previewing = id; }
    public static void unequip(String id) { equipped.remove(id); if (id.equals(previewing)) previewing = null; }
}
