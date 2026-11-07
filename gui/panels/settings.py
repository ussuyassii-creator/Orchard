"""Settings section."""

import tkinter as tk
from gui.theme import BG, ACCENT, TEXT, TEXT_DIM, FONT_FAMILY, FONT_SIZE


def build(parent):
    tk.Label(
        parent, text="SETTINGS", bg=BG, fg=ACCENT,
        font=(FONT_FAMILY, 14, "bold"), anchor="w",
    ).pack(fill="x", padx=24, pady=(20, 10))

    for label in ("Accent color", "Font scale", "GUI opacity"):
        row = tk.Frame(parent, bg=BG)
        row.pack(fill="x", padx=24, pady=6)
        tk.Label(row, text=label, bg=BG, fg=TEXT,
                 font=(FONT_FAMILY, FONT_SIZE)).pack(side="left")
        tk.Scale(row, from_=0, to=100, orient="horizontal",
                 bg=BG, fg=TEXT_DIM, highlightthickness=0,
                 length=200).pack(side="right")