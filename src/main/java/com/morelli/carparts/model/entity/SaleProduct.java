package com.morelli.carparts.model.entity;


import jakarta.persistence.*;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(SaleProductId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleProduct {

    @Id
    @Column(name = "sale_id")
    private Long saleId;

    @Id
    @Column(name = "bar_code")
    private Long barCode;

    @Column(name = "quantity_sold")
    private Integer quantitySold;


    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale;
}



