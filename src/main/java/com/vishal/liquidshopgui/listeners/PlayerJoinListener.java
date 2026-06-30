package com.vishal.liquidshopgui.listeners;

import com.vishal.liquidshopgui.LiquidShopGUI;
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
        // TODO: Initialize player data on join
    }
}
