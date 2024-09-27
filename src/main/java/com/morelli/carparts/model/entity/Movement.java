package com.morelli.carparts.model.entity;


import com.morelli.carparts.model.enumeration.TypeMovement;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.Instant;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movement")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "barCode")
    private Long barCode;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement")
    private TypeMovement movement;

    @Column(name = "date")
    private Instant date;
}





