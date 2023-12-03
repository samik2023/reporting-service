package com.inventory.management.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.management.entity.Event;
import com.inventory.management.entity.Order;
import com.inventory.management.entity.Product;
import com.inventory.management.repository.ReportingOrderRepository;
import com.inventory.management.repository.ReportingProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReportingService {

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    ReportingProductRepository productPepository;

    @Autowired
    ReportingOrderRepository orderRepository;

    public void syncOrders(Event event){

        Order order = null;
        try {
            order = mapper.readValue(event.getCommandObjStr(), Order.class);
            Optional<Order> orderRepo = orderRepository.findById(event.getOrderId());
            if(orderRepo.isPresent()){
                orderRepo.get().setStatus(order.getStatus());
                orderRepo.get().setUserId(order.getUserId());
                orderRepo.get().setProductId(order.getProductId());
                orderRepo.get().setUserName(order.getUserName());
                orderRepository.save(orderRepo.get());
            }else{
                orderRepository.save(order);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public void syncProducts(Event event){

        Product product = null;
        try {
            product = mapper.readValue(event.getCommandObjStr(), Product.class);
            Optional<Product> productRepo = productPepository.findById(product.getProductId());
            if(productRepo.isPresent()){
                productRepo.get().setQuantity(product.getQuantity());
                productRepo.get().setPrice(product.getPrice());
                productPepository.save(productRepo.get());
            }else{
                productPepository.save(product);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Product> getProductsByType(String type){
       List<Product> products = productPepository.getProductsByType(type);
        return products;
    }

    @CircuitBreaker(name="reporingCircuitBreaker", fallbackMethod = "fallbackToCustomerService")
    public List<Order> getOrdersByUserName(String userName){
        List<Order> orders = orderRepository.getOrdersByUserName(userName);
        return orders;
    }


    public void fallbackToCustomerService(Order order,Throwable t){

        System.out.print("Inside fallback");

    }
    /*
    public ResponseEntity<String> rateLimitFallback(Order order,Throwable t){

        return new ResponseEntity<>("Too many attempts within a  time " + "failed to process order for product id: " +
                order.getProductId(), HttpStatus.OK);
    }

    public ResponseEntity<String> retryFallback(Order order,Throwable t){

        return new ResponseEntity<>("All retry exhausted for product id: " +
                order.getProductId(), HttpStatus.OK);
    }
    */
}