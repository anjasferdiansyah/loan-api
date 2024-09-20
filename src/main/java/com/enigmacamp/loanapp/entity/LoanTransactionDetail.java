package com.enigmacamp.loanapp.entity;


import com.enigmacamp.loanapp.constant.ELoanStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "trx_loan_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "transaction_date")
    private Long transactionDate;

    @Column
    private Double nominal;

    @ManyToOne
    @JoinColumn(name = "trx_loan_id")
    @JsonBackReference
    private LoanTransaction loanTransaction;

    @Column(name = "loan_status")
    @Enumerated(EnumType.STRING)
    private ELoanStatus  loanStatus;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;

}
