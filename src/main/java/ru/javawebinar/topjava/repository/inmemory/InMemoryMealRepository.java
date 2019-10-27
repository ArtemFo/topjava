package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenTime;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return getAll(meal -> true, userId);
    }

    @Override
    public Collection<Meal> getAll(
            LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId) {
        return getAll(meal -> isBetweenDate(meal.getDate(), startDate, endDate)
                && isBetweenTime(meal.getTime(), startTime, endTime), userId);
    }

    private Collection<Meal> getAll(Predicate<Meal> filter, int userId) {
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId()==userId)
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime)
                .reversed())
                .collect(Collectors.toList());
    }
}

