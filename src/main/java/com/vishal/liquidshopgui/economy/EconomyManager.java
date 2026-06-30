package com.vishal.liquidshopgui.economy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EconomyManager {

    private final Map<UUID, Double> balances;

    public EconomyManager() {
        this.balances = new HashMap<>();
    }

    public double getBalance(UUID playerId) {
        return balances.getOrDefault(playerId, 0.0);
    }

    public boolean deposit(UUID playerId, double amount) {
        if (amount < 0) return false;
        balances.merge(playerId, amount, Double::sum);
        return true;
    }

    public boolean withdraw(UUID playerId, double amount) {
        double current = balances.getOrDefault(playerId, 0.0);
        if (current < amount) return false;
        balances.put(playerId, current - amount);
        return true;
    }

    public boolean has(UUID playerId, double amount) {
        return balances.getOrDefault(playerId, 0.0) >= amount;
    }
}
