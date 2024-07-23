package com.daniel.home.core.database;

import com.daniel.home.Main;
import com.daniel.home.model.entity.Home;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class Database implements IDatabase {

    @Override
    public void execute(String query, Consumer<PreparedStatement> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            try (Connection connection = getConnection()) {
                try(PreparedStatement stm = connection.prepareStatement(query)) {
                    consumer.accept(stm);
                    stm.executeUpdate();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Houve um problema com o banco de dados! " + e);
            }
        });
    }

    @Override
    public <T> void query(String query, Consumer<PreparedStatement> consumer, Consumer<ResultSet> function) {
           try(Connection connection = getConnection()) {
               if (connection != null) {
                   try(PreparedStatement stm = connection.prepareStatement(query)) {
                        consumer.accept(stm);
                        try(ResultSet rs = stm.executeQuery()) {
                            function.accept(rs);
                        }
                   }
               }
           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
    }

    public abstract void initTable();
    public abstract List<Home> findHome(Player player, int page);
    public abstract Home findHome(Player player, String home);
    public abstract void insertHome(Home home);
    public abstract void deleteHome(UUID uuid);
}
