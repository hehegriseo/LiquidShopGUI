package com.vishal.liquidshopgui.economy;

import java.util.UUID;

public class EconomyManager {

    public EconomyManager() {
    }

    public double getBalance(UUID playerId) {
        return 0.0;
    }

    public boolean deposit(UUID playerId, double amount) {
        return false;
    }

    public boolean withdraw(UUID playerId, double amount) {
        return false;
    }

    public boolean has(UUID playerId, double amount) {
        return false;
    }
}
