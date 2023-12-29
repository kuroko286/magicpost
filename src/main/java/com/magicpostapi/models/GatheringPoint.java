package com.magicpostapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Set;

@Entity
@Table(name = "gatheringpoint")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GatheringPoint {
    @Id
    @GeneratedValue(generator = "custom_id")
    @GenericGenerator(name = "custom_id", type = com.magicpostapi.components.CustomGeneratedId.class, parameters = {
            @Parameter(name = "prefix", value = "GRP_"),
            @Parameter(name = "field", value = "gatheringpoint") })
    @Column(name = "id_gathering_point", nullable = false, unique = true)
    private String id;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "gatheringPoint", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<TransactionPoint> transactionPoints;
    @OneToMany(mappedBy = "gatheringPoint", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<User> users; // staff

    public GatheringPoint(String id, String address, String city, String name) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.name = name;
    }

    // @Override
    // public String toString() {
    // return "GatheringPoint{" +
    // "id=" + id +
    // ", address='" + address + '\'' +
    // '}';
    // }

    // @Override
    // public boolean equals(Object o) {
    // if (this == o)
    // return true;
    // if (!(o instanceof GatheringPoint that))
    // return false;
    // return Objects.equals(id, that.id);
    // }

    // @Override
    // public int hashCode() {
    // return Objects.hash(id);
    // }
}
