# Orchard

A premium Minecraft Fabric client with a modern, rich, smooth interface.
Light purple / deep violet palette, glass-like surfaces, soft shadows, smooth animations.

## Build

```bash
./gradlew build
```

Output jar: `build/libs/orchard-0.1.0.jar` (drop into your Fabric `mods/` folder, Minecraft 1.21.1).

## Open the GUI

Press **Right Shift** in game (keybind `key.orchard.clickgui`).

## Sections

| Section    | Icon style | Status |
|------------|-----------|--------|
| Sword      | Sword icon | Placeholder |
| Mace       | Mace icon | Placeholder |
| Cart       | Minecart icon | Placeholder |
| UHC        | Heart / golden apple icon | Placeholder |
| Spear      | Trident icon | Placeholder |
| Crystal    | End crystal icon | Placeholder |
| Visuals    | Eye / glowing display icon | Populated (visual modules) |
| Silent     | Ghost / hidden-eye icon | Populated (starter) |
| Cosmetics  | Star / sparkle / crown icon | Populated (starter) |
| Settings   | Gear icon | Populated |
| Player Model | Player silhouette icon | Placeholder |

## Design rules

- No plain gray rectangles, no vanilla buttons, no 2011 vibe
- Rounded glass panels with soft shadows and subtle glow
- Animated hover/active states, touch-friendly hit targets (PojavLauncher ready)
- Procedural icons — no external assets needed
