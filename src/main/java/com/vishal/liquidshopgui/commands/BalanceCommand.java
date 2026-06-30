package com.vishal.liquidshopgui.commands;

import com.vishal.liquidshopgui.LiquidShopGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BalanceCommand implements CommandExecutor {

    private final LiquidShopGUI plugin;

    public BalanceCommand(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(Component.text("This command can only be used by players.", NamedTextColor.RED));
            return true;
        }

        double balance = plugin.economyManager().getBalance(player.getUniqueId());
        String symbol = plugin.getConfig().getString("currency-symbol", "$");

        player.sendMessage(
            Component.text("Your balance: ", NamedTextColor.GREEN)
                .append(Component.text(symbol, NamedTextColor.YELLOW))
                .append(Component.text(String.format("%.2f", balance), NamedTextColor.YELLOW))
        );
        return true;
    }
}
