package com.vishal.liquidshopgui.listeners;

import com.vishal.liquidshopgui.LiquidShopGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private final LiquidShopGUI plugin;

    public PlayerJoinListener(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        var economy = plugin.economyManager();

        if (!economy.hasAccount(player.getUniqueId())) {
            double starting = plugin.getConfig().getDouble("starting-balance", 1000.0);
            economy.setStartingBalance(player.getUniqueId(), starting);
            player.sendMessage(
                Component.text("Welcome! You received ", NamedTextColor.GREEN)
                    .append(Component.text(plugin.getConfig().getString("currency-symbol", "$") + String.format("%.0f", starting), NamedTextColor.YELLOW))
                    .append(Component.text(" as starting balance.", NamedTextColor.GREEN))
            );
        }
    }
}
