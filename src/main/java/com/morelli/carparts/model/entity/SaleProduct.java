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
@Table(name = "sale_product")
public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "bar_code", referencedColumnName = "bar_code", nullable = false)
    private Product product;

    @Column(name = "quantity_sold")
    private Integer quantitySold;

    @Column(name = "save_timestamp")
    private Instant saveTimestamp;
}



