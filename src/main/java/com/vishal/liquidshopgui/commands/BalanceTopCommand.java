package com.vishal.liquidshopgui.commands;

import com.vishal.liquidshopgui.LiquidShopGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.UUID;

public class BalanceTopCommand implements CommandExecutor {

    private final LiquidShopGUI plugin;

    public BalanceTopCommand(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Map<UUID, Double> top = plugin.economyManager().getTopBalances(10);
        String symbol = plugin.getConfig().getString("currency-symbol", "$");

        sender.sendMessage(Component.text("=== Top 10 Richest Players ===", NamedTextColor.GOLD));

        if (top.isEmpty()) {
            sender.sendMessage(Component.text("No balances found.", NamedTextColor.GRAY));
            return true;
        }

        int index = 1;
        for (Map.Entry<UUID, Double> entry : top.entrySet()) {
            OfflinePlayer offline = plugin.getServer().getOfflinePlayer(entry.getKey());
            String name = offline.getName() != null ? offline.getName() : entry.getKey().toString().substring(0, 8);

            NamedTextColor color = switch (index) {
                case 1 -> NamedTextColor.AQUA;
                case 2 -> NamedTextColor.GREEN;
                case 3 -> NamedTextColor.LIGHT_PURPLE;
                default -> NamedTextColor.WHITE;
            };

            sender.sendMessage(
                Component.text("#" + index + " ", color)
                    .append(Component.text(name, NamedTextColor.AQUA))
                    .append(Component.text(" - ", NamedTextColor.GRAY))
                    .append(Component.text(symbol + String.format("%.2f", entry.getValue()), NamedTextColor.YELLOW))
            );
            index++;
        }

        return true;
    }
}
