package com.daniel.home.command;

import com.daniel.home.Main;
import com.daniel.home.core.controller.HomeController;
import com.daniel.home.model.entity.Home;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    private final int COOLDOWN_TIME = Main.getInstance().getConfig().getInt("cooldown");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("§cUse, /home <home>");
            return true;
        }

        String name = args[0];

        if (!HomeController.hasHome(player, name)) {
            player.sendMessage("§cHome inexistente");
            return true;
        }

        Home home = HomeController.getHome(player, name);
        if (home != null) {

            player.sendMessage("§8Teleportando em §7" + COOLDOWN_TIME + " §8segundos");
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
                Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                    player.teleport(home.getLocation());
                    player.sendMessage("§aTeleportado com sucesso.");
                });
            }, 20L * COOLDOWN_TIME);


        } else {
            player.sendMessage("§cOcorreu um erro ao teleportar.");
        }
        return true;
    }
}
