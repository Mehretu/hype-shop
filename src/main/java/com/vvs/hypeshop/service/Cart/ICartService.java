package com.vvs.hypeshop.service.Cart;

import com.vvs.hypeshop.model.Cart;
import com.vvs.hypeshop.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

//    Long initializeNewCart();

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
