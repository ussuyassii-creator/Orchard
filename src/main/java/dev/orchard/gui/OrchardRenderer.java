package dev.orchard.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

/**
 * Rounded-rect helpers on top of vanilla rendering so panels get the
 * smooth, glass-like Orchard look instead of flat 2011 rectangles.
 */
public final class OrchardRenderer {

    private OrchardRenderer() {}

    /**
     * Draws a filled rounded rectangle using layered quads (corner radius faked
     * with concentric insets — cheap, smooth, works everywhere incl. Pojav).
     */
    public static void drawRoundedPanel(DrawContext ctx, int x, int y, int w, int h, int radius, int color) {
        // Soft shadow (2 offset layers below)
        fillRounded(ctx, x + 2, y + 3, w, h, radius, OrchardTheme.withAlpha(0x000000, 70));
        fillRounded(ctx, x + 1, y + 2, w, h, radius, OrchardTheme.withAlpha(0x000000, 40));
        // Glass body
        fillRounded(ctx, x, y, w, h, radius, color);
        // Subtle top sheen for the glass effect
        int sheen = OrchardTheme.withAlpha(0xFFFFFF, 14);
        ctx.fill(x + radius, y + 1, x + w - radius, y + 2, sheen);
    }

    /** Glass stroke border. */
    public static void drawGlassStroke(DrawContext ctx, int x, int y, int w, int h, int radius, int color) {
        // top & bottom edges
        ctx.fill(x + radius, y, x + w - radius, y + 1, color);
        ctx.fill(x + radius, y + h - 1, x + w - radius, y + h, color);
        // left & right edges
        ctx.fill(x, y + radius, x + 1, y + h - radius, color);
        ctx.fill(x + w - 1, y + radius, x + w, y + h - radius, color);
        // corners: small stepped blocks approximating the rounding
        for (int i = 0; i < radius; i++) {
            int inset = (int) Math.round(Math.sqrt(radius * radius - i * i));
            ctx.fill(x + i, y + radius - inset, x + i + 1, y + radius - inset + 1, color);
            ctx.fill(x + i, y + h - radius + inset - 1, x + i + 1, y + h - radius + inset, color);
            ctx.fill(x + w - i - 1, y + radius - inset, x + w - i, y + radius - inset + 1, color);
            ctx.fill(x + w - i - 1, y + h - radius + inset - 1, x + w - i, y + h - radius + inset, color);
        }
    }

    private static void fillRounded(DrawContext ctx, int x, int y, int w, int h, int radius, int color) {
        // body
        ctx.fill(x + radius, y, x + w - radius, y + h, color);
        // sides
        ctx.fill(x, y + radius, x + radius, y + h - radius, color);
        ctx.fill(x + w - radius, y + radius, x + w, y + h - radius, color);
        // corner arcs (quarter circles via per-column inset)
        for (int i = 0; i < radius; i++) {
            int inset = (int) Math.round(Math.sqrt(radius * radius - i * i));
            // top-left
            ctx.fill(x + i, y + radius - inset, x + i + 1, y + radius, color);
            // top-right
            ctx.fill(x + w - i - 1, y + radius - inset, x + w - i, y + radius, color);
            // bottom-left
            ctx.fill(x + i, y + h - radius, x + i + 1, y + h - radius + inset, color);
            // bottom-right
            ctx.fill(x + w - i - 1, y + h - radius, x + w - i, y + h - radius + inset, color);
        }
    }

    public static void drawGlow(DrawContext ctx, int x, int y, int w, int h, int color) {
        for (int i = 3; i >= 1; i--) {
            int c = OrchardTheme.withAlpha(color & 0x00FFFFFF, 90 / i);
            ctx.fill(x - i, y - i, x + w + i, y, c);
            ctx.fill(x - i, y + h, x + w + i, y + h + i, c);
            ctx.fill(x - i, y, x, y + h, c);
            ctx.fill(x + w, y, x + w + i, y + h, c);
        }
    }

    public static float animationDelta() {
        return MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true);
    }
}
