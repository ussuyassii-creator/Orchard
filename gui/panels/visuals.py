"""Visuals section — visual modules."""

import tkinter as tk
from gui.theme import BG, ACCENT, TEXT, FONT_FAMILY, FONT_SIZE

MODULES = [
    "Fullbright",
    "Custom Sky",
    "Block Outlines",
    "Item Physics",
    "Particles",
]


def build(parent):
    tk.Label(
        parent, text="VISUALS", bg=BG, fg=ACCENT,
        font=(FONT_FAMILY, 14, "bold"), anchor="w",
    ).pack(fill="x", padx=24, pady=(20, 10))
    for name in MODULES:
        row = tk.Frame(parent, bg=BG)
        row.pack(fill="x", padx=24, pady=4)
        tk.Label(row, text=name, bg=BG, fg=TEXT,
                 font=(FONT_FAMILY, FONT_SIZE)).pack(side="left")
        var = tk.BooleanVar(value=False)
        tk.Checkbutton(row, variable=var, bg=BG, activebackground=BG,
                       highlightthickness=0).pack(side="right")