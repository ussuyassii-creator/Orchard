"""Orchard — premium GUI entry point."""

import tkinter as tk
from gui.theme import BG, BG_ALT, ACCENT, TEXT, TEXT_DIM, FONT_FAMILY, FONT_SIZE

SECTIONS = [
    "sword", "mace", "crystal", "cart", "spear", "uhc",
    "visuals", "client", "settings",
]


class OrchardGUI(tk.Tk):
    def __init__(self):
        super().__init__()
        self.title("Orchard")
        self.geometry("960x600")
        self.configure(bg=BG)
        self._build_sidebar()
        self._build_content()

    def _build_sidebar(self):
        sidebar = tk.Frame(self, bg=BG_ALT, width=200)
        sidebar.pack(side="left", fill="y")
        tk.Label(
            sidebar, text="ORCHARD", bg=BG_ALT, fg=ACCENT,
            font=(FONT_FAMILY, 16, "bold"),
        ).pack(pady=(18, 24))
        for name in SECTIONS:
            btn = tk.Button(
                sidebar, text=name.upper(), anchor="w",
                bg=BG_ALT, fg=TEXT_DIM, relief="flat",
                activebackground=BG, activeforeground=TEXT,
                font=(FONT_FAMILY, FONT_SIZE),
                command=lambda n=name: self.show_section(n),
            )
            btn.pack(fill="x", padx=10, pady=3)

    def _build_content(self):
        self.content = tk.Frame(self, bg=BG)
        self.content.pack(side="right", fill="both", expand=True)
        self.show_section("visuals")

    def show_section(self, name):
        for child in self.content.winfo_children():
            child.destroy()
        from gui.panels import load_panel
        load_panel(self.content, name)


if __name__ == "__main__":
    OrchardGUI().mainloop()