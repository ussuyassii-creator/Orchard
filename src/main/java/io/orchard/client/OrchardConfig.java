package io.orchard.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class OrchardConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("orchard.json");

    public static int accent = 0xFFB7E4C7;
    public static int background = 0xFF101416;
    public static int panel = 0xE61B2225;
    public static int text = 0xFFF3F7F4;
    public static float blur = 0.45f;
    public static float scale = 1.0f;
    public static String pattern = "orchard";

    private OrchardConfig() { }

    public static void load() {
        if (!Files.exists(FILE)) return;
        try (Reader reader = Files.newBufferedReader(FILE)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json == null) return;
            accent = json.has("accent") ? json.get("accent").getAsInt() : accent;
            background = json.has("background") ? json.get("background").getAsInt() : background;
            panel = json.has("panel") ? json.get("panel").getAsInt() : panel;
            text = json.has("text") ? json.get("text").getAsInt() : text;
            blur = json.has("blur") ? json.get("blur").getAsFloat() : blur;
            scale = json.has("scale") ? json.get("scale").getAsFloat() : scale;
            pattern = json.has("pattern") ? json.get("pattern").getAsString() : pattern;
        } catch (Exception ignored) { }
    }

    public static void save() {
        JsonObject json = new JsonObject();
        json.addProperty("accent", accent);
        json.addProperty("background", background);
        json.addProperty("panel", panel);
        json.addProperty("text", text);
        json.addProperty("blur", blur);
        json.addProperty("scale", scale);
        json.addProperty("pattern", pattern);
        try {
            Files.createDirectories(FILE.getParent());
            try (Writer writer = Files.newBufferedWriter(FILE)) { GSON.toJson(json, writer); }
        } catch (Exception ignored) { }
    }
}
