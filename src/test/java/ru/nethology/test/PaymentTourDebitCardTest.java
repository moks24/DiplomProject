package ru.nethology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nethology.dataBase.DatabaseQueries;
import ru.nethology.page.CardDetails;
import ru.nethology.page.ChoiceOfPayment;
import ru.nethology.page.WindowPage;
import ru.nethology.data.CardInfo;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentTourDebitCardTest {

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
        var payCard = new CardDetails().payCard(CardInfo.getApprovedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithDebitCard();

        assertEquals("APPROVED", status);
    }

    @Test
    void shouldPayWithADeclinedDebitCard() {
        var choiceOfPayment = new ChoiceOfPayment().debitCardPayment();
        var payCard = new CardDetails().payCard(CardInfo.getDeclinedCard());
        var window = new WindowPage().checkSuccessWindow();
        var status = new DatabaseQueries().checkDatabaseWhenPayingWithDebitCard();

        assertEquals("DECLINED", status);
    }

    @Test
    void shouldGiveBankRejectionError() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().payCard(CardInfo.getOtherCard("4444_5555_1111_2336"));
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
                "4435_5324_7727_6661", "11", "29", "Леонид Агутин", "512"
        );
        var error = new CardDetails().error(0);
        assertEquals("Неверно указан срок действия карты", error);
    }


}
