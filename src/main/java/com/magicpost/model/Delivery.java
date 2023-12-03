package com.magicpost.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "deliveries")
    private List<Order> orders;
    @Column(name = "is_delivered")
    private boolean isDelivered;
    @CreationTimestamp
    private LocalDateTime deliveryAt;
    private Point pointFrom;
}
