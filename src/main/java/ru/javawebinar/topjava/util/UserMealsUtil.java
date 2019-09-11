package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 8, 0), "preЗавтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 6, 59), "preЗавтрак", 1000)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2510);
//        .toLocalDate();
//        .toLocalTime();
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(
            List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCaloriesPerDay = mealList.stream()
                .collect(groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(),
                        mapping(UserMeal::getCalories, summingInt(calories -> calories))));

//        for(Map.Entry<LocalDate, Integer> entry : sumOfCaloriesPerDay.entrySet()){
//            System.out.println(entry.getKey() + " " + entry.getValue());
//        }

        List<UserMealWithExceed> filteredList = mealList.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> buildUserMealWithExceed(userMeal, sumOfCaloriesPerDay, caloriesPerDay))
                .collect(toList());

//        for (UserMealWithExceed meal : filteredList) {
//            System.out.println(meal);
//        }

        return filteredList;
    }

    private static UserMealWithExceed buildUserMealWithExceed(UserMeal userMeal, Map<LocalDate, Integer> calories,
                                                              Integer caloriesPerDay) {
        boolean exceed = calories.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay;
        return new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }
}
