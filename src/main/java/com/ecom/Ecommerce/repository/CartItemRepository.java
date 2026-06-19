package com.ecom.Ecommerce.repository;

import com.ecom.Ecommerce.entity.CartItem;
import com.ecom.Ecommerce.entity.Product;
import com.ecom.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);

    List<CartItem> findByUserIdAndProductId(String userID, Long productId);
}
