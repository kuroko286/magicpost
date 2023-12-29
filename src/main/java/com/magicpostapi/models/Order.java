package com.magicpostapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magicpostapi.enums.OrderStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "orders")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(generator = "custom_id")
    @GenericGenerator(name = "custom_id", type = com.magicpostapi.components.CustomGeneratedId.class, parameters = {
            @Parameter(name = "prefix", value = "ORD_"),
            @Parameter(name = "field", value = "orderId") })
    @Column(name = "id_order", nullable = false, unique = true)
    private String id;
    @Column(name = "sender_name", nullable = false)
    private String senderName;
    @Column(name = "sender_phone", nullable = false)
    private String senderPhone;
    @Column(name = "sender_address")
    private String senderAddress;
    @Column(name = "recipient_name", nullable = false)
    private String recipientName;
    @Column(name = "recipient_phone", nullable = false)
    private String recipientPhone;
    @Column(name = "recipient_address")
    private String recipientAddress;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "weight")
    private double weight;
    @Column(name = "recipient_cod")
    private String cod;
    @Column(name = "last_update", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_from", referencedColumnName = "id_transaction_point")
    @JsonIgnore
    private TransactionPoint transactionPointFrom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_to", referencedColumnName = "id_transaction_point")
    @JsonIgnore
    private TransactionPoint transactionPointTo;

    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<Delivery> deliveries;
    @Column(name = "tellers_name")
    private String tellersName;
    @Column(name = "note")
    private String note;
    @Column(name = "mainCharge")
    private double mainCharge;
    @Column(name = "subCharge")
    private double subCharge;

    @PrePersist
    protected void onInsert() {
        lastUpdate = new Date(System.currentTimeMillis());
        dateCreated = new Date(System.currentTimeMillis());
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Date(System.currentTimeMillis());
    }
}
