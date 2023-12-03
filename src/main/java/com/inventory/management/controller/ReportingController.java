package com.inventory.management.controller;

import com.inventory.management.entity.Order;
import com.inventory.management.entity.Product;
import com.inventory.management.service.ReportingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequestMapping("/api/reports")
public class ReportingController {


    @Autowired
    ReportingService service;

    @GetMapping(value = "/getOrdersByProduct/{type}",
            produces =APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> getProductByType(@PathVariable String type){

        List<Product> products = service.getProductsByType(type);
        return ResponseEntity.ok(products);
      }
    @GetMapping(value = "/getOrdersByUser/{name}",
            produces =APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getOrdersByName(@PathVariable String name){

        List<Order> orders = service.getOrdersByUserName(name);
        return ResponseEntity.ok(orders);
    }
}
