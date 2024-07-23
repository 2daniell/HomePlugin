package com.daniel.home;

import com.daniel.home.command.HomesCommand;
import com.daniel.home.command.SetHomeCommand;
import com.daniel.home.core.database.Database;
import com.daniel.home.core.database.factory.DatabaseFactory;
import com.daniel.home.listeners.InventoryClick;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private static Database database;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        database = DatabaseFactory.initDatabase(this);
        database.initTable();

        initListeners();
        initCommands();
    }

    private void initCommands() {
        getCommand("homes").setExecutor(new HomesCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
    }
    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
    }
}