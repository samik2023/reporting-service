package com.inventory.management.repository;

import com.inventory.management.entity.Order;
import com.inventory.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportingOrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.userName= :userName ")
    List<Order> getOrdersByUserName(@Param("userName") String userName);

}
