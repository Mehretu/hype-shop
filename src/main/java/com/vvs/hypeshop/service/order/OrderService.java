package com.vvs.hypeshop.service.order;

import com.vvs.hypeshop.Repository.OrderRepository;
import com.vvs.hypeshop.Repository.ProductRepository;
import com.vvs.hypeshop.dto.OrderDto;
import com.vvs.hypeshop.enums.OrderStatus;
import com.vvs.hypeshop.exceptions.ResourceNotFoundException;
import com.vvs.hypeshop.model.Cart;
import com.vvs.hypeshop.model.Order;
import com.vvs.hypeshop.model.OrderItem;
import com.vvs.hypeshop.model.Product;
import com.vvs.hypeshop.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;

    @Override
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);

        Order order = createOrder(cart);
        List<OrderItem> orderItemList = createrOrderItems(order,cart);

        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));
        Order orderSaved = orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return orderSaved;
    }

    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }

    private List<OrderItem> createrOrderItems(Order order, Cart cart) {
        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    product,
                    order,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
            );
        }).toList();
    }


    private BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        return orderItems
                .stream()
                .map(orderItem -> orderItem.getPrice()
                        .multiply(new BigDecimal(orderItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()->new ResourceNotFoundException("Order not found"));
    }

    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::convertToDto).toList();
    }
    @Override
    public OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
