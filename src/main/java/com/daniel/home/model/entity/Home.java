package com.daniel.home.model.entity;

import com.daniel.home.util.ItemBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

@Getter @AllArgsConstructor
public class Home {

    private final UUID id;
    private final String name;
    private final Location location;
    private final UUID owner;

    public Home(String name, Location location, UUID owner) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.owner = owner;
    }

    public ItemStack getItem() {
        return new ItemBuilder(Material.PAPER)
                .addEnchant(Enchantment.KNOCKBACK, 1)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .setDisplayName("§eHome: §f" + this.name)
                .setLore(Arrays.asList(
                        "§c" + UUID.randomUUID(),
                        "",
                        "§7Localização:",
                        "   §7X: §f" + (int) location.getX(),
                        "   §7Y: §f" + (int) location.getY(),
                        "   §7Z: §f" + (int) location.getZ(),
                        "",
                        "§eClique para deletar!"
                )).build();
    }

}
