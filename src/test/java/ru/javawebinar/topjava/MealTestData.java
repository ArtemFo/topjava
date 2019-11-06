package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 7;
    public static final int ADMIN_MEAL_ID_1 = START_SEQ + 8;
    public static final int ADMIN_MEAL_ID_2 = START_SEQ + 9;

    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID,
            LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID_1,
            LocalDateTime.of(2015, Month.JUNE, 11, 13, 0),
            "Админ Обед", 510);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID_2,
            LocalDateTime.of(2015, Month.JUNE, 14, 20, 0),
            "Админ Ужин", 1500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "userId", "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("userId", "dateTime").isEqualTo(expected);
    }
}
