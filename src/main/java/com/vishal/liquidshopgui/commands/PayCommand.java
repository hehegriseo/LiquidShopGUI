package com.vishal.liquidshopgui.commands;

import com.vishal.liquidshopgui.LiquidShopGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PayCommand implements CommandExecutor {

    private final LiquidShopGUI plugin;

    public PayCommand(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by players.", NamedTextColor.RED));
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(Component.text("Usage: /pay <player> <amount>", NamedTextColor.RED));
            return true;
        }

        Player target = plugin.getServer().getPlayerExact(args[0]);
        if (target == null) {
            player.sendMessage(
                Component.text("Player ", NamedTextColor.RED)
                    .append(Component.text(args[0], NamedTextColor.AQUA))
                    .append(Component.text(" is not online.", NamedTextColor.RED))
            );
            return true;
        }

        if (target.equals(player)) {
            player.sendMessage(Component.text("You cannot pay yourself.", NamedTextColor.RED));
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage(Component.text("Invalid amount.", NamedTextColor.RED));
            return true;
        }

        if (amount <= 0) {
            player.sendMessage(Component.text("Amount must be positive.", NamedTextColor.RED));
            return true;
        }

        var economy = plugin.economyManager();
        String symbol = plugin.getConfig().getString("currency-symbol", "$");

        if (!economy.has(player.getUniqueId(), amount)) {
            player.sendMessage(
                Component.text("Insufficient funds. You need ", NamedTextColor.RED)
                    .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                    .append(Component.text(" but you only have ", NamedTextColor.RED))
                    .append(Component.text(symbol + String.format("%.2f", economy.getBalance(player.getUniqueId())), NamedTextColor.YELLOW))
                    .append(Component.text(".", NamedTextColor.RED))
            );
            return true;
        }

        economy.withdraw(player.getUniqueId(), amount);
        economy.deposit(target.getUniqueId(), amount);

        player.sendMessage(
            Component.text("You paid ", NamedTextColor.GREEN)
                .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                .append(Component.text(" to ", NamedTextColor.GREEN))
                .append(Component.text(target.getName(), NamedTextColor.AQUA))
                .append(Component.text(".", NamedTextColor.GREEN))
        );

        target.sendMessage(
            Component.text("You received ", NamedTextColor.GREEN)
                .append(Component.text(symbol + String.format("%.2f", amount), NamedTextColor.YELLOW))
                .append(Component.text(" from ", NamedTextColor.GREEN))
                .append(Component.text(player.getName(), NamedTextColor.AQUA))
                .append(Component.text(".", NamedTextColor.GREEN))
        );

        return true;
    }
}
