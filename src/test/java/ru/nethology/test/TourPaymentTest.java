package ru.nethology.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nethology.page.CardDetails;
import ru.nethology.page.ChoiceOfPayment;
import ru.nethology.payment.DebitCard;

import static com.codeborne.selenide.Selenide.open;

public class TourPaymentTest {

    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @Test
    void shouldSuccessfullyCompleteThePaymentByDebitCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getCardInfo());
    }

    @Test
    void shouldFailToPayByDebitCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.debitCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getOtherCardInfo());
    }

    @Test
    void shouldSuccessfullyCompleteThePaymentByByCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getCardInfo());
    }

    @Test
    void shouldFailToPayByCreditCard() {
        var choiceOfPayment = new ChoiceOfPayment();
        choiceOfPayment.creditCardPayment();
        var payCard = new CardDetails().payCard(DebitCard.getOtherCardInfo());
    }

//    @Test
//    void shouldDontAcceptFieldValue(){
//        var choiceOfPayment = new ChoiceOfPayment();
//        choiceOfPayment.creditCardPayment();
//        var payCard = new CardDetails().payCard(DebitCard.getOtherCardInfo());
//    }
//
//    @Test
//    void shouldDontAcceptEmptyField(){
//        var choiceOfPayment = new ChoiceOfPayment();
//        choiceOfPayment.creditCardPayment();
//        var payCard = new CardDetails().payCard(DebitCard.getOtherCardInfo());
//    }
}
