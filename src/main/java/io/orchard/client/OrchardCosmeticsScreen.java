package io.orchard.client;

import io.orchard.client.cosmetics.CosmeticRegistry;
import io.orchard.client.cosmetics.CosmeticState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class OrchardCosmeticsScreen extends Screen {
    private static final int BG = 0xFF120F1A;
    private static final int PANEL = 0xF0221D2E;
    private static final int PANEL_ALT = 0xFF2D253D;
    private static final int ACCENT = 0xFFD9B8FF;
    private static final int ACCENT_2 = 0xFF9D7CFF;
    private static final int TEXT = 0xFFF7F1FF;
    private static final int MUTED = 0xFFB9A9C8;

    private final Screen parent;
    private int selected;
    private long openedAt;

    public OrchardCosmeticsScreen(Screen parent) {
        super(Component.literal("Orchard Cosmetics"));
        this.parent = parent;
    }

    @Override protected void init() { openedAt = System.currentTimeMillis(); }

    @Override public void render(GuiGraphics g, int mouseX, int mouseY, float delta) {
        int w = width, h = height;
        int shellW = Math.min(1120, w - 36), shellH = Math.min(650, h - 30);
        int left = (w - shellW) / 2, top = (h - shellH) / 2;
        int rail = 220, right = 280;
        g.fill(0, 0, w, h, BG);
        for (int x = 0; x < w; x += 28) g.fill(x, 0, x + 1, h, 0x101F1730);
        g.fill(left, top, left + shellW, top + shellH, PANEL);
        g.fill(left, top, left + rail, top + shellH, 0xEF181420);
        g.fill(left + shellW - right, top, left + shellW, top + shellH, 0xD91A1624);
        g.fill(left + rail, top, left + rail + 1, top + shellH, ACCENT_2);
        g.fill(left + shellW - right - 1, top, left + shellW - right, top + shellH, 0xFF4B3A65);

        g.drawString(font, Component.literal("ORCHARD"), left + 24, top + 24, TEXT, false);
        g.drawString(font, Component.literal("COSMETICS STUDIO"), left + 24, top + 43, ACCENT, false);
        g.drawString(font, Component.literal("TRIAL MODE"), left + shellW - right + 24, top + 24, ACCENT, false);
        g.drawString(font, Component.literal("Preview before you equip"), left + shellW - right + 24, top + 43, MUTED, false);

        var cosmetics = CosmeticRegistry.all();
        int listTop = top + 78;
        for (int i = 0; i < cosmetics.size(); i++) {
            int y = listTop + i * 34;
            boolean active = i == selected;
            if (active) g.fill(left + 12, y - 5, left + rail - 12, y + 23, ACCENT);
            int color = active ? 0xFF21172B : TEXT;
            g.drawString(font, Component.literal(cosmetics.get(i).badge()), left + 24, y + 1, active ? 0xFF4B3266 : ACCENT_2, false);
            g.drawString(font, Component.literal(cosmetics.get(i).name()), left + 78, y + 1, color, false);
        }

        int cx = left + rail + 28, cy = top + 80;
        g.drawString(font, Component.literal("PLAYER PREVIEW"), cx, top + 27, TEXT, false);
        g.drawString(font, Component.literal("Drag to rotate  •  Scroll to zoom"), cx, top + 47, MUTED, false);
        int previewRight = left + shellW - right - 28;
        g.fill(cx, cy, previewRight, top + shellH - 28, 0x662E2640);
        g.fill((cx + previewRight) / 2 - 46, cy + 116, (cx + previewRight) / 2 + 46, cy + 276, 0xFF6C557E);
        g.fill((cx + previewRight) / 2 - 28, cy + 72, (cx + previewRight) / 2 + 28, cy + 128, 0xFFD4B7DB);
        g.fill((cx + previewRight) / 2 - 63, cy + 172, (cx + previewRight) / 2 - 45, cy + 262, 0xFF4B3A65);
        g.fill((cx + previewRight) / 2 + 45, cy + 172, (cx + previewRight) / 2 + 63, cy + 262, 0xFF4B3A65);
        g.drawString(font, Component.literal("ORCHARD AVATAR"), cx + 18, top + shellH - 54, ACCENT, false);

        var item = cosmetics.get(selected);
        int infoX = left + shellW - right + 24, infoY = top + 84;
        g.drawString(font, Component.literal(item.name()), infoX, infoY, TEXT, false);
        g.drawString(font, Component.literal(item.category().toUpperCase()), infoX, infoY + 22, ACCENT_2, false);
        g.drawString(font, Component.literal(item.description()), infoX, infoY + 52, MUTED, false);
        g.drawString(font, Component.literal("LIVE TRIAL"), infoX, infoY + 94, ACCENT, false);
        g.fill(infoX, infoY + 112, left + shellW - 24, infoY + 150, PANEL_ALT);
        g.drawString(font, Component.literal(CosmeticState.previewing() != null && CosmeticState.previewing().equals(item.id()) ? "PREVIEWING" : "PREVIEW"), infoX + 16, infoY + 126, TEXT, false);
        g.fill(infoX, infoY + 162, left + shellW - 24, infoY + 200, ACCENT_2);
        String action = CosmeticState.isEquipped(item.id()) ? "UNEQUIP" : "EQUIP COSMETIC";
        g.drawString(font, Component.literal(action), infoX + 16, infoY + 176, 0xFF20152B, false);
        g.drawString(font, Component.literal("EQUIPPED SLOTS"), infoX, infoY + 236, TEXT, false);
        g.drawString(font, Component.literal("Head  •  Back  •  Aura  •  Companion"), infoX, infoY + 260, MUTED, false);
        g.drawString(font, Component.literal("New categories can be added anytime."), infoX, top + shellH - 52, MUTED, false);
        g.drawString(font, Component.literal("LEGACY STARTS HERE  /  COSMETICS 01"), left + 24, top + shellH - 18, ACCENT, false);

        if ((System.currentTimeMillis() - openedAt) < 180) g.fill(0, 0, w, h, 0x33120F1A);
    }

    @Override public boolean mouseClicked(double mx, double my, int button) {
        int shellW = Math.min(1120, width - 36), shellH = Math.min(650, height - 30);
        int left = (width - shellW) / 2, top = (height - shellH) / 2, rail = 220, right = 280;
        int listTop = top + 78;
        if (mx >= left + 12 && mx <= left + rail - 12) {
            int index = (int) ((my - listTop + 5) / 34);
            if (index >= 0 && index < CosmeticRegistry.all().size()) { selected = index; CosmeticState.preview(CosmeticRegistry.all().get(index).id()); return true; }
        }
        int infoX = left + shellW - right + 24, infoY = top + 84;
        if (mx >= infoX && mx <= left + shellW - 24 && my >= infoY + 104 && my <= infoY + 154) { CosmeticState.preview(CosmeticRegistry.all().get(selected).id()); return true; }
        if (mx >= infoX && mx <= left + shellW - 24 && my >= infoY + 158 && my <= infoY + 208) {
            String id = CosmeticRegistry.all().get(selected).id();
            if (CosmeticState.isEquipped(id)) CosmeticState.unequip(id); else CosmeticState.equip(id);
            return true;
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers) { if (keyCode == 256) { Minecraft.getInstance().setScreen(parent); return true; } return super.keyPressed(keyCode, scanCode, modifiers); }
    @Override public void onClose() { Minecraft.getInstance().setScreen(parent); }
}
