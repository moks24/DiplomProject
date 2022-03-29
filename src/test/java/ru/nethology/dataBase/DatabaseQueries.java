package ru.nethology.dataBase;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseQueries {

    private static final String url = System.getProperty("db_url");
    private static final String user = System.getProperty("db_user");
    private static final String password = System.getProperty("db_password");
    private static Connection connection;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @SneakyThrows
    public String checkDatabaseWhenPayingWithDebitCard(){
        String cardSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        String status = "";

        try (
                var conn = getConnection();
                var cardsStmt = conn.prepareStatement(cardSql);
        ) {
            try (var rs = cardsStmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                    return status;
                }
            }
        }
        return status;
    }

    @SneakyThrows
    public String checkDatabaseWhenPayingWithCreditCard() {
        String cardSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        String status = "";

        try (
                var conn = getConnection();
                var cardsStmt = conn.prepareStatement(cardSql);
        ) {
            try (var rs = cardsStmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                    return status;
                }
            }
        }
        return status;
    }
}
