package com.order.order.service;

import com.inventory.Inventory.dto.InventoryDTO;
import com.inventory.Inventory.model.Inventory;
import com.order.order.comman.ErrorOrderResponse;
import com.order.order.comman.OrderResponse;
import com.order.order.comman.SuccessOrderResponse;
import com.order.order.dto.OrderDTO;
import com.order.order.model.Order;
import com.order.order.repo.OrderRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Transactional
public class OrderService {
     private final WebClient webClient;


    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient.Builder webClientBuilder ,OrderRepo orderRepo, ModelMapper modelMapper   ) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api/v1").build();
        this.orderRepo = orderRepo;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getAllOrders() {


        List<Order> orderList = orderRepo.findAll();
        return modelMapper.map(orderList , new TypeToken<List<OrderDTO>>() {}.getType());
    }

    public OrderResponse saveOrder(OrderDTO orderDTO){

        Integer itemId = orderDTO.getItemId();
        try{
          InventoryDTO inventoryResponse=  webClient.get(  )
                    .uri(uriBuilder -> uriBuilder.path("/getinventory/{id}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponse != null;

            System.out.println(inventoryResponse);

            if(inventoryResponse.getQuantity()>0){
              orderRepo.save(modelMapper.map(orderDTO, Order.class));
              return new SuccessOrderResponse(orderDTO);
          }else{
                return new ErrorOrderResponse("Item not available");
            }

        } catch (Exception e){
            e.printStackTrace();
        }


//        Order order = modelMapper.map(orderDTO, Order.class);
//        Order savedOrder=orderRepo.save(order);
//        return modelMapper.map(savedOrder ,OrderDTO.class);
return new ErrorOrderResponse("Error in saving order");
    }


    public OrderDTO updateOrder(OrderDTO orderDTO){
        Order order = modelMapper.map(orderDTO, Order.class);
        Order savedOrder=orderRepo.save(order);
        return modelMapper.map(savedOrder ,OrderDTO.class);

    }

   public String deleteOrder(OrderDTO orderDTO){
        Order order = orderRepo.findById(orderDTO.getId()).get();
        orderRepo.delete(order);
        return "Order Deleted Successfully";
   }

    public OrderDTO getOrderById(Integer id) {

        Order order = orderRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        return modelMapper.map(order, OrderDTO.class);
    }

    public String deleteOrderById(Integer id) {
        orderRepo.deleteById(id);
        return "Order Deleted Successfully";
    }





}
