package com.daniel.home.inventory;

import com.daniel.home.core.controller.HomeController;
import com.daniel.home.model.entity.Home;
import com.daniel.home.model.menu.PaginatedMenu;
import com.daniel.home.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class HomeInventory extends PaginatedMenu {

    public HomeInventory(Player player) {
        super(player, "Homes " + player.getName(), 4*9, 14);
    }

    @Override
    public void onClick(InventoryClickEvent event) {

        ItemStack clicked = event.getCurrentItem();

        if (clicked == null || !clicked.hasItemMeta() || !clicked.getItemMeta().hasDisplayName()) return;

        if (clicked.getType() == Material.ARROW) {

            String itemName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());

            if (itemName.equalsIgnoreCase("Avançar")) page++;
            if (itemName.equalsIgnoreCase("voltar")) page--;

            player.openInventory(getInventory());
            return;
        }

        if (clicked.getType() == Material.PAPER) {

            UUID homeId = UUID.fromString(ChatColor.stripColor(clicked.getItemMeta().getLore().get(0)));
            HomeController.delHome(homeId);
            player.sendMessage("§aHome deletada com sucesso.");
            player.openInventory(getInventory());
        }

    }

    @Override
    public void setItens(Inventory inventory) {

        inventory.clear();
       var homes = HomeController.getHomes(player, page);

       int i = 10;
       for (Home home : homes) {

           if (i == 17) i = 19;

           inventory.setItem(i++, home.getItem());
       }

       if (homes.size() >= maxItensPerPage) {
           inventory.setItem(32, new ItemBuilder(Material.ARROW).setDisplayName("§eAvançar").build());
       }

       if (page > 1) {
           inventory.setItem(30, new ItemBuilder(Material.ARROW).setDisplayName("§eVoltar").build());
       }

    }
}
