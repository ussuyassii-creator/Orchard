package io.orchard.client.cosmetics;

public final class CosmeticPreview {
    private boolean trialMode = true;
    private float scale = 1.0f;
    private float rotation;
    private float speed = 1.0f;
    private int color = 0xFFD9B8FF;
    private int quality = 2;

    public boolean trialMode() { return trialMode; }
    public void trialMode(boolean value) { trialMode = value; }
    public float scale() { return scale; }
    public void scale(float value) { scale = clamp(value, 0.25f, 3.0f); }
    public float rotation() { return rotation; }
    public void rotation(float value) { rotation = value % 360.0f; }
    public float speed() { return speed; }
    public void speed(float value) { speed = clamp(value, 0.1f, 4.0f); }
    public int color() { return color; }
    public void color(int value) { color = value; }
    public int quality() { return quality; }
    public void quality(int value) { quality = Math.max(0, Math.min(3, value)); }
    private static float clamp(float value, float min, float max) { return Math.max(min, Math.min(max, value)); }
}
