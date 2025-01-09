package com.example.restaurant_reservation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TableEntity extends BaseEntity {

    private int capacity;
    private boolean isMergeable;
    private String zone; // smoking, non-smoking, indoor, outdoor
    private String tableName;

    @Column(nullable = false)
    private Long restaurantId;

    @OneToMany(mappedBy = "table", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppointmentEntity> appointments;

    public AppointmentEntity getAppointmentByLocalDateTime(LocalDateTime localDateTime) {
        return appointments.stream()
                .filter(appointment -> appointment.getDate().equals(localDateTime))
                .findFirst()
                .orElse(null);
    }
}
