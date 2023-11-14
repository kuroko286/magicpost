package com.magicpost.model;

import com.magicpost.model.dto.OrdersDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`Orders`")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String image;
    @CreatedDate
    private Date createdDate;
    private String nameSender;
    private String nameReceiver;
    private String phoneSender;
    private String phoneReceiver;
    private String addressSender;
    private String addressReceiver;
    private String width;
    private String height;
    private double weight;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private Type type;
    @OneToMany
    private List<Status> status;
    @ManyToMany
    @JoinTable(name = "orders_transaction_points", joinColumns = @JoinColumn(name = "orders_id"), inverseJoinColumns = @JoinColumn(name = "transaction_points_id"))
    private List<TransactionPoint> transactionPoints;

    @ManyToMany
    @JoinTable(name = "orders_consolidation_points", joinColumns = @JoinColumn(name = "orders_id"), inverseJoinColumns = @JoinColumn(name = "consolidation_points_id"))
    private List<ConsolidationPoint> consolidationPoints;

    public OrdersDTO orderDTO() {
        return new OrdersDTO(this.id, this.image, this.createdDate, this.nameSender, this.nameReceiver,
                this.phoneSender, this.phoneReceiver,
                this.addressSender, this.addressReceiver, this.width, this.height, this.weight, this.type.getName());
    }

}
