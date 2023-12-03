package com.magicpost.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.magicpost.utils.constant.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 6, max = 30)
    private String username;

    @Column(nullable = false)
    @Size(min = 8, max = 30)
    private String password;

    @Email(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")
    @Column(unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_point_id", referencedColumnName = "id")
    @JsonIgnore
    private TransactionPoint transactionPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gathering_point_id", referencedColumnName = "id")
    @JsonIgnore
    private GatheringPoint gatheringPoint;
}
