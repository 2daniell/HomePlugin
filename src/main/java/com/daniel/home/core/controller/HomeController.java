package com.daniel.home.core.controller;

import com.daniel.home.Main;
import com.daniel.home.core.database.Database;
import com.daniel.home.model.entity.Home;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public final class HomeController {

    private static final Database database;

    static {
        database = Main.getDatabase();
    }

    public static List<Home> getHomes(Player player, int page) {
        return database.findHome(player, page);
    }

    public static Home getHome(Player player, String home) {
        return database.findHome(player, home);
    }

    public static void insertHome(Home home) {
        database.insertHome(home);
    }

    public static void delHome(UUID home) {
        database.deleteHome(home);
    }

    public static boolean hasHome(Player player, String home) {
        return database.hasHome(player, home);
    }

    public static void createHome(Home home) {
        database.insertHome(home);
    }

}
