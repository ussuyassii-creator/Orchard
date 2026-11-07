package net.orchard;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public class OrchardClient implements ClientModInitializer {
    private static boolean guiOpen = false;
    private static OrchardScreen screen;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OrchardKeybinds.OPEN_GUI.wasPressed()) {
                client.setScreen(getScreen());
            }
        });
    }

    public static OrchardScreen getScreen() {
        if (screen == null) screen = new OrchardScreen();
        return screen;
    }
}
