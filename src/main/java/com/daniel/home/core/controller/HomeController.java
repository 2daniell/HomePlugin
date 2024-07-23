package com.daniel.home.core.controller;

import com.daniel.home.Main;
import com.daniel.home.core.database.Database;
import com.daniel.home.model.entity.Home;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public final class HomeController {

    private static final Database database;

    static {
        database = Main.getDatabase();
    }

    public static List<Home> getHomes(Player player, int page) {
        return database.findHome(player, page);
    }

    /*public static Home getHome(Player player, String name) {
        try {
            //return database.findHome(player.getUniqueId(), name);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Não foi possivel realizar a consulta: " + e);
        }
    }*/

    public static void createHome(Home home) {
        database.insertHome(home);
    }

}
