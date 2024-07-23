package com.daniel.home.command;

import com.daniel.home.inventory.HomeInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.openInventory(new HomeInventory(player).getInventory());
            return true;
        }

        return false;
    }
}
