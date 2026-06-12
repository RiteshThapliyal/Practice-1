package com.ecom.Ecommerce;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface repository extends JpaRepository<User, Long> {
}
