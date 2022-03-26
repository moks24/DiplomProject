package ru.nethology.page;

import com.codeborne.selenide.ElementsCollection;
import lombok.val;
import ru.nethology.payment.DebitCard;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDetails {
    private ElementsCollection fields = $$("[class=\"input__control\"]");
    private ElementsCollection button = $$("[class=\"button button_view_extra button_size_m button_theme_alfa-on-white\"]");
    private ElementsCollection errorBelowField = $$("[class=\"input__sub\"]");


    public WindowPage payCard(DebitCard.CardInfo info) {
        fields.get(0).val(info.getNumberCard());
        fields.get(1).val(DebitCard.getRandomMonth());
        fields.get(2).val(DebitCard.getRandomYear());
        fields.get(3).val(DebitCard.getRandomName());
        fields.get(4).val(DebitCard.getRandomCode());
        button.get(1).click();
        return new WindowPage();
    }
    public WindowPage paymentInInputFields(String numberCard,String month, String year, String name, String code) {
        fields.get(0).val(numberCard);
        fields.get(1).val(month);
        fields.get(2).val(year);
        fields.get(3).val(name);
        fields.get(4).val(code);
        button.get(1).click();
        return new WindowPage();
    }

    public String error(int index){
        val text = errorBelowField.get(index).shouldBe(visible).text();
        return text;
    }

}
