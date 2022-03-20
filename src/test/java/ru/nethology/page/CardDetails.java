package ru.nethology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.checkerframework.checker.units.qual.C;
import ru.nethology.payment.DebitCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDetails {
    private ElementsCollection cards = $$("[class=\"input__control\"]");
    private ElementsCollection button = $$("[class=\"button button_view_extra button_size_m button_theme_alfa-on-white\"]");

    public WindowPage payCard(DebitCard.CardInfo info) {
        cards.get(0).val(info.getNumberCard());
        cards.get(1).val(DebitCard.getRandomMonth());
        cards.get(2).val(DebitCard.getRandomYear());
        cards.get(3).val(DebitCard.getRandomName());
        cards.get(4).val(DebitCard.getRandomCode());
        button.get(1).click();
        return new WindowPage();
    }

}
