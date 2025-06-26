package com.microservice.product.model;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 50)
    private String brand;
    @Column(nullable = false, length = 200)
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Integer stock;




}
