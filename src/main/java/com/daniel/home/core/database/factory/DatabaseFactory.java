package com.daniel.home.core.database.factory;

import com.daniel.home.core.database.Database;
import com.daniel.home.core.database.IDatabase;
import com.daniel.home.core.database.impl.MariaDBDatabase;
import com.daniel.home.core.database.impl.MySQLDatabase;
import com.daniel.home.core.database.impl.SQLiteDatabase;
import com.daniel.home.core.database.type.DatabaseType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Objects;
import java.util.Optional;

public class DatabaseFactory {

    public static Database initDatabase(Plugin plugin) {

        FileConfiguration config = plugin.getConfig();
        if (!config.contains("database")) {
            plugin.getLogger().severe("Configuração do banco de dados não encontrada.");
            Bukkit.getPluginManager().disablePlugin(plugin);
        }

        String type = config.getString("database.type");

        DatabaseType databaseType = DatabaseType.of(Optional.ofNullable(type).orElseThrow(() -> new RuntimeException("Banco de dados não encontrado. [database.type]")));

        Database database = null;

        switch (Objects.requireNonNull(databaseType)) {

            case MYSQL -> {
                String host = Optional
                        .ofNullable(plugin.getConfig().getString("database.host"))
                        .orElseThrow(() -> new RuntimeException("O host do banco de dados não foi fornecido."));

                String db = Optional
                        .ofNullable(plugin.getConfig().getString("database.database"))
                        .orElseThrow(() -> new RuntimeException("A database não foi fornecida."));

                String user = Optional
                        .ofNullable(plugin.getConfig().getString("database.user"))
                        .orElseThrow(() -> new RuntimeException("O usuário do banco de dados não foi fornecido"));

                String password = Optional
                        .ofNullable(plugin.getConfig().getString("database.password"))
                        .orElseThrow(() -> new RuntimeException("A senha do banco de dados não foi fornecida"));

                String port = Optional.ofNullable(plugin.getConfig().getString("database.port"))
                        .orElseThrow(() -> new RuntimeException("Porta do banco de dados não foi fornecida."));


                database = new MySQLDatabase(host, user, password, port, db);

            }

            case MARIADB -> {
                String host = Optional
                        .ofNullable(plugin.getConfig().getString("database.host"))
                        .orElseThrow(() -> new RuntimeException("O host do banco de dados não foi fornecido."));

                String db = Optional
                        .ofNullable(plugin.getConfig().getString("database.database"))
                        .orElseThrow(() -> new RuntimeException("A database não foi fornecida."));

                String user = Optional
                        .ofNullable(plugin.getConfig().getString("database.user"))
                        .orElseThrow(() -> new RuntimeException("O usuário do banco de dados não foi fornecido"));

                String password = Optional
                        .ofNullable(plugin.getConfig().getString("database.password"))
                        .orElseThrow(() -> new RuntimeException("A senha do banco de dados não foi fornecida"));

                String port = Optional.ofNullable(plugin.getConfig().getString("database.port"))
                        .orElseThrow(() -> new RuntimeException("Porta do banco de dados não foi fornecida."));

                database = new MariaDBDatabase(host, user, password, port, db);
            }

            case SQLITE -> {
                database = new SQLiteDatabase();
            }

            default -> {
                plugin.getLogger().severe("O tipo de banco de dados informado não é suportado.");
                Bukkit.getPluginManager().disablePlugin(plugin);
            }

        }

        return database;
    }
}
