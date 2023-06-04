package com.project.boardgames.repositories;

import com.project.boardgames.entities.CartItem;
import com.project.boardgames.entities.Product;

import java.util.List;

public interface CartItemRepository extends GenericRepository<CartItem> {
    List<CartItem> findByProduct(Product product);
}
