package com.inventory.management.repository;

import com.inventory.management.entity.Order;
import com.inventory.management.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportingProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.type= :type ")
    List<Product> getProductsByType(@Param("type") String type);

}
