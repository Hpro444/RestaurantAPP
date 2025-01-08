package com.example.restaurant_reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableEntity extends BaseEntity{
    private int capacity;
    private boolean isMergeable;
    private String zone;    // smoking, non smoking, indoor, outdoor
    private String tableName;

    @Column(nullable = false)
//    @Index(name = "idx_restaurant_id") // JPA annotation for index
    private Long restaurantId;

}
