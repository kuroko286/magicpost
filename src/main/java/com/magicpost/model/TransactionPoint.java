package com.magicpost.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPoint extends Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // maybe generate custom id, like prefix with TSP_
    private String name;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_point_id", referencedColumnName = "id")
    @JsonIgnore
    private GatheringPoint gatheringPoint;

}
