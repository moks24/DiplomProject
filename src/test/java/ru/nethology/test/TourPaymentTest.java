package ru.nethology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nethology.page.CardDetails;
import ru.nethology.page.ChoiceOfPayment;
import ru.nethology.page.WindowPage;
import ru.nethology.payment.DebitCard;

import java.sql.DriverManager;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TourPaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @Test
    @SneakyThrows
    void shouldPayWithAnApprovedDebitCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getApprovedCard());
        var window = new WindowPage().windowSuccefully();

        String cardSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var cardsStmt = conn.prepareStatement(cardSql);
        ) {
            try (var rs = cardsStmt.executeQuery()) {
                while (rs.next()) {
                    var status = rs.getString("status");
                    assertEquals("APPROVED", status);
                }
            }
        }
    }

    @Test
    @SneakyThrows
    void shouldPayWithADeclinedDebitCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getDeclinedCard());
        var window = new WindowPage().windowSuccefully();

        String cardSql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var cardsStmt = conn.prepareStatement(cardSql);
        ) {
            try (var rs = cardsStmt.executeQuery()) {
                while (rs.next()) {
                    var status = rs.getString("status");
                    assertEquals("DECLINED", status);
                }
            }
        }
    }

    @Test
    @SneakyThrows
    void shouldPayWithAnApprovedCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getApprovedCard());
        var window = new WindowPage().windowSuccefully();

        String cardSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var cardsStmt = conn.prepareStatement(cardSql);
        ) {
            try (var rs = cardsStmt.executeQuery()) {
                while (rs.next()) {
                    var status = rs.getString("status");
                    assertEquals("APPROVED", status);
                }
            }
        }
    }

    @Test
    @SneakyThrows
    void shouldPayWithADeclinedCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getDeclinedCard());
        var window = new WindowPage().windowSuccefully();

        String cardSql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
                var cardsStmt = conn.prepareStatement(cardSql);
        ) {
            try (var rs = cardsStmt.executeQuery()) {
                while (rs.next()) {
                    var status = rs.getString("status");
                    assertEquals("DECLINED", status);
                }
            }
        }

    }

    @Test
    void shouldGiveBankRejectionError(){
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getOtherCard("4444_5555_1111_2336"));
        var window = new WindowPage().windowError();
    }

//    @Test
//    void shouldThrowAFieldError(){
//        var choiceOfPayment = new ChoiceOfPayment();
//        choiceOfPayment.creditCardPayment();
//        var payCard = new CardDetails().payCard()
//    }
}
