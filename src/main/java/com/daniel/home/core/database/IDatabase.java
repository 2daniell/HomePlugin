package com.daniel.home.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.function.Consumer;
import java.util.function.Function;

public interface IDatabase {

    void execute(String query, Consumer<PreparedStatement> consumer);
    <T> void query(String query, Consumer<PreparedStatement> consumer, Consumer<ResultSet> function);
    Connection getConnection();
}
