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
            new Section("◉", "Visuals"), new Section("◌", "Silent"), new Section("⚙", "Settings"),
            new Section("♙", "Player Model")
    );

    private final Screen parent;
    private int selected = 0;
    private long openedAt;

    public OrchardScreen(Screen parent) {
        super(Component.literal("Orchard"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        openedAt = System.currentTimeMillis();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        int width = this.width;
        int height = this.height;
        float progress = Math.min(1.0f, (System.currentTimeMillis() - openedAt) / 180.0f);
        int panelWidth = Math.min(920, width - 48);
        int panelHeight = Math.min(560, height - 48);
        int left = (width - panelWidth) / 2;
        int top = (height - panelHeight) / 2;
        int sidebar = Math.min(190, panelWidth / 4);

        graphics.fill(0, 0, width, height, OrchardConfig.background);
        drawPattern(graphics, width, height);
        graphics.fill(left, top, left + panelWidth, top + panelHeight, OrchardConfig.panel);
        graphics.fill(left, top, left + sidebar, top + panelHeight, 0xCC111719);
        graphics.fill(left + sidebar, top, left + sidebar + 1, top + panelHeight, OrchardConfig.accent);

        graphics.drawString(this.font, Component.literal("ORCHARD"), left + 24, top + 24, OrchardConfig.text, false);
        graphics.drawString(this.font, Component.literal("legacy starts here"), left + 24, top + 40, 0xFF8FA59A, false);

        int itemTop = top + 72;
        for (int i = 0; i < SECTIONS.size(); i++) {
            int y = itemTop + i * 35;
            boolean active = i == selected;
            if (active) graphics.fill(left + 10, y - 5, left + sidebar - 10, y + 24, OrchardConfig.accent);
            int iconColor = active ? 0xFF15201A : 0xFF9CB8A8;
            int textColor = active ? 0xFF15201A : OrchardConfig.text;
            graphics.drawString(this.font, Component.literal(SECTIONS.get(i).icon), left + 22, y + 2, iconColor, false);
            graphics.drawString(this.font, Component.literal(SECTIONS.get(i).name), left + 48, y + 2, textColor, false);
        }

        int contentLeft = left + sidebar + 30;
        int contentTop = top + 28;
        graphics.drawString(this.font, Component.literal(SECTIONS.get(selected).name), contentLeft, contentTop, OrchardConfig.text, false);
        graphics.drawString(this.font, Component.literal("ORCHARD / " + SECTIONS.get(selected).name.toUpperCase()), contentLeft, contentTop + 22, 0xFF8FA59A, false);

        int cardTop = contentTop + 58;
        graphics.fill(contentLeft, cardTop, left + panelWidth - 28, cardTop + 92, 0xAA202A2B);
        graphics.drawString(this.font, Component.literal("This section is ready for your modules."), contentLeft + 18, cardTop + 24, OrchardConfig.text, false);
        graphics.drawString(this.font, Component.literal("The Orchard framework is online."), contentLeft + 18, cardTop + 45, 0xFF9CB8A8, false);

        if (selected == 8) renderSettings(graphics, contentLeft, cardTop + 122, left + panelWidth - 28);
        else if (selected == 9) renderPlayerPreview(graphics, contentLeft, cardTop + 122);
        else renderEmptyState(graphics, contentLeft, cardTop + 122);

        if (progress < 1.0f) graphics.fill(0, 0, width, height, ((int) ((1.0f - progress) * 70) << 24));
        super.render(graphics, mouseX, mouseY, delta);
    }

    private void renderSettings(GuiGraphics graphics, int x, int y, int right) {
        graphics.drawString(this.font, Component.literal("Theme controls"), x, y, OrchardConfig.text, false);
        drawSetting(graphics, "Accent color", "Mint / #B7E4C7", x, y + 28, right);
        drawSetting(graphics, "Background pattern", OrchardConfig.pattern, x, y + 56, right);
        drawSetting(graphics, "Blur strength", String.format("%.2f", OrchardConfig.blur), x, y + 84, right);
        drawSetting(graphics, "UI scale", String.format("%.2f", OrchardConfig.scale), x, y + 112, right);
        graphics.drawString(this.font, Component.literal("Click controls will become live in the next pass."), x, y + 154, 0xFF8FA59A, false);
    }

    private void drawSetting(GuiGraphics graphics, String label, String value, int x, int y, int right) {
        graphics.fill(x, y - 6, right, y + 18, 0x662B3736);
        graphics.drawString(this.font, Component.literal(label), x + 12, y + 1, OrchardConfig.text, false);
        graphics.drawString(this.font, Component.literal(value), right - this.font.width(value) - 12, y + 1, OrchardConfig.accent, false);
    }

    private void renderPlayerPreview(GuiGraphics graphics, int x, int y) {
        graphics.drawString(this.font, Component.literal("Player Model"), x, y, OrchardConfig.text, false);
        graphics.fill(x + 40, y + 30, x + 120, y + 110, 0xFF31413C);
        graphics.fill(x + 58, y + 12, x + 102, y + 56, 0xFF8FB3A1);
        graphics.drawString(this.font, Component.literal("Preview foundation"), x + 145, y + 62, 0xFF9CB8A8, false);
    }

    private void renderEmptyState(GuiGraphics graphics, int x, int y) {
        graphics.drawString(this.font, Component.literal("No modules installed"), x, y, OrchardConfig.text, false);
        graphics.drawString(this.font, Component.literal("Tell me what Orchard should grow next."), x, y + 24, 0xFF8FA59A, false);
    }

    private void drawPattern(GuiGraphics graphics, int width, int height) {
        for (int x = 0; x < width; x += 32) graphics.fill(x, 0, x + 1, height, 0x081FFFFFF);
        for (int y = 0; y < height; y += 32) graphics.fill(0, y, width, y + 1, 0x081FFFFFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int panelWidth = Math.min(920, width - 48);
        int panelHeight = Math.min(560, height - 48);
        int left = (width - panelWidth) / 2;
        int top = (height - panelHeight) / 2;
        int sidebar = Math.min(190, panelWidth / 4);
        int itemTop = top + 72;
        if (mouseX >= left + 10 && mouseX <= left + sidebar - 10) {
            for (int i = 0; i < SECTIONS.size(); i++) {
                int y = itemTop + i * 35;
                if (mouseY >= y - 5 && mouseY <= y + 24) { selected = i; return true; }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { Minecraft.getInstance().setScreen(parent); return true; }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() { Minecraft.getInstance().setScreen(parent); }

    private record Section(String icon, String name) { }
}
