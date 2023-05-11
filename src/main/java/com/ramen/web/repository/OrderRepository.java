package com.ramen.web.repository;

import com.ramen.web.models.Order;
import com.ramen.web.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query(
            value= "SELECT o FROM Order o WHERE o.meal.id in :ids and o.user = :user"
    )
    List<Order> findAllByListIdsAndUser(@Param("ids") List<Integer> ids, @Param("user") UserEntity user);


    List<Order> findAllByUser(UserEntity userEntity);
}
