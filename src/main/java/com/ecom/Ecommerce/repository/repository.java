package com.ecom.Ecommerce.repository;

import com.ecom.Ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repository extends JpaRepository<User, Long> {
}
