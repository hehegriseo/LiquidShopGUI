package com.vishal.liquidshopgui.commands;

import com.vishal.liquidshopgui.LiquidShopGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class WorthCommand implements CommandExecutor {

    private final LiquidShopGUI plugin;

    public WorthCommand(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by players.", NamedTextColor.RED));
            return true;
        }

        Material material;
        if (args.length == 0) {
            ItemStack held = player.getInventory().getItemInMainHand();
            if (held.getType() == Material.AIR) {
                player.sendMessage(Component.text("You are not holding any item.", NamedTextColor.RED));
                return true;
            }
            material = held.getType();
        } else {
            String input = args[0].toUpperCase().replace("MINECRAFT:", "");
            try {
                material = Material.valueOf(input);
            } catch (IllegalArgumentException e) {
                player.sendMessage(
                    Component.text("Unknown item: ", NamedTextColor.RED)
                        .append(Component.text(args[0], NamedTextColor.AQUA))
                );
                return true;
            }
        }

        double price = plugin.getConfig().getDouble("worth." + material.name(), -1.0);
        String symbol = plugin.getConfig().getString("currency-symbol", "$");

        if (price < 0) {
            player.sendMessage(
                Component.text(material.name(), NamedTextColor.AQUA)
                    .append(Component.text(" has no set value.", NamedTextColor.RED))
            );
        } else {
            player.sendMessage(
                Component.text("Worth of ", NamedTextColor.GREEN)
                    .append(Component.text(formatMaterial(material), NamedTextColor.AQUA))
                    .append(Component.text(": ", NamedTextColor.GREEN))
                    .append(Component.text(symbol + String.format("%.2f", price), NamedTextColor.YELLOW))
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
