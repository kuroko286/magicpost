package com.magicpost.model;

import com.magicpost.model.dto.LeaderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Leader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String phoneNumber;
    @ManyToOne
    private Account account;

    public LeaderDTO leaderDTO() {
        return new LeaderDTO(this.id, this.name, this.phoneNumber);
    }
}
