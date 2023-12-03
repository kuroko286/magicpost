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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_point_id", referencedColumnName = "id")
    @JsonIgnore
    private GatheringPoint gatheringPoint;

}
