package com.enigmacamp.loanapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_loan_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "loan_type_id")
    private String id;

    @Column
    private String type;

    @Column(name = "maximum_loan")
    private Double maxLoan;
}
