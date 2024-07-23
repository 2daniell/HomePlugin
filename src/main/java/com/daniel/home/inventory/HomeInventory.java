package com.daniel.home.inventory;

import com.daniel.home.core.controller.HomeController;
import com.daniel.home.model.entity.Home;
import com.daniel.home.model.menu.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class HomeInventory extends PaginatedMenu {

    public HomeInventory(Player player) {
        super(player, "Homes " + player.getName(), 4*9, 14);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

    }

    @Override
    public void setItens(Inventory inventory) {

       var homes = HomeController.getHomes(player, page);

       int i = 10;
       for (Home home : subList(homes)) {
           inventory.setItem(i++, home.getItem());
       }

    }
}
