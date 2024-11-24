package com.order.order.controller;


import com.order.order.comman.OrderResponse;
import com.order.order.dto.OrderDTO;
import com.order.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1")
public class OrderController {

    @Autowired
private OrderService orderService;

    @GetMapping("/getorder")
    public List<OrderDTO> getOrder(){
        return orderService.getAllOrders();
    }

    @GetMapping("/getorder/{id}")
    public OrderDTO getOrderById(@PathVariable Integer id) {
        return orderService.getOrderById(id);
    }

    @PutMapping("/updateorder")
    public OrderDTO updateOrder(@RequestBody OrderDTO order){
        return orderService.updateOrder(order);
    }

    @DeleteMapping("/deleteorder")
    public String deleteOrder(@RequestBody OrderDTO order){
        return orderService.deleteOrder(order);
    }

    @DeleteMapping("deleteorder/{id}")
    public String deleteOrderById(@PathVariable Integer id){
        return orderService.deleteOrderById(id);

    }

    @PostMapping("/addorder")
    public OrderResponse addOrder(@RequestBody OrderDTO orderDTO){
        return  orderService.saveOrder(orderDTO);
    }




}