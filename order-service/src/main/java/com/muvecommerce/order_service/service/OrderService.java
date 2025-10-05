package com.muvecommerce.order_service.service;

import com.muvecommerce.order_service.dto.InventoryResponse;
import com.muvecommerce.order_service.dto.OrderLineItemDto;
import com.muvecommerce.order_service.dto.OrderRequest;
import com.muvecommerce.order_service.entity.Order;
import com.muvecommerce.order_service.entity.OrderLineList;
import com.muvecommerce.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional()
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;

   public void placeOrder(@RequestBody OrderRequest orderRequest) {

       Order order = new Order();
       order.setOrderNumber(UUID.randomUUID().toString());

       List<OrderLineList> orderLineItems = orderRequest.getOrderLineItemDtoList().stream()
               .map(this::mapToDto)
               .toList();

        order.setOrderLineLists(orderLineItems);

       List<String> skuCodes = order.getOrderLineLists().stream()
               .map(OrderLineList::getSkuCode).toList();

//        call inventory service to check weather product is in stock
       InventoryResponse[] inventoryResponseArray =  webClient.get()
            .uri("http://localhost:8082/api/inventory", uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
            .retrieve()
            .bodyToMono(InventoryResponse[].class).block();
       boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
               .allMatch(InventoryResponse::isInStock);


       if (allProductsInStock) {
           orderRepository.save(order);
       }else  {
           try {
               throw new IllegalAccessException("Product is not in stock");
           } catch (IllegalAccessException e) {
               throw new RuntimeException(e);
           }
       }


   }

    private OrderLineList mapToDto(OrderLineItemDto orderLineItemDto) {
       return OrderLineList.builder()
               .price(orderLineItemDto.getPrice())
               .skuCode(orderLineItemDto.getSkuCode())
               .quantity(orderLineItemDto.getQuantity())
               .build();

    }
}
