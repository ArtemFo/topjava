package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(meal, USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundMeal() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getWrongUser() throws Exception {
        service.get(ADMIN_MEAL_ID_1, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(100002, 100000);
        service.delete(100003, 100000);
        service.delete(100004, 100000);
        service.delete(100005, 100000);
        service.delete(100006, 100000);
        assertMatch(service.getAll(USER_ID), USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundMeal() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteWrongUser() throws Exception {
        service.delete(ADMIN_MEAL_ID_1, USER_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> meals = service.getBetweenDates(
                LocalDate.of(2015, Month.JUNE, 11),
                LocalDate.of(2015, Month.JUNE, 11), ADMIN_ID);
        assertMatch(meals, ADMIN_MEAL_1);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_MEAL);
        updated.setDescription("Updated Meal");
        updated.setCalories(1234);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateWrongUser() throws Exception {
        Meal updated = new Meal(USER_MEAL);
        updated.setDescription("Updated Meal");
        updated.setCalories(1234);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(USER_MEAL_ID, ADMIN_ID), updated);
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "New", 555);
        service.create(newMeal, ADMIN_ID);
        assertMatch(service.getAll(ADMIN_ID), newMeal, ADMIN_MEAL_2, ADMIN_MEAL_1);
    }
}