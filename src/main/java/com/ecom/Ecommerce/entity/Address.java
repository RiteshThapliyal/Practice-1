package com.ecom.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_ADDRESS_USER")
    )
    private User user;

    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
