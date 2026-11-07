package dev.orchard.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Orchard ClickGUI: dark violet glass backdrop, icon rail on the left,
 * smooth animated transitions, touch-friendly hit targets (Pojav-ready).
 */
public class OrchardClickGUIScreen extends Screen {

    private static final int RAIL_WIDTH = 76;
    private static final int ICON_SPACING = 8;
    private static final int ICON_SIZE = 52;
    private static final int PANEL_RADIUS = 14;
    private static final float HOVER_SPEED = 0.18f;

    private final List<SectionButton> sections = new ArrayList<>();
    private SectionIcon active = SectionIcon.VISUALS;

    public OrchardClickGUIScreen() {
        super(Text.literal("Orchard"));
        for (SectionIcon icon : SectionIcon.values()) {
            sections.add(new SectionButton(icon));
        }
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        // dim backdrop
        ctx.fill(0, 0, this.width, this.height, OrchardTheme.withAlpha(0x0A0716, 130));

        int railX = 12;
        int railH = sections.size() * (ICON_SIZE + ICON_SPACING) - ICON_SPACING + 24;
        int railY = (this.height - railH) / 2;

        OrchardRenderer.drawRoundedPanel(ctx, railX, railY, RAIL_WIDTH, railH, PANEL_RADIUS, OrchardTheme.GLASS_BG);
        OrchardRenderer.drawGlassStroke(ctx, railX, railY, RAIL_WIDTH, railH, PANEL_RADIUS, OrchardTheme.GLASS_STROKE);

        for (SectionButton b : sections) {
            b.update(mouseX, mouseY, delta);
            b.render(ctx, railX + (RAIL_WIDTH - ICON_SIZE) / 2, railY + 12 + b.index * (ICON_SIZE + ICON_SPACING));
        }

        // content panel
        int panelX = railX + RAIL_WIDTH + 12;
        int panelW = Math.min(420, this.width - panelX - 12);
        int panelH = Math.min(360, this.height - 24);
        int panelY = (this.height - panelH) / 2;

        OrchardRenderer.drawRoundedPanel(ctx, panelX, panelY, panelW, panelH, PANEL_RADIUS, OrchardTheme.GLASS_BG);
        OrchardRenderer.drawGlassStroke(ctx, panelX, panelY, panelW, panelH, PANEL_RADIUS, OrchardTheme.GLASS_STROKE);

        var text = MinecraftClient.getInstance().textRenderer;
        ctx.drawText(text, active.label.toUpperCase(), panelX + 16, panelY + 14, OrchardTheme.TEXT_PRIMARY, true);
        ctx.drawText(text, "Modules coming soon", panelX + 16, panelY + 32, OrchardTheme.TEXT_DIM, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (SectionButton b : sections) {
            if (b.contains(mouseX, mouseY)) {
                active = b.icon;
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() { return false; }

    /** One rail button with animated hover/active glow. */
    private class SectionButton {
        final SectionIcon icon;
        final int index;
        float hover; // 0..1 animated

        SectionButton(SectionIcon icon) {
            this.icon = icon;
            this.index = sections.size();
        }

        int x, y;

        void update(double mx, double my, float delta) {
            float target = contains(mx, my) || icon == active ? 1f : 0f;
            hover += (target - hover) * HOVER_SPEED;
        }

        boolean contains(double mx, double my) {
            return mx >= x && mx <= x + ICON_SIZE && my >= y && my <= y + ICON_SIZE;
        }

        void render(DrawContext ctx, int bx, int by) {
            x = bx; y = by;
            // active/hover glow behind icon
            if (hover > 0.02f) {
                int glow = OrchardTheme.lerp(OrchardTheme.withAlpha(OrchardTheme.VIOLET, 0),
                        OrchardTheme.ACCENT_GLOW, hover);
                int pad = Math.round(6 * hover);
                OrchardRenderer.drawGlow(ctx, x - pad, y - pad, ICON_SIZE + pad * 2, ICON_SIZE + pad * 2, glow);
            }
            int bg = OrchardTheme.lerp(OrchardTheme.GLASS_BG, OrchardTheme.VIOLET, hover * 0.85f);
            OrchardRenderer.fillRoundedPublic(ctx, x, y, ICON_SIZE, ICON_SIZE, 12, bg);
            var text = MinecraftClient.getInstance().textRenderer;
            int labelW = text.getWidth(icon.label);
            ctx.drawText(text, icon.label, x + (ICON_SIZE - labelW) / 2, y + ICON_SIZE / 2 - 4,
                    OrchardTheme.lerp(OrchardTheme.TEXT_DIM, OrchardTheme.TEXT_PRIMARY, hover), false);
        }
    }
}
