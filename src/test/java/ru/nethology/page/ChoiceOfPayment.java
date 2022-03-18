package ru.nethology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class ChoiceOfPayment {
    private SelenideElement buy = $("[class='button button_size_m button_theme_alfa-on-white']");
    private SelenideElement buyInCredit = $("[class='button button_view_extra button_size_m button_theme_alfa-on-white']");

    public CardDetails debitCardPayment() {
        buy.click();
        return new CardDetails();
    }
    public CardDetails creditCardPayment() {
        buyInCredit.click();
        return new CardDetails();
    }

}
