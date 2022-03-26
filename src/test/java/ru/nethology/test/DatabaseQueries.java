package ru.nethology.test;

import lombok.SneakyThrows;

import java.sql.DriverManager;

public class DatabaseQueries {
    @SneakyThrows
    public String checkDatabaseWhenPayingWithDebitCard(String url, String user, String pass){
        String cardSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        String status = "";

        try (
                var conn = DriverManager.getConnection(url, user, pass);
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
    public String checkDatabaseWhenPayingWithCreditCard(String url, String user, String pass) {
        String cardSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        String status = "";

        try (
                var conn = DriverManager.getConnection(url, user, pass);
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
