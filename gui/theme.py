"""Orchard premium theme — dark base, custom accent palette."""

# Custom color combos (edit freely)
BG = "#0f1117"          # main background
BG_ALT = "#161a23"      # panel background
ACCENT = "#7c5cff"      # primary accent (premium violet)
ACCENT2 = "#3ddc97"     # secondary accent (mint)
TEXT = "#e8eaf2"
TEXT_DIM = "#8b90a3"
BORDER = "#232838"

FONT_FAMILY = "Segoe UI"
FONT_SIZE = 11

PALETTE = {
    "visuals":  ACCENT,
    "client":   ACCENT2,
    "settings": "#f5a742",
    "sword":    TEXT_DIM,
    "mace":     TEXT_DIM,
    "crystal":  TEXT_DIM,
    "cart":     TEXT_DIM,
    "spear":    TEXT_DIM,
    "uhc":      TEXT_DIM,
}


def style_panel(widget_bg=BG_ALT, border=BORDER):
    return {
        "bg": widget_bg,
        "highlightthickness": 1,
        "highlightbackground": border,
    }