package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryFormTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldInputTheDateAndSubmitForm() {
        $("[data-test-id='city'] input").setValue("Южно-Сахалинск");
        $("[data-test-id='date'] .input__control")
                .setValue("'\ue009' + '\ue003'")
                .setValue(date(5, "dd.MM.yyyy"));
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79998881111");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(withText("Встреча успешно забронирована")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldSelectADateAndSubmitForm() {
        $("[data-test-id='city'] input").setValue("Ве");
        $$(".menu-item__control").find(exactText("Великий Новгород")).click();
        $(".calendar-input__custom-control button").click();
        SelenideElement calendarDay = $$("div [role='gridcell']").find(exactText(date(20, "d")));
        if (!calendarDay.is(attribute("data-day"))) {
            $("div.calendar__title div:nth-child(4)").click();
        }
        calendarDay.click();
        $("[data-test-id='date'] .input__control").shouldHave(exactValue(date(20, "dd.MM.yyyy")));
        $("[data-test-id='name'] input").setValue("Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79998881111");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Забронировать")).click();
        $(withText("Встреча успешно забронирована")).shouldBe(visible, Duration.ofSeconds(15));
    }

    String date(long numDays, String formatter) {
        return LocalDate.now()
                .plusDays(numDays)
                .format(DateTimeFormatter.ofPattern(formatter));
    }

}
