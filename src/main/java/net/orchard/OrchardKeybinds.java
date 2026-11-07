package net.orchard;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class OrchardKeybinds {
    public static final KeyBinding OPEN_GUI = KeyBindingHelper.registerKeyBinding(
        new KeyBinding("key.orchard.opengui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "Orchard")
    );
}
