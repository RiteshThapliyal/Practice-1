package com.ecom.Ecommerce.repository;

import com.ecom.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<User, Long> {
}
