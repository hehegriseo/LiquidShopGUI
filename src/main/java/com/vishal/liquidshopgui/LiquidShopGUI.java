package com.vishal.liquidshopgui;

import com.vishal.liquidshopgui.commands.BalanceCommand;
import com.vishal.liquidshopgui.commands.BalanceTopCommand;
import com.vishal.liquidshopgui.commands.EcoCommand;
import com.vishal.liquidshopgui.commands.PayCommand;
import com.vishal.liquidshopgui.commands.SellCommand;
import com.vishal.liquidshopgui.commands.SetWorthCommand;
import com.vishal.liquidshopgui.commands.ShopCommand;
import com.vishal.liquidshopgui.commands.WorthCommand;
import com.vishal.liquidshopgui.economy.EconomyManager;
import com.vishal.liquidshopgui.listeners.InventoryListener;
import com.vishal.liquidshopgui.listeners.PlayerJoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public class LiquidShopGUI extends JavaPlugin {

    private EconomyManager economyManager;

    @Override
    public void onEnable() {
        this.economyManager = new EconomyManager();

        registerConfigs();
        registerCommands();
        registerListeners();

        getLogger().info("LiquidShopGUI enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("LiquidShopGUI disabled.");
    }

    public EconomyManager economyManager() {
        return economyManager;
    }

    private void registerConfigs() {
        saveDefaultConfig();
    }

    private void registerCommands() {
        getCommand("balance").setExecutor(new BalanceCommand(this));
        getCommand("balancetop").setExecutor(new BalanceTopCommand(this));
        getCommand("pay").setExecutor(new PayCommand(this));
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("eco").setExecutor(new EcoCommand(this));
        getCommand("worth").setExecutor(new WorthCommand(this));
        getCommand("sell").setExecutor(new SellCommand(this));
        getCommand("setworth").setExecutor(new SetWorthCommand(this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
    }
}
