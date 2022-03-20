package ru.nethology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WindowPage {

    private ElementsCollection window = $$("[class=\"notification__content\"]");

    public  WindowPage windowSuccefully(){
        window.get(0).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Операция одобрена Банком."));
        return new WindowPage();
    }
    public WindowPage  windowError(){
        window.get(1).shouldBe(visible, Duration.ofSeconds(15)).shouldHave(exactText("Ошибка! Банк отказал в проведении операции."));
        return new WindowPage();
    }
}
