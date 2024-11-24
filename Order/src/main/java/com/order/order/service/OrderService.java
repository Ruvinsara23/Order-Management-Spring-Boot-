package com.order.order.service;

import com.inventory.Inventory.dto.InventoryDTO;
import com.inventory.Inventory.model.Inventory;
import com.order.order.comman.ErrorOrderResponse;
import com.order.order.comman.OrderResponse;
import com.order.order.comman.SuccessOrderResponse;
import com.order.order.dto.OrderDTO;
import com.order.order.model.Order;
import com.order.order.repo.OrderRepo;
import com.product.Product.dto.ProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Transactional
public class OrderService {
     private final WebClient inventoryWebClient;
     private final WebClient productWebClient;


    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService(WebClient inventoryWebClient, WebClient productWebClient ,OrderRepo orderRepo, ModelMapper modelMapper   ) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient= productWebClient;
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
          InventoryDTO inventoryResponse=  inventoryWebClient.get(  )
                    .uri(uriBuilder -> uriBuilder.path("/getinventory/{id}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDTO.class)
                    .block();

            assert inventoryResponse != null;

          Integer productId = inventoryResponse.getProductId();
            ProductDTO productResponse=  productWebClient.get(  )
                    .uri(uriBuilder -> uriBuilder.path("/getproduct/{productId}").build(productId  ))
                    .retrieve()
                    .bodyToMono(ProductDTO .class)
                    .block();



            System.out.println(inventoryResponse);
            assert productResponse != null;




            if(inventoryResponse.getQuantity()>0){
                if (productResponse.getForSale() == 1) {
                    orderRepo.save(modelMapper.map(orderDTO, Order.class));
                }
                else{
                    return new ErrorOrderResponse("This item not for sale");
                }

              return new SuccessOrderResponse(orderDTO);
          }else{
                return new ErrorOrderResponse("Item not available");
            }

        } catch (WebClientResponseException e){

            if(e.getStatusCode().is5xxServerError()){
                return new ErrorOrderResponse("Something went wrong");
            }
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
