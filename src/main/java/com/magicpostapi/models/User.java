package com.magicpostapi.models;

import com.magicpostapi.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account", unique = true)
    private Long id;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "name")
    private String name;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @JsonProperty(access = Access.WRITE_ONLY)
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    @Column(name = "phone")
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_gathering_point", referencedColumnName = "id_gathering_point")
    @JsonIgnore
    private GatheringPoint gatheringPoint;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_transaction_point", referencedColumnName = "id_transaction_point")
    @JsonIgnore
    private TransactionPoint transactionPoint;

    public User(String username, String password, Role role, boolean active) {
        this.username = username;
        this.password = password;
        this.role = role;

    }

    public User(Long id, String username, String password, Role role, boolean active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;

    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role.name();
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setGatheringPoint(GatheringPoint gatheringPoint) {
        this.gatheringPoint = gatheringPoint;
    }

    public TransactionPoint getTransactionPoint() {
        return transactionPoint;
    }

    public void setTransactionPoint(TransactionPoint transactionPoint) {
        this.transactionPoint = transactionPoint;
    }

    public String getLocation() {
        if (this.gatheringPoint != null) {
            return this.gatheringPoint.getId();
        }
        if (this.transactionPoint != null) {
            return this.transactionPoint.getId();
        }
        return null;
    }
}
