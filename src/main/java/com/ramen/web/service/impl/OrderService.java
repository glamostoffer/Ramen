package com.ramen.web.service.impl;

import com.ramen.web.models.Meal;
import com.ramen.web.models.Order;
import com.ramen.web.models.UserEntity;
import com.ramen.web.repository.MealRepository;
import com.ramen.web.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MealRepository mealRepository;

    public Boolean addToOrder(UserEntity user, Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new RuntimeException("cannot find meal"));

        Order order = new Order();
        order.setMeal(meal);
        order.setUser(user);
        order.setPrice(meal.getPrice());
        orderRepository.save(order);
        return true;
    }

    public Boolean payOrder(UserEntity user) {
        var toDelete = orderRepository.findAllByUser(user) ;
        orderRepository.deleteAll(toDelete);
        return true;
    }

    public List<Order> getOrderByUser(UserEntity userEntity) {
        return orderRepository.findAllByUser(userEntity);
    }

    public void deleteFromOrderById(Long mealId) {
        orderRepository.deleteById(mealId);
    }
}
