package com.daniel.home.core.database.impl;

import com.daniel.home.Main;
import com.daniel.home.core.database.Database;
import com.daniel.home.model.entity.Home;
import com.daniel.home.util.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class SQLiteDatabase extends Database{

    public Connection getConnection() {
        File folder = new File(Main.getInstance().getDataFolder(), "database");
        if(!folder.exists()) folder.mkdirs();

        File file = new File(folder, "database.db");
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + file);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("A conexão com o SQLite falhou! " + e);
        }
    }

    @Override
    public List<Home> findHome(Player player, int page) {
        List<Home> resultHomes = new ArrayList<>();
        query("SELECT * FROM home_database WHERE owner = ? LIMIT ? OFFSET ?", stm -> {
            try {

                int limit = 14;
                int offset = (page - 1) * limit;

                stm.setString(1, player.getUniqueId().toString());
                stm.setInt(2, limit);
                stm.setInt(3, offset);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, resultSet -> {
            try {

                while (resultSet.next()) {
                    UUID homeId = UUID.fromString(resultSet.getString("uuid"));
                    String homeName = resultSet.getString("name");
                    Location homeLocation = Utils.getDeserializedLocation(resultSet.getString("location"));
                    UUID owner = UUID.fromString(resultSet.getString("owner"));

                    resultHomes.add(new Home(homeId, homeName, homeLocation, owner));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return resultHomes;
    }

    @Override
    public Home findHome(Player player, String home) {
        final Home[] foundHome = new Home[1];

        query("SELECT * FROM home_database WHERE name = ? AND owner = ? LIMIT 1", stm -> {
            try {
                stm.setString(1, home);
                stm.setString(2, player.getUniqueId().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, resultSet -> {
            try {
                while (resultSet.next()) {
                    UUID homeId = UUID.fromString(resultSet.getString("uuid"));
                    String homeName = resultSet.getString("name");
                    Location homeLocation = Utils.getDeserializedLocation(resultSet.getString("location"));
                    UUID homeOwner = UUID.fromString(resultSet.getString("owner"));

                    foundHome[0] = new Home(homeId, homeName, homeLocation, homeOwner);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        return foundHome[0];
    }


    @Override
    public void insertHome(Home home) {
        execute("INSERT INTO home_database (uuid, name, owner, location) " +
                "VALUES (?, ?, ?, ?);", stm -> {
            try {
                stm.setString(1, home.getId().toString());
                stm.setString(2, home.getName());
                stm.setString(3, home.getOwner().toString());
                stm.setString(4, Utils.getSerializedLocation(home.getLocation()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void deleteHome(UUID uuid) {
        execute("DELETE FROM home_database WHERE uuid = ?", stm -> {
            try {
                stm.setString(1, uuid.toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public boolean hasHome(Player player, String home) {
        final boolean[] exists = new boolean[1];

        query("SELECT 1 FROM home_database WHERE name = ? AND owner = ? LIMIT 1", stm -> {
            try {
                stm.setString(1, home);
                stm.setString(2, player.getUniqueId().toString());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, resultSet -> {
            try {
                exists[0] = resultSet.next();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return exists[0];
    }


    @Override
    public void initTable() {
        execute("CREATE TABLE IF NOT EXISTS home_database (" +
                "uuid TEXT PRIMARY KEY," +
                "name TEXT," +
                "owner TEXT," +
                "location TEXT)", stm -> {});
    }
}
