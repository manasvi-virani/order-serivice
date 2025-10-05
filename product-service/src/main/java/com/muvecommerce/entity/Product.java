package com.muvecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name can't blank")
    private String name;

    @NotBlank(message = "Product description can't blank")
    private String description;

    @Min(value = 0)
    private BigDecimal price;
}
