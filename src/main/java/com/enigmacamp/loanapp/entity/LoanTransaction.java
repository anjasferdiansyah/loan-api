package com.enigmacamp.loanapp.entity;

import com.enigmacamp.loanapp.constant.EApprovalStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "trx_loan")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "loan_type_id")
    private LoanType loanType;

    @ManyToOne
    @JoinColumn(name = "installment_type_id")
    private InstallmentType instalmentType;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private Double nominal;

    @Column
    private Long approvedAt;

    @Column
    private String approvedBy;

    @Column
    @Enumerated(EnumType.STRING)
    private EApprovalStatus approvalStatus;

    @OneToMany(mappedBy = "loanTransaction")
    @JsonManagedReference
    private List<LoanTransactionDetail> loanTransactionDetails;

    @Column
    private Long createdAt;
    @Column
    private Long updatedAt;
}
