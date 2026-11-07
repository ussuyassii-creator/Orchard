package io.orchard.client.cosmetics;

import net.minecraft.client.gui.GuiGraphics;

public interface CosmeticRenderer {
    void renderPreview(GuiGraphics graphics, int left, int top, int right, int bottom, CosmeticRegistry.CosmeticDefinition definition, CosmeticPreview preview);
}
