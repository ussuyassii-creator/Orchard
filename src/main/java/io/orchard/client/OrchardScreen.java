package io.orchard.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;

public final class OrchardScreen extends Screen {
    private static final List<Section> SECTIONS = List.of(
            new Section("⚔", "Sword"), new Section("✦", "Mace"), new Section("▣", "Cart"),
            new Section("♥", "UHC"), new Section("➶", "Spear"), new Section("◇", "Crystal"),
            new Section("◉", "Visuals"), new Section("◌", "Silent"), new Section("◈", "Cosmetics"),
            new Section("⚙", "Settings"), new Section("♙", "Player Model")
    );

    private final Screen parent;
    private int selected;
    private long openedAt;

    public OrchardScreen(Screen parent) { super(Component.literal("Orchard")); this.parent = parent; }
    @Override protected void init() { openedAt = System.currentTimeMillis(); }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        int width = this.width, height = this.height;
        float progress = Math.min(1.0f, (System.currentTimeMillis() - openedAt) / 180.0f);
        int panelWidth = Math.min(920, width - 48), panelHeight = Math.min(560, height - 48);
        int left = (width - panelWidth) / 2, top = (height - panelHeight) / 2, sidebar = Math.min(190, panelWidth / 4);
        graphics.fill(0, 0, width, height, OrchardConfig.background);
        drawPattern(graphics, width, height);
        graphics.fill(left, top, left + panelWidth, top + panelHeight, OrchardConfig.panel);
        graphics.fill(left, top, left + sidebar, top + panelHeight, 0xE5171320);
        graphics.fill(left + sidebar, top, left + sidebar + 1, top + panelHeight, OrchardConfig.accent);
        graphics.drawString(font, Component.literal("ORCHARD"), left + 24, top + 24, OrchardConfig.text, false);
        graphics.drawString(font, Component.literal("PREMIUM CLIENT / LIGHT PURPLE"), left + 24, top + 41, OrchardConfig.muted, false);
        int itemTop = top + 72;
        for (int i = 0; i < SECTIONS.size(); i++) {
            int y = itemTop + i * 35;
            boolean active = i == selected;
            if (active) graphics.fill(left + 10, y - 5, left + sidebar - 10, y + 24, OrchardConfig.accent);
            graphics.drawString(font, Component.literal(SECTIONS.get(i).icon), left + 22, y + 2, active ? 0xFF241B2B : OrchardConfig.secondary, false);
            graphics.drawString(font, Component.literal(SECTIONS.get(i).name), left + 48, y + 2, active ? 0xFF241B2B : OrchardConfig.text, false);
        }
        int contentLeft = left + sidebar + 30, contentTop = top + 28;
        graphics.drawString(font, Component.literal(SECTIONS.get(selected).name), contentLeft, contentTop, OrchardConfig.text, false);
        graphics.drawString(font, Component.literal("ORCHARD / " + SECTIONS.get(selected).name.toUpperCase()), contentLeft, contentTop + 22, OrchardConfig.muted, false);
        int cardTop = contentTop + 58;
        graphics.fill(contentLeft, cardTop, left + panelWidth - 28, cardTop + 92, 0xA62E2540);
        graphics.drawString(font, Component.literal("This section is ready for your modules."), contentLeft + 18, cardTop + 24, OrchardConfig.text, false);
        graphics.drawString(font, Component.literal("The Orchard framework is online."), contentLeft + 18, cardTop + 45, OrchardConfig.muted, false);
        if (selected == 8) { Minecraft.getInstance().setScreen(new OrchardCosmeticsScreen(this)); return; }
        if (selected == 9) renderSettings(graphics, contentLeft, cardTop + 122, left + panelWidth - 28);
        else if (selected == 10) renderPlayerPreview(graphics, contentLeft, cardTop + 122);
        else renderEmptyState(graphics, contentLeft, cardTop + 122);
        if (progress < 1.0f) graphics.fill(0, 0, width, height, (int) ((1.0f - progress) * 70) << 24);
        super.render(graphics, mouseX, mouseY, delta);
    }

    private void renderSettings(GuiGraphics g, int x, int y, int right) {
        g.drawString(font, Component.literal("Theme controls"), x, y, OrchardConfig.text, false);
        drawSetting(g, "Accent color", "Light Purple / #D9B8FF", x, y + 28, right);
        drawSetting(g, "Background pattern", OrchardConfig.pattern, x, y + 56, right);
        drawSetting(g, "Blur strength", String.format("%.2f", OrchardConfig.blur), x, y + 84, right);
        drawSetting(g, "UI scale", String.format("%.2f", OrchardConfig.scale), x, y + 112, right);
        g.drawString(font, Component.literal("Theme is persisted in orchard.json."), x, y + 154, OrchardConfig.muted, false);
    }
    private void drawSetting(GuiGraphics g, String label, String value, int x, int y, int right) {
        g.fill(x, y - 6, right, y + 18, 0x66392D4A);
        g.drawString(font, Component.literal(label), x + 12, y + 1, OrchardConfig.text, false);
        g.drawString(font, Component.literal(value), right - font.width(value) - 12, y + 1, OrchardConfig.accent, false);
    }
    private void renderPlayerPreview(GuiGraphics g, int x, int y) {
        g.drawString(font, Component.literal("Player Model"), x, y, OrchardConfig.text, false);
        g.fill(x + 40, y + 30, x + 120, y + 110, 0xFF514165);
        g.fill(x + 58, y + 12, x + 102, y + 56, 0xFFD8B9DD);
        g.drawString(font, Component.literal("Preview foundation"), x + 145, y + 62, OrchardConfig.muted, false);
    }
    private void renderEmptyState(GuiGraphics g, int x, int y) {
        g.drawString(font, Component.literal("No modules installed"), x, y, OrchardConfig.text, false);
        g.drawString(font, Component.literal("Tell me what Orchard should grow next."), x, y + 24, OrchardConfig.muted, false);
    }
    private void drawPattern(GuiGraphics g, int width, int height) {
        for (int x = 0; x < width; x += 32) g.fill(x, 0, x + 1, height, 0x081FFFFF);
        for (int y = 0; y < height; y += 32) g.fill(0, y, width, y + 1, 0x081FFFFF);
    }
    @Override public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelWidth = Math.min(920, width - 48), panelHeight = Math.min(560, height - 48);
        int left = (width - panelWidth) / 2, top = (height - panelHeight) / 2, sidebar = Math.min(190, panelWidth / 4);
        int itemTop = top + 72;
        if (mouseX >= left + 10 && mouseX <= left + sidebar - 10) {
            for (int i = 0; i < SECTIONS.size(); i++) { int y = itemTop + i * 35; if (mouseY >= y - 5 && mouseY <= y + 24) { selected = i; return true; } }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override public boolean keyPressed(int keyCode, int scanCode, int modifiers) { if (keyCode == 256) { Minecraft.getInstance().setScreen(parent); return true; } return super.keyPressed(keyCode, scanCode, modifiers); }
    @Override public void onClose() { Minecraft.getInstance().setScreen(parent); }
    private record Section(String icon, String name) { }
}
