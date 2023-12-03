package com.magicpost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class GatheringPoint extends Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gatheringPoint")
    @JsonIgnore
    private TransactionPoint transactionPoint;

}
