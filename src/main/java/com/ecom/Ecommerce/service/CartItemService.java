package com.ecom.Ecommerce.service;

import com.ecom.Ecommerce.dto.CartItemRequest;
import com.ecom.Ecommerce.dto.CartItemResponse;
import com.ecom.Ecommerce.entity.CartItem;
import com.ecom.Ecommerce.entity.Product;
import com.ecom.Ecommerce.entity.User;
import com.ecom.Ecommerce.exception.ResourceNotFoundException;
import com.ecom.Ecommerce.repository.CartItemRepository;
import com.ecom.Ecommerce.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.ecom.Ecommerce.repository.Repository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CartItemService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final Repository repository;

    public boolean addToCart(String userID, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProductId());

        if (productOpt.isEmpty())
            return false;

        Product product = productOpt.get();
        if (product.getStockQuantity() < request.getQuantity())
            return false;

        Optional<User> userOpt = repository.findById(Long.valueOf(userID));
        if (userOpt.isEmpty())
            return false;

        User user = userOpt.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);

        if (existingCartItem != null)
        {
            // Update the Quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }
        else
        {
            // Create new Cart Item
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartItemRepository.save(cartItem);
        }

        return true;
    }

    public boolean deleteItemFromCart(String userID, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = repository.findById(Long.valueOf(userID));

        if (productOpt.isPresent() && userOpt.isPresent())
        {
            cartItemRepository.deleteByUserAndProduct(userOpt.get(), productOpt.get());
            return true;
        }
        return false;
    }


    public List<CartItemResponse> getCartItems(String userID, Long productId) {

        List<CartItem> items = cartItemRepository.findByUserIdAndProductId(userID, productId);

        if (items.isEmpty()) {
            throw new ResourceNotFoundException(
                    String.format(
                            "Cart item not found for User: %s and Product: %d",
                            userID,
                            productId
                    )
            );
        }

        return items.stream()
                .map(item -> mapToResponse(item, item.getProduct()))
                .collect(Collectors.toList());
    }

    private CartItemResponse mapToResponse(CartItem item, Product product) {
        CartItemResponse response = new CartItemResponse();

        response.setProductId(String.valueOf(item.getProduct().getId()));
        response.setProductName(item.getProduct().getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setQuantity(item.getQuantity());
        response.setCreatedAt(item.getCreatedAt());
        response.setUpdatedAt(item.getUpdatedAt());

        return response;
    }

}
