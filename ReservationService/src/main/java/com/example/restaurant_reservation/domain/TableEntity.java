package com.example.restaurant_reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableEntity extends BaseEntity {

    private int capacity;

    @Column(name = "is_mergeable", nullable = false)
    private boolean isMergeable;

    private String zone;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;
}
