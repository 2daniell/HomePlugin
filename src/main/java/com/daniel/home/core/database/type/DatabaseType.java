package com.daniel.home.core.database.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor @Getter
public enum DatabaseType {

    MYSQL("mysql"), MARIADB("mariadb"), SQLITE("sqlite");

    private final String name;

    public static DatabaseType of(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
