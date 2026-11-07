package net.orchard.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

/** Orchard main GUI: glass rail with icon tiles + smooth panel content. */
public class OrchardScreen extends Screen {
    private static final int RAIL_W = 76;
    private static final int TILE = 56;
    private static final int GAP = 10;

    private int railX, railY, railH;
    private float anim;          // 0..1 open animation
    private Section selected = Sections.ALL.get(6); // visuals default
    private final float[] hover = new float[Sections.ALL.size()];

    public OrchardScreen() {
        super(Text.literal("Orchard"));
    }

    @Override
    protected void init() {
        int h = this.height;
        railH = Math.min(h - 40, Sections.ALL.size() * (TILE + GAP) + GAP);
        railY = (h - railH) / 2;
        railX = 14;
        anim = 0f;
    }

    @Override
    public void tick() {
        anim = Math.min(1f, anim + 0.08f); // smooth open
        for (int i = 0; i < hover.length; i++) {
            boolean hov = isOverTile(i);
            hover[i] += ((hov ? 1f : 0f) - hover[i]) * 0.25f;
        }
    }

    private boolean isOverTile(int i) {
        double mx = client.mouse.getX() * client.getWindow().getScaledWidth() / client.getWindow().getWidth();
        double my = client.mouse.getY() * client.getWindow().getScaledHeight() / client.getWindow().getHeight();
        int y = tileY(i);
        return mx >= railX && mx <= railX + RAIL_W && my >= y && my <= y + TILE;
    }

    private int tileY(int i) {
        return railY + GAP + i * (TILE + GAP);
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // soft dim backdrop (subtle, not pitch black)
        ctx.fill(0, 0, width, height, 0x66080614);

        float slide = (1f - anim) * -30f;
        int x = (int)(railX + slide);

        // glass rail panel
        ctx.fill(x, railY, x + RAIL_W, railY + railH, OrchardTheme.GLASS);
        ctx.drawBorder(x, railY, RAIL_W, railH, OrchardTheme.LAVENDER);

        for (int i = 0; i < Sections.ALL.size(); i++) {
            Section s = Sections.ALL.get(i);
            int y = tileY(i);
            boolean sel = s.id.equals(selected.id);

            int base = sel ? OrchardTheme.VIOLET : OrchardTheme.GLASS;
            ctx.fill(x + 10, y, x + RAIL_W - 10, y + TILE, lerpAlpha(base, hover[i] * (sel ? 0f : 0.5f)));
            if (sel) ctx.drawBorder(x + 10, y, RAIL_W - 20, TILE, OrchardTheme.ACCENT);

            ctx.drawCenteredTextWithShadow(textRenderer, s.iconGlyph, x + RAIL_W / 2, y + 12, OrchardTheme.TEXT);
            ctx.drawCenteredTextWithShadow(textRenderer, s.label, x + RAIL_W / 2, y + TILE - 16,
                sel ? OrchardTheme.TEXT : OrchardTheme.TEXT_DIM);
        }

        // content panel
        int px = x + RAIL_W + 14;
        int pw = Math.min(320, width - px - 14);
        ctx.fill(px, railY, px + pw, railY + railH, OrchardTheme.GLASS);
        ctx.drawBorder(px, railY, pw, railH, OrchardTheme.LAVENDER);
        ctx.drawCenteredTextWithShadow(textRenderer, selected.label.toUpperCase(), px + pw / 2, railY + 14, OrchardTheme.ACCENT);
        renderPanel(ctx, px, pw);
    }

    private void renderPanel(DrawContext ctx, int px, int pw) {
        int cy = railY + 40;
        switch (selected.id) {
            case "visuals" -> {
                String[] mods = {"Fullbright", "Custom Sky", "Block Outlines", "Item Physics", "Particles"};
                for (String m : mods) {
                    ctx.drawText(textRenderer, "\u25CB  " + m, px + 16, cy, OrchardTheme.TEXT, true);
                    cy += 22;
                }
            }
            case "cosmetics" -> {
                ctx.drawText(textRenderer, "Browse & edit cosmetics", px + 16, cy, OrchardTheme.TEXT_DIM, true);
            }
            case "settings" -> {
                ctx.drawText(textRenderer, "Accent \u2022 Blur \u2022 Scale \u2022 Keybinds", px + 16, cy, OrchardTheme.TEXT_DIM, true);
            }
            case "player" -> {
                ctx.drawText(textRenderer, "Player preview coming here", px + 16, cy, OrchardTheme.TEXT_DIM, true);
            }
            default -> {
                ctx.drawText(textRenderer, "Modules coming soon", px + 16, cy, OrchardTheme.TEXT_DIM, true);
            }
        }
    }

    private static int lerpAlpha(int color, float extra) {
        int a = (color >>> 24);
        a = Math.min(255, a + (int)(extra * 80));
        return (a << 24) | (color & 0xFFFFFF);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            for (int i = 0; i < Sections.ALL.size(); i++) {
                int y = tileY(i);
                if (mx >= railX && mx <= railX + RAIL_W && my >= y && my <= y + TILE) {
                    selected = Sections.ALL.get(i);
                    return true;
                }
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean shouldPause() { return false; }

    @Override
    public void close() { client.setScreen(null); }
}
