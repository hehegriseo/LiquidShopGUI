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
import org.bukkit.inventory.PlayerInventory;

public class SellCommand implements CommandExecutor {

    private final LiquidShopGUI plugin;

    public SellCommand(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by players.", NamedTextColor.RED));
            return true;
        }

        if (args.length == 0 || args[0].equalsIgnoreCase("hand")) {
            return sellHand(player);
        }

        return switch (args[0].toLowerCase()) {
            case "all" -> sellAll(player, false);
            case "blocks" -> sellAll(player, true);
            default -> {
                player.sendMessage(Component.text("Usage: /sell [hand|all|blocks]", NamedTextColor.RED));
                yield true;
            }
        };
    }

    private boolean sellHand(Player player) {
        ItemStack held = player.getInventory().getItemInMainHand();
        if (held.getType() == Material.AIR) {
            player.sendMessage(Component.text("You are not holding any item.", NamedTextColor.RED));
            return true;
        }

        double price = plugin.getConfig().getDouble("worth." + held.getType().name(), -1.0);
        if (price < 0) {
            player.sendMessage(
                Component.text(formatMaterial(held.getType()) + " has no value.", NamedTextColor.RED));
            return true;
        }

        int amount = held.getAmount();
        double total = price * amount;
        String symbol = plugin.getConfig().getString("currency-symbol", "$");

        plugin.economyManager().deposit(player.getUniqueId(), total);
        player.getInventory().setItemInMainHand(null);

        player.sendMessage(
            Component.text("Sold ", NamedTextColor.GREEN)
                .append(Component.text(amount + " ", NamedTextColor.WHITE))
                .append(Component.text(formatMaterial(held.getType()), NamedTextColor.AQUA))
                .append(Component.text(" for ", NamedTextColor.GREEN))
                .append(Component.text(symbol + String.format("%.2f", total), NamedTextColor.YELLOW))
                .append(Component.text(".", NamedTextColor.GREEN))
        );
        return true;
    }

    private boolean sellAll(Player player, boolean blocksOnly) {
        PlayerInventory inv = player.getInventory();
        String symbol = plugin.getConfig().getString("currency-symbol", "$");
        double grandTotal = 0;
        int totalItems = 0;
        var economy = plugin.economyManager();

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack item = inv.getItem(i);
            if (item == null || item.getType() == Material.AIR) continue;

            if (blocksOnly && !item.getType().isBlock()) continue;

            double price = plugin.getConfig().getDouble("worth." + item.getType().name(), -1.0);
            if (price < 0) continue;

            int amount = item.getAmount();
            grandTotal += price * amount;
            totalItems += amount;
            inv.setItem(i, null);
        }

        if (totalItems == 0) {
            String type = blocksOnly ? "sellable blocks" : "sellable items";
            player.sendMessage(Component.text("You have no " + type + ".", NamedTextColor.RED));
            return true;
        }

        economy.deposit(player.getUniqueId(), grandTotal);

        player.sendMessage(
            Component.text("Sold ", NamedTextColor.GREEN)
                .append(Component.text(String.valueOf(totalItems), NamedTextColor.WHITE))
                .append(Component.text(" items for ", NamedTextColor.GREEN))
                .append(Component.text(symbol + String.format("%.2f", grandTotal), NamedTextColor.YELLOW))
                .append(Component.text(".", NamedTextColor.GREEN))
        );
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
