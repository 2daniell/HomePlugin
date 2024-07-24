package com.daniel.home.command;

import com.daniel.home.core.controller.HomeController;
import com.daniel.home.model.entity.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("§cUse, /sethome <nome>");
            return true;
        }

        String name = args[0];
        if (HomeController.hasHome(player, name)) {
            player.sendMessage("§cHome já existente.");
            return true;
        }
        Home home = new Home(name, player.getLocation(), player.getUniqueId());
        HomeController.createHome(home);
        player.sendMessage("§aHome §f" + home.getName() + " §acriada com sucesso!");
        return true;
    }
}
