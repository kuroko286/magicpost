package com.magicpost.model;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.magicpost.utils.constant.OrderStatus;
import com.magicpost.utils.constant.OrderType;

@Data
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // sender and receiver information
    @Column(name = "sender", nullable = false)
    private String senderName;
    @Column(name = "sender_phone", nullable = false)
    private String senderPhone;
    @Column(name = "sender_address")
    private String senderAddress;
    @Column(name = "receiver", nullable = false)
    private String receiverName;
    @Column(name = "receiver_phone", nullable = false)
    private String receiverPhone;
    @Column(name = "receiver_address")
    private String receiverAddress;

    // order information
    @Column(name = "lading_code", nullable = false, unique = true)
    private String ladingCode;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;
    @Column(name = "weight", nullable = false)
    private Integer weight;
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @CreationTimestamp
    private LocalDateTime dateCreated;
    @UpdateTimestamp
    private LocalDateTime lastUpdate;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "order_delivery", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "delivery_id"))
    private List<Delivery> deliveries;
    @Column(name = "teller_name", nullable = false)
    private String tellerName;
    private String note;
    @ElementCollection
    @CollectionTable(name = "charges", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "charge_key")
    @Column(name = "charge_value")
    private Map<String, Integer> charges;

}
