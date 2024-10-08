package com.vvs.hypeshop.service.Cart;

import com.vvs.hypeshop.model.Cart;
import com.vvs.hypeshop.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
