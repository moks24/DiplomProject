package ru.nethology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nethology.data.CardInfo;
import ru.nethology.dataBase.DatabaseQueries;
import ru.nethology.page.CardDetails;
import ru.nethology.page.ChoiceOfPayment;
import ru.nethology.page.WindowPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTourCreditCardTest {

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
    void shouldPayWithAnApprovedCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment().creditCardPayment();
        var payCard = new CardDetails().payCard(CardInfo.getApprovedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithCreditCard();

        assertEquals("APPROVED", status);

    }

    @Test
    void shouldPayWithADeclinedCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment().creditCardPayment();
        var payCard = new CardDetails().payCard(CardInfo.getDeclinedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithCreditCard();
        assertEquals("DECLINED", status);
    }

    @Test
    void shouldThrowErrorInvalidFieldFormat() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().paymentInInputFields(
                "5354_5566_7278_6621", "07", "25", "", "896"
        );
        var error = new CardDetails().error(0);
        assertEquals("Неверный формат", error);
    }

    @Test
    void shouldGiveBankRejectionError() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().payCard(CardInfo.getOtherCard("4235_5535_1895_2543"));
        var window = new WindowPage().checkErrorWindow();
    }

    @Test
    void shouldThrowCardExpirationError() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().paymentInInputFields(
                "4435_5324_7727_6661", "99", "22", "Леонид Алехин", "512"
        );
        var error = new CardDetails().error(0);
        assertEquals("Неверно указан срок действия карты", error);
    }
}
