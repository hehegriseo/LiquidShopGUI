package com.vishal.liquidshopgui.commands;

import com.vishal.liquidshopgui.LiquidShopGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EcoCommand implements CommandExecutor {

    private final LiquidShopGUI plugin;

    public EcoCommand(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("liquidshopgui.admin")) {
            sender.sendMessage(Component.text("You do not have permission to use this command.", NamedTextColor.RED));
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(Component.text("Usage: /eco <give|take|set> <player> <amount>", NamedTextColor.RED));
            return true;
        }

        String action = args[0].toLowerCase();
        Player target = plugin.getServer().getPlayerExact(args[1]);

        if (target == null) {
            sender.sendMessage(
                Component.text("Player ", NamedTextColor.RED)
                    .append(Component.text(args[1], NamedTextColor.AQUA))
                    .append(Component.text(" is not online.", NamedTextColor.RED))
            );
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("Invalid amount.", NamedTextColor.RED));
            return true;
        }

        if (amount < 0) {
            sender.sendMessage(Component.text("Amount cannot be negative.", NamedTextColor.RED));
            return true;
        }

        var economy = plugin.economyManager();
        String symbol = plugin.getConfig().getString("currency-symbol", "$");

        switch (action) {
            case "give" -> {
                economy.deposit(target.getUniqueId(), amount);
                sender.sendMessage(
                    Component.text("Gave ", NamedTextColor.GREEN)
                        .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                        .append(Component.text(" to ", NamedTextColor.GREEN))
                        .append(Component.text(target.getName(), NamedTextColor.AQUA))
                        .append(Component.text(".", NamedTextColor.GREEN))
                );
                target.sendMessage(
                    Component.text("You received ", NamedTextColor.GREEN)
                        .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                        .append(Component.text(".", NamedTextColor.GREEN))
                );
            }
            case "take" -> {
                if (!economy.has(target.getUniqueId(), amount)) {
                    sender.sendMessage(
                        Component.text(target.getName(), NamedTextColor.AQUA)
                            .append(Component.text(" does not have enough funds. Balance: ", NamedTextColor.RED))
                            .append(Component.text(symbol + String.format("%.2f", economy.getBalance(target.getUniqueId())), NamedTextColor.YELLOW))
                    );
                    return true;
                }
                economy.withdraw(target.getUniqueId(), amount);
                sender.sendMessage(
                    Component.text("Took ", NamedTextColor.GREEN)
                        .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                        .append(Component.text(" from ", NamedTextColor.GREEN))
                        .append(Component.text(target.getName(), NamedTextColor.AQUA))
                        .append(Component.text(".", NamedTextColor.GREEN))
                );
                target.sendMessage(
                    Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW)
                        .append(Component.text(" was taken from your balance.", NamedTextColor.RED))
                );
            }
            case "set" -> {
                economy.set(target.getUniqueId(), amount);
                sender.sendMessage(
                    Component.text("Set ", NamedTextColor.GREEN)
                        .append(Component.text(target.getName(), NamedTextColor.AQUA))
                        .append(Component.text("'s balance to ", NamedTextColor.GREEN))
                        .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                        .append(Component.text(".", NamedTextColor.GREEN))
                );
                target.sendMessage(
                    Component.text("Your balance has been set to ", NamedTextColor.GREEN)
                        .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                        .append(Component.text(".", NamedTextColor.GREEN))
                );
            }
            default -> {
                sender.sendMessage(
                    Component.text("Unknown action. Use: give, take, or set.", NamedTextColor.RED)
                );
            }
        }

        return true;
    }
}
