package com.playtheatria.jliii.theatriasecurity.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;

public class GeneralUtils {

    private static TextComponent pluginPrefix = Component.text()
            .content("[").build()
            .append(Component.text("TheatriaSecurity", NamedTextColor.RED)
                    .append(Component.text("] ", NamedTextColor.WHITE)));

    public static void sendLog(String message) {
        Audience console = Bukkit.getConsoleSender();
        console.sendMessage(pluginPrefix.append(Component.text(message, NamedTextColor.YELLOW)));
    }

}
