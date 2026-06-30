package com.vishal.liquidshopgui.economy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class EconomyManager {

    private final Map<UUID, Double> balances;

    public EconomyManager() {
        this.balances = new HashMap<>();
    }

    public boolean hasAccount(UUID playerId) {
        return balances.containsKey(playerId);
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
        if (amount < 0) return false;
        double current = balances.getOrDefault(playerId, 0.0);
        if (current < amount) return false;
        balances.put(playerId, current - amount);
        return true;
    }

    public boolean has(UUID playerId, double amount) {
        return balances.getOrDefault(playerId, 0.0) >= amount;
    }

    public void set(UUID playerId, double amount) {
        balances.put(playerId, Math.max(0, amount));
    }

    public double setStartingBalance(UUID playerId, double amount) {
        balances.put(playerId, amount);
        return amount;
    }

    public Map<UUID, Double> getAllBalances() {
        return new HashMap<>(balances);
    }

    public Map<UUID, Double> getTopBalances(int limit) {
        return balances.entrySet()
            .stream()
            .sorted(Map.Entry.<UUID, Double>comparingByValue().reversed())
            .limit(limit)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a, b) -> a,
                LinkedHashMap::new
            ));
    }
}
