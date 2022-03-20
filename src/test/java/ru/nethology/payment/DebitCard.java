package ru.nethology.payment;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;

public class DebitCard{
    private DebitCard(){}

    private static final Faker faker = new Faker(new Locale("ru"));

    @Value
    public static class CardInfo {
        private String numberCard;
    }
    public static CardInfo getOtherCard(String card) {
        return new CardInfo(card);
    }
    public static CardInfo getApprovedCard() {
        return new CardInfo("4444_4444_4444_4441");
    }
    public static CardInfo getDeclinedCard() {
        return new CardInfo("4444_4444_4444_4442");
    }
    public static String getRandomMonth() {
        String[] month = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        int n = (int) Math.round(Math.random() * month.length);
        return month[n];
    }
    public static String getRandomYear() {
        int num = faker.number().numberBetween(22, 28);
        String year  = Integer. toString(num);
        return year;
    }
    public static String getRandomName() {
        String name = faker.name().name();
        return name;
    }
    public static String getRandomCode() {
        int num = faker.number().numberBetween(100, 999);
        String code  = Integer. toString(num);
        return code;
    }
}
