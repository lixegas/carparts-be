package com.morelli.carparts.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "bar_code")
    private Long barCode;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(name = "description")
    private String description;

    @Column(name = "size")
    private String size;

    @Column(name = "color")
    private String color;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "unit_cost")
    private Double unitCost;

    @Column(name = "unit_price")
    private Double unitPrice;

    @Column(name = "save_timestamp")
    private Instant saveTimestamp;

    @Column(name = "update_timestamp")
    private Instant updateTimestamp;
}


