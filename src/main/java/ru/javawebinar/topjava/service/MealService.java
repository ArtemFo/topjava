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

    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    public Meal get(int id, int userId) throws NotFoundException{
        return checkUserIdInMeal(checkNotFoundWithId(repository.get(id, userId), id), userId);
    }

    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public void delete(int id, int userId) throws NotFoundException {
        checkUserIdInMeal(checkNotFoundWithId(repository.get(id, userId), id), userId);
        repository.delete(id, userId);
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

    public List<Meal> getAll(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        return (List<Meal>) repository.getBetween(startDate, endDate, startTime, endTime, userId);
    }

}