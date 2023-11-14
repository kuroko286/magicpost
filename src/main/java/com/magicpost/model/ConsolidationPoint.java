package com.magicpost.model;

import com.magicpost.model.dto.ConsolidationPointDTO;
import com.magicpost.model.dto.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ConsolidationPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String address;
    @OneToOne
    private Leader leader;
    @OneToMany
    private List<Employee> employee;

    public ConsolidationPointDTO noEmployeeConsolidationPointDTO() {
        return new ConsolidationPointDTO(this.id, this.name, this.address);
    }

    public ConsolidationPointDTO consolidationPointDTOLeader() {
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for (Employee e : this.employee) {
            employeeDTOS.add(e.employeeDTO());
        }
        return new ConsolidationPointDTO(this.id, this.name, this.address, this.leader.leaderDTO(), employeeDTOS);
    }
}
