package dev.orchard;

import dev.orchard.gui.OrchardClickGUIScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class OrchardClient implements ClientModInitializer {
    public static final String MOD_ID = "orchard";
    private static KeyBinding openClickGUI;

    @Override
    public void onInitializeClient() {
        openClickGUI = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.orchard.clickgui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.orchard"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openClickGUI.wasPressed()) {
                client.setScreen(new OrchardClickGUIScreen());
            }
        });
    }
}
