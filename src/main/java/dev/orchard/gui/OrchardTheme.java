package dev.orchard.gui;

import java.awt.Color;

/**
 * Orchard theme: light purple + deep violet, glass-like surfaces,
 * soft shadows, no 2011 gray-rectangle vibes.
 */
public final class OrchardTheme {
    // Core palette
    public static final int DEEP_VIOLET = rgb(0x2A, 0x1B, 0x4E);   // #2A1B4E
    public static final int VIOLET      = rgb(0x7C, 0x5C, 0xFF);   // #7C5CFF
    public static final int LIGHT_PURPLE= rgb(0xC4, 0xB0, 0xFF);   // #C4B0FF
    public static final int GLASS_BG    = withAlpha(rgb(0x1E, 0x14, 0x38), 190);
    public static final int GLASS_STROKE= withAlpha(rgb(0xC4, 0xB0, 0xFF), 60);
    public static final int TEXT_PRIMARY= rgb(0xF2, 0xEE, 0xFF);
    public static final int TEXT_DIM    = withAlpha(rgb(0xC4, 0xB0, 0xFF), 160);
    public static final int ACCENT_GLOW = withAlpha(VIOLET, 90);

    private OrchardTheme() {}

    public static int rgb(int r, int g, int b) {
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    public static int withAlpha(int argb, int alpha) {
        return (argb & 0x00FFFFFF) | ((alpha & 0xFF) << 24);
    }

    /** Blend two colors, t in [0,1] — used for smooth hover animations. */
    public static int lerp(int from, int to, float t) {
        float a1 = (from >>> 24) & 0xFF, a2 = (to >>> 24) & 0xFF;
        float r1 = (from >>> 16) & 0xFF, r2 = (to >>> 16) & 0xFF;
        float g1 = (from >>> 8) & 0xFF, g2 = (to >>> 8) & 0xFF;
        float b1 = from & 0xFF, b2 = to & 0xFF;
        int a = Math.round(a1 + (a2 - a1) * t);
        int r = Math.round(r1 + (r2 - r1) * t);
        int g = Math.round(g1 + (g2 - g1) * t);
        int b = Math.round(b1 + (b2 - b1) * t);
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
