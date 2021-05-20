package com.alifiks.companymanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyBillingRequest {

    @NotBlank(message = "Date of Billing cannot be empty")
    private LocalDate date;

    private BigDecimal netEarnings;
}
