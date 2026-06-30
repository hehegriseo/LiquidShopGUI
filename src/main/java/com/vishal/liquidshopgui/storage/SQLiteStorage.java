package com.vishal.liquidshopgui.storage;

import com.vishal.liquidshopgui.LiquidShopGUI;
import com.vishal.liquidshopgui.models.PlayerBalance;

import java.util.Optional;
import java.util.UUID;

public class SQLiteStorage implements BalanceRepository {

    private final LiquidShopGUI plugin;

    public SQLiteStorage(LiquidShopGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public Optional<PlayerBalance> findByPlayerId(UUID playerId) {
        // TODO: Implement SQLite lookup
        return Optional.empty();
    }

    @Override
    public void save(PlayerBalance balance) {
        // TODO: Implement SQLite insert/update
    }

    @Override
    public void deleteByPlayerId(UUID playerId) {
        // TODO: Implement SQLite delete
    }
}
