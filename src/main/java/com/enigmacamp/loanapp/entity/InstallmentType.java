package com.enigmacamp.loanapp.entity;


import com.enigmacamp.loanapp.constant.EInstalmentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "t_installment_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstallmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "installment_type")
    @NotBlank(message = "Installment Type Must not be null")
    @Enumerated(EnumType.STRING)
    private EInstalmentType installmentType;


}
