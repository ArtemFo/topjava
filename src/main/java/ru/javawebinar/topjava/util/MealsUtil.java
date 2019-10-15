package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;



    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return getFiltered(meals, meal -> true, caloriesPerDay);
    }

    public static List<MealTo> getTosD(Collection<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return getFiltered(meals, meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime), caloriesPerDay);
    }

    public static List<MealTo> getFiltered(Collection<Meal> meals, Predicate<Meal> filter, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}