package com.daniel.home.listeners;

import com.daniel.home.model.menu.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClick implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        var holder = event.getInventory().getHolder();
        if (!(holder instanceof Menu)) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null) return;
        ((Menu) holder).onClick(event);
    }
}
