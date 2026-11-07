package io.orchard.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class OrchardClient implements ClientModInitializer {
    public static final String MOD_ID = "orchard";
    private static KeyMapping openMenu;

    @Override
    public void onInitializeClient() {
        OrchardConfig.load();
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.orchard.open_menu", GLFW.GLFW_KEY_RIGHT_SHIFT, "category.orchard"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.consumeClick()) {
                if (client.screen == null) client.setScreen(new OrchardScreen(null));
            }
        });
    }
}
