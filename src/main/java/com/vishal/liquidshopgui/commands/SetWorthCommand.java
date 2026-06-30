package com.vishal.liquidshopgui.commands;

import com.vishal.liquidshopgui.LiquidShopGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetWorthCommand implements CommandExecutor {

    private final LiquidShopGUI plugin;

    public SetWorthCommand(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("liquidshopgui.admin")) {
            sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(Component.text("Usage: /setworth <item> <price>", NamedTextColor.RED));
            return true;
        }

        String input = args[0].toUpperCase().replace("MINECRAFT:", "");
        Material material;
        try {
            material = Material.valueOf(input);
        } catch (IllegalArgumentException e) {
            sender.sendMessage(
                Component.text("Unknown item: ", NamedTextColor.RED)
                    .append(Component.text(args[0], NamedTextColor.AQUA))
            );
            return true;
        }

        double price;
        try {
            price = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("Invalid price.", NamedTextColor.RED));
            return true;
        }

        if (price < 0) {
            sender.sendMessage(Component.text("Price cannot be negative.", NamedTextColor.RED));
            return true;
        }

        String symbol = plugin.getConfig().getString("currency-symbol", "$");

        if (price == 0) {
            plugin.getConfig().set("worth." + material.name(), null);
            plugin.saveConfig();
            sender.sendMessage(
                Component.text("Removed worth for ", NamedTextColor.GREEN)
                    .append(Component.text(formatMaterial(material), NamedTextColor.AQUA))
                    .append(Component.text(".", NamedTextColor.GREEN))
            );
        } else {
            plugin.getConfig().set("worth." + material.name(), price);
            plugin.saveConfig();
            sender.sendMessage(
                Component.text("Set worth of ", NamedTextColor.GREEN)
                    .append(Component.text(formatMaterial(material), NamedTextColor.AQUA))
                    .append(Component.text(" to ", NamedTextColor.GREEN))
                    .append(Component.text(symbol + String.format("%.2f", price), NamedTextColor.YELLOW))
                    .append(Component.text(".", NamedTextColor.GREEN))
            );
        }

        return true;
    }

    private String formatMaterial(Material material) {
        StringBuilder result = new StringBuilder();
        String[] words = material.name().toLowerCase().split("_");
        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                      .append(word.substring(1))
                      .append(" ");
            }
        }
        return result.toString().trim();
    }
}
