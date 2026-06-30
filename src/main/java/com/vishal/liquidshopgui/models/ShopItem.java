package com.vishal.liquidshopgui.models;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class ShopItem {
    private final Material material;
    private final double buyPrice;
    private final double sellPrice;
    private final Component displayName;

    public ShopItem(Material material, double buyPrice, double sellPrice, Component displayName) {
        this.material = material;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.displayName = displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public Component getDisplayName() {
        return displayName;
    }
}
