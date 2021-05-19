package com.alifiks.companymanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MonthlyBilling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingId;

    @NotBlank(message = "Date of Billing cannot be empty")
    private LocalDate date;

    private BigDecimal grossEarnings;

    private BigDecimal netEarnings;

    private BigDecimal vat;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MonthlyCost> monthlyCosts;
}
