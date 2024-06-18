package net.mamesosu.Object;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    final String host;
    final int port;
    final String user;
    final String password;

    public DataBase() {
        Dotenv dotenv = Dotenv.load();
        host = dotenv.get("HOST");
        port = Integer.parseInt(dotenv.get("PORT"));
        user = dotenv.get("USER");
        password = dotenv.get("PASSWORD");
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost/MamestaServer?autoReconnect=true",
                    "mames1basshhii0610",
                    "mamestagrammames1passwordadmin"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
