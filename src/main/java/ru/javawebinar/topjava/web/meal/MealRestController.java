package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.*;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        meal.setUserId(authUserId());
        return service.create(meal, authUserId());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        checkUserIdInMeal(meal, authUserId());
        service.update(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public List<Meal> getAll() {
        log.info("get all");
        return service.getAll(authUserId());
    }

    public List<Meal> getBetween(
            LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        log.info("get between");
        startDate = startDate==null ? LocalDate.MIN : startDate;
        endDate = endDate==null ? LocalDate.MAX : endDate;
        startTime = startTime==null ? LocalTime.MIN : startTime;
        endTime = endTime==null ? LocalTime.MAX : endTime;
        return service.getAll(startDate, endDate, startTime, endTime, authUserId());
    }




}