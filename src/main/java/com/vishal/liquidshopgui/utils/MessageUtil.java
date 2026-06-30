package com.vishal.liquidshopgui.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class MessageUtil {

    public static Component info(String message) {
        return Component.text(message, NamedTextColor.GREEN);
    }

    public static Component error(String message) {
        return Component.text(message, NamedTextColor.RED);
    }

    public static Component warning(String message) {
        return Component.text(message, NamedTextColor.YELLOW);
    }

    public static Component primary(String message) {
        return Component.text(message, NamedTextColor.AQUA);
    }
}
