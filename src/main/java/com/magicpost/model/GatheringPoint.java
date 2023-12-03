package com.magicpost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gatheringPoint")
    @JsonIgnore
    private List<TransactionPoint> transactionPoints;

}
