package com.vvs.hypeshop.service.order;

import com.vvs.hypeshop.dto.OrderDto;
import com.vvs.hypeshop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
