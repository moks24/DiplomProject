package ru.nethology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.C;
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
    void shouldPayWithAnApprovedDebitCard() {
        var choiceOfPayment = new ChoiceOfPayment().debitCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getApprovedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithDebitCard(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

        assertEquals("APPROVED", status);
    }

    @Test
    void shouldPayWithADeclinedDebitCard() {
        var choiceOfPayment = new ChoiceOfPayment().debitCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getDeclinedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithDebitCard(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

        assertEquals("DECLINED", status);


    }

    @Test
    void shouldPayWithAnApprovedCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment().creditCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getApprovedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithCreditCard(
                "jdbc:mysql://localhost:3306/app", "app", "pass");

        assertEquals("APPROVED", status);

    }

    @Test
    void shouldPayWithADeclinedCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment().creditCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getDeclinedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithCreditCard(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
        assertEquals("DECLINED", status);
    }


    @Test
    void shouldGiveBankRejectionError() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getOtherCard("4444_5555_1111_2336"));
        var window = new WindowPage().checkErrorWindow();
    }

    @Test
    void shouldThrowErrorInvalidFieldFormat() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().paymentInInputFields(
                "4444_5558_7777_6661", "", "23", "Кирилл Петров", "896"
        );
        var error = new CardDetails().error(0);
        assertEquals("Неверный формат", error);
    }

    @Test
    void shouldThrowCardExpirationError() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().paymentInInputFields(
                "4435_5324_7727_6661", "11", "29", "Антон Кириллов", "512"
        );
        var error = new CardDetails().error(0);
        assertEquals("Неверно указан срок действия карты", error);
    }
}
