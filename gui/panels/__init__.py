"""Panel loader: visuals/client/settings are populated; others are blank."""

import importlib

POPULATED = ("visuals", "client", "settings")


def load_panel(parent, name: str):
    if name in POPULATED:
        module = importlib.import_module(f"gui.panels.{name}")
        module.build(parent)
    else:
        _placeholder(parent, name)


def _placeholder(parent, name: str):
    import tkinter as tk
    from gui.theme import BG, TEXT_DIM, FONT_FAMILY, FONT_SIZE
    tk.Label(
        parent, text=f"{name.upper()} — coming soon",
        bg=BG, fg=TEXT_DIM, font=(FONT_FAMILY, FONT_SIZE),
    ).pack(expand=True)