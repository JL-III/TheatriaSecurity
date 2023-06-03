package com.playtheatria.jliii.theatriasecurity.enums;

import net.kyori.adventure.text.format.NamedTextColor;

public enum Status {
    VALID(NamedTextColor.GREEN),
    INVALID(NamedTextColor.DARK_RED);
    private final NamedTextColor color;


    Status(NamedTextColor color) {
        this.color = color;
    }

    public NamedTextColor getColor() {
        return color;
    }
}
