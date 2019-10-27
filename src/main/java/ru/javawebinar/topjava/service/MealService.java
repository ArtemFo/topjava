package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkUserIdInMeal;

@Service
public class MealService {

    private final MealRepository repository;

    @Autowired
    public  MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    public Meal get(int id, int userId) throws NotFoundException{
        return checkUserIdInMeal(checkNotFoundWithId(repository.get(id), id), userId);
    }

    public void update(Meal meal) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }

    public void delete(int id, int userId) throws NotFoundException {
        checkUserIdInMeal(checkNotFoundWithId(repository.get(id), id), userId);
        repository.delete(id);
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    public List<Meal> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        return (List<Meal>) repository.getAll(startDate, endDate, startTime, endTime, userId);
    }

}