package com.magicpostapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(generator = "custom_id")
    @GenericGenerator(name = "custom_id", type = com.magicpostapi.components.CustomGeneratedId.class, parameters = {
            @Parameter(name = "prefix", value = "DEL_"),
            @Parameter(name = "field", value = "delivery") })
    @Column(name = "delivery_id")
    private String id;

    @Column(name = "present_des", nullable = false)
    private String presentDes;
    @Column(name = "next_des", nullable = false)
    private String nextDes;
    @Column(name = "done", nullable = false)
    private boolean done;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "order_id", referencedColumnName = "id_order", nullable = false)
    private Order order;
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    @PrePersist
    protected void onInsert() {
        dateCreated = new Date(System.currentTimeMillis());
    }
}
